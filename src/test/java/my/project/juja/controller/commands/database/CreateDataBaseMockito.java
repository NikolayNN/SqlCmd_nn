package my.project.juja.controller.commands.database;

import my.project.juja.controller.commands.Command;
import my.project.juja.controller.commands.database.ConnectDataBase;
import my.project.juja.controller.commands.database.CreateDataBase;
import my.project.juja.model.Storeable;
import my.project.juja.view.View;
import org.junit.Before;
import org.junit.Test;
import org.mockito.ArgumentCaptor;
import org.mockito.Mockito;

import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.verify;

/**
 * Created by Nikol on 9/25/2016.
 */
public class CreateDataBaseMockito {
    private Storeable store;
    private View view;

    @Before
    public void setup(){
        store = Mockito.mock(Storeable.class);
        view = Mockito.mock(View.class);
    }

    @Test
    public void test(){
        //given
        String commandString = Command.CREATE_DATA_BASE + Command.SEPARATOR + "dbName";
        Command command =new CreateDataBase(store, view);
        command.setup(commandString);
        //when
        command.perform();

        //then
        ArgumentCaptor<String> captor = ArgumentCaptor.forClass(String.class);
        verify(view).writeln(captor.capture());
        assertEquals("[data base 'dbName' created.]", captor.getAllValues().toString());
    }
}
