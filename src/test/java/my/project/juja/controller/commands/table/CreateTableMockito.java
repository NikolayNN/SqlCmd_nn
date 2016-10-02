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
import static org.mockito.Mockito.atLeast;
import static org.mockito.Mockito.verify;

/**
 * Created by Nikol on 9/26/2016.
 */
public class CreateTableMockito {
    private Storeable store;
    private View view;
    private Connection connection;
    Set<String> availableTables;


    @Before
    public void setup() {
        store = Mockito.mock(Storeable.class);
        view = Mockito.mock(View.class);
        connection = Mockito.mock(Connection.class);
    }

    @Test
    public void testColumnCharacter() {
        //given
        Command command = new CreateTable(store, view);
        String commandString = Command.CREATE_TABLE + Command.SEPARATOR + "tableName";
        command.setup(commandString);
        Mockito.when(store.getConnectToDataBase()).thenReturn(connection);
        Mockito.when(view.read()).thenReturn("name")
                .thenReturn("character")
                .thenReturn("")
                .thenReturn("n")
                .thenReturn("save");
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

    @Test
    public void testColumnNameWithSpace() {
        //given
        Command command = new CreateTable(store, view);
        String commandString = Command.CREATE_TABLE + Command.SEPARATOR + "tableName";
        command.setup(commandString);
        Mockito.when(store.getConnectToDataBase()).thenReturn(connection);
        Mockito.when(view.read())
                .thenReturn("name with space")
                .thenReturn("name")
                .thenReturn("character")
                .thenReturn("")
                .thenReturn("n")
                .thenReturn("save");
        //when
        command.perform();
        //then
        ArgumentCaptor<String> captor = ArgumentCaptor.forClass(String.class);
        verify(view, atLeast(2)).writeln(captor.capture());
        assertEquals("input column name:\n" +
                "ERROR. you can't use spaces here\n" +
                "input column name:\n" +
                "choose column type:\n" +
                "[text, character, integer, real]\n" +
                "input length or press 'enter' to set length = 256\n" +
                "Column can be null?(Y/N)\n" +
                "input 'save' to save the table, input 'add' to add one more column, input 'cancel' to cancel\n" +
                "table 'tableName' added\n" +
                "[[name*(character)]]\n", TestUtils.getString(captor));
    }

    @Test
    public void testCancelCreateTable() {
        //given
        Command command = new CreateTable(store, view);
        String commandString = Command.CREATE_TABLE + Command.SEPARATOR + "tableName";
        command.setup(commandString);
        Mockito.when(store.getConnectToDataBase()).thenReturn(connection);
        Mockito.when(view.read()).thenReturn("name")
                .thenReturn("character")
                .thenReturn("")
                .thenReturn("n")
                .thenReturn("cancel");
        //when
        String errorMessage = "";
        try {
            command.perform();
        } catch (RuntimeException ex) {
            errorMessage = ex.getMessage();
        }
        //then
        assertEquals("create table cancelled", errorMessage);
    }

    @Test
    public void testCreateTableWithTwoColumns() {
        //given
        Command command = new CreateTable(store, view);
        String commandString = Command.CREATE_TABLE + Command.SEPARATOR + "tableName";
        command.setup(commandString);
        Mockito.when(store.getConnectToDataBase()).thenReturn(connection);
        Mockito.when(view.read()).thenReturn("name")
                .thenReturn("character")
                .thenReturn("")
                .thenReturn("n")
                .thenReturn("add")
                .thenReturn("name")
                .thenReturn("character")
                .thenReturn("")
                .thenReturn("n")
                .thenReturn("save");
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
                "input column name:\n" +
                "choose column type:\n" +
                "[text, character, integer, real]\n" +
                "input length or press 'enter' to set length = 256\n" +
                "Column can be null?(Y/N)\n" +
                "input 'save' to save the table, input 'add' to add one more column, input 'cancel' to cancel\n" +
                "table 'tableName' added\n" +
                "[[name*(character)], [name*(character)]]\n", TestUtils.getString(captor));
    }

