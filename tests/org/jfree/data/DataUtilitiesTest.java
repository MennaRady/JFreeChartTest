package tests.org.jfree.data;
import org.jfree.data.DataUtilities;
import org.junit.Before;
import org.junit.Test;
import static org.junit.Assert.*;

public class DataUtilitiesTest {
    double[][] x,y;

    @Before
    public void setUp(){
        x = new double[2][2];
        y = new double[2][2];
    }

    @Test
    public void equal_SameDimensionsTest(){
        assertTrue(DataUtilities.equal(x, y));
    }

    @Test
    public void equal_DifferentDimensionsTest(){
        x = new double[3][2];
        y = new double[2][2];
        assertFalse(DataUtilities.equal(x, y));
    }

    @Test
    public void equal_SameDimensionsNaNTest(){
        x[0][0] = 0.0/0.0;
        y[0][0] = 0.0/0.0;
        assertTrue(DataUtilities.equal(x, y));

    }

    @Test
    public void equal_SameDimensionsPositiveINFTest(){
        x[0][0] = Double.POSITIVE_INFINITY;
        y[0][0] = Double.POSITIVE_INFINITY;
        assertTrue(DataUtilities.equal(x, y));
    }

    @Test
    public void equal_SameDimensionsNegativeINFTest(){
        x[0][0] = Double.NEGATIVE_INFINITY;
        y[0][0] = Double.NEGATIVE_INFINITY;
        assertTrue(DataUtilities.equal(x, y));
    }

    @Test
    public void equal_SameDimensionsNullValueTest(){
        assertFalse(DataUtilities.equal(x, null));
    }

    @Test
    public void equal_SameDimensionsDifferentValues(){
        x[0][0] = Double.NEGATIVE_INFINITY;
        y[0][0] = Double.POSITIVE_INFINITY;
        assertFalse(DataUtilities.equal(x, y));
    }

    @Test
    public void equal_SameDimensionsSameValues(){
        x[0][0] = 1.5;
        y[0][0] = 1.5;
        assertTrue(DataUtilities.equal(x, y));
    }


}
