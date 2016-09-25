package my.project.juja.model;

import my.project.juja.model.table.Cell;
import my.project.juja.model.table.Row;
import my.project.juja.model.table.Table;
import my.project.juja.utils.TestDataBase;
import org.junit.*;
import org.junit.rules.ExpectedException;

import java.io.FileInputStream;
import java.io.IOException;
import java.sql.SQLException;
import java.util.Properties;
import java.util.Set;

import static org.junit.Assert.assertEquals;

/**
 * Created by Nikol on 4/15/2016.
 */
public class DataBaseTest {
    private DataBase dataBase;
    private static String tableName;
    private static TestDataBase testDB;

    @BeforeClass
    public static void prepare() {
        testDB = new TestDataBase();
        testDB.createTestDataBase();
        testDB.createTestTable();
        tableName = testDB.getTableName();
    }

    @AfterClass
    public static void delete() {
        testDB.dropTestDataBase();
    }

    @Before
    public void setup() {
        dataBase = new DataBase();
        dataBase.connectToServer(testDB.getServerURL(),testDB.getLogin(),testDB.getPassword());
        dataBase.connectToDataBase(testDB.getDbName());
    }

    @After
    public void finish(){
        dataBase.disconectDataBase();
    }

    @Rule
    public ExpectedException expectedEx = ExpectedException.none();


    @Test
    public void testAddRecordWrongTableName(){
        expectedEx.expect(RuntimeException.class);
        dataBase.clearTable("wrongTableName");
    }

    @Test
    public void getTableListTest(){
        Set<String> actual = dataBase.getTableList();

        assertEquals("[users]", actual.toString());
    }

    @Test
    public void getTableDataWithWrongTableName(){
        expectedEx.expect(RuntimeException.class);
        dataBase.getTableData("wrongTableName");
    }

    @Test
    public void testAddRecord() throws SQLException {
        dataBase.clearTable(tableName);
        Table expectedTable = new Table(tableName, dataBase.getColumnInformation(tableName));
        Row row = new Row(expectedTable.getCellInfos());
        for (Cell cell : row.getCells()) {
            cell.setValue("235", true);
        }
        expectedTable.addRow(row);
        dataBase.addRecord(expectedTable);
        Table actualTable = dataBase.getTableData(tableName);
        assertEquals(actualTable.toString(), expectedTable.toString());
    }

    @Test(expected = SQLException.class)
    public void testWrongTypeValue() throws SQLException {
        dataBase.clearTable(tableName);
        Table expectedTable = new Table(tableName, dataBase.getColumnInformation(tableName));
        Row row = new Row(expectedTable.getCellInfos());
        for (Cell cell : row.getCells()) {
            cell.setValue("sss", true);
        }
        expectedTable.addRow(row);
        dataBase.addRecord(expectedTable);
    }

    @Test
    public void testUpdateRecord() throws SQLException {
        Table tableUpdate = new Table(tableName, testDB.getTable().getCellInfos());
        Row rowUpdate = new Row(tableUpdate.getCellInfos());
        rowUpdate.getCell(0).setValue("", false);
        rowUpdate.getCell(1).setValue("400", false);
        rowUpdate.getCell(2).setValue("400", false);
        rowUpdate.getCell(3).setValue("", false);
        tableUpdate.addRow(rowUpdate);
        dataBase.updateRecord("id='3'", tableUpdate);
        Table actualTable = dataBase.getTableData(tableName);
        assertEquals("users\n" +
                "-----------------------------------------\n" +
                "id | firstname | lastname | password   | \n" +
                "-----------------------------------------\n" +
                "1  | Vasya     | Pupkin   | qwerty     | \n" +
                "2  | Kirril    | Ivanov   | 0000       | \n" +
                "3  | 400       | 400      | 157862asdw | \n" , actualTable.toString());
    }
}
