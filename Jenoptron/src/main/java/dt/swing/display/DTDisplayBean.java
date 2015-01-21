package dt.swing.display;

import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.util.ArrayList;
import java.util.List;

import javax.swing.JPanel;

import jdt.icore.IDecisionTable;

import org.apache.commons.collections.OrderedMap;
import org.apache.commons.collections.map.ListOrderedMap;
import org.apache.commons.lang.builder.ToStringBuilder;
import org.apache.commons.lang.builder.ToStringStyle;
import org.taHjaj.wo.events.Observable;
import org.taHjaj.wo.events.ObservableMediator;

import dt.generators.Generator;
import dt.generators.GeneratorFactorySPI;

public class DTDisplayBean implements PropertyChangeListener, Observable {
	public static final String PROP_DISPLAY_MODE = "displayMode";

	private IDecisionTable decisionTable;
	private final OrderedMap dtPlugins;
	private Generator currentGenerator;
	private String displayMode;
	
	@SuppressWarnings("unchecked")
	public DTDisplayBean( final IDecisionTable decisionTable) {
		this.decisionTable = decisionTable;
		decisionTable.addPropertyChangeListener( this);

		dtPlugins = new ListOrderedMap();

		populatePlugins(decisionTable);
		
		if( !dtPlugins.isEmpty()) {
			displayMode = (String)dtPlugins.firstKey();
			currentGenerator = (Generator)dtPlugins.get( displayMode);
		}
	}

	private void populatePlugins(final IDecisionTable decisionTable) {
		for( final Generator generator : GeneratorFactorySPI.getAllFileGenerators()) {
			generator.setDecisionTable( decisionTable);
			dtPlugins.put( generator.getShortDescription(), generator);
		}
	}

	public boolean isEnabled() {
		return getDisplayOptions().size() > 0;
	}
	
	public IDecisionTable getDecisionTable() {
		return decisionTable;
	}

	public void setDecisionTable(final IDecisionTable decisionTable) {
		this.decisionTable = decisionTable;
		
		ObservableMediator.getInstance().fire( this);
	}

	public String getDisplayMode() {
		return displayMode;
	}
	
	public void setDisplayMode(final String displayMode) {
		this.displayMode = displayMode; 
		currentGenerator = (Generator) dtPlugins.get( displayMode);
		
		ObservableMediator.getInstance().fire( this);
	}

	@SuppressWarnings("unchecked")
	public List<String> getDisplayOptions() {
		final List<String> displayOptions = new ArrayList<String>();

		displayOptions.addAll( dtPlugins.keySet());

		return displayOptions;
	}

	public JPanel getDetails() {
		final JPanel panel = null; // currentDTPlugin.getConfiguration();

		return panel;
	}

	public void propertyChange(final PropertyChangeEvent evt) {
		ObservableMediator.getInstance().fire( this);
	}

	@Override
	public String toString() {
		return ToStringBuilder.reflectionToString( this, ToStringStyle.MULTI_LINE_STYLE);
	}

	public Generator getCurrentGenerator() {
		return currentGenerator;
	}
}