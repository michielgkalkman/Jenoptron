package jdt.core.test;

import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;

import org.apache.log4j.Logger;

import jdt.core.DecisionTable;
import jdt.core.binary.BinaryCondition;
import jdt.core.junit.AbstractTestCase;
import jdt.icore.ICondition;
import jdt.icore.IDecisionTable;

public class PropertyChangeTest extends AbstractTestCase {
	private static final Logger logger = Logger.getLogger( PropertyChangeTest.class);
	
	public void testSimple() {
		final IDecisionTable decisionTable = new DecisionTable();

		// Add condition
		ICondition condition;
		{
			condition = new BinaryCondition();
			decisionTable.add( condition);
		}

		
		decisionTable.addPropertyChangeListener( new PropertyChangeListener() {
			public void propertyChange(PropertyChangeEvent evt) {
				// TODO Auto-generated method stub
				logger.debug( "test:" + evt.getPropertyName());
				assertEquals( evt.getPropertyName(), IDecisionTable.CHANGE_EVENT);
			}
		});
		
		condition.setShortDescription( "x");
	}
	
	public void testSimple2() {
		final IDecisionTable decisionTable = new DecisionTable();

		// Add condition
		ICondition condition;
		{
			condition = new BinaryCondition();
			decisionTable.add( condition);
		}

		
		decisionTable.addPropertyChangeListener( new PropertyChangeListener() {
			public void propertyChange(PropertyChangeEvent evt) {
				// TODO Auto-generated method stub
				logger.debug( "test:" + evt.getPropertyName());
				assertEquals( evt.getPropertyName(), IDecisionTable.PROP_SHORT_DESCRIPTION);
			}
		});
		
		decisionTable.setShortDescription( "x");
	}
}
