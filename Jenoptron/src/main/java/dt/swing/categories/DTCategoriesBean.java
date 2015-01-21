package dt.swing.categories;

import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.util.ArrayList;
import java.util.List;

import com.jgoodies.binding.beans.ExtendedPropertyChangeSupport;
import com.jgoodies.binding.beans.Observable;

import jdt.icore.ICondition;
import jdt.icore.IDecisionTable;

public class DTCategoriesBean implements Observable, PropertyChangeListener {
	private IDecisionTable decisionTable;
	private List<String> availableConditions;
	private String currentSelection;
	
	public static final String PROP_AVAILABLE_CONDITIONS = "availableConditions";
	public static final String PROP_CURRENT_SELECTION = "currentSelection";
	
	private final ExtendedPropertyChangeSupport extendedPropertyChangeSupport =
		new ExtendedPropertyChangeSupport( this);

	public DTCategoriesBean( final IDecisionTable decisionTable) {
		this.decisionTable = decisionTable;
		decisionTable.addPropertyChangeListener( this);
		availableConditions = new ArrayList<String>();
		for( final ICondition condition : decisionTable.getConditions()) {
			availableConditions.add( condition.getShortDescription());
		}
	}
	
	public void addPropertyChangeListener( final PropertyChangeListener propertyChangeListener) {
		extendedPropertyChangeSupport.addPropertyChangeListener( propertyChangeListener);
	}

	public void removePropertyChangeListener( final PropertyChangeListener propertyChangeListener) {
		extendedPropertyChangeSupport.removePropertyChangeListener( propertyChangeListener);
	}

	public void propertyChange(final PropertyChangeEvent evt) {
		final List<String> newAvailableConditions = new ArrayList<String>();
		for( final ICondition condition : decisionTable.getConditions()) {
			newAvailableConditions.add( condition.getShortDescription());
		}
		setAvailableConditions( newAvailableConditions);
	}

	public IDecisionTable getDecisionTable() {
		return decisionTable;
	}

	public List<String> getAvailableConditions() {
		return availableConditions;
	}

	public void setAvailableConditions( final List<String> availableConditions) {
		final List<String> oldAvailableConditions = this.availableConditions;
		this.availableConditions = availableConditions;
		extendedPropertyChangeSupport.firePropertyChange( PROP_AVAILABLE_CONDITIONS, oldAvailableConditions, availableConditions);
	}

	public String getCurrentSelection() {
		return currentSelection;
	}

	public void setCurrentSelection( final String currentSelection) {
		final String oldCurrentSelection = this.currentSelection;
		this.currentSelection = currentSelection;
		extendedPropertyChangeSupport.firePropertyChange( PROP_CURRENT_SELECTION, oldCurrentSelection, currentSelection);
	}
}
