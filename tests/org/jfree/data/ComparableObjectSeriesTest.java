package org.jfree.data;

import org.jfree.data.general.SeriesException;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mockito;


import static org.junit.Assert.*;
import static org.mockito.Mockito.*;

public class ComparableObjectSeriesTest {
    ComparableObjectSeries series;
    Integer key;

    @Before
    public void setUp(){
        key = 1;
        series = new ComparableObjectSeries(key);
    }

    @Test
    public void ComparableObjectSeriesTest_InitializeAttributes(){
        series = new ComparableObjectSeries("XYZ", false, true);
        assertEquals("XYZ", series.getKey());
        assertFalse(series.getAutoSort());
        assertTrue(series.getAllowDuplicateXValues());
        assertEquals(0, series.getItemCount());
        assertEquals(Integer.MAX_VALUE, series.getMaximumItemCount());
        assertEquals(null, series.getDescription());
    }

    @Test
    public void ComparableObjectSeriesTest_AutoInitialization(){
        series = new ComparableObjectSeries("XYZ");
        assertEquals("XYZ", series.getKey());
        assertTrue(series.getAutoSort());
        assertTrue(series.getAllowDuplicateXValues());
        assertEquals(0, series.getItemCount());
        assertEquals(Integer.MAX_VALUE, series.getMaximumItemCount());
        assertEquals(null, series.getDescription());
    }

    @Test
    public void SetMaximumItemCount_ValidNum(){
        series = new ComparableObjectSeries(key);

        series.setMaximumItemCount(5);

        int actual = series.getMaximumItemCount();
        assertEquals(actual, 5);
    }

    @Test(expected = IndexOutOfBoundsException.class)
    public void SetMaximumItemCount_InvalidNum(){
        series = new ComparableObjectSeries(key);
        series.setMaximumItemCount(-1);
    }

    @Test
    /// asserts that if the data size exceeds the maximum item count, data will be removed
    // from the beginning of the series and fireSeriesChanged will be invoked.
    public void SetMaximumItemCount_DataSizeExceedsMaxItemCount_RemoveData(){
        series = new ComparableObjectSeries(key);
        ComparableObjectSeries spySeries = Mockito.spy(series);
        series.add(1, null);
        series.add(2, null);
        series.add(3, null);

        spySeries.setMaximumItemCount(1);

        assertEquals(1, series.data.size());
        assertEquals(0, series.indexOf(3));
        verify(spySeries).fireSeriesChanged();
    }

    @Test
    public void SetKey_ValidKey_ExceptionThrown(){
        series = new ComparableObjectSeries(key);
        series.setKey("XYZ");

        assertEquals("XYZ", series.getKey());
    }

    @Test(expected = IllegalArgumentException.class)
    public void SetKey_InvalidKey_ExceptionThrown(){
        series = new ComparableObjectSeries(key);
        series.setKey(null);
    }

    @Test
    public void GetKey_ReturnsKey(){
        series = new ComparableObjectSeries(key);
        assertEquals(key, series.getKey());
    }

    @Test
    public void GetNotify_ReturnsNotification(){
        series = new ComparableObjectSeries(key);
        series.setNotify(true);

        assertTrue(series.getNotify());
    }

    @Test
    public void GetDescription_ReturnsDescription(){
        series = new ComparableObjectSeries(key);
        series.setDescription("XYZ");

        assertEquals("XYZ", series.getDescription());
    }

    @Test
    public void GetItemCount_ReturnsDataSize() {
        series = new ComparableObjectSeries(key);
        series.add(1, null);
        series.add(2, null);
        series.add(3, null);

        assertEquals(3, series.getItemCount());
    }

    @Test
    public void GetItemCount_EmptySeries_ReturnsZero() {
        series = new ComparableObjectSeries(key);

        assertEquals(0, series.getItemCount());
    }

    @Test
    public void Add_UnsortedSeriesThatDoNotAllowsDuplication_AddItemAndNotify(){
        ComparableObjectSeries series = new ComparableObjectSeries(key, false, false);
        series.add(1, null);
        ComparableObjectItem item = Mockito.mock(ComparableObjectItem.class);
        Mockito.doReturn(2).when(item).getComparable();
        ComparableObjectSeries spySeries = Mockito.spy(series);
        Mockito.doReturn(-1).when(spySeries).indexOf(2);

        spySeries.add(item, true);

        assertEquals(2, spySeries.data.size());
        verify(spySeries).fireSeriesChanged();
    }

