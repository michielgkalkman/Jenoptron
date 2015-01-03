package jdt.core.category;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import jdt.core.binary.BinaryConditionValue;
import jdt.icore.IAction;
import jdt.icore.ICondition;
import jdt.icore.IRule;


public class ExclusiveConditionsGroup implements IExclusiveConditionGroup {
	/**
	 * 
	 */
	private static final long serialVersionUID = 2141178139072446295L;
	private final List<ICondition> mutualExclusiveConditions;
	private String shortDescription;

	ExclusiveConditionsGroup() {
		this( "Deze constructor niet gebruiken !");
	}

	public ExclusiveConditionsGroup( final String shortDescription) {
		this.shortDescription = shortDescription;
		mutualExclusiveConditions = new ArrayList<ICondition>();
	}
	
	public void add( final ICondition... conditions) {
		for( final ICondition condition : conditions) {
			mutualExclusiveConditions.add( condition);
			condition.setGroup( this);
		}
	}

	public boolean isValid( final IRule rule) {
		int countYes = 0;
		int countIrrelevant = 0;
		
		for( final ICondition condition : mutualExclusiveConditions) {
			if( rule.getConditionValue( condition).equals( 
					BinaryConditionValue.YES)) {
				countYes++;
			} else if( rule.getConditionValue( condition).equals( 
					BinaryConditionValue.IRRELEVANT)) {
				countIrrelevant++;
			}
		}
		
		return countYes == 1 || countIrrelevant == mutualExclusiveConditions.size();
	}

	public Iterable< ICondition> conditions() {
		return mutualExclusiveConditions;
	}

	public Iterable<IAction> actions() {
		return Collections.EMPTY_LIST;
	}

	public void setActionValues(final IRule rule) {
		// No actions, so no action here.
	}

	public String getShortDescription() {
		return shortDescription;
	}

	public void setShortDescription(final String shortDescription) {
		this.shortDescription = shortDescription;
	}

	public String getShortDescription( final String groupMemberShortDescription) {
		return getShortDescription() + ":" + groupMemberShortDescription;
	}
}
