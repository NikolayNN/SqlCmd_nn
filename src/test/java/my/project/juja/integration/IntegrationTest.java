package my.project.juja.integration;

import my.project.juja.utilsForTest.TestDataBase;
import my.project.juja.controller.Main;
import my.project.juja.controller.commands.Command;
import my.project.juja.utilsForTest.TestUtils;
import org.junit.*;


import java.io.ByteArrayOutputStream;
import java.io.PrintStream;
import java.io.UnsupportedEncodingException;

import static org.junit.Assert.assertEquals;

/**
 * Created by Nikol on 4/25/2016.
 */
public class IntegrationTest {

    private static TestDataBase testDB;
    private ConfigurableInputStream in;
    private ByteArrayOutputStream out;
    private final String SEPARATOR = Command.SEPARATOR_TO_STRING;
    private final String COMMAND_CONNECT_SERVER = Command.CONNECTION_TO_SERVER + SEPARATOR
            + testDB.getServerURL() + SEPARATOR
            + testDB.getLogin() + SEPARATOR
            + testDB.getPassword();
    private final String COMMAND_CONECT_DATABASE = Command.CONNECTION_TO_DB + SEPARATOR +
                            testDB.getDbName();



    @BeforeClass
    public static void prepare() {
        testDB = new TestDataBase();
        testDB.createTestDataBase();
    }

    @AfterClass
    public static void delete() {
        testDB.dropTestDataBase();
    }