    @Test
    public void Add_UnsortedSeriesThatDoNotAllowsDuplication_AddItemAndDoNotNotify(){
        ComparableObjectSeries series = new ComparableObjectSeries(key, false, false);
        series.add(1, null);
        ComparableObjectItem item = Mockito.mock(ComparableObjectItem.class);
        Mockito.doReturn(2).when(item).getComparable();
        ComparableObjectSeries spySeries = Mockito.spy(series);
        Mockito.doReturn(-1).when(spySeries).indexOf(2);

        spySeries.add(item, false);

        assertEquals(2, spySeries.data.size());
        verify(spySeries, times(0)).fireSeriesChanged();
    }

    @Test(expected = SeriesException.class)
    public void Add_DupliactedItemToUnsortedSeriesThatDoNotAllowsDuplication_ExceptionThrown(){
        series = new ComparableObjectSeries(key, false, false);

        series.add(1, null);
        series.add(1, null);
    }

    @Test
    public void Add_ItemsExceedTheMaxItemCount_AddTheNewItemAndDeleteTheFirstItem(){
        ComparableObjectSeries series = new ComparableObjectSeries(key, false, false);
        series.add(1, null);
        series.setMaximumItemCount(1);
        ComparableObjectItem item = Mockito.mock(ComparableObjectItem.class);
        Mockito.doReturn(2).when(item).getComparable();
        ComparableObjectSeries spySeries = Mockito.spy(series);
        Mockito.doReturn(-1).when(spySeries).indexOf(2);

        spySeries.add(item, true);

        assertEquals(1, spySeries.data.size());
        assertTrue(spySeries.indexOf(1) < 0);   /// Item with comparable 1 removed.
        verify(spySeries).fireSeriesChanged();
    }

    @Test(expected = IllegalArgumentException.class)
    public void Add_IllegalArgument_ExceptionThrown(){
        series = new ComparableObjectSeries(key);

        series.add(null, false);
    }

    @Test(expected = SeriesException.class)
    public void Add_SortedSeriesThatDoNotAllowsDuplication_ExceptionThrown(){
        series = new ComparableObjectSeries(key, true, false);

        series.add(2, null);
        series.add(2, null);
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
        series = new ComparableObjectSeries(key, false, false);
        series.add(4, null);
        series.add(0, null);
        series.add(2, null);
        int actual = series.indexOf(4);

        assertEquals(0, actual);
    }

    @Test
    public void IndexOf_IndexOfNullItem_NegativeValue(){
        series = new ComparableObjectSeries(key, false, false);
        series.add(4, null);
        series.add(0, null);
        series.add(2, null);
        Comparable obj = null;
        int actual = series.indexOf(obj);

        assertEquals(-1, actual);
    }

    @Test(expected = SeriesException.class)
    public void Update_UpdatingNonExistingItem_ExceptionThrown(){
        series = new ComparableObjectSeries(key);
        series.add(4, null);

        series.update(2, null);
    }

    @Test
    public void Update_MethodsAreInvokedWithCorrectParam(){
        series = new ComparableObjectSeries(key);
        series.add(4, null);
        ComparableObjectSeries spySeries = Mockito.spy(series);
        ComparableObjectItem item = Mockito.mock(ComparableObjectItem.class);
        Mockito.doReturn(item).when(spySeries).getDataItem(0);

        spySeries.update(4, "ABC");

        verify(item).setObject("ABC");
        verify(spySeries).fireSeriesChanged();
    }

    @Test(expected = IllegalArgumentException.class)
    public void Update_NullComparable_ExceptionThrown(){
        series = new ComparableObjectSeries(key);
        series.add(4, null);

        series.update(null, "ABC");
    }

    @Test
    public void UpdateByIndex_MethodsAreInvokedWithCorrectParam(){
        series = new ComparableObjectSeries(key);
        ComparableObjectSeries spySeries = Mockito.spy(series);
        ComparableObjectItem item = Mockito.mock(ComparableObjectItem.class);
        Mockito.doReturn(item).when(spySeries).getDataItem(0);

        spySeries.updateByIndex(0, "ABC");

        verify(item).setObject("ABC");
        verify(spySeries).fireSeriesChanged();
    }

    @Test(expected = IndexOutOfBoundsException.class)
    public void UpdateByIndex_IndexOutOfBounds_ExceptionThrown(){
        series = new ComparableObjectSeries(key);
        ComparableObjectSeries spySeries = Mockito.spy(series);
        ComparableObjectItem item = Mockito.mock(ComparableObjectItem.class);
        Mockito.doReturn(item).when(spySeries).getDataItem(0);

        spySeries.updateByIndex(2, "ABC");
    }

