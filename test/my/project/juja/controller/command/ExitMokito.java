package my.project.juja.controller.command;

import my.project.juja.controller.commands.Command;
import my.project.juja.controller.commands.Exit;
import my.project.juja.model.Storeable;
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
 * Created by Nikol on 5/3/2016.
 */
public class ExitMokito {
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
    public void test(){
        //given
        Command command = new Exit(store, view);
        Mockito.when(store.getConnection()).thenReturn(connection);
        //when
        command.perform();
        //then
        ArgumentCaptor<String> captor = ArgumentCaptor.forClass(String.class);
        verify(view, atLeast(2)).writeln(captor.capture());
        assertEquals("[Connection to data base was closed, Goodbye]", captor.getAllValues().toString());
    }
}