    @Before
    public void setup() {
        in = new ConfigurableInputStream();
        out = new ByteArrayOutputStream();
        System.setIn(in);
        System.setOut(new PrintStream(out));
        testDB.createTestTable();
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
    public void testHelp() {
        //given
        in.add(Command.HELP);
        in.add(Command.EXIT);
        //when
        Main.main(new String[0]);
        //then
        String expected = "Hello\n" +
                "Input your command or 'help'\n" +
                "connect-server\n" +
                "\t - Connect to a server 'connect-server|serverUrl|login|password'\n" +
                "\t * Example: 'connect-server|localhost:5432|postgres|root'\n" +
                "connect-db\n" +
                "\t - Connect to database 'connect-db|dbname'\n" +
                "disconnect-db\n" +
                "\t - disonnect current database 'disconnect-db'\n" +
                "list-db\n" +
                "\t - Show available data bases in the current server 'list-db'\n" +
                "current-db\n" +
                "\t - Show current data base name 'current-db'\n" +
                "drop-db\n" +
                "\t - Delete the data base 'drop-db|dataBaseName'\n" +
                "create-db\n" +
                "\t - Create new data base 'create-db|dataBaseName'\n" +
                "create-table\n" +
                "\t - Create new table in the current data base 'create-table|tableName '\n" +
                "drop-table\n" +
                "\t - Drop table 'drop-table|tableName '\n" +
                "table-list\n" +
                "\t - Show exist tables in the current database 'table-list'\n" +
                "table-data\n" +
                "\t - Show all table rows 'table-data|tableName'\n" +
                "table-data-where\n" +
                "\t - Show table rows with the condition WHERE 'table-data-where|tableName'\n" +
                "add-record\n" +
                "\t - Add record in the selected table 'add-record|tableName'\n" +
                "update-table\n" +
                "\t - Update record in the selected table 'update-table|tableName\n" +
                "clear-table\n" +
                "\t - Clear selected table 'clear-table|tableName'\n" +
                "exit\n" +
                "\t - Close connection to a database and exit\n" +
                "Input your command or 'help'\n" +
                "Goodbye\n";
        assertEquals(expected, getData().replaceAll("\\r\\n","\n"));

    }


    @Test
    public void testCurrentDataBase(){
//        given
        in.add(COMMAND_CONNECT_SERVER);
        in.add(COMMAND_CONECT_DATABASE);
        in.add(Command.CURRENT_DATA_BASE_NAME);
        in.add(Command.EXIT);
//        when
        Main.main(new String[0]);
//        then
        String expected = "Hello\n" +
                "Input your command or 'help'\n" +
                "Connect to the server successful!\n" +
                "Input your command or 'help'\n" +
                "Connect to the data base 'test729451' successful!\n" +
                "Input your command or 'help'\n" +
                "[test729451]\n" +
                "Input your command or 'help'\n" +
                "Connection to data base was closed\n" +
                "Goodbye\n";
        assertEquals(TestUtils.replaceLineSeparator(expected), getData());
    }

    @Test
    public void testDisconnectCurrentDataBaseNormal(){
//        given
        in.add(COMMAND_CONNECT_SERVER);
        in.add(COMMAND_CONECT_DATABASE);
        in.add(Command.DISCONECT_DATA_BASE);
        in.add(Command.EXIT);
//        when
        Main.main(new String[0]);
//        then
        String expected = "Hello\n" +
                "Input your command or 'help'\n" +
                "Connect to the server successful!\n" +
                "Input your command or 'help'\n" +
                "Connect to the data base 'test729451' successful!\n" +
                "Input your command or 'help'\n" +
                "Data base 'test729451' disconnected.\n" +
                "Input your command or 'help'\n" +
                "Goodbye\n";
        assertEquals(TestUtils.replaceLineSeparator(expected), getData());
    }

    @Test
    public void testDisconnectCurrentDataBaseWithoutConnectionDataBase(){
//        given
        in.add(COMMAND_CONNECT_SERVER);
        in.add(Command.DISCONECT_DATA_BASE);
        in.add(Command.EXIT);
//        when
        Main.main(new String[0]);
//        then
        String expected = "Hello\n" +
                "Input your command or 'help'\n" +
                "Connect to the server successful!\n" +
                "Input your command or 'help'\n" +
                "Data base '' disconnected.\n" +
                "Input your command or 'help'\n" +
                "Goodbye\n";
        assertEquals(TestUtils.replaceLineSeparator(expected), getData());
    }

    @Test
    public void testTableListWithoutConnect() {
        //given
        in.add(Command.TABLE_LIST);
        in.add(Command.EXIT);
        //when
        Main.main(new String[0]);
        //then
        String expected = "Hello\n" +
                "Input your command or 'help'\n" +
                "ERROR. At first connect to a server.\n" +
                "Input your command or 'help'\n" +
                "Goodbye\n";
        assertEquals(TestUtils.replaceLineSeparator(expected), getData());
    }

    @Test
    public void testTableDataWithoutConnect() {
        //given
        in.add(Command.TABLE_DATA);
        in.add(Command.EXIT);
        //when
        Main.main(new String[0]);
        //then
        String expected = "Hello\n" +
                "Input your command or 'help'\n" +
                "ERROR. at first connect to server\n" +
                "Input your command or 'help'\n" +
                "Goodbye\n";
        assertEquals(TestUtils.replaceLineSeparator(expected), getData());
    }

    @Test
    public void testWrongCommand() {
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
                "Goodbye\r\n", getData());
    }

    @Test
    public void testConnect() {
        //given
        in.add(Command.CONNECTION_TO_SERVER + SEPARATOR
                + testDB.getServerURL() + SEPARATOR
                + testDB.getLogin() + SEPARATOR
                + testDB.getPassword());
        in.add(Command.CONNECTION_TO_DB + SEPARATOR
                + testDB.getDbName());
        in.add(Command.EXIT);
        //when
        Main.main(new String[0]);
        //then
        String expected = "Hello\n" +
                "Input your command or 'help'\n" +
                "Connect to the server successful!\n" +
                "Input your command or 'help'\n" +
                "Connect to the data base 'test729451' successful!\n" +
                "Input your command or 'help'\n" +
                "Connection to data base was closed\n" +
                "Goodbye\n";
        assertEquals(TestUtils.replaceLineSeparator(expected), getData());
    }

    @Test
    public void testConnectWithWrongDBName() {
        //given
        in.add(Command.CONNECTION_TO_SERVER + SEPARATOR
                + testDB.getServerURL() + SEPARATOR
                + testDB.getLogin() + SEPARATOR
                + testDB.getPassword());
        in.add(Command.CONNECTION_TO_DB + SEPARATOR
                + "WrongTableName");
        in.add(Command.EXIT);
        //when
        Main.main(new String[0]);
        //then
        String expected = "Hello\n" +
                "Input your command or 'help'\n" +
                "Connect to the server successful!\n" +
                "Input your command or 'help'\n" +
                "ERROR. connect to database unsuccessful, check your command. FATAL: database \"WrongTableName\" does not exist\n" +
                "Input your command or 'help'\n" +
                "Goodbye\n";
        assertEquals(TestUtils.replaceLineSeparator(expected), getData());
    }

