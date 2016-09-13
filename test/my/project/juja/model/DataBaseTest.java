package my.project.juja.model;

import my.project.juja.controller.commands.table.Cell;
import my.project.juja.controller.commands.table.CellInfo;
import my.project.juja.controller.commands.table.Row;
import my.project.juja.controller.commands.table.Table;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;

import javax.swing.text.TabableView;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;

import static org.junit.Assert.assertEquals;

/**
 * Created by Nikol on 4/15/2016.
 */
public class DataBaseTest {
    private DataBase dataBase;
    private static final String tableName = "users";
    @Before
    public void setup() {
        dataBase = new DataBase();
        dataBase.getConnection("sqlcmd", "postgres", "root");
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

        assertEquals("[users, users2]", actual.toString());
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

    @Test
    public void getTableData(){
//        dataBase.clearTable("users");
//        dataBase.addRecord("users","name password Id", "login1 pass1 1");
//        dataBase.addRecord("users","name password Id", "login2 pass2 2");
//        dataBase.addRecord("users","name password Id", "login3 pass3 3");
//
//        List<String> actualList = dataBase.getTableData("users");
//        String actual = "";
//        for (String s : actualList) {
//            actual += s + "\n";
//        }
//
//        assertEquals(   "login1|pass1|1|\n" +
//                        "login2|pass2|2|\n" +
//                        "login3|pass3|3|\n", actual);
    }









}
