package my.project.juja.controller.command;

import my.project.juja.controller.commands.ClearTable;
import my.project.juja.controller.commands.Command;
import my.project.juja.controller.commands.Exit;
import my.project.juja.model.Storeable;
import my.project.juja.view.View;
import org.junit.Before;
import org.junit.Test;
import org.junit.rules.ExpectedException;
import org.mockito.ArgumentCaptor;
import org.mockito.Mockito;

import java.sql.Connection;

import static org.junit.Assert.assertEquals;
import static org.mockito.Matchers.anyInt;
import static org.mockito.Matchers.anyString;
import static org.mockito.Mockito.*;

/**
 * Created by Nikol on 5/17/2016.
 */
public class ClearTableMockito {
    private Storeable store;
    private View view;
    private Connection connection;

    @Before
    public void setup(){
        store = Mockito.mock(Storeable.class);
        view = Mockito.mock(View.class);
    }

    @Test
    public void clearTableWithValidParameter(){
        //given
        String tableName = "table";
        String commandString = Command.CLEAR_TABLE + Command.SEPARATOR + tableName;
        Command command = new ClearTable(store, view);
        command.setup(commandString);
        //when
        command.perform();
        //then
        ArgumentCaptor<String> captor = ArgumentCaptor.forClass(String.class);
        verify(view, atLeast(1)).writeln(captor.capture());
        assertEquals("[table has been cleared]", captor.getAllValues().toString());
    }

    @Test(expected = RuntimeException.class)
    public void clearTableWithoutParameter(){
        //given
        String commandString = Command.CLEAR_TABLE;
        String[] anyArrayString ={anyString()};
        Command command = new ClearTable(store, view);
        command.setup(commandString);
        //when
        try {
            command.perform();
        }catch (RuntimeException ex){
            //then
            assertEquals("Wrong count parameters expected 1, but found 0",ex.getMessage());
            throw ex;
        }
    }
}
