package jdt.core;

import java.io.Serializable;
import java.util.Comparator;

import jdt.icore.IAction;
import jdt.icore.ICondition;
import jdt.icore.IDecisionTable;
import jdt.icore.IRule;

public class RuleComparator implements Comparator<IRule>, Serializable {
	/**
	 * 
	 */
	private static final long serialVersionUID = -2435669279524367671L;
	private final IDecisionTable decisionTable;
	
	public RuleComparator( final IDecisionTable decisionTable) {
		this.decisionTable = decisionTable;
	}
	
	public int compare( final IRule rule1, final IRule rule2) {
		int result = 0;
		
		{
			final int nrConditions1 = rule1.getConditions().size();
			final int nrConditions2 = rule2.getConditions().size();
			
			if( nrConditions1 < nrConditions2) {
				result = -1;
			} else if( nrConditions1 == nrConditions2) {
				final int nrActions1 = rule1.getActions().size();
				final int nrActions2 = rule2.getActions().size();
				
				if( nrActions1 < nrActions2) {
					result = -1;
				} else if( nrActions1 > nrActions2) {
					result = 1;
				}
			} else {
				result = 1;
			}
		}
		
		if( result == 0) {
			for( final ICondition condition : decisionTable.getConditions()) {
				result = rule1.getConditionValue( condition).compareTo( rule2.getConditionValue(condition));
				if( result != 0) {
					break;
				}
			}
		}
		
		if( result == 0) {
			for( final IAction action : decisionTable.getActions()) {
				result = rule1.getActionValue( action).compareTo( rule2.getActionValue( action));
				if( result != 0) {
					break;
				}
			}
		}
		
		return result;
	}
}
