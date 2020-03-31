package org.jfree.data.general;

import org.jfree.data.ComparableObjectSeries;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mockito;

import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.beans.PropertyVetoException;
import java.beans.VetoableChangeListener;


class DummySeriesChangeListener implements SeriesChangeListener {

    @Override
    public void seriesChanged(SeriesChangeEvent seriesChangeEvent) {
        /// do something
    }
}

class DummyVetoableChangeListener implements VetoableChangeListener {

    @Override
    public void vetoableChange(PropertyChangeEvent propertyChangeEvent) throws PropertyVetoException {
        /// do something
    }
}

class DummyPropertyChangeListener implements PropertyChangeListener {

    @Override
    public void propertyChange(PropertyChangeEvent propertyChangeEvent) {
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

    @Test
    public void AddVetoableChangeListener() throws PropertyVetoException {
        VetoableChangeListener listener = new DummyVetoableChangeListener();
        VetoableChangeListener spyListener = Mockito.spy(listener);

        comparableSeries.addVetoableChangeListener(spyListener);
        comparableSeries.fireVetoableChange(null, null, null);

        Mockito.verify(spyListener).vetoableChange(Mockito.any());
    }

    @Test
    public void RemoveVetoableChangeListener() throws PropertyVetoException {
        VetoableChangeListener listener = new DummyVetoableChangeListener();
        VetoableChangeListener spyListener = Mockito.spy(listener);

        comparableSeries.removeVetoableChangeListener(spyListener);
        comparableSeries.fireVetoableChange(null, null, null);

        Mockito.verifyNoInteractions(spyListener);
    }

    @Test
    public void AddPropertyChangeListener() throws NoSuchFieldException {
        PropertyChangeListener listener = new DummyPropertyChangeListener();
        PropertyChangeListener spyListener = Mockito.spy(listener);

        comparableSeries.addPropertyChangeListener(spyListener);
        comparableSeries.firePropertyChange(null, null, null);

        Mockito.verify(spyListener).propertyChange(Mockito.any());
    }

    @Test
    public void RemovePropertyChangeListener() throws NoSuchFieldException {
        PropertyChangeListener listener = new DummyPropertyChangeListener();
        PropertyChangeListener spyListener = Mockito.spy(listener);

        comparableSeries.removePropertyChangeListener(spyListener);
        comparableSeries.firePropertyChange(null, null, null);

        Mockito.verifyNoInteractions(spyListener);
    }
}
