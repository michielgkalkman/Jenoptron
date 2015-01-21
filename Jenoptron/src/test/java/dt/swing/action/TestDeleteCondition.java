
package dt.swing.action;
import jdt.core.DecisionTable;
import jdt.core.junit.AbstractTestCase;
import jdt.icore.IDecisionTable;

import org.apache.log4j.Logger;

import dt.generators.GeneratorFactorySPI;
import dt.generators.TextGenerator;

public class TestDeleteCondition extends AbstractTestCase {
	private static final Logger logger = Logger.getLogger( TestDeleteCondition.class);

	public void testSimple() {
		final IDecisionTable decisionTable = new DecisionTable();

		ActionUtil.addCondition(decisionTable);
		ActionUtil.addAction(decisionTable);

		final TextGenerator textGenerator = GeneratorFactorySPI.getAnyTextGenerator();

		logger.debug( textGenerator.getText( decisionTable));

		decisionTable.reduce();

		logger.debug( textGenerator.getText( decisionTable));

		ActionUtil.removeCondition(decisionTable,1);

		logger.debug( textGenerator.getText( decisionTable));

		assertEquals( "Expecting 0 rules", decisionTable.getRules().size(), 0);
	}
}
