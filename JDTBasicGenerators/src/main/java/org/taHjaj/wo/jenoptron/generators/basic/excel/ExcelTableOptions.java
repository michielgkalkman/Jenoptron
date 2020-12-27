package org.taHjaj.wo.jenoptron.generators.basic.excel;

import com.jgoodies.binding.beans.ExtendedPropertyChangeSupport;
import org.taHjaj.wo.jenoptron.generators.basic.GeneratorOptions;

import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;

public class ExcelTableOptions implements GeneratorOptions, PropertyChangeListener {
	/**
	 *
	 */
	private static final long serialVersionUID = 353838050855412550L;

	@Override
	public void copy( final GeneratorOptions generatorOptions) {
		if( generatorOptions instanceof ExcelTableOptions) {
			setOptions( (ExcelTableOptions) generatorOptions);
		}
	}

	public void setOptions( final ExcelTableOptions asciiTableOptions) {
		this.fBorders = asciiTableOptions.fBorders;
	}

	private boolean fBorders = true;

	public ExcelTableOptions( final boolean fBorders) {
		this.fBorders = fBorders;
	}

	public boolean isFBorders() {
		return fBorders;
	}

	public void setFBorders(final boolean borders) {
		fBorders = borders;
	}

	private final ExtendedPropertyChangeSupport extendedPropertyChangeSupport =
		new ExtendedPropertyChangeSupport( this);

	public void propertyChange( final PropertyChangeEvent propertyChangeEvent) {
		extendedPropertyChangeSupport.firePropertyChange( propertyChangeEvent);
	}

	public void addPropertyChangeListener( final PropertyChangeListener propertyChangeListener) {
		extendedPropertyChangeSupport.addPropertyChangeListener( propertyChangeListener);
	}

	public void removePropertyChangeListener( final PropertyChangeListener propertyChangeListener) {
		extendedPropertyChangeSupport.removePropertyChangeListener( propertyChangeListener);
	}
}
