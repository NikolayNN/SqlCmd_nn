package my.project.juja.controller.commands;

import my.project.juja.controller.commands.table.AddRecord;
import my.project.juja.model.Storeable;
import my.project.juja.model.table.Table;
import my.project.juja.utils.TestTable;
import my.project.juja.utils.WhereConstructor;
import my.project.juja.view.View;
import org.junit.Before;
import org.junit.Test;
import org.mockito.ArgumentCaptor;
import org.mockito.Mockito;

import java.sql.Connection;
import java.sql.SQLException;

import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.atLeast;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

/**
 * Created by Nikol on 9/25/2016.
 */
public class UpdateRecordMockitoNem {
    private Storeable store;
    private View view;
    private Connection connection;
    private WhereConstructor whereConstructor;
    private Table table;

    @Before
    public void setup(){
        store = Mockito.mock(Storeable.class);
        view = Mockito.mock(View.class);
        connection = Mockito.mock(Connection.class);
        whereConstructor = Mockito.mock(WhereConstructor.class);
        table = new TestTable().getTable();
    }

    @Test
    public void testNormal(){
        //given
        Command command = new AddRecord(store, view);
        String commandString = Command.UPDATE_TABLE + Command.SEPARATOR + table.getTableName();
        command.setup(commandString);
        Mockito.when(store.getConnectToDataBase()).thenReturn(connection);
        Mockito.when(whereConstructor.toString()).thenReturn("name='Pasha'");
        Mockito.when(store.getColumnInformation(table.getTableName())).thenReturn(table.getCellInfos());
        Mockito.when(view.read()).thenReturn("123").thenReturn("TestFName").thenReturn("TestLName").thenReturn("TestPassword");
        //when
        command.perform();
        //then
        ArgumentCaptor<String> captor = ArgumentCaptor.forClass(String.class);
        verify(view, atLeast(5)).writeln(captor.capture());
        assertEquals("[input value for the column [id*(integer)] or just press 'enter' to skip input, input value for the column [firstName*(text)] or just press 'enter' to skip input, input value for the column [lastName(text)] or just press 'enter' to skip input, input value for the column [password*(character)] or just press 'enter' to skip input, successful added, testTable174856\n" +
                "---------------------------------------------\n" +
                "id  | firstName | lastName  | password     | \n" +
                "---------------------------------------------\n" +
                "123 | TestFName | TestLName | TestPassword | \n" +
                "]", captor.getAllValues().toString());
    }

    @Test
    public void testWhenInputNullForNotNullColumn(){
        //given
        Command command = new AddRecord(store, view);
        String commandString = Command.UPDATE_TABLE + Command.SEPARATOR + table.getTableName();
        command.setup(commandString);
        Mockito.when(store.getConnectToDataBase()).thenReturn(connection);
        Mockito.when(whereConstructor.toString()).thenReturn("name='Pasha'");
        Mockito.when(store.getColumnInformation(table.getTableName())).thenReturn(table.getCellInfos());
        Mockito.when(view.read()).thenReturn("").thenReturn("123").thenReturn("TestFName").thenReturn("TestLName").thenReturn("TestPassword");
        //when
        command.perform();
        //then
        ArgumentCaptor<String> captor = ArgumentCaptor.forClass(String.class);
        verify(view, atLeast(5)).writeln(captor.capture());
        assertEquals("[input value for the column [id*(integer)] or just press 'enter' to skip input, column \"id\" can't be null, input value for the column [id*(integer)] or just press 'enter' to skip input, input value for the column [firstName*(text)] or just press 'enter' to skip input, input value for the column [lastName(text)] or just press 'enter' to skip input, input value for the column [password*(character)] or just press 'enter' to skip input, successful added, testTable174856\n" +
                "---------------------------------------------\n" +
                "id  | firstName | lastName  | password     | \n" +
                "---------------------------------------------\n" +
                "123 | TestFName | TestLName | TestPassword | \n" +
                "]", captor.getAllValues().toString());
    }

//    @Test
//    public void testinputWrongTableName() throws SQLException {
//        //given
//        Command command = new AddRecord(store, view);
//        String commandString = Command.UPDATE_TABLE + Command.SEPARATOR + table.getTableName();
//        command.setup(commandString);
//        Mockito.when(store.getConnectToDataBase()).thenReturn(connection);
//        Mockito.when(whereConstructor.toString()).thenReturn("name='Pasha'");
//        Mockito.when(store.getColumnInformation(table.getTableName())).thenReturn(table.getCellInfos());
//        Mockito.when(view.read()).thenReturn("123").thenReturn("TestFName").thenReturn("TestLName").thenReturn("TestPassword");
//
//
//        //when
//        command.perform();
//        //then
//        ArgumentCaptor<String> captor = ArgumentCaptor.forClass(String.class);
//        verify(view, atLeast(5)).writeln(captor.capture());
//
//
//    }
}
