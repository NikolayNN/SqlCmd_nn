package my.project.juja.model.table;

import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

import static org.junit.Assert.assertEquals;

/**
 * Created by Nikol on 9/27/2016.
 */
public class CellInfoTest {
    CellInfo testCellInfo;

    @Before
    public void setup() {
        testCellInfo = new CellInfo("TestColumnName", "integer", false, false, 0);
    }

    @Test
    public void getTypeTest() {
        assertEquals("integer", testCellInfo.getType());
    }

    @Test
    public void getLengthTest() {
        assertEquals(0, testCellInfo.getLength());
    }

    @Test
    public void isHasDefault() {
        assertEquals(false, testCellInfo.isHasDefault());
    }

}
