package org.jfree.data;

import org.jfree.data.general.SeriesException;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mockito;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;
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
    public void Delete_DeletingFromEmptySeries_ExceptionThrown(){
        series = new ComparableObjectSeries(key);

        int start = 0;
        int end = 3;

        series.delete(start, end);
    }

}
