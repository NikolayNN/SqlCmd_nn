package my.project.juja.controller.commands.utils;

import my.project.juja.model.table.Table;
import my.project.juja.utils.TestTable;
import my.project.juja.utils.WhereConstructor;
import my.project.juja.view.View;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mockito;

import static org.junit.Assert.assertEquals;

/**
 * Created by Nikol on 9/26/2016.
 */
public class WhereConstructorMockito {
    private View view;
    private Table table;

    @Before
    public void setup() {
        view = Mockito.mock(View.class);
        table = new TestTable().getTable();
    }

    @Test
    public void testCreateConditionUseConstructor() {
        WhereConstructor whereConstructor = new WhereConstructor(view, table.getCellInfos());

        Mockito.when(view.read()).thenReturn("y")
                .thenReturn("firstName")
                .thenReturn("=")
                .thenReturn("value")
                .thenReturn("n");
        whereConstructor.create();
        assertEquals("firstName='value'", whereConstructor.toString());
    }

    @Test
    public void testCreateConditionDoNotUseConstructor() {
        WhereConstructor whereConstructor = new WhereConstructor(view, table.getCellInfos());
        Mockito.when(view.read()).thenReturn("n")
                .thenReturn("colName=value");
        whereConstructor.create();
        assertEquals("colName=value", whereConstructor.toString());
    }

    @Test
    public void testCreateTwoConditionsWithConstructor() {
        WhereConstructor whereConstructor = new WhereConstructor(view, table.getCellInfos());
        Mockito.when(view.read()).thenReturn("y")
                .thenReturn("firstName")
                .thenReturn("=")
                .thenReturn("value1")
                .thenReturn("y")
                .thenReturn("or")
                .thenReturn("id")
                .thenReturn(">=")
                .thenReturn("1")
                .thenReturn("n");
        whereConstructor.create();
        assertEquals("firstName='value1' or id>='1'", whereConstructor.toString());
    }

    @Test
    public void testInputWrongColumnName() {
        WhereConstructor whereConstructor = new WhereConstructor(view, table.getCellInfos());
        Mockito.when(view.read()).thenReturn("y")
                .thenReturn("wrongColumnName")
                .thenReturn("firstName")
                .thenReturn("=")
                .thenReturn("value1")
                .thenReturn("n");

        whereConstructor.create();
        assertEquals("firstName='value1'", whereConstructor.toString());
    }

    @Test
    public void testWrongSymbol() {
        WhereConstructor whereConstructor = new WhereConstructor(view, table.getCellInfos());

        Mockito.when(view.read()).thenReturn("y")
                .thenReturn("firstName")
                .thenReturn("wrongSymbol")
                .thenReturn("=")
                .thenReturn("value")
                .thenReturn("n");
        whereConstructor.create();
        assertEquals("firstName='value'", whereConstructor.toString());
    }

    @Test
    public void testCreateTwoConditionsWithWrongCondition() {
        WhereConstructor whereConstructor = new WhereConstructor(view, table.getCellInfos());
        Mockito.when(view.read()).thenReturn("y")
                .thenReturn("firstName")
                .thenReturn("=")
                .thenReturn("value1")
                .thenReturn("y")
                .thenReturn("wrongCondition")
                .thenReturn("or")
                .thenReturn("id")
                .thenReturn(">=")
                .thenReturn("1")
                .thenReturn("n");
        whereConstructor.create();
        assertEquals("firstName='value1' or id>='1'", whereConstructor.toString());
    }
}
