package my.project.juja.integration;

import my.project.juja.controller.Main;
import my.project.juja.controller.commands.Command;
import org.junit.Before;
import org.junit.Ignore;
import org.junit.Test;


import java.io.ByteArrayOutputStream;
import java.io.PrintStream;
import java.io.UnsupportedEncodingException;

import static org.junit.Assert.assertEquals;

/**
 * Created by Nikol on 4/25/2016.
 */
public class IntegrationTest {

    private  ConfigurableInputStream in;
    private  ByteArrayOutputStream out;
    private static final String DB_NAME = "sqlcmd";
    private static final String DB_LOGIN = "postgres";
    private static final String DB_PASSWORD = "root";
    private static final String TEST_TABLE = "users";
    private static final String SEPARATOR = Command.SEPARATOR_TO_STRING;

    @Before
    public void setup(){
        in = new ConfigurableInputStream();
        out = new ByteArrayOutputStream();
        System.setIn(in);
        System.setOut(new PrintStream(out));
    }

    public String getData() {
        try {
            String result = new String(out.toByteArray(), "UTF-8");
            out.reset();
            return result;
        } catch (UnsupportedEncodingException e) {
            return e.getMessage();
        }
    }

    @Test
    public void testHelp(){
        //given
        in.add(Command.HELP);
        in.add(Command.EXIT);
        //when
        Main.main(new String[0]);
        //then
        String expected = "Hello\n" +
                "Input your command or 'help'\n" +
                "connect\n" +
                "\t - Connect to database 'connect|dbname|login|password'\n" +
                "table-list\n" +
                "\t - Show exist tables in the current database 'table-list'\n" +
                "table-data\n" +
                "\t - Show table rows 'table-data|tableName'\n" +
                "add-record\n" +
                "\t - Add record in the selectd table 'add-record|tableName'\n" +
                "update-table\n" +
                "\t - Update record in the selectd table 'update-table|tableName|where'\n" +
                "clear-table\n" +
                "\t - Clear selected table 'clear-table|tableName'\n" +
                "exit\n" +
                "\t - Close connection to a database and exit\n" +
                "Input your command or 'help'\n" +
                "Goodbye\n";
        expected.replaceAll("\n", System.lineSeparator());
        assertEquals(expected, getData());

    }
    @Test
    public void testTableListWithoutConnect(){
        //given
        in.add(Command.TABLE_LIST);
        in.add(Command.EXIT);
        //when
        Main.main(new String[0]);
        //then
        assertEquals("Hello\r\n" +
                "Input your command or 'help'\r\n" +
                "ERROR. at first connect to database\r\n" +
                "Input your command or 'help'\r\n" +
                "Goodbye\r\n", getData());
    }

    @Test
    public void testTableDataWithoutConnect(){
        //given
        in.add(Command.TABLE_DATA);
        in.add(Command.EXIT);
        //when
        Main.main(new String[0]);
        //then
        assertEquals("Hello\r\n" +
                "Input your command or 'help'\r\n" +
                "ERROR. Wrong count parameters expected 1, but found 0\r\n" +
                "Input your command or 'help'\r\n" +
                "Goodbye\r\n",getData());
    }

    @Test
    public void testWrongCommand(){
        //given
        in.add("unsupportedCommand");
        in.add(Command.EXIT);
        //when
        Main.main(new String[0]);
        //then
        assertEquals("Hello\r\n" +
                "Input your command or 'help'\r\n" +
                "The command doesn't exist\r\n" +
                "Input your command or 'help'\r\n" +
                "Goodbye\r\n",getData());
    }

    @Test
    public void testConnect(){
        //given
        in.add(Command.CONNECTION + SEPARATOR
                + DB_NAME + SEPARATOR
                + DB_LOGIN + SEPARATOR
                + DB_PASSWORD);
        in.add(Command.EXIT);
        //when
        Main.main(new String[0]);
        //then
        assertEquals("Hello\r\n" +
                "Input your command or 'help'\r\n" +
                "Connect to the data base 'sqlcmd' successful!\r\n" +
                "Input your command or 'help'\r\n" +
                "Connection to data base was closed\r\n" +
                "Goodbye\r\n",getData());
    }

    @Test
    public void testConnectWithWrongDBName(){
        //given
        in.add(Command.CONNECTION + SEPARATOR
                + "WrongDataBaseName" + SEPARATOR
                + DB_LOGIN + SEPARATOR
                + DB_PASSWORD);
        in.add(Command.EXIT);
        //when
        Main.main(new String[0]);
        //then
        assertEquals("Hello\r\n" +
                "Input your command or 'help'\r\n" +
                "ERROR. connect to database unsuccessful, check your command. FATAL: database \"WrongDataBaseName\" does not exist\r\n" +
                "Input your command or 'help'\r\n" +
                "Goodbye\r\n", getData());
    }

    @Test
    public void testConnectWithWrongLogin(){
        //given
        in.add(Command.CONNECTION + SEPARATOR
                + DB_NAME + SEPARATOR
                + "wrongLogin" + SEPARATOR
                + DB_PASSWORD);
        in.add(Command.EXIT);
        //when
        Main.main(new String[0]);
        //then
        assertEquals("Hello\r\n" +
                "Input your command or 'help'\r\n" +
                "ERROR. connect to database unsuccessful, check your command. FATAL: password authentication failed for user \"wrongLogin\"\r\n" +
                "Input your command or 'help'\r\n" +
                "Goodbye\r\n", getData());
    }

