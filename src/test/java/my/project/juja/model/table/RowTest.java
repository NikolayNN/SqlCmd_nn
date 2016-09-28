package my.project.juja.model.table;

import my.project.juja.utilsForTest.TestTable;
import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.assertEquals;

/**
 * Created by Nikol on 9/27/2016.
 */
public class RowTest {
    Row testRow;

    @Before
    public void setup(){
        TestTable testTable = new TestTable();
        testRow = testTable.getTable().getRow(0);
    }

    @Test
    public  void testTostring(){
        assertEquals("1 | Vasya | Pupkin | qwerty | ", testRow.toString());
    }
}
