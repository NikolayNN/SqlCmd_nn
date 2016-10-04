package my.project.juja.controller.commands.table;

import my.project.juja.controller.commands.Command;
import my.project.juja.model.Storeable;
import my.project.juja.model.table.Table;
import my.project.juja.utilsForTest.TestTable;
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
 * Created by Nikol on 9/25/2016.
 */
public class AddRecordMockito {
    private Storeable store;
    private View view;
    private Connection connection;
    private Table table;

    @Before
    public void setup(){
        store = Mockito.mock(Storeable.class);
        view = Mockito.mock(View.class);
        connection = Mockito.mock(Connection.class);
        table = new TestTable().getTable();
    }

    @Test
    public void testNormal(){
        //given
        Table testTable = table;
        Set<String> availableTables = new HashSet<>();
        availableTables.add(testTable.getTableName());
        Command command = new AddRecord(store, view);
        String commandString = Command.ADD_RECORD + Command.SEPARATOR + table.getTableName();
        command.setup(commandString);
        Mockito.when(store.getConnectToDataBase()).thenReturn(connection);
        Mockito.when(store.getConnectToServer()).thenReturn(connection);
        Mockito.when(store.getTableList()).thenReturn(availableTables);
        Mockito.when(store.getColumnInformation(table.getTableName())).thenReturn(testTable.getTableHeader());
        Mockito.when(view.read()).thenReturn("123")
                .thenReturn("TestFName")
                .thenReturn("TestLName")
                .thenReturn("TestPassword");
        //when
        command.perform();
        //then
        ArgumentCaptor<String> captor = ArgumentCaptor.forClass(String.class);
        verify(view, atLeast(5)).writeln(captor.capture());
        assertEquals("input value for the column [id*(integer)] or just press 'enter' to skip input\n" +
                "input value for the column [firstName*(text)] or just press 'enter' to skip input\n" +
                "input value for the column [lastName(text)] or just press 'enter' to skip input\n" +
                "input value for the column [password*(character)] or just press 'enter' to skip input\n" +
                "successful added\n" +
                "testTable174856\n" +
                "---------------------------------------------\n" +
                "id  | firstName | lastName  | password     | \n" +
                "---------------------------------------------\n" +
                "123 | TestFName | TestLName | TestPassword | \n" +
                "\n", TestUtils.getString(captor));
    }

}
