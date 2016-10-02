package my.project.juja.controller.commands.table;

import my.project.juja.controller.commands.Command;
import my.project.juja.model.Storeable;
import my.project.juja.utilsForTest.TestTable;
import my.project.juja.view.View;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mockito;

import java.sql.Connection;

import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.atLeastOnce;
import static org.mockito.Mockito.verify;

/**
 * Created by Nikol on 9/27/2016.
 */
public class TableDataMockito {
    private Storeable store;
    private View view;
    private Connection connection;
    private Command command;

    @Before
    public void setup() {
        store = Mockito.mock(Storeable.class);
        view = Mockito.mock(View.class);
        connection = Mockito.mock(Connection.class);
        command = new TableData(store, view);
    }

    @Test
    public void tableData() {
        //given
        String tableName = "table";
        String commandString = Command.TABLE_DATA + Command.SEPARATOR + tableName;
        command.setup(commandString);
        Mockito.when(store.getConnectToDataBase()).thenReturn(connection);
        Mockito.when(store.getTableData(tableName)).thenReturn(new TestTable().getTable());
        //when
        command.perform();
        //then
        verify(store, atLeastOnce()).getTableData(tableName);
    }
}
