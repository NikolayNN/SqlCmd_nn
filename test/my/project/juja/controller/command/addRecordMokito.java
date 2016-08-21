//package my.project.juja.controller.command;
//
//import my.project.juja.controller.commands.AddRecord;
//import my.project.juja.controller.commands.Command;
//import my.project.juja.model.Storeable;
//import my.project.juja.model.Table;
//import my.project.juja.view.View;
//import org.junit.Before;
//import org.junit.Test;
//import org.mockito.ArgumentCaptor;
//import org.mockito.Mockito;
//
//import java.util.ArrayList;
//import java.util.List;
//
//import static org.junit.Assert.assertEquals;
//import static org.mockito.Mockito.*;
//
///**
// * Created by Nikol on 5/15/2016.
// */
//public class addRecordMokito {
//    private Storeable store;
//    private View view;
//    private Table table;
//
//    @Before
//    public void setup(){
//        store = mock(Storeable.class);
//        view = mock(View.class);
//        table = mock(Table.class);
//
//    }
//
//    @Test
//    public void test(){
//        //given
//        List<String> columnNames = new ArrayList<>();
//        String columnToEdit = "0 1 2";
//        columnNames.add("id");
//        columnNames.add("name");
//        columnNames.add("password");
//        Command command = new AddRecord(store,view);
//        command.setup("add-record tableName");
//        when(table.getColumnNames()).thenReturn(columnNames);
//        when(view.read()).thenReturn(columnToEdit);
//        when(table.setColumnsToEditIdx(anyString()))
//
//        //when
//        command.perform();
//
//        //then
//
//        ArgumentCaptor<String> captor = ArgumentCaptor.forClass(String.class);
//        verify(view, atLeast(7)).writeln(captor.capture());
//        assertEquals("[[TestTable1, TestTable2]]", captor.getAllValues().toString());
//    }
//
//}
