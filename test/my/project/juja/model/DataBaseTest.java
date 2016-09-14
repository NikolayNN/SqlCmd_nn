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
        dataBase.clearTable(tableName);
        Table table = new Table(tableName, dataBase.getColumnInformation(tableName));
        Row row = new Row(table.getCellInfos());
        for (Cell cell : row.getCells()) {
            cell.setValue("235", true);
        }
        table.addRow(row);
        dataBase.addRecord(table);
        Table tableUpdate = new Table(tableName, table.getCellInfos());
        Row rowUpdate = new Row(table.getCellInfos());
        rowUpdate.getCell(0).setValue("", false);
        rowUpdate.getCell(1).setValue("400", false);
        rowUpdate.getCell(2).setValue("", false);
        tableUpdate.addRow(rowUpdate);
        dataBase.updateRecord("name='235'", tableUpdate);
        Table expectedTable = tableUpdate;
        expectedTable.getRow(0).getCell(0).setValue("235", false);
        expectedTable.getRow(0).getCell(2).setValue("235", false);
        Table actualTable = dataBase.getTableData(tableName);
        assertEquals(expectedTable.toString() , actualTable.toString());
    }

    @Test
    public void testUpdateRecordUpdateTwoValues() throws SQLException {
        dataBase.clearTable(tableName);
        Table table = new Table(tableName, dataBase.getColumnInformation(tableName));
        Row row = new Row(table.getCellInfos());
        for (Cell cell : row.getCells()) {
            cell.setValue("235", true);
        }
        table.addRow(row);
        dataBase.addRecord(table);
        Table tableUpdate = new Table(tableName, table.getCellInfos());
        Row rowUpdate = new Row(table.getCellInfos());
        rowUpdate.getCell(0).setValue("400", false);
        rowUpdate.getCell(1).setValue("400", false);
        rowUpdate.getCell(2).setValue("", false);
        tableUpdate.addRow(rowUpdate);
        dataBase.updateRecord("name='235'", tableUpdate);
        Table expectedTable = tableUpdate;
        expectedTable.getRow(0).getCell(2).setValue("235", false);
        Table actualTable = dataBase.getTableData(tableName);
        assertEquals(expectedTable.toString() , actualTable.toString());
    }
}
