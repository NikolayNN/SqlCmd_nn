package my.project.juja.controller.commands.table;

import my.project.juja.controller.commands.Command;
import my.project.juja.model.Storeable;
import my.project.juja.model.table.Table;
import my.project.juja.utilsForTest.TestTable;
import my.project.juja.view.View;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mockito;

import java.sql.Connection;

import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.atLeast;
import static org.mockito.Mockito.verify;

/**
 * Created by Nikol on 9/27/2016.
 */
public class DeleteRecordMockito {
    private Storeable store;
    private View view;
    private Connection connection;
    private Table testTable;
    private Command spyCommand;
    private Command command;

    @Before
    public void setup(){
        store = Mockito.mock(Storeable.class);
        view = Mockito.mock(View.class);
        connection = Mockito.mock(Connection.class);
        testTable = new TestTable().getTable();
        command = new DeleteRecord(store, view);
        spyCommand = Mockito.spy(command);
    }

    @Test
    public void testNormal(){
        //given
        String where = testTable.getCellInfos(0).getColumnName() + "=" + "value";
        String commandString = Command.DELETE_RECORD + Command.SEPARATOR + testTable.getTableName();
        spyCommand.setup(commandString);
        Mockito.when(store.getConnectToDataBase()).thenReturn(connection);
        Mockito.when(store.getColumnInformation(testTable.getTableName())).thenReturn(testTable.getTableHeader());
        Mockito.when(store.getTableData(testTable.getTableName(), where)).thenReturn(testTable);
        Mockito.when(view.read()).thenReturn("n")
                .thenReturn(where)
                .thenReturn("y");
        spyCommand.perform();
        //then
        verify(store, atLeast(1)).deleteRecord(testTable.getTableName(),where);

        }
}
