package tests.org.jfree.data;
import org.jfree.data.DataUtilities;
import org.jfree.data.Values2D;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mockito;
import java.util.Arrays;
import static org.junit.Assert.*;
import static org.mockito.Mockito.verify;

public class DataUtilitiesTest {
    double[][] x,y;
    Values2D values2D;

    @Before
    public void setUp(){
        x = new double[2][2];
        y = new double[2][2];
        values2D = Mockito.mock(Values2D.class);
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
        Mockito.when(values2D.getRowCount()).thenReturn(2);
        Mockito.when(values2D.getValue(0, 2)).thenReturn(2);
        Mockito.when(values2D.getValue(1, 2)).thenReturn(2);
        assertEquals(4.0,DataUtilities.calculateColumnTotal(values2D, 2), 1e-15);
        verify(values2D).getRowCount();
        verify(values2D).getValue(0, 2);
        verify(values2D).getValue(1, 2);
    }

    @Test
    public void calculateColumnTotal2_DoubleValueTest(){
        Mockito.when(values2D.getRowCount()).thenReturn(2);
        Mockito.when(values2D.getValue(0, 2)).thenReturn(2.2);
        Mockito.when(values2D.getValue(1, 2)).thenReturn(0.1);
        assertEquals(2.3,DataUtilities.calculateColumnTotal(values2D, 2), 1e-15);
    }

    @Test
    public void calculateColumnTotal2_DoubleAndIntValueTest(){
        Mockito.when(values2D.getRowCount()).thenReturn(3);
        Mockito.when(values2D.getValue(0, 2)).thenReturn(2);
        Mockito.when(values2D.getValue(1, 2)).thenReturn(3);
        Mockito.when(values2D.getValue(2, 2)).thenReturn(0.5);
        assertEquals(5.5, DataUtilities.calculateColumnTotal(values2D,2),1e-15);
    }

    @Test(expected = IllegalArgumentException.class)
    public void calculateColumnTotal2_NullValueTest(){
        Mockito.when(values2D.getRowCount()).thenReturn(2);
        Mockito.when(values2D.getValue(0, 2)).thenReturn(2);
        Mockito.when(values2D.getValue(1, 2)).thenReturn(3);
        double temp = DataUtilities.calculateColumnTotal(null, 2);
    }

    @Test
    public void calculateColumnTotal3_IntValueTest(){
        //Whole array
        int[] validRows = {0,1};
        Mockito.when(values2D.getRowCount()).thenReturn(4);
        Mockito.when(values2D.getValue(0, 4)).thenReturn(2);
        Mockito.when(values2D.getValue(1, 4)).thenReturn(3);
        assertEquals(5.0, DataUtilities.calculateColumnTotal(values2D,4,validRows), 1e-15);
    }

    @Test
    public void calculateColumnTotal3_DoubleValueTest(){
        //Random elements within the array
        int[] validRows = {0,2};
        Mockito.when(values2D.getRowCount()).thenReturn(4);
        Mockito.when(values2D.getValue(0, 4)).thenReturn(3.5);
        Mockito.when(values2D.getValue(2, 4)).thenReturn(0.1);
        assertEquals(3.6, DataUtilities.calculateColumnTotal(values2D, 4, validRows), 1e-15);
    }

    @Test
    public void calculateColumnTotal3_DoubleAndIntValueTest(){
        //End of array
        int[] validRows = {2,3};
        Mockito.when(values2D.getRowCount()).thenReturn(4);
        Mockito.when(values2D.getValue(2, 4)).thenReturn(3.5);
        Mockito.when(values2D.getValue(3, 4)).thenReturn(3);
        assertEquals(6.5, DataUtilities.calculateColumnTotal(values2D, 4, validRows), 1e-15);
    }

    @Test
    public void calculateColumnTotal3_OutOfBoundsArrayElementsTest(){
        int[] validRows = {4,5};
        Mockito.when(values2D.getRowCount()).thenReturn(2);
        Mockito.when(values2D.getValue(0, 2)).thenReturn(10);
        Mockito.when(values2D.getValue(1, 2)).thenReturn(5);
        assertEquals(0.0, DataUtilities.calculateColumnTotal(values2D, 2, validRows), 1e-15);
    }

    @Test(expected = IllegalArgumentException.class)
    public void calculateColumnTotal3_NullValueTest(){
        //Random elements of the array
        int[] validRows = {0,3};
        Mockito.when(values2D.getRowCount()).thenReturn(4);
        Mockito.when(values2D.getValue(0, 4)).thenReturn(10);
        Mockito.when(values2D.getValue(3, 4)).thenReturn(5);
        double temp = DataUtilities.calculateColumnTotal(null, 4);
    }

    @Test
    public void caculateRowTotal2_IntValueTest(){
        Mockito.when(values2D.getColumnCount()).thenReturn(2);
        Mockito.when(values2D.getValue(2, 0)).thenReturn(2);
        Mockito.when(values2D.getValue(2, 1)).thenReturn(2);
        assertEquals(4.0,DataUtilities.calculateRowTotal(values2D, 2), 1e-15);
        verify(values2D).getColumnCount();
        verify(values2D).getValue(2, 0);
        verify(values2D).getValue(2, 1);
    }

    @Test
    public void calculateRowTotal2_DoubleValueTest(){
        Mockito.when(values2D.getColumnCount()).thenReturn(2);
        Mockito.when(values2D.getValue(2, 0)).thenReturn(2.2);
        Mockito.when(values2D.getValue(2, 1)).thenReturn(0.1);
        assertEquals(2.3,DataUtilities.calculateRowTotal(values2D, 2), 1e-15);
    }

    @Test
    public void calculateRowTotal2_DoubleAndIntValueTest(){
        Mockito.when(values2D.getColumnCount()).thenReturn(3);
        Mockito.when(values2D.getValue(2, 0)).thenReturn(2);
        Mockito.when(values2D.getValue(2, 1)).thenReturn(3);
        Mockito.when(values2D.getValue(2, 2)).thenReturn(0.5);
        assertEquals(5.5, DataUtilities.calculateRowTotal(values2D,2),1e-15);
    }

    @Test(expected = IllegalArgumentException.class)
    public void calculateRowTotal2_NullValueTest(){
        Mockito.when(values2D.getColumnCount()).thenReturn(2);
        Mockito.when(values2D.getValue(2, 0)).thenReturn(2);
        Mockito.when(values2D.getValue(2, 1)).thenReturn(3);
        double temp = DataUtilities.calculateColumnTotal(null, 2);
    }

}