    @Test(expected = IndexOutOfBoundsException.class)
    public void UpdateByIndex_NegativeIndex_ExceptionThrown(){
        series = new ComparableObjectSeries(key);
        ComparableObjectSeries spySeries = Mockito.spy(series);
        ComparableObjectItem item = Mockito.mock(ComparableObjectItem.class);
        Mockito.doReturn(item).when(spySeries).getDataItem(0);

        spySeries.updateByIndex(-1, "ABC");
    }

    @Test
    /// This test is to make sure that the start index of the data is deleted
    public void Delete_DeletingItemsFromStartToEndIndex_StartDeleted(){
        series = new ComparableObjectSeries(key);
        series.add(4, null);
        series.add(0, null);
        series.add(2, null);
        ComparableObjectSeries spySeries = Mockito.spy(series);

        int start = 0;
        int end = 2;

        spySeries.delete(start, end);
        int actual = spySeries.indexOf(4);
        assertTrue(actual < 0);
        Mockito.verify(spySeries).fireSeriesChanged();
    }

    @Test
    /// This test is to make sure that the end index of the data is deleted
    public void Delete_DeletingItemsFromStartToEndIndex_EndDeleted(){
        series = new ComparableObjectSeries(key);
        series.add(4, null);
        series.add(0, null);
        series.add(2, null);
        ComparableObjectSeries spySeries = Mockito.spy(series);

        int start = 0;
        int end = 2;

        spySeries.delete(start, end);
        int actual = spySeries.indexOf(2);
        assertTrue(actual < 0);
        Mockito.verify(spySeries).fireSeriesChanged();
    }

    @Test
    /// This test is to make sure that the all the required data is deleted
    public void Delete_DeletingItemsFromStartToEndIndex_AllDeleted(){
        series = new ComparableObjectSeries(key);
        series.add(4, null);
        series.add(0, null);
        series.add(2, null);
        ComparableObjectSeries spySeries = Mockito.spy(series);

        int start = 0;
        int end = 2;
        int actual_size = series.data.size() - (end - start) - 1;

        spySeries.delete(start, end);
        assertEquals(actual_size, spySeries.data.size());
        Mockito.verify(spySeries).fireSeriesChanged();
    }

    @Test
    /// This test is to make sure that the all the required data is deleted
    public void Delete_StartEqualsEndIndex_ItemDeleted(){
        series = new ComparableObjectSeries(key);
        series.add(4, null);
        series.add(0, null);
        series.add(2, null);
        ComparableObjectSeries spySeries = Mockito.spy(series);

        int start = 1;
        int end = 1;
        int actual_size = series.data.size() - (end - start) - 1;

        spySeries.delete(start, end);
        assertEquals(actual_size, spySeries.data.size());
        assertTrue(spySeries.indexOf(1) < 0); /// Item not found in series
        Mockito.verify(spySeries).fireSeriesChanged();
    }

