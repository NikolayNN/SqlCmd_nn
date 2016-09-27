package my.project.juja.model.table;

import org.junit.Test;

import static org.junit.Assert.assertEquals;

/**
 * Created by Nikol on 9/27/2016.
 */
public class CellTest {


    @Test
    public void test() {
//        given
        CellInfo cellInfo = new CellInfo("columnName", "character", false, false, 0);
        String value = "columnValue";
//when
        Cell cell = new Cell(cellInfo, value);
//then
        assertEquals("[columnName*(character)]", cell.getCellInfo().toString());
    }
}
