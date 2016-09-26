package my.project.juja.controller.commands.table;

import my.project.juja.controller.commands.Command;
import my.project.juja.model.Storeable;
import my.project.juja.utils.TestUtils;
import my.project.juja.view.View;
import org.junit.Before;
import org.junit.Test;
import org.mockito.ArgumentCaptor;
import org.mockito.Mockito;

import java.sql.Connection;

import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.atLeast;
import static org.mockito.Mockito.verify;

/**
 * Created by Nikol on 9/26/2016.
 */
public class DeleteRecordMockito {
    private Storeable store;
    private View view;
    private Connection connection;


    @Before
    public void setup(){
        store = Mockito.mock(Storeable.class);
        view = Mockito.mock(View.class);
        connection = Mockito.mock(Connection.class);

    }

    @Test
    public void testColumnCharacter(){
        //given
        Command command = new DeleteRecord(store, view);
        String commandString = Command.DELETE_RECORD + Command.SEPARATOR + "tableName";
        command.setup(commandString);
        Mockito.when(store.getConnectToDataBase()).thenReturn(connection);
        Mockito.when(view.read()).thenReturn("name").thenReturn("character").thenReturn("").thenReturn("n").thenReturn("save");
        //when
        command.perform();
        //then
        ArgumentCaptor<String> captor = ArgumentCaptor.forClass(String.class);
        verify(view, atLeast(2)).writeln(captor.capture());
        assertEquals("input column name:\n" +
                "choose column type:\n" +
                "[text, character, integer, real]\n" +
                "input length or press 'enter' to set length = 256\n" +
                "Column can be null?(Y/N)\n" +
                "input 'save' to save the table, input 'add' to add one more column, input 'cancel' to cancel\n" +
                "table 'tableName' added\n" +
                "[[name*(character)]]\n", TestUtils.getString(captor));
    }
}
