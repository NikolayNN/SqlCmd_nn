package my.project.juja.controller.commands;

import my.project.juja.controller.commands.table.AddRecord;
import my.project.juja.model.table.CellInfo;
import my.project.juja.model.table.Row;
import my.project.juja.model.table.Table;
import my.project.juja.model.Storeable;
import my.project.juja.view.View;
import org.junit.Before;
import org.junit.Test;
import org.mockito.ArgumentCaptor;
import org.mockito.Mockito;


import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.atLeast;
import static org.mockito.Mockito.verify;


/**
 * Created by Nikol on 9/11/2016.
 */
public class AddRecordMockito{
    private Storeable store;
    private View view;
    private Table table;

    @Before
    public void setup(){
        store = Mockito.mock(Storeable.class);
        view = Mockito.mock(View.class);
    }

    @Test
    public void testNormal() throws Exception {
        //given
        String tableName = "users";
        Command command = new AddRecord(store, view);
        String commandString = Command.ADD_RECORD + Command.SEPARATOR + tableName;
        command.setup(commandString);
        CellInfo cellInfoId = new CellInfo("id", "integer", false, true, 0);
        CellInfo cellInfoName = new CellInfo("name", "text", true, true, 1);
        CellInfo cellInfoPass = new CellInfo("password", "character", true, true, 2);
        List<CellInfo> cellInfos = new ArrayList<>();
        cellInfos.add(cellInfoId);
        cellInfos.add(cellInfoName);
        cellInfos.add(cellInfoPass);
        Mockito.when(store.getColumnInformation(tableName)).thenReturn(cellInfos);
        String value1 = "1";
        String value2 = "Alex";
        String value3 = "111";
        Mockito.when(view.read()).thenReturn(value1).thenReturn(value2).thenReturn(value3);

        //when
        command.perform();

        //then
        ArgumentCaptor<String> captor = ArgumentCaptor.forClass(String.class);
        verify(view, atLeast(5)).writeln(captor.capture());
        String str1 = "[";
        for (CellInfo cellInfo : cellInfos) {
            str1 += "input value for the column " + cellInfo.toString() + " or just press 'enter' to skip input, ";
        }
        str1 += "successful added" + ", ";
        Table table = new Table(tableName, cellInfos);
        Row row = new Row(table.getCellInfos());
        row.getCell(0).setValue(value1, false);
        row.getCell(1).setValue(value2, false);
        row.getCell(2).setValue(value3, false);
        table.addRow(row);

        assertEquals(str1 + table.toString() +
                "]", captor.getAllValues().toString());
    }

    @Test
    public void testOneNullableColumnEmpty() throws Exception {
    //given
    String tableName = "users";
    Command command = new AddRecord(store, view);
    String commandString = Command.ADD_RECORD + Command.SEPARATOR + tableName;
    command.setup(commandString);
    CellInfo cellInfoId = new CellInfo("id", "integer", false, true, 0);
    CellInfo cellInfoName = new CellInfo("name", "text", true, true, 1);
    CellInfo cellInfoPass = new CellInfo("password", "character", true, true, 2);
    List<CellInfo> cellInfos = new ArrayList<>();
    cellInfos.add(cellInfoId);
    cellInfos.add(cellInfoName);
    cellInfos.add(cellInfoPass);
    Mockito.when(store.getColumnInformation(tableName)).thenReturn(cellInfos);
    String value1 = "1";
    String value2 = "";
    String value3 = "111";
    Mockito.when(view.read()).thenReturn(value1).thenReturn(value2).thenReturn(value3);

    //when
    command.perform();

    //then
    ArgumentCaptor<String> captor = ArgumentCaptor.forClass(String.class);
    verify(view, atLeast(5)).writeln(captor.capture());
    String str1 = "[";
    for (CellInfo cellInfo : cellInfos) {
        str1 += "input value for the column " + cellInfo.toString() + " or just press 'enter' to skip input, ";
    }
    str1 += "successful added" + ", ";
    Table table = new Table(tableName, cellInfos);
    Row row = new Row(table.getCellInfos());
    row.getCell(0).setValue(value1, false);
    row.getCell(1).setValue(value2, false);
    row.getCell(2).setValue(value3, false);
    table.addRow(row);

    assertEquals(str1 + table.toString() +
            "]", captor.getAllValues().toString());
}