    @Test
    /// This test is to make sure that nothing is deleted if the end is greater than the start index.
    public void Delete_StartGreaterThanEndIndex_ItemDeleted(){
        series = new ComparableObjectSeries(key);
        series.add(4, null);
        series.add(0, null);
        series.add(2, null);
        ComparableObjectSeries spySeries = Mockito.spy(series);
        int start = 2;
        int end = 1;

        spySeries.delete(start, end);

        assertEquals(3, spySeries.data.size());
        Mockito.verify(spySeries).fireSeriesChanged();
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
    public void Remove_RemoveByIndex_ItemRemoved(){
        series.add(1, null);
        series.add(2, null);
        ComparableObjectSeries spySeries = Mockito.spy(series);

        Object actual = spySeries.remove(0);
        assertTrue(actual instanceof ComparableObjectItem);
        assertEquals(1, spySeries.data.size());
        verify(spySeries).fireSeriesChanged();
    }

    @Test(expected = IndexOutOfBoundsException.class)
    public void Remove_RemoveItemOfNegativeIndex_ExceptionThrown(){
        series.add(1, null);

        Object actual = series.remove(-1);
    }

    @Test(expected = IndexOutOfBoundsException.class)
    public void Remove_RemoveItemOfIndexOutOfBounds_ExceptionThrown(){
        series.add(1, null);

        series.remove(2);
    }

    @Test
    public void Remove_RemoveByValue_ItemRemoved(){
        series.add(1, null);
        series.add(2, null);
        ComparableObjectSeries spySeries = Mockito.spy(series);
        Comparable val = 2;

        Object actual = spySeries.remove(val);

        assertTrue(actual instanceof ComparableObjectItem);
        assertEquals(1, spySeries.data.size());
        verify(spySeries).remove(1);
        verify(spySeries).fireSeriesChanged();
    }

    @Test(expected = IndexOutOfBoundsException.class)
    public void Remove_RemoveNonExistingItem_ExceptionThrown(){
        series.add(1, null);
        series.add(2, null);
        Comparable val = 3;

        series.remove(val);
    }

    @Test
    public void Clear_ClearingSeries_AllDeleted(){
        series = new ComparableObjectSeries(key);
        series.add(1, null);
        series.add(2, null);
        series.add(3, null);
        ComparableObjectSeries spySeries = Mockito.spy(series);

        spySeries.clear();
        int actual = spySeries.data.size();

        assertEquals(0, actual);
        Mockito.verify(spySeries).fireSeriesChanged();
    }

    @Test
    public void Clear_EmptySeries_ExceptionThrown(){
        series = new ComparableObjectSeries(key);
        ComparableObjectSeries spySeries = Mockito.spy(series);

        spySeries.clear();

        Mockito.verify(spySeries, times(0)).fireSeriesChanged();
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
    /// Passing a series with different description.
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

    @Test
    /// Passing null object
    public void Equals_NullObject_False(){
        series = new ComparableObjectSeries(key);

        assertFalse(series.equals(null));
    }

    @Test
    public void IsEmpty_EmptySeries_True(){
        series = new ComparableObjectSeries(key);
        assertTrue(series.isEmpty());
    }

    @Test
    public void IsEmpty_HasData_False(){
        series = new ComparableObjectSeries(key);
        series.add(0, null);

        assertFalse(series.isEmpty());
    }

    @Test
    public void Clone_CloneSeries_ReturnEquivalentSeries() throws CloneNotSupportedException {
        ComparableObjectSeries series1 = new ComparableObjectSeries(key);
        ComparableObjectSeries series2;
        series.add(1, null);

        series2 = (ComparableObjectSeries) series1.clone();

        assertEquals(series1, series2);
    }

    @Test
    public void Clone_CloneSeries_NotSameSeries() throws CloneNotSupportedException {
        ComparableObjectSeries series1 = new ComparableObjectSeries(key);
        ComparableObjectSeries series2;
        series.add(1, null);

        series2 = (ComparableObjectSeries) series1.clone();

        assertNotSame(series1, series2);
    }

    @Test
    public void Clone_CloneSeries_SeriesOfSameInstance() throws CloneNotSupportedException {
        ComparableObjectSeries series1 = new ComparableObjectSeries(key);
        ComparableObjectSeries series2;
        series.add(1, null);

        series2 = (ComparableObjectSeries) series1.clone();

        assertEquals(series1.getClass(), series2.getClass());
    }


    //// Integration Tests
    @Test
    public void ClearTest(){
        series = new ComparableObjectSeries(key);
        series.add(1, null);
        series.add(2, null);

        series.clear();
        int actual = series.data.size();

        assertEquals(0, actual);
    }

    @Test
    /// This test is to make sure that the all the required data is deleted
    public void DeleteTest_Valid(){
        series = new ComparableObjectSeries(key);
        series.add(1, null);
        series.add(2, null);
        int start = 0;
        int end = 1;
        int actual_size = series.data.size() - (end - start) - 1;

        series.delete(start, end);

        assertEquals(actual_size, series.data.size());
    }

    @Test(expected = IndexOutOfBoundsException.class)
    public void DeleteTest_Invalid(){
        series = new ComparableObjectSeries(key);
        int start = 0;
        int end = 1;
        int actual_size = series.data.size() - (end - start) - 1;

        series.delete(start, end);

        assertEquals(actual_size, series.data.size());
    }

    @Test
    public void HashCodeTest(){
        ComparableObjectSeries series1 = new ComparableObjectSeries("123");
        ComparableObjectSeries series2 = new ComparableObjectSeries("123");

        assertEquals(series1, series2);
        assertEquals(series1.hashCode(), series2.hashCode());

        series1.add(1, "4");
        series2.add(1, "4");

        assertEquals(series1, series2);
        assertEquals(series1.hashCode(), series2.hashCode());

        series1.add(2, "5");
        series2.add(2, "5");

        assertEquals(series1, series2);
        assertEquals(series1.hashCode(), series2.hashCode());

        series1.add(3, null);
        series2.add(3, null);

        assertEquals(series1, series2);
        assertEquals(series1.hashCode(), series2.hashCode());
    }

    @Test
    public void RemoveByValueTest(){
        series.add(1, null);
        series.add(2, null);
        Comparable val = 2;

        Object actual = series.remove(val);

        assertTrue(actual instanceof ComparableObjectItem);
        assertEquals(1, series.data.size());
    }

    @Test
    public void RemoveByIndexTest(){
        series.add(1, null);
        series.add(2, null);

        Object actual = series.remove(0);
        assertTrue(actual instanceof ComparableObjectItem);
        assertEquals(1, series.data.size());
    }

}
