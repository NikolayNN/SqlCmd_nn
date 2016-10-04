package my.project.juja.controller.commands.table;

import my.project.juja.controller.commands.Command;
import my.project.juja.model.Storeable;
import my.project.juja.utilsForTest.TestUtils;
import my.project.juja.view.View;
import org.junit.Before;
import org.junit.Test;
import org.mockito.ArgumentCaptor;
import org.mockito.Mockito;

import java.sql.Connection;
import java.util.HashSet;
import java.util.Set;

import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.*;

/**
 * Created by Nikol on 5/17/2016.
 */
public class ClearTableMockito {
    private Storeable store;
    private View view;
    private Connection connection;
    private String tableName = "table";
    private Set<String> availableTables;

    @Before
    public void setup() {
        store = Mockito.mock(Storeable.class);
        view = Mockito.mock(View.class);
        connection = Mockito.mock(Connection.class);
        availableTables = new HashSet<>();
        availableTables.add(tableName);
    }

    @Test
    public void clearTableWithValidParameter() {
        //given
        String commandString = Command.CLEAR_TABLE + Command.SEPARATOR + tableName;
        Command command = new ClearTable(store, view);
        command.setup(commandString);
        Mockito.when(store.getTableList()).thenReturn(availableTables);
        Mockito.when(store.getConnectToServer()).thenReturn(connection);
        Mockito.when(store.getConnectToDataBase()).thenReturn(connection);
        Mockito.when(view.read()).thenReturn("y");
        //when
        command.perform();
        //then
        ArgumentCaptor<String> captor = ArgumentCaptor.forClass(String.class);
        verify(view, atLeast(1)).writeln(captor.capture());
        assertEquals("Are you sure clear table 'table'? (Y/N)\n" +
                "table has been cleared\n", TestUtils.getString(captor));
    }

    @Test
    public void clearTableWithoutParameter() {
        //given
        String commandString = Command.CLEAR_TABLE;
        Command command = new ClearTable(store, view);
        command.setup(commandString);
        Mockito.when(store.getConnectToServer()).thenReturn(connection);
        Mockito.when(store.getConnectToDataBase()).thenReturn(connection);
        //when
        try {
            command.perform();
        } catch (RuntimeException ex) {
            //then
            assertEquals("ERROR. Wrong count parameters expected 1, but found 0", ex.getMessage());
        }
    }
}
