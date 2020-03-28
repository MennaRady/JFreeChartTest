package org.jfree.data;

import org.jfree.data.general.SeriesException;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mockito;

import static org.junit.Assert.*;
import static org.mockito.Mockito.when;

public class ComparableObjectSeriesTest {
    ComparableObjectSeries series;
    Integer key;

    @Before
    public void setUp(){
        key = 1;
        series = new ComparableObjectSeries(key);
    }

    @Test
    /// Passing a non-existing value
    public void IndexOf_GettingIndexOfNonExistingItem_NegativeValue(){
        series = new ComparableObjectSeries(key, true, false);
        series.add(0, null);
        series.add(2, null);

        int actual = series.indexOf(1);

        assertTrue(actual < 0);
    }

    @Test
    /// Passing a value at the end of a sorted list
    public void IndexOf_IndexOfItemInSortedSeries_GetIndex(){
        series = new ComparableObjectSeries(key, true, false);
        series.add(0, null);
        series.add(2, null);
        series.add(4, null);

        int actual = series.indexOf(4);

        assertEquals(2, actual);
    }

    @Test
    /// Passing a value at the beginning of an unsorted list
    public void IndexOf_IndexOfItemInUnsortedSeries_GetIndex(){
        ComparableObjectItem item = Mockito.mock(ComparableObjectItem.class);
        when(item.getComparable()).thenReturn(4);
        series = new ComparableObjectSeries(key, false, false);
        series.add(4, null);
        series.add(0, null);
        series.add(2, null);

        int actual = series.indexOf(4);

        assertEquals(0, actual);
    }

    @Test(expected = SeriesException.class)
    public void Update_UpdatingNonExistingItem_ExceptionThrown(){
        series = new ComparableObjectSeries(key);
        series.add(4, null);

        series.update(2, null);
    }

    @Test
    /// This test is to make sure that the start index of the data is deleted
    public void Delete_DeletingItemsFromStartToEndIndex_StartDeleted(){
        series = new ComparableObjectSeries(key);
        series.add(4, null);
        series.add(0, null);
        series.add(2, null);

        int start = 0;
        int end = 2;

        series.delete(start, end);
        int actual = series.indexOf(4);
        assertTrue(actual < 0);
    }

    @Test
    /// This test is to make sure that the end index of the data is deleted
    public void Delete_DeletingItemsFromStartToEndIndex_EndDeleted(){
        series = new ComparableObjectSeries(key);
        series.add(4, null);
        series.add(0, null);
        series.add(2, null);

        int start = 0;
        int end = 2;

        series.delete(start, end);
        int actual = series.indexOf(2);
        assertTrue(actual < 0);
    }

    @Test
    /// This test is to make sure that the all the required data is deleted
    public void Delete_DeletingItemsFromStartToEndIndex_AllDeleted(){
        series = new ComparableObjectSeries(key);
        series.add(4, null);
        series.add(0, null);
        series.add(2, null);

        int start = 0;
        int end = 2;
        int actual_size = series.data.size() - (end - start) - 1;

        series.delete(start, end);
        assertEquals(actual_size, series.data.size());
    }

    @Test(expected = IndexOutOfBoundsException.class)
    public void Delete_IndexExceedingTheSize_ExceptionThrown(){
        series = new ComparableObjectSeries(key);
        series.add(4, null);
        series.add(0, null);
        series.add(2, null);

        int start = 0;
        int end = 3;

        series.delete(start, end);
    }

    @Test(expected = IndexOutOfBoundsException.class)
    public void Delete_NegativeIndex_ExceptionThrown(){
        series = new ComparableObjectSeries(key);
        series.add(4, null);
        series.add(0, null);
        series.add(2, null);

        int start = -1;
        int end = 2;

        series.delete(start, end);
    }

    @Test(expected = IndexOutOfBoundsException.class)
    public void Delete_DeletingFromEmptySeries_ExceptionThrown(){
        series = new ComparableObjectSeries(key);
        int start = 0;
        int end = 3;

        series.delete(start, end);
    }

    @Test
    /// Passing same/identical series
    public void Equals_SameSeries_True(){
        series = new ComparableObjectSeries(key);

        assertTrue(series.equals(series));
    }

    @Test
    /// Passing object of different instance.
    public void Equals_DifferentInstance_False(){
        ComparableObjectItem Item = new ComparableObjectItem(key, new Object());
        series = new ComparableObjectSeries(key);

        assertFalse(series.equals(Item));
    }

    @Test
    /// Passing a series with different data size.
    public void Equals_DifferentDataSize_False(){
        ComparableObjectSeries series1 = new ComparableObjectSeries(key);
        ComparableObjectSeries series2 = new ComparableObjectSeries(key);
        series1.add(1, null);
        series1.add(2, null);
        series2.add(1, null);

        assertFalse(series1.equals(series2));
    }

    @Test
    /// Passing a series with different data.
    public void Equals_DifferentData_False(){
        ComparableObjectSeries series1 = new ComparableObjectSeries(key);
        ComparableObjectSeries series2 = new ComparableObjectSeries(key);
        series1.add(1, null);
        series2.add(2, null);

        assertFalse(series1.equals(series2));
    }

    @Test
    /// Passing a series with different key.
    public void Equals_DifferentKey_False(){
        ComparableObjectSeries series1 = new ComparableObjectSeries(1);
        ComparableObjectSeries series2 = new ComparableObjectSeries(2);
        series1.add(1, null);
        series2.add(1, null);

        assertFalse(series1.equals(series2));
    }

    @Test
    /// Passing a series with different key.
    public void Equals_DifferentDescription_False(){
        ComparableObjectSeries series1 = new ComparableObjectSeries(key);
        ComparableObjectSeries series2 = new ComparableObjectSeries(key);
        series1.setDescription("abc");
        series2.setDescription("xyz");

        assertFalse(series1.equals(series2));
    }

    @Test
    /// Passing an unsorted series.
    public void Equals_CompareToUnsortedSeries_False(){
        ComparableObjectSeries series1 = new ComparableObjectSeries(key);
        ComparableObjectSeries series2 = new ComparableObjectSeries(key, false, true);
        series1.add(1, null);
        series1.add(2, null);
        series2.add(2, null);
        series2.add(1, null);

        assertFalse(series1.equals(series2));
    }

    @Test
    /// Passing a series that allows duplication of data.
    public void Equals_CompareToSeriesThatAllowsDuplicateValues_False(){
        ComparableObjectSeries series1 = new ComparableObjectSeries(key);
        ComparableObjectSeries series2 = new ComparableObjectSeries(key, true, false);
        series1.add(1, null);
        series2.add(1, null);

        assertFalse(series1.equals(series2));
    }

}