    @Test
    public void testCreateTableWrongConfirmThenSave() {
        //given
        Command command = new CreateTable(store, view);
        String commandString = Command.CREATE_TABLE + Command.SEPARATOR + "tableName";
        command.setup(commandString);
        Mockito.when(store.getConnectToDataBase()).thenReturn(connection);
        Mockito.when(view.read()).thenReturn("name")
                .thenReturn("character")
                .thenReturn("")
                .thenReturn("n")
                .thenReturn("wrongConfirm")
                .thenReturn("save");
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
                "You input wrong command 'wrongConfirm'. Available comands: 'save', 'add', 'cancel' \n" +
                "input 'save' to save the table, input 'add' to add one more column, input 'cancel' to cancel\n" +
                "table 'tableName' added\n" +
                "[[name*(character)]]\n", TestUtils.getString(captor));
    }

    @Test
    public void testInputWrongType() {
        //given
        Command command = new CreateTable(store, view);
        String commandString = Command.CREATE_TABLE + Command.SEPARATOR + "tableName";
        command.setup(commandString);
        Mockito.when(store.getConnectToDataBase()).thenReturn(connection);
        Mockito.when(view.read()).thenReturn("name")
                .thenReturn("wrongType")
                .thenReturn("character")
                .thenReturn("")
                .thenReturn("n")
                .thenReturn("save");
        //when
        command.perform();
        //then
        ArgumentCaptor<String> captor = ArgumentCaptor.forClass(String.class);
        verify(view, atLeast(2)).writeln(captor.capture());
        assertEquals("input column name:\n" +
                "choose column type:\n" +
                "[text, character, integer, real]\n" +
                "you input wrong type.\n" +
                "choose column type:\n" +
                "[text, character, integer, real]\n" +
                "input length or press 'enter' to set length = 256\n" +
                "Column can be null?(Y/N)\n" +
                "input 'save' to save the table, input 'add' to add one more column, input 'cancel' to cancel\n" +
                "table 'tableName' added\n" +
                "[[name*(character)]]\n", TestUtils.getString(captor));
    }

    @Test
    public void testAddNullableColumn() {
        //given
        Command command = new CreateTable(store, view);
        String commandString = Command.CREATE_TABLE + Command.SEPARATOR + "tableName";
        command.setup(commandString);
        Mockito.when(store.getConnectToDataBase()).thenReturn(connection);
        Mockito.when(view.read())
                .thenReturn("name")
                .thenReturn("character")
                .thenReturn("")
                .thenReturn("y")
                .thenReturn("save");
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
                "[[name(character)]]\n", TestUtils.getString(captor));
    }

    @Test
    public void testColumnCharacterWithNotDefaultLength() {
        //given
        Command command = new CreateTable(store, view);
        String commandString = Command.CREATE_TABLE + Command.SEPARATOR + "tableName";
        command.setup(commandString);
        Mockito.when(store.getConnectToDataBase()).thenReturn(connection);
        Mockito.when(view.read())
                .thenReturn("name")
                .thenReturn("character")
                .thenReturn("30")
                .thenReturn("n")
                .thenReturn("save");
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

    @Test
    public void testColumnCharacterWithNotDefaultLengthAndWithWrongValue() {
        //given
        Command command = new CreateTable(store, view);
        String commandString = Command.CREATE_TABLE + Command.SEPARATOR + "tableName";
        command.setup(commandString);
        Mockito.when(store.getConnectToDataBase()).thenReturn(connection);
        Mockito.when(view.read()).thenReturn("name")
                .thenReturn("character")
                .thenReturn("wrongValue")
                .thenReturn("30")
                .thenReturn("n")
                .thenReturn("save");
        //when
        command.perform();
        //then
        ArgumentCaptor<String> captor = ArgumentCaptor.forClass(String.class);
        verify(view, atLeast(2)).writeln(captor.capture());
        assertEquals("input column name:\n" +
                "choose column type:\n" +
                "[text, character, integer, real]\n" +
                "input length or press 'enter' to set length = 256\n" +
                "you inputed wrong number\n" +
                "input length or press 'enter' to set length = 256\n" +
                "Column can be null?(Y/N)\n" +
                "input 'save' to save the table, input 'add' to add one more column, input 'cancel' to cancel\n" +
                "table 'tableName' added\n" +
                "[[name*(character)]]\n", TestUtils.getString(captor));
    }

    @Test
    public void testCreateTableWrongConfirm() {
        //given
        Command command = new CreateTable(store, view);
        String commandString = Command.CREATE_TABLE + Command.SEPARATOR + "tableName";
        command.setup(commandString);
        Mockito.when(store.getConnectToDataBase()).thenReturn(connection);
        Mockito.when(view.read()).thenReturn("name")
                .thenReturn("character")
                .thenReturn("")
                .thenReturn("wrongConfirm")
                .thenReturn("y")
                .thenReturn("save");
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
                "you have to input 'Y' or 'N'\n" +
                "Column can be null?(Y/N)\n" +
                "input 'save' to save the table, input 'add' to add one more column, input 'cancel' to cancel\n" +
                "table 'tableName' added\n" +
                "[[name(character)]]\n", TestUtils.getString(captor));
    }

    @Test
    public void testCreateColumnInteger() {
        //given
        Command command = new CreateTable(store, view);
        String commandString = Command.CREATE_TABLE + Command.SEPARATOR + "tableName";
        command.setup(commandString);
        Mockito.when(store.getConnectToDataBase()).thenReturn(connection);
        Mockito.when(view.read()).thenReturn("name")
                .thenReturn("integer")
                .thenReturn("n")
                .thenReturn("save");
        //when
        command.perform();
        //then
        ArgumentCaptor<String> captor = ArgumentCaptor.forClass(String.class);
        verify(view, atLeast(2)).writeln(captor.capture());
        assertEquals("input column name:\n" +
                "choose column type:\n" +
                "[text, character, integer, real]\n" +
                "Column can be null?(Y/N)\n" +
                "input 'save' to save the table, input 'add' to add one more column, input 'cancel' to cancel\n" +
                "table 'tableName' added\n" +
                "[[name*(integer)]]\n", TestUtils.getString(captor));
    }

    @Test
    public void testGetName() {
        //given
        Command command = new CreateTable(store, view);
        //when
        String s = command.getName();
//        then
        assertEquals(Command.CREATE_TABLE, s);
    }
}
