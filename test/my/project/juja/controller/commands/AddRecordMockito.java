package my.project.juja.controller.commands;

import junit.framework.TestCase;
import my.project.juja.controller.commands.table.CellInfo;
import my.project.juja.model.Storeable;
import my.project.juja.view.View;
import org.junit.Before;
import org.junit.Test;
import org.mockito.ArgumentCaptor;
import org.mockito.Mockito;

import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.atLeast;
import static org.mockito.Mockito.verify;

/**
 * Created by Nikol on 9/11/2016.
 */
public class AddRecordMockito{
    private Storeable store;
    private View view;

    @Before
    public void setup(){
        store = Mockito.mock(Storeable.class);
        view = Mockito.mock(View.class);
    }

    @Test
    public void perform() throws Exception {
        //given
        String tableName = "users";
        Command command = new AddRecord(store, view);
        String commandString = Command.ADD_RECORD + Command.SEPARATOR + tableName;
        command.setup(commandString);
        CellInfo cellInfoId = new CellInfo("id", "integer", false, true, 0);
        CellInfo cellInfoName = new CellInfo("name", "text", true, true, 1);
        CellInfo cellInfoPass = new CellInfo("password", "character", true, true, 2);
        List<CellInfo> cellInfos = new ArrayList<>();
        cellInfos.add(cellInfoId);
        cellInfos.add(cellInfoName);
        cellInfos.add(cellInfoPass);
        Mockito.when(store.getColumnInformation(tableName)).thenReturn(cellInfos);
        Mockito.when(view.read()).thenReturn("1").thenReturn("Alex").thenReturn("111");

        //when
        command.perform();

        //then
        ArgumentCaptor<String> captor = ArgumentCaptor.forClass(String.class);
        verify(view, atLeast(5)).writeln(captor.capture());
        assertEquals("[input value for [id(integer)], input value for [name(text)], input value for [password(character)], successful added, users\n" +
                "----------------------------------------------\n" +
                "id | name | password | \n" +
                "----------------------------------------------\n" +
                "1 | Alex | 111 | \n" +
                "]", captor.getAllValues().toString());


    }
}