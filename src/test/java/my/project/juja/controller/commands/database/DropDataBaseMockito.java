package my.project.juja.controller.commands.database;

import my.project.juja.controller.commands.Command;
import my.project.juja.controller.commands.utils.JujaUtilsTest;
import my.project.juja.model.Storeable;
import my.project.juja.utilsForTest.TestUtils;
import my.project.juja.view.View;
import org.junit.Before;
import org.junit.Test;
import org.mockito.ArgumentCaptor;
import org.mockito.Mockito;

import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.atLeast;
import static org.mockito.Mockito.verify;

/**
 * Created by Nikol on 9/26/2016.
 */
public class DropDataBaseMockito {
    private Storeable store;
    private View view;

    @Before
    public void setup() {
        store = Mockito.mock(Storeable.class);
        view = Mockito.mock(View.class);
    }

    @Test(expected = RuntimeException.class)
    public void testNormalConfirmN() {
        //given
        String commandString = Command.DROP_DATA_BASE + Command.SEPARATOR + "dbName";
        Command command = new DropDataBase(store, view);
        command.setup(commandString);
        Mockito.when(view.read()).thenReturn("n");
        //when
        command.perform();
    }

    @Test
    public void testNormalConfirmY() {
        //given
        String commandString = Command.DROP_DATA_BASE + Command.SEPARATOR + "dbName";
        Command command = new DropDataBase(store, view);
        command.setup(commandString);
        Mockito.when(view.read()).thenReturn("y");
        //when
        command.perform();
        //then
        ArgumentCaptor<String> captor = ArgumentCaptor.forClass(String.class);
        verify(view, atLeast(2)).writeln(captor.capture());
        assertEquals("Are you sure delete dbName? (Y/N)\n" +
                "data base 'dbName' deleted\n", TestUtils.getString(captor));
    }

    @Test
    public void testNormalConfirmWrongThenY() {
        //given
        String commandString = Command.DROP_DATA_BASE + Command.SEPARATOR + "dbName";
        Command command = new DropDataBase(store, view);
        command.setup(commandString);
        Mockito.when(view.read()).thenReturn("wrongConfirm").thenReturn("y");
        //when
        command.perform();
        //then
        ArgumentCaptor<String> captor = ArgumentCaptor.forClass(String.class);
        verify(view, atLeast(2)).writeln(captor.capture());
        assertEquals("Are you sure delete dbName? (Y/N)\n" +
                "wrong input\n" +
                "Are you sure delete dbName? (Y/N)\n" +
                "data base 'dbName' deleted\n", TestUtils.getString(captor));
    }
}
