package jdt.core.test;

import jdt.core.DecisionTable;
import jdt.core.binary.BinaryAction;
import jdt.core.binary.BinaryActionValue;
import jdt.core.binary.BinaryCondition;
import jdt.core.junit.AbstractTestCase;
import jdt.icore.IAction;
import jdt.icore.ICondition;
import jdt.icore.IDecisionTable;
import jdt.icore.IRule;

import org.apache.log4j.Logger;

public class RuleTemplateTest extends AbstractTestCase {
	private static final Logger logger = Logger.getLogger( RuleTemplateTest.class);

	public RuleTemplateTest(final String name) {
		super(name);
	}

	public void testSimple2() {
		final IDecisionTable decisionTable = new DecisionTable();

		// Add condition
		{
			final ICondition condition = new BinaryCondition();
			decisionTable.add( condition);
		}

		// Add action
		{
			final IAction action = new BinaryAction();
			decisionTable.add( action);

			decisionTable.split();

			final Iterable<IRule> rules = decisionTable.getRules();

			rules.iterator().next().setActionValue(action, BinaryActionValue.DO);
		}

		logger.debug(dump( decisionTable));
	}

	public void testSimple() {
			final IDecisionTable decisionTable = new DecisionTable();

			// Add condition
			{
				final ICondition condition = new BinaryCondition();
				decisionTable.add( condition);
			}

			// Add action
			{
				final IAction action = new BinaryAction();
				decisionTable.add( action);
			}

		// Add action
		{
			final IAction action = new BinaryAction();
			decisionTable.add( action);
		}

		logger.debug(dump( decisionTable));

		decisionTable.split();

		logger.debug(dump( decisionTable));

		decisionTable.split();

		logger.debug(dump( decisionTable));

		// Add condition
		{
			final ICondition condition = new BinaryCondition();
			decisionTable.add( condition);
		}

		logger.debug(dump( decisionTable));

		decisionTable.split();

		logger.debug(dump( decisionTable));

		// Add condition
		{
			final ICondition condition = new BinaryCondition();
			decisionTable.add( condition);
		}

		decisionTable.split();

		logger.debug(dump( decisionTable));

		logger.debug("Done.");
	}
}