    @Test
    public void testConnectWithWrongLogin() {
        //given
        in.add(Command.CONNECTION_TO_SERVER + SEPARATOR
                + testDB.getServerURL() + SEPARATOR
                + "WrongLogin" + SEPARATOR
                + testDB.getPassword());
        in.add(Command.CONNECTION_TO_DB + SEPARATOR
                + testDB.getTableName());
        in.add(Command.EXIT);
        //when
        Main.main(new String[0]);
        //then
        String expected = "Hello\n" +
                "Input your command or 'help'\n" +
                "ERROR. connect to database unsuccessful, check your command. FATAL: password authentication failed for user \"WrongLogin\"\n" +
                "Input your command or 'help'\n" +
                "ERROR. connect to database unsuccessful, check your command. The connection attempt failed.\n" +
                "Input your command or 'help'\n" +
                "Goodbye\n";
        assertEquals(TestUtils.replaceLineSeparator(expected), getData());
    }

    @Test
    public void testUnsuportedCommandAfterConnect() {
        //given
        in.add(Command.CONNECTION_TO_SERVER + SEPARATOR
                + testDB.getServerURL() + SEPARATOR
                + testDB.getLogin() + SEPARATOR
                + testDB.getPassword());
        in.add(Command.CONNECTION_TO_DB + SEPARATOR
                + testDB.getDbName());
        in.add("unsuportedcommand");
        in.add(Command.EXIT);
        //when
        Main.main(new String[0]);
        //then
        String expected = "Hello\n" +
                "Input your command or 'help'\n" +
                "Connect to the server successful!\n" +
                "Input your command or 'help'\n" +
                "Connect to the data base 'test729451' successful!\n" +
                "Input your command or 'help'\n" +
                "The command doesn't exist\n" +
                "Input your command or 'help'\n" +
                "Connection to data base was closed\n" +
                "Goodbye\n";
        assertEquals(TestUtils.replaceLineSeparator(expected), getData());
    }