    @Test
    public void testConnectWithWrongPassword(){
        //given
        in.add(Command.CONNECTION + SEPARATOR
                + DB_NAME + SEPARATOR
                + DB_LOGIN + SEPARATOR
                + "wrongPassword");
        in.add(Command.EXIT);
        //when
        Main.main(new String[0]);
        //then
        assertEquals("Hello\r\n" +
                "Input your command or 'help'\r\n" +
                "ERROR. connect to database unsuccessful, check your command. FATAL: password authentication failed for user \"postgres\"\r\n" +
                "Input your command or 'help'\r\n" +
                "Goodbye\r\n", getData());
    }

    @Test
    public void testUnsuportedCommandAfterConnect(){
        //given
        in.add(Command.CONNECTION + SEPARATOR
                + DB_NAME + SEPARATOR
                + DB_LOGIN + SEPARATOR
                + DB_PASSWORD);
        in.add("unsuportedcommand");
        in.add(Command.EXIT);
        //when
        Main.main(new String[0]);
        //then
        assertEquals("Hello\r\n" +
                "Input your command or 'help'\r\n" +
                "Connect to the data base 'sqlcmd' successful!\r\n" +
                "Input your command or 'help'\r\n" +
                "The command doesn't exist\r\n" +
                "Input your command or 'help'\r\n" +
                "Connection to data base was closed\r\n" +
                "Goodbye\r\n", getData());
    }

    @Test
     public void testConnectionWithoutParametrs(){
        //given
        in.add(Command.CONNECTION);
        in.add(Command.EXIT);
        //when
        Main.main(new String[0]);
        //then
        assertEquals("Hello\r\n" +
                "Input your command or 'help'\r\n" +
                "ERROR. Wrong count parameters expected 3, but found 0\r\n" +
                "Input your command or 'help'\r\n" +
                "Goodbye\r\n", getData());
    }

    @Test
    public void testTableListAfterConnect(){
        //given
        in.add(Command.CONNECTION + SEPARATOR
                + DB_NAME + SEPARATOR
                + DB_LOGIN + SEPARATOR
                + DB_PASSWORD);
        in.add(Command.TABLE_LIST + SEPARATOR);
        in.add(Command.EXIT);
        //when
        Main.main(new String[0]);
        //then
        assertEquals("Hello\r\n" +
                "Input your command or 'help'\r\n" +
                "Connect to the data base 'sqlcmd' successful!\r\n" +
                "Input your command or 'help'\r\n" +
                "[users2, users]\r\n" +
                "Input your command or 'help'\r\n" +
                "Connection to data base was closed\r\n" +
                "Goodbye\r\n", getData());
    }

    @Test
    public void testClearTable(){
        //given
        in.add(Command.CONNECTION + SEPARATOR
                + DB_NAME + SEPARATOR
                + DB_LOGIN + SEPARATOR
                + DB_PASSWORD);
        in.add(Command.CLEAR_TABLE + SEPARATOR + TEST_TABLE);
        in.add(Command.EXIT);
        //when
        Main.main(new String[0]);
        //then
        assertEquals("Hello\r\n" +
                "Input your command or 'help'\r\n" +
                "Connect to the data base 'sqlcmd' successful!\r\n" +
                "Input your command or 'help'\r\n" +
                "users has been cleared\r\n" +
                "Input your command or 'help'\r\n" +
                "Connection to data base was closed\r\n" +
                "Goodbye\r\n", getData());
    }

    @Test
    public void testClearTableWithoutConnect(){
        //given
        in.add(Command.CLEAR_TABLE + SEPARATOR + TEST_TABLE);
        in.add(Command.EXIT);
        //when
        Main.main(new String[0]);
        //then
        assertEquals("Hello\r\n" +
                "Input your command or 'help'\r\n" +
                "ERROR. at first connect to database\r\n" +
                "Input your command or 'help'\r\n" +
                "Goodbye\r\n", getData());
    }

    @Test
    public void testClearTableWithError(){
        //given
        in.add(Command.CONNECTION + SEPARATOR
                + DB_NAME + SEPARATOR
                + DB_LOGIN + SEPARATOR
                + DB_PASSWORD);
        in.add(Command.CLEAR_TABLE + SEPARATOR + "wrongTableName");
        in.add(Command.EXIT);
        //when
        Main.main(new String[0]);
        //then
        assertEquals("Hello\r\n" +
                "Input your command or 'help'\r\n" +
                "Connect to the data base 'sqlcmd' successful!\r\n" +
                "Input your command or 'help'\r\n" +
                "ERROR. check table name\r\n" +
                "Input your command or 'help'\r\n" +
                "Connection to data base was closed\r\n" +
                "Goodbye\r\n", getData());
    }

    @Test
    public void testClearTableWithoutParameters(){
        //given
        in.add(Command.CONNECTION + SEPARATOR
                + DB_NAME + SEPARATOR
                + DB_LOGIN + SEPARATOR
                + DB_PASSWORD);
        in.add(Command.CLEAR_TABLE);
        in.add(Command.EXIT);
        //when
        Main.main(new String[0]);
        //then
        assertEquals("Hello\r\n" +
                "Input your command or 'help'\r\n" +
                "Connect to the data base 'sqlcmd' successful!\r\n" +
                "Input your command or 'help'\r\n" +
                "ERROR. Wrong count parameters expected 1, but found 0\r\n" +
                "Input your command or 'help'\r\n" +
                "Connection to data base was closed\r\n" +
                "Goodbye\r\n", getData());
    }



}
