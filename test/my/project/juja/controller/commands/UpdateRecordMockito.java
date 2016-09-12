package my.project.juja.controller.commands;

import my.project.juja.controller.commands.table.CellInfo;
import my.project.juja.controller.commands.table.Row;
import my.project.juja.controller.commands.table.Table;
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
public class UpdateRecordMockito {
    private Storeable store;
    private View view;
    private static final String SEPARATOR = "|";


    @Before
    public void setup(){
        store = Mockito.mock(Storeable.class);
        view = Mockito.mock(View.class);
    }

    @Test
    public void testNormal() throws Exception {
        //given
        String tableName = "users";
        String where = "name='Alex'";
        Command command = new UpdateRecord(store, view);
        String commandString =  Command.UPDATE_TABLE + SEPARATOR + tableName +
                                SEPARATOR + where;

        System.out.println(commandString);
        command.setup(commandString);
        CellInfo cellInfoId = new CellInfo("id", "integer", false, true, 0);
        CellInfo cellInfoName = new CellInfo("name", "text", true, true, 1);
        CellInfo cellInfoPass = new CellInfo("password", "character", true, true, 2);
        List<CellInfo> cellInfos = new ArrayList<>();
        cellInfos.add(cellInfoId);
        cellInfos.add(cellInfoName);
        cellInfos.add(cellInfoPass);
        Table table = new Table(tableName, cellInfos);
        Row row = new Row(table.getCellInfos());
        String value1 = "1";
        String value2 = "Alex";
        String value3 = "111";
        row.getCell(0).setValue(value1, false);
        row.getCell(1).setValue(value2, false);
        row.getCell(2).setValue(value3, false);
        table.addRow(row);
        Mockito.when(store.getTableData(tableName, "name='Alex'")).thenReturn(table);
        String updValue1 = "";
        String updValue2 = "Vovan";
        String updValue3 = "";
        Mockito.when(view.read()).thenReturn(updValue1).thenReturn(updValue2).thenReturn(updValue3);

        //when
        command.perform();

        //then
        ArgumentCaptor<String> captor = ArgumentCaptor.forClass(String.class);
        verify(view, atLeast(5)).writeln(captor.capture());
        assertEquals("[users\n" +
                "----------------------------------------------\n" +
                "id | name | password | \n" +
                "----------------------------------------------\n" +
                "1 | Alex | 111 | \n" +
                ", input new value or skip, [id(integer)], input new value or skip, [name(text)], input new value or skip, [password(character)], table updated]", captor.getAllValues().toString());
    }

    @Test(expected = RuntimeException.class)
    public void testWhereIsNull() throws Exception {
        //given
        String tableName = "users";
        Command command = new UpdateRecord(store, view);
        String commandString =  Command.UPDATE_TABLE + SEPARATOR + tableName;
        command.setup(commandString);
        command.perform();
    }

    @Test(expected = RuntimeException.class)
    public void testAllParametersAreNull() throws Exception {
        //given
        String tableName = "users";
        Command command = new UpdateRecord(store, view);
        String commandString =  Command.UPDATE_TABLE;
        command.setup(commandString);
        command.perform();
    }
}