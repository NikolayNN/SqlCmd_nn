package my.project.juja.model;

import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;

import java.util.List;
import java.util.Set;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

/**
 * Created by Nikol on 4/15/2016.
 */
public class DataBaseTest {
    DataBase DataBase;
    @Before
    public void setup() {
        DataBase = new DataBase();
        DataBase.getConnection("sqlcmd", "postgres", "root");
    }


    @Rule
    public ExpectedException expectedEx = ExpectedException.none();

    @Test
    public void testAddRecord(){

        DataBase.clearTable("users");

        DataBase.addRecord("users","name password Id", "alex FadH74Gne 123");

        List<String> recordList = DataBase.getTableData("users");
        String actual = recordList.get(0);

        assertEquals("alex|FadH74Gne|123|", actual);

    }

    @Test
    public void testAddRecordWrongTableName(){
        expectedEx.expect(RuntimeException.class);
        DataBase.clearTable("wrongTableName");
    }

    @Test
    public void getColumnNameTest(){
        List<String> actual = DataBase.getColumnName("users");
        assertEquals("[name, password, Id]", actual.toString());
    }
    @Test
    public void getColumnNameWithWrongTableName() {
        expectedEx.expect(RuntimeException.class);
        DataBase.getColumnName("wrongTableName");
    }

    @Test
    public void getTableListTest(){
        Set<String> actual = DataBase.getTableList();

        assertEquals("[users, users2]", actual.toString());
    }

    @Test
    public void getTableData(){
        DataBase.clearTable("users");
        DataBase.addRecord("users","name password Id", "login1 pass1 1");
        DataBase.addRecord("users","name password Id", "login2 pass2 2");
        DataBase.addRecord("users","name password Id", "login3 pass3 3");

        List<String> actualList = DataBase.getTableData("users");
        String actual = "";
        for (String s : actualList) {
            actual += s + "\n";
        }

        assertEquals(   "login1|pass1|1|\n" +
                        "login2|pass2|2|\n" +
                        "login3|pass3|3|\n", actual);
    }

    @Test
    public void getTableDataWithWrongTableName(){
        expectedEx.expect(RuntimeException.class);
        DataBase.getTableData("wrongTableName");
    }








}
