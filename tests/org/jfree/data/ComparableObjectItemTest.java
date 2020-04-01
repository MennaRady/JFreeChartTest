package org.jfree.data;

import org.junit.*;
import static org.junit.Assert.*;

public class ComparableObjectItemTest {
    Integer x, a;
    Object y, b;
    ComparableObjectItem firstItem, secondItem;

    @Before
    public void setUp(){
        //arrange
        x = 1;
        y = new Object();
        a = 2;
        b = new Object();
        firstItem = new ComparableObjectItem(x, y);
        secondItem = new ComparableObjectItem(a, b);
    }

    @Test
    public void CompareTo_ComparingToLargerValue_NegativeValue(){
        assertTrue(firstItem.compareTo(secondItem) < 0); //negative value
    }

    @Test
    public void CompareTo_ComparingToSmallerValue_PositiveValue(){
        assertTrue(secondItem.compareTo(firstItem) > 0); //positive value
    }

    @Test
    public void CompareTo_ItemsWithSameValues_Zero(){
        assertTrue(firstItem.compareTo(firstItem) == 0); //equals zero
    }

    @Test
    public void CompareTo_ComparingToNegativeValue_PositiveValue(){
        Comparable s = -2;
        Object t = new Object();
        ComparableObjectItem item = new ComparableObjectItem(s, t);
        assertEquals(item.getComparable(), -2);
        assertTrue(firstItem.compareTo(item) > 0);
    }

    @Test
    public void CompareTo_ObjecOfAnotherInstance_ReturnsOne(){
        Object obj = new Object();
        assertTrue(firstItem.compareTo(obj) == 1);
    }

    @Test
    public void Equals_IdenticalItems_True(){
        assertTrue(firstItem.equals(firstItem));
    }

    @Test
    public void Equals_DifferentItems_False(){
        assertFalse(firstItem.equals(secondItem));
    }

    @Test
    public void Equals_DifferentObjectType_False(){
        Object obj = new Object();
        assertFalse(firstItem.equals(obj));
    }

    @Test
    public void Equals_ComparingToNull_False(){
        assertFalse(secondItem.equals(null));
    }

    @Test
    public void Clone_ReturnEquivalentItem() throws CloneNotSupportedException {
        ComparableObjectItem temp = (ComparableObjectItem) firstItem.clone();
        assertEquals(temp, firstItem);
    }

    @Test
    public void Clone_NotTheSameItem() throws CloneNotSupportedException {
        ComparableObjectItem temp = (ComparableObjectItem) firstItem.clone();
        assertNotSame(temp, firstItem);
    }

    @Test
    public void HashCode_ComparingToItemWithSameHashCode_ReturnsEqualCodes(){
        assertEquals(firstItem.hashCode(), firstItem.hashCode());
    }

    @Test
    public void HashCode_ComparingToItemWithDifferentHashCode_ReturnsDifferentCodes(){
        assertNotEquals(firstItem.hashCode(), secondItem.hashCode());
    }

}

