import org.junit.*;
import org.jfree.data.ComparableObjectItem;

import javax.swing.plaf.synth.SynthStyle;

import static org.junit.Assert.*;

public class ComparableObjectItemTest {

    Integer x, a;
    Object y, b;
    ComparableObjectItem firstItem, secondItem;

    @Before
    public void setUp(){
        //arrange
        x = 1;
        a = 2;
        y = new Object();
        b = new Object();
        firstItem = new ComparableObjectItem(x, y);
        secondItem = new ComparableObjectItem(a, b);
    }

    @Test
    public void compareToTest(){
        //assert and act
        assertTrue(firstItem.compareTo(secondItem) < 0); //negative value
        assertTrue(secondItem.compareTo(firstItem) > 0); //positive value
        assertTrue(firstItem.compareTo(firstItem) == 0); //equals zero
    }

    @Test
    public void equalsTest(){
        //assert and act
        assertTrue(firstItem.equals(firstItem));
        assertFalse(firstItem.equals(y));
        assertFalse(firstItem.equals(secondItem));
        assertFalse(secondItem.equals(null));

    }

    @Test
    public void cloneTest() throws CloneNotSupportedException {
        ComparableObjectItem temp = (ComparableObjectItem) firstItem.clone();
        assertEquals(temp, firstItem);

    }

    @Test
    public void hashCodeTest(){
        assertEquals(firstItem.hashCode(), firstItem.hashCode());
        assertNotEquals(firstItem.hashCode(), secondItem.hashCode());
    }

}