    @Test
    public void testTwoNullableColumnEmpty() throws Exception {
        //given
        String tableName = "users";
        Command command = new AddRecord(store, view);
        String commandString = Command.ADD_RECORD + Command.SEPARATOR + tableName;
        command.setup(commandString);
        CellInfo cellInfoId = new CellInfo("id", "integer", false, true, 0);
        CellInfo cellInfoName = new CellInfo("name", "text", true, true, 1);
        CellInfo cellInfoPass = new CellInfo("password", "character", true, true, 2);
        List<CellInfo> cellInfos = new ArrayList<>();
        cellInfos.add(cellInfoId);
        cellInfos.add(cellInfoName);
        cellInfos.add(cellInfoPass);
        Mockito.when(store.getColumnInformation(tableName)).thenReturn(cellInfos);
        String value1 = "1";
        String value2 = "";
        String value3 = "";
        Mockito.when(view.read()).thenReturn(value1).thenReturn(value2).thenReturn(value3);

        //when
        command.perform();

        //then
        ArgumentCaptor<String> captor = ArgumentCaptor.forClass(String.class);
        verify(view, atLeast(5)).writeln(captor.capture());
        String str1 = "[";
        for (CellInfo cellInfo : cellInfos) {
            str1 += "input value for the column " + cellInfo.toString() + " or just press 'enter' to skip input, ";
        }
        str1 += "successful added" + ", ";
        Table table = new Table(tableName, cellInfos);
        Row row = new Row(table.getCellInfos());
        row.getCell(0).setValue(value1, false);
        row.getCell(1).setValue(value2, false);
        row.getCell(2).setValue(value3, false);
        table.addRow(row);

        assertEquals(str1 + table.toString() +
                "]", captor.getAllValues().toString());
    }

    @Test
    public void testNotNulablleColumnEmpty() throws Exception {
        //given
        String tableName = "users";
        Command command = new AddRecord(store, view);
        String commandString = Command.ADD_RECORD + Command.SEPARATOR + tableName;
        command.setup(commandString);
        CellInfo cellInfoId = new CellInfo("id", "integer", false, true, 0);
        CellInfo cellInfoName = new CellInfo("name", "text", false, false, 1);
        CellInfo cellInfoPass = new CellInfo("password", "character", true, true, 2);
        List<CellInfo> cellInfos = new ArrayList<>();
        cellInfos.add(cellInfoId);
        cellInfos.add(cellInfoName);
        cellInfos.add(cellInfoPass);
        Mockito.when(store.getColumnInformation(tableName)).thenReturn(cellInfos);
        String value1 = "1";
        String value2 = "Alex";
        String value3 = "111";
        String valueEmpty = "";
        Mockito.when(view.read()).thenReturn(value1).thenReturn(valueEmpty)
                .thenReturn(value2).thenReturn(value3);

        //when
        command.perform();

        //then
        ArgumentCaptor<String> captor = ArgumentCaptor.forClass(String.class);
        verify(view, atLeast(5)).writeln(captor.capture());
        String str1 = "[input value for the column [id(integer)] or just press 'enter' to skip input, input value for the column [name*(text)] or just press 'enter' to skip input, column \"name\" can't be null, input value for the column [name*(text)] or just press 'enter' to skip input, input value for the column [password(character)] or just press 'enter' to skip input, successful added, ";
        Table table = new Table(tableName, cellInfos);
        Row row = new Row(table.getCellInfos());
        row.getCell(0).setValue(value1, false);
        row.getCell(1).setValue(value2, false);
        row.getCell(2).setValue(value3, false);
        table.addRow(row);

        assertEquals(str1 + table.toString() +
                "]", captor.getAllValues().toString());
    }


}