package my.project.juja.controller.commands.database;

import my.project.juja.controller.commands.Command;
import my.project.juja.controller.commands.database.DataBasesNames;
import my.project.juja.controller.commands.table.ClearTable;
import my.project.juja.model.Storeable;
import my.project.juja.view.View;
import org.junit.Before;
import org.junit.Test;
import org.mockito.ArgumentCaptor;
import org.mockito.Mockito;

import java.sql.Connection;
import java.util.LinkedHashSet;
import java.util.Set;

import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.atLeast;
import static org.mockito.Mockito.verify;

/**
 * Created by Nikol on 9/26/2016.
 */
public class DataBasesNamesMockito {
    private Storeable store;
    private View view;
    private Connection connection;

    @Before
    public void setup() {
        store = Mockito.mock(Storeable.class);
        view = Mockito.mock(View.class);
        connection = Mockito.mock(Connection.class);
    }

    @Test
    public void dataBasesNamesNormal() {
        //given
        String commandString = Command.DATA_BASES_NAMES;
        Command command = new DataBasesNames(store, view);
        command.setup(commandString);
        Set<String> dataBasesNames = new LinkedHashSet<>();
        dataBasesNames.add("db1");
        dataBasesNames.add("db2");
        Mockito.when(store.getDataBasesNames()).thenReturn(dataBasesNames);
        //when
        command.perform();
        //then
        ArgumentCaptor<String> captor = ArgumentCaptor.forClass(String.class);
        verify(view, atLeast(1)).writeln(captor.capture());
        assertEquals("[[db1, db2]]", captor.getAllValues().toString());
    }

}
