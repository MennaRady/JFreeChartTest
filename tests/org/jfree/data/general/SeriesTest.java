package org.jfree.data.general;

import org.jfree.data.ComparableObjectSeries;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mockito;


class DummySeriesChangeListener implements SeriesChangeListener {

    @Override
    public void seriesChanged(SeriesChangeEvent seriesChangeEvent) {
        /// do something
    }
}


public class SeriesTest {
    String key;
    Series comparableSeries;

    @Before
    public void set_Up(){
        key = "123";
        comparableSeries = new ComparableObjectSeries(key);
    }

    @Test
    public void AddChangeListener() throws NoSuchFieldException {
        SeriesChangeListener listener = new DummySeriesChangeListener();
        SeriesChangeListener spyListener = Mockito.spy(listener);
        comparableSeries.addChangeListener(spyListener);

        comparableSeries.fireSeriesChanged();

        Mockito.verify(spyListener).seriesChanged(Mockito.any());
    }

    @Test
    public void RemoveChangeListener() throws NoSuchFieldException {
        SeriesChangeListener listener = new DummySeriesChangeListener();
        SeriesChangeListener spyListener = Mockito.spy(listener);

        comparableSeries.removeChangeListener(spyListener);
        comparableSeries.fireSeriesChanged();

        Mockito.verifyNoInteractions(spyListener);
    }

}
