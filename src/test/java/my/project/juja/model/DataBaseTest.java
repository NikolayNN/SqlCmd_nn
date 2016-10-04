package my.project.juja.model;

import my.project.juja.model.table.Cell;
import my.project.juja.model.table.Row;
import my.project.juja.model.table.Table;
import my.project.juja.utilsForTest.TestDataBase;
import org.junit.*;
import org.junit.rules.ExpectedException;

import java.sql.Connection;
import java.sql.SQLException;
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
        dataBase.connectToServer(testDB.getServerURL(), testDB.getLogin(), testDB.getPassword());
        dataBase.connectToDataBase(testDB.getDbName());
        testDB.createTestTable();
    }

    @After
    public void finish() {
        dataBase.disconectDataBase();
    }

    @Rule
    public ExpectedException expectedEx = ExpectedException.none();


    @Test(expected = RuntimeException.class)
    public void connectToDataBaseWrongDataBaseName() {
        dataBase.connectToDataBase("wrongDataBaseName");
    }

    @Test
    public void getConnectionToDataBase() {
        Connection connection = dataBase.getConnectToDataBase();
        if (connection != null) {
            assert (true);
        } else {
            assert (false);
        }
    }

    @Test
    public void getTableData() {
        Table actualTable = dataBase.getTableData(testDB.getTableName(), "id>=2");
        assertEquals("users\n" +
                "-----------------------------------------\n" +
                "id | firstname | lastname | password   | \n" +
                "-----------------------------------------\n" +
                "2  | Kirril    | Ivanov   | 0000       | \n" +
                "3  | Pasha     | Sidorov  | 157862asdw | \n", actualTable.toString());
    }


    @Test
    public void testAddRecordWrongTableName() {
        expectedEx.expect(RuntimeException.class);
        dataBase.clearTable("wrongTableName");
    }

    @Test
    public void testGetDataBasesNames() {
        Set<String> names = dataBase.getDataBasesNames();
        if (names.contains(testDB.getDbName())) {
            assert (true);
        } else {
            assert (false);
        }
    }

    @Test
    public void testgetNameCurrentDataBase() {
        String actualDbName = dataBase.getNameCurrentDataBase();
        assertEquals(testDB.getDbName(), actualDbName);
    }

    @Test(expected = RuntimeException.class)
    public void dropDataBaseWithoutDisconect() {
        dataBase.dropDataBase(testDB.getDbName());
    }

    @Test(expected = RuntimeException.class)
    public void dropDataBaseWithWrongDataBaseName() {
        dataBase.dropDataBase("wrongDataBaseName");
    }

    @Test(expected = RuntimeException.class)
    public void createDataBaseWithWrongName() {
        dataBase.createDataBase("wrong data base name");
    }

    @Test(expected = RuntimeException.class)
    public void createTableWithWrongName() {
        dataBase.createTable("Wrong table name", testDB.getTable().getTableHeader());
    }

    @Test(expected = RuntimeException.class)
    public void dropTableWithWrongName() {
        dataBase.dropTable("Wrong table name");
    }

    @Test
    public void testdeleteRecord() {
        dataBase.deleteRecord(testDB.getTableName(), "id=1");
        Table actualTable = dataBase.getTableData(testDB.getTableName());
        assertEquals("users\n" +
                "-----------------------------------------\n" +
                "id | firstname | lastname | password   | \n" +
                "-----------------------------------------\n" +
                "2  | Kirril    | Ivanov   | 0000       | \n" +
                "3  | Pasha     | Sidorov  | 157862asdw | \n", actualTable.toString());
    }

    @Test(expected = RuntimeException.class)
    public void testDeleteRecordWithWrongTableName() {
        dataBase.deleteRecord("wrongTableName55151754", "id=1");
    }


    @Test
    public void getTableListTest() {
        Set<String> actual = dataBase.getTableList();

        assertEquals("[users]", actual.toString());
    }

    @Test
    public void getTableDataWithWrongTableName() {
        expectedEx.expect(RuntimeException.class);
        dataBase.getTableData("wrongTableName");
    }

    @Test
    public void testAddRecord() throws SQLException {
        dataBase.clearTable(tableName);
        Table expectedTable = new Table(tableName, dataBase.getColumnInformation(tableName));
        Row row = new Row(expectedTable.getTableHeader());
        for (Cell cell : row.getCells()) {
            cell.setValue("235", true);
        }
        expectedTable.addRow(row);
        dataBase.addRecord(expectedTable);
        Table actualTable = dataBase.getTableData(tableName);
        assertEquals(actualTable.toString(), expectedTable.toString());
    }

    @Test(expected = RuntimeException.class)
    public void testWrongTypeValue() throws SQLException {
        dataBase.clearTable(tableName);
        Table expectedTable = new Table(tableName, dataBase.getColumnInformation(tableName));
        Row row = new Row(expectedTable.getTableHeader());
        for (Cell cell : row.getCells()) {
            cell.setValue("sss", true);
        }
        expectedTable.addRow(row);
        dataBase.addRecord(expectedTable);
    }

    @Test
    public void testUpdateRecord() throws SQLException {
        Table tableUpdate = new Table(tableName, testDB.getTable().getTableHeader());
        Row rowUpdate = new Row(tableUpdate.getTableHeader());
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
                "3  | 400       | 400      | 157862asdw | \n", actualTable.toString());
    }
}
