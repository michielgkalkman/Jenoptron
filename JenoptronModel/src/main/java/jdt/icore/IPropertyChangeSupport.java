package jdt.icore;

import java.beans.PropertyChangeListener;

public interface IPropertyChangeSupport extends PropertyChangeListener {
	void addPropertyChangeListener(String propertyName,
            PropertyChangeListener listener);
	
	void removePropertyChangeListener(String propertyName,
            PropertyChangeListener listener);
	
	void firePropertyChange(String propertyName,
            Object oldValue,
            Object newValue);
}
