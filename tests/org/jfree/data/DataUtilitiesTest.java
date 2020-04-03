package org.jfree.data;

import org.junit.Before;
import org.junit.Test;
import org.mockito.Mockito;
import java.util.Arrays;
import static org.junit.Assert.*;
import static org.mockito.Mockito.verify;

class DummyValues2D implements Values2D{
    double[][] dummy = {{1,2,3},{5,6,7}};

    @Override
    public int getRowCount() {
        return 2;
    }

    @Override
    public int getColumnCount() {
        return 3;
    }

    @Override
    public Number getValue(int i, int i1) {
        return dummy[i][i1];
    }
}

public class DataUtilitiesTest {
    double[][] x,y;
    Comparable a;
    Values2D values2D;
    KeyedValues keyedValues;
    DefaultKeyedValues defaultKeyedValues;

    @Before
    public void setUp(){
        x = new double[2][2];
        y = new double[2][2];
        values2D = Mockito.mock(Values2D.class);
        keyedValues = Mockito.mock(KeyedValues.class);
        defaultKeyedValues = Mockito.mock(DefaultKeyedValues.class);
    }

    @Test
    public void equal_Equivalent2DArraysTest(){
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
        x[0][0] = Double.NaN;
        y[0][0] = Double.NaN;
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
    public void cloneTest(){
        double[][] temp =  DataUtilities.clone(x);
        assertTrue(Arrays.deepEquals(temp, x));
        assertNotSame(temp, x);
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

    @Test
    public void calculateColumnTotal2_NegativeValueTest(){
        Mockito.when(values2D.getRowCount()).thenReturn(3);
        Mockito.when(values2D.getValue(0, 2)).thenReturn(2);
        Mockito.when(values2D.getValue(1, 2)).thenReturn(-3);
        Mockito.when(values2D.getValue(2, 2)).thenReturn(0.5);
        assertEquals(-0.5, DataUtilities.calculateColumnTotal(values2D,2),1e-15);
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

    @Test
    public void calculateColumnTotal3_RepeatedRowElementsTest(){
        int[] validRows = {5,5};
        Mockito.when(values2D.getRowCount()).thenReturn(6);
        Mockito.when(values2D.getValue(5, 6)).thenReturn(10);
        assertEquals(20.0, DataUtilities.calculateColumnTotal(values2D, 6, validRows), 1e-15);
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
    public void calculateColumnTotal3_NegativeIndexTest(){
        int[] validRows = {-3};
        Mockito.when(values2D.getRowCount()).thenReturn(4);
        assertEquals(0.0, DataUtilities.calculateColumnTotal(values2D, 4,validRows), 1e-15);
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
    public void calculateRowTotal2_NegativeValueTest(){
        Mockito.when(values2D.getColumnCount()).thenReturn(3);
        Mockito.when(values2D.getValue(3, 0)).thenReturn(2);
        Mockito.when(values2D.getValue(3, 1)).thenReturn(-3);
        Mockito.when(values2D.getValue(3, 2)).thenReturn(0.5);
        assertEquals(-0.5, DataUtilities.calculateRowTotal(values2D,3),1e-15);
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

    @Test
    public void calculateRow3_IntValueTest(){
        //Whole array
        int[] validColumns = {0,1};
        Mockito.when(values2D.getColumnCount()).thenReturn(4);
        Mockito.when(values2D.getValue(4, 0)).thenReturn(2);
        Mockito.when(values2D.getValue(4, 1)).thenReturn(3);
        assertEquals(5.0, DataUtilities.calculateRowTotal(values2D,4, validColumns), 1e-15);
    }

    @Test
    public void calculateRowTotal3_DoubleValueTest(){
        //Random elements within the array
        int[] validColumns = {0,2};
        Mockito.when(values2D.getColumnCount()).thenReturn(4);
        Mockito.when(values2D.getValue(4, 0)).thenReturn(3.5);
        Mockito.when(values2D.getValue(4, 2)).thenReturn(0.1);
        assertEquals(3.6, DataUtilities.calculateRowTotal(values2D, 4, validColumns), 1e-15);
    }

    @Test
    public void calculateRowTotal3_DoubleAndIntValueTest(){
        //End of array
        int[] validColumns = {2,3};
        Mockito.when(values2D.getColumnCount()).thenReturn(4);
        Mockito.when(values2D.getValue(4, 2)).thenReturn(3.5);
        Mockito.when(values2D.getValue(4, 3)).thenReturn(3);
        assertEquals(6.5, DataUtilities.calculateRowTotal(values2D, 4, validColumns), 1e-15);
    }

    @Test
    public void calculateRowTotal3_OutOfBoundsArrayElementsTest(){
        int[] validColumns = {4,5};
        Mockito.when(values2D.getColumnCount()).thenReturn(2);
        Mockito.when(values2D.getValue(2, 0)).thenReturn(10);
        Mockito.when(values2D.getValue(2, 1)).thenReturn(5);
        assertEquals(0.0, DataUtilities.calculateRowTotal(values2D, 2, validColumns), 1e-15);
    }

    @Test(expected = IllegalArgumentException.class)
    public void calculateRowTotal3_NullValueTest(){
        //Random elements of the array
        int[] validColumns = {0,3};
        Mockito.when(values2D.getRowCount()).thenReturn(4);
        Mockito.when(values2D.getValue(4, 0)).thenReturn(10);
        Mockito.when(values2D.getValue(4, 3)).thenReturn(5);
        double temp = DataUtilities.calculateRowTotal(null, 4);
    }

    @Test
    public void calculateRowTotal3_RepeatedIndexTest(){
        int[] validColumns = {3,3};
        Mockito.when(values2D.getColumnCount()).thenReturn(4);
        Mockito.when(values2D.getValue(4, 3)).thenReturn(10);
        assertEquals(20.0 , DataUtilities.calculateRowTotal(values2D, 4, validColumns) , 1e-15);
    }

    @Test
    public void calculateRowTotal3_NegativeIndexTest(){
        int[] validColumns = {-3};
        Mockito.when(values2D.getColumnCount()).thenReturn(4);
        assertEquals(0.0 , DataUtilities.calculateRowTotal(values2D, 4, validColumns) , 1e-15);
    }

    @Test
    public void createNumberArray_StartOfArrayTest(){
    double[] doubleArray = new double[]{1.0,2.5,3.1};
    Number[] temp = DataUtilities.createNumberArray(doubleArray);
    assertEquals(temp[0], doubleArray[0]);
    }

    @Test
    public void createNumberArray_EndOfArrayTest(){
        double[] doubleArray = new double[]{1.0,2.5,3.1};
        Number[] temp = DataUtilities.createNumberArray(doubleArray);
        assertEquals(temp[2], doubleArray[2]);
    }

    @Test
    public void createNumberArray_LengthOfArrayTest(){
        double[] doubleArray = new double[]{1.0,2.5,3.1,5,6};
        Number[] temp = DataUtilities.createNumberArray(doubleArray);
        assertEquals(temp.length, doubleArray.length);
    }

    @Test(expected = IllegalArgumentException.class)
    public void createNumberArray_NullValueTest(){
        Number[] temp = DataUtilities.createNumberArray(null);
    }

    @Test
    public void createNumberArray_EmptyArrayTest(){
        double[] doubleArray = new double[]{};
        assertEquals(0, DataUtilities.createNumberArray(doubleArray).length);
        assertEquals(doubleArray.length, DataUtilities.createNumberArray(doubleArray).length);
    }

    @Test
    public void createNumberArray2D_StartOfArrayTest(){
        double[][] doubleArray = new double[][]{{1.0,2.5},{3.1,5}};
        Number[][] temp = DataUtilities.createNumberArray2D(doubleArray);
        assertEquals(temp[0][0], doubleArray[0][0]);
    }

    @Test
    public void createNumberArray2D_EndOfArrayTest(){
        double[][] doubleArray = new double[][]{{1.0,2.5,7.9},{3.1,5,6.0},{4,7,9.9}};
        Number[][] temp = DataUtilities.createNumberArray2D(doubleArray);
        assertEquals(temp[2][2], doubleArray[2][2]);
    }

    @Test(expected = IndexOutOfBoundsException.class)
    public void createNumberArray2D_OutOfBoundsArrayTest(){
        double[][] doubleArray = new double[][]{{1.0,2.5,7.9},{3.1,5,6.0}};
        Number[][] temp = DataUtilities.createNumberArray2D(doubleArray);
        assertEquals(temp[3][3], doubleArray[3][3]);
    }

    @Test(expected = IllegalArgumentException.class)
    public void createNumberArray2D_NullValueTest(){
        Number[][] temp = DataUtilities.createNumberArray2D(null);
    }

    @Test
    public void createNumberArray2D_EmptyArrayTest(){
        double[][] doubleArray = new double[][]{};
        assertEquals(0, DataUtilities.createNumberArray2D(doubleArray).length);
        assertEquals(doubleArray.length, DataUtilities.createNumberArray2D(doubleArray).length);

    }

    @Test
    public void getCumulativePercentage_Test(){
        Mockito.when(keyedValues.getValue(0)).thenReturn(3);
        Mockito.when(keyedValues.getValue(1)).thenReturn(1);
        Mockito.when(keyedValues.getValue(2)).thenReturn(4);
        Mockito.when(keyedValues.getKey(0)).thenReturn(0);
        Mockito.when(keyedValues.getKey(1)).thenReturn(1);
        Mockito.when(keyedValues.getKey(2)).thenReturn(2);
        Mockito.when(keyedValues.getItemCount()).thenReturn(3);
        assertEquals(0.375, DataUtilities.getCumulativePercentages(keyedValues).getValue(0));
        assertEquals(0.5, DataUtilities.getCumulativePercentages(keyedValues).getValue(1));
        assertEquals(1.0, DataUtilities.getCumulativePercentages(keyedValues).getValue(2));
    }

    //// Integration tests
    @Test(expected = IndexOutOfBoundsException.class)
    public void calculateColumnTotal2_NegativeIndexTest(){
        DummyValues2D dummyValues2D = new DummyValues2D();
        DataUtilities.calculateColumnTotal(dummyValues2D, -2);
    }

    @Test(expected = IndexOutOfBoundsException.class)
    public void calculateColumnTotal2_OutOfBoundsTest(){
        DummyValues2D dummyValues2D = new DummyValues2D();
        DataUtilities.calculateColumnTotal(dummyValues2D, 6);
    }

    @Test
    public void calculateColumnTotal2_ValidTest(){
        DummyValues2D dummyValues2D = new DummyValues2D();
        assertEquals(6.0, DataUtilities.calculateColumnTotal(dummyValues2D, 0), 1e-15);
    }

    @Test
    public void calculateColumnTotal3_ValidTest(){
        DummyValues2D dummyValues2D = new DummyValues2D();
        int[] validRows = {0};
        assertEquals(1.0, DataUtilities.calculateColumnTotal(dummyValues2D, 0,validRows), 1e-15);
    }

    @Test(expected = IndexOutOfBoundsException.class)
    public void calculateRowTotal2_NegativeIndexTest(){
        DummyValues2D dummyValues2D = new DummyValues2D();
        DataUtilities.calculateRowTotal(dummyValues2D, -2);
    }

    @Test(expected = IndexOutOfBoundsException.class)
    public void calculateRowTotal2_OutOfBoundsTest(){
        DummyValues2D dummyValues2D = new DummyValues2D();
        DataUtilities.calculateRowTotal(dummyValues2D, 6);
    }

    @Test
    public void calculateRowTotal2_ValidTest(){
        DummyValues2D dummyValues2D = new DummyValues2D();
        assertEquals(6.0, DataUtilities.calculateRowTotal(dummyValues2D, 0), 1e-15);
    }

    @Test
    public void calculateRowTotal3_ValidTest(){
        DummyValues2D dummyValues2D = new DummyValues2D();
        int[] validColumn = {1};
        assertEquals(6.0, DataUtilities.calculateColumnTotal(dummyValues2D, 1,validColumn), 1e-15);
    }

    @Test
    public void getCumulativePercentage_ValidTest() {
        KeyedValues keyedValue;
        Comparable i = 1, j = 2, k = 3;
        double a = 5, b = 2, c = 3;
        DefaultKeyedValues defaultKeyedValues = new DefaultKeyedValues();
        defaultKeyedValues.addValue(i, a);
        defaultKeyedValues.addValue(j, b);
        defaultKeyedValues.addValue(k, c);

        keyedValue = DataUtilities.getCumulativePercentages(defaultKeyedValues);

        assertEquals(0.5, keyedValue.getValue(0));
        assertEquals(0.7, keyedValue.getValue(1));
        assertEquals(1.0, keyedValue.getValue(2));
    }
}

