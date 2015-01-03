package jdt.core;

import jdt.icore.IDecisionTable;
import jdt.icore.IRule;

import org.apache.commons.collections.Predicate;

public class LegalRulePredicate implements Predicate {
	final private IDecisionTable decisionTable;

	public LegalRulePredicate( final IDecisionTable decisionTable) {
		this.decisionTable = decisionTable;
	}
	
	public boolean evaluate( final Object object) {
		final IRule rule = (IRule) object; 
		return decisionTable.isValid(rule);
	}
}