    @Test
    public void testConnectionWithoutParametrs() {
        //given
        in.add(Command.CONNECTION_TO_SERVER );
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
    public void testTableListAfterConnect() {
        //given
        in.add(Command.CONNECTION_TO_SERVER + SEPARATOR
                + testDB.getServerURL() + SEPARATOR
                + testDB.getLogin() + SEPARATOR
                + testDB.getPassword());
        in.add(Command.CONNECTION_TO_DB + SEPARATOR
                + testDB.getDbName());
        in.add(Command.TABLE_LIST + SEPARATOR);
        in.add(Command.EXIT);
        //when
        Main.main(new String[0]);
        //then
        String expected = "Hello\n" +
                "Input your command or 'help'\n" +
                "Connect to the server successful!\n" +
                "Input your command or 'help'\n" +
                "Connect to the data base 'test729451' successful!\n" +
                "Input your command or 'help'\n" +
                "[users]\n" +
                "Input your command or 'help'\n" +
                "Connection to data base was closed\n" +
                "Goodbye\n";
        assertEquals(TestUtils.replaceLineSeparator(expected), getData());
    }

    @Test
    public void testClearTableY() {
        //given
        in.add(Command.CONNECTION_TO_SERVER + SEPARATOR
                + testDB.getServerURL() + SEPARATOR
                + testDB.getLogin() + SEPARATOR
                + testDB.getPassword());
        in.add(Command.CONNECTION_TO_DB + SEPARATOR
                + testDB.getDbName());
        in.add(Command.CLEAR_TABLE + SEPARATOR + testDB.getTableName());
        in.add("y");
        in.add(Command.EXIT);
        //when
        Main.main(new String[0]);
        //then
        String expected = "Hello\n" +
                "Input your command or 'help'\n" +
                "Connect to the server successful!\n" +
                "Input your command or 'help'\n" +
                "Connect to the data base 'test729451' successful!\n" +
                "Input your command or 'help'\n" +
                "Are you sure clear table 'users'? (Y/N)\n" +
                "users has been cleared\n" +
                "Input your command or 'help'\n" +
                "Connection to data base was closed\n" +
                "Goodbye\n";
        assertEquals(TestUtils.replaceLineSeparator(expected), getData());
    }

    @Test
    public void testClearTableN() {
        //given
        in.add(Command.CONNECTION_TO_SERVER + SEPARATOR
                + testDB.getServerURL() + SEPARATOR
                + testDB.getLogin() + SEPARATOR
                + testDB.getPassword());
        in.add(Command.CONNECTION_TO_DB + SEPARATOR
                + testDB.getDbName());
        in.add(Command.CLEAR_TABLE + SEPARATOR + testDB.getTableName());
        in.add("n");
        in.add(Command.EXIT);
        //when
        Main.main(new String[0]);
        //then
        String expected = "Hello\n" +
                "Input your command or 'help'\n" +
                "Connect to the server successful!\n" +
                "Input your command or 'help'\n" +
                "Connect to the data base 'test729451' successful!\n" +
                "Input your command or 'help'\n" +
                "Are you sure clear table 'users'? (Y/N)\n" +
                "Canceled\n" +
                "Input your command or 'help'\n" +
                "Connection to data base was closed\n" +
                "Goodbye\n";
        assertEquals(TestUtils.replaceLineSeparator(expected), getData());
    }

    @Test
    public void testClearTableWrongConfirm() {
        //given
        in.add(Command.CONNECTION_TO_SERVER + SEPARATOR
                + testDB.getServerURL() + SEPARATOR
                + testDB.getLogin() + SEPARATOR
                + testDB.getPassword());
        in.add(Command.CONNECTION_TO_DB + SEPARATOR
                + testDB.getDbName());
        in.add(Command.CLEAR_TABLE + SEPARATOR + testDB.getTableName());
        in.add("wrongCommand");
        in.add("y");
        in.add(Command.EXIT);
        //when
        Main.main(new String[0]);
        //then
        String expected = "Hello\n" +
                "Input your command or 'help'\n" +
                "Connect to the server successful!\n" +
                "Input your command or 'help'\n" +
                "Connect to the data base 'test729451' successful!\n" +
                "Input your command or 'help'\n" +
                "Are you sure clear table 'users'? (Y/N)\n" +
                "wrong input\n" +
                "Are you sure clear table 'users'? (Y/N)\n" +
                "users has been cleared\n" +
                "Input your command or 'help'\n" +
                "Connection to data base was closed\n" +
                "Goodbye\n";
        assertEquals(TestUtils.replaceLineSeparator(expected), getData());
    }

    @Test
    public void testClearTableWithoutConnect() {
        //given
        in.add(Command.CLEAR_TABLE + SEPARATOR + testDB.getTableName());
        in.add(Command.EXIT);
        //when
        Main.main(new String[0]);
        //then
        String expected = "Hello\n" +
                "Input your command or 'help'\n" +
                "ERROR. at first connect to server\n" +
                "Input your command or 'help'\n" +
                "Goodbye\n";
        assertEquals(TestUtils.replaceLineSeparator(expected), getData());
    }

    @Test
    public void testClearTableWithError() {
        //given
        in.add(Command.CONNECTION_TO_SERVER + SEPARATOR
                + testDB.getServerURL() + SEPARATOR
                + testDB.getLogin() + SEPARATOR
                + testDB.getPassword());
        in.add(Command.CONNECTION_TO_DB + SEPARATOR
                + testDB.getDbName());
        in.add(Command.CLEAR_TABLE + SEPARATOR + "wrongTableName");
        in.add("y");
        in.add(Command.EXIT);
        //when
        Main.main(new String[0]);
        //then
        String expected = "Hello\n" +
                "Input your command or 'help'\n" +
                "Connect to the server successful!\n" +
                "Input your command or 'help'\n" +
                "Connect to the data base 'test729451' successful!\n" +
                "Input your command or 'help'\n" +
                "ERROR. The table 'wrongTableName' isn't exist. Available tables [users]\n" +
                "Input your command or 'help'\n" +
                "The command doesn't exist\n" +
                "Input your command or 'help'\n" +
                "Connection to data base was closed\n" +
                "Goodbye\n";
        assertEquals(TestUtils.replaceLineSeparator(expected), getData());
    }

    @Test
    public void testClearTableWithoutParameters() {
        //given
        in.add(Command.CONNECTION_TO_SERVER + SEPARATOR
                + testDB.getServerURL() + SEPARATOR
                + testDB.getLogin() + SEPARATOR
                + testDB.getPassword());
        in.add(Command.CONNECTION_TO_DB + SEPARATOR
                + testDB.getDbName());
        in.add(Command.CLEAR_TABLE);
        in.add(Command.EXIT);
        //when
        Main.main(new String[0]);
        //then
        String expected = "Hello\n" +
                "Input your command or 'help'\n" +
                "Connect to the server successful!\n" +
                "Input your command or 'help'\n" +
                "Connect to the data base 'test729451' successful!\n" +
                "Input your command or 'help'\n" +
                "ERROR. Wrong count parameters expected 1, but found 0\n" +
                "Input your command or 'help'\n" +
                "Connection to data base was closed\n" +
                "Goodbye\n";
        assertEquals(TestUtils.replaceLineSeparator(expected), getData());
    }

    @Test
    public void dropTableConfirmN(){
        in.add(COMMAND_CONNECT_SERVER);
        in.add(COMMAND_CONECT_DATABASE);
        in.add(Command.DROP_TABLE + Command.SEPARATOR_TO_STRING + testDB.getTableName());
        in.add("n");
        in.add(Command.EXIT);
        //when
        Main.main(new String[0]);
        //then
        String expected = "Hello\n" +
                "Input your command or 'help'\n" +
                "Connect to the server successful!\n" +
                "Input your command or 'help'\n" +
                "Connect to the data base 'test729451' successful!\n" +
                "Input your command or 'help'\n" +
                "Are you sure delete table 'users'? (Y/N)\n" +
                "Canceled\n" +
                "Input your command or 'help'\n" +
                "Connection to data base was closed\n" +
                "Goodbye\n";
        assertEquals(TestUtils.replaceLineSeparator(expected), getData());
    }

    @Test
    public void dropTableConfirmY(){
        in.add(COMMAND_CONNECT_SERVER);
        in.add(COMMAND_CONECT_DATABASE);
        in.add(Command.DROP_TABLE + Command.SEPARATOR_TO_STRING + testDB.getTableName());
        in.add("y");
        in.add(Command.EXIT);
        //when
        Main.main(new String[0]);
        //then
        String expected = "Hello\n" +
                "Input your command or 'help'\n" +
                "Connect to the server successful!\n" +
                "Input your command or 'help'\n" +
                "Connect to the data base 'test729451' successful!\n" +
                "Input your command or 'help'\n" +
                "Are you sure delete table 'users'? (Y/N)\n" +
                "table 'users' deleted\n" +
                "Input your command or 'help'\n" +
                "Connection to data base was closed\n" +
                "Goodbye\n";
        assertEquals(TestUtils.replaceLineSeparator(expected), getData());
    }

    @Test
    public void dropTableAtFirstWrongConfirm(){
        in.add(COMMAND_CONNECT_SERVER);
        in.add(COMMAND_CONECT_DATABASE);
        in.add(Command.DROP_TABLE + Command.SEPARATOR_TO_STRING + testDB.getTableName());
        in.add("sss");
        in.add("y");
        in.add(Command.EXIT);
        //when
        Main.main(new String[0]);
        //then
        String expected = "Hello\n" +
                "Input your command or 'help'\n" +
                "Connect to the server successful!\n" +
                "Input your command or 'help'\n" +
                "Connect to the data base 'test729451' successful!\n" +
                "Input your command or 'help'\n" +
                "Are you sure delete table 'users'? (Y/N)\n" +
                "wrong input\n" +
                "Are you sure delete table 'users'? (Y/N)\n" +
                "table 'users' deleted\n" +
                "Input your command or 'help'\n" +
                "Connection to data base was closed\n" +
                "Goodbye\n";
        assertEquals(TestUtils.replaceLineSeparator(expected), getData());
    }











}
