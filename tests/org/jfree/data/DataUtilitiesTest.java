package tests.org.jfree.data;
import org.jfree.data.DataUtilities;
import org.jfree.data.Values2D;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mockito;
import java.util.Arrays;
import static org.junit.Assert.*;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;

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

    @Test
    public void cloneTest(){
        double[][] temp =  DataUtilities.clone(x);
        assertTrue(Arrays.deepEquals(temp, x));
    }

    @Test(expected = IllegalArgumentException.class)
    public void clone_NullValueTest(){
        double[][] temp = DataUtilities.clone(null);
    }

    @Test
    public void caculateColumnTotal2_IntValueTest(){
        Values2D values2D = Mockito.mock(Values2D.class);
        Mockito.when(values2D.getRowCount()).thenReturn(2);
        Mockito.when(values2D.getValue(0, 2)).thenReturn(2);
        Mockito.when(values2D.getValue(1, 2)).thenReturn(2);
        assertEquals(4.0,DataUtilities.calculateColumnTotal(values2D, 2), 0.0f);
        verify(values2D).getRowCount();
        verify(values2D).getValue(0, 2);
        verify(values2D).getValue(1, 2);
    }

    @Test
    public void calculateColumnTotal2_DoubleValueTest(){
        Values2D values2D = Mockito.mock(Values2D.class);
        Mockito.when(values2D.getRowCount()).thenReturn(2);
        Mockito.when(values2D.getValue(0, 2)).thenReturn(2.2);
        Mockito.when(values2D.getValue(1, 2)).thenReturn(0.1);
        assertEquals(2.3000000000000003,DataUtilities.calculateColumnTotal(values2D, 2), 0.0D);
    }

    @Test
    public void calculateColumnTotal2_DoubleAndIntValueTest(){
        Values2D values2D = Mockito.mock(Values2D.class);
        Mockito.when(values2D.getRowCount()).thenReturn(3);
        Mockito.when(values2D.getValue(0, 2)).thenReturn(2);
        Mockito.when(values2D.getValue(1, 2)).thenReturn(3);
        Mockito.when(values2D.getValue(2, 2)).thenReturn(0.5);
        assertEquals(5.50000000000000005, DataUtilities.calculateColumnTotal(values2D,2),0.0D);
    }

    @Test(expected = IllegalArgumentException.class)
    public void calculateColumnTotal2_NullValueTest(){
        Values2D values2D = Mockito.mock(Values2D.class);
        Mockito.when(values2D.getRowCount()).thenReturn(2);
        Mockito.when(values2D.getValue(0, 2)).thenReturn(2);
        Mockito.when(values2D.getValue(1, 2)).thenReturn(3);
        double temp = DataUtilities.calculateColumnTotal(null, 2);
    }


}
