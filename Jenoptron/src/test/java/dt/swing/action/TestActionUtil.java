package dt.swing.action;


import jdt.core.DecisionTable;
import jdt.core.binary.BinaryActionValue;
import jdt.core.junit.AbstractTestCase;
import jdt.icore.IDecisionTable;

import org.apache.log4j.Logger;

import dt.generators.GeneratorFactorySPI;
import dt.generators.TextGenerator;

public class TestActionUtil extends AbstractTestCase {
	private static final Logger logger = Logger.getLogger( TestActionUtil.class);

	public void testSplit() {
		final IDecisionTable decisionTable = new DecisionTable();

		ActionUtil.addCondition(decisionTable);
		ActionUtil.addAction(decisionTable);
		decisionTable.split();
	}
	public void testInsertCondition() {
		final IDecisionTable decisionTable = new DecisionTable();

		ActionUtil.addCondition(decisionTable);
		decisionTable.reduce();
		ActionUtil.insertCondition(decisionTable, 1);
		ActionUtil.getValue( decisionTable, 0, 0);

		final TextGenerator textGenerator = GeneratorFactorySPI.getAnyTextGenerator();

		logger.debug( textGenerator.getText(decisionTable));

		ActionUtil.setValue(decisionTable, 1, 1, BinaryActionValue.DO);

		ActionUtil.addAction(decisionTable);

		logger.debug( textGenerator.getText(decisionTable));

		ActionUtil.setValue(decisionTable, 3, 1, BinaryActionValue.DO);

		logger.debug( textGenerator.getText(decisionTable));

		decisionTable.split();

		logger.debug( textGenerator.getText(decisionTable));

		ActionUtil.setValue(decisionTable, 3, 1, BinaryActionValue.DONT);

		logger.debug( textGenerator.getText(decisionTable));

		decisionTable.split();

		logger.debug( textGenerator.getText(decisionTable));

		decisionTable.reduce();

		logger.debug( textGenerator.getText(decisionTable));

		decisionTable.split();

		logger.debug( textGenerator.getText(decisionTable));

		decisionTable.reduce();

		logger.debug( textGenerator.getText(decisionTable));
	}

	public void testAddActionFirst() {
		final IDecisionTable decisionTable = new DecisionTable();

		final TextGenerator textGenerator = GeneratorFactorySPI.getAnyTextGenerator();

		ActionUtil.addAction(decisionTable);

		logger.debug( textGenerator.getText(decisionTable));

		ActionUtil.insertCondition(decisionTable, 1);

		logger.debug( textGenerator.getText(decisionTable));
	}

	public void testReduction() {
		final IDecisionTable decisionTable = new DecisionTable();

		final TextGenerator textGenerator = GeneratorFactorySPI.getAnyTextGenerator();

		ActionUtil.addCondition(decisionTable);
		ActionUtil.addCondition(decisionTable);
		ActionUtil.addAction(decisionTable);
		decisionTable.split();
		ActionUtil.setValue(decisionTable, 3, 2, BinaryActionValue.DO);

		logger.debug( textGenerator.getText(decisionTable));

		decisionTable.reduce();

		logger.debug( textGenerator.getText(decisionTable));
	}

	public void testReduction1() {
		final IDecisionTable decisionTable = new DecisionTable();

		final TextGenerator textGenerator = GeneratorFactorySPI.getAnyTextGenerator();

		ActionUtil.addCondition(decisionTable);
		ActionUtil.addCondition(decisionTable);
		ActionUtil.addAction(decisionTable);
		decisionTable.split();
		ActionUtil.setValue(decisionTable, 3, 2, BinaryActionValue.DO);
		ActionUtil.setValue(decisionTable, 3, 4, BinaryActionValue.DO);

		logger.debug( textGenerator.getText(decisionTable));

		decisionTable.reduce();

		logger.debug( textGenerator.getText(decisionTable));
	}

	public void testReduction2() {
		final IDecisionTable decisionTable = new DecisionTable();

		final TextGenerator textGenerator = GeneratorFactorySPI.getAnyTextGenerator();

		ActionUtil.addCondition(decisionTable);
		ActionUtil.addCondition(decisionTable);
		ActionUtil.addAction(decisionTable);
		decisionTable.split();
		ActionUtil.setValue(decisionTable, 3, 1, BinaryActionValue.DO);

		logger.debug( textGenerator.getText(decisionTable));

		decisionTable.reduce();

		logger.debug( textGenerator.getText(decisionTable));
	}

	public void testReduction3() {
		final IDecisionTable decisionTable = new DecisionTable();

		final TextGenerator textGenerator = GeneratorFactorySPI.getAnyTextGenerator();

		ActionUtil.addCondition(decisionTable);
		ActionUtil.addCondition(decisionTable);
		ActionUtil.addAction(decisionTable);
		decisionTable.split();
		ActionUtil.setValue(decisionTable, 3, 4, BinaryActionValue.DO);

		logger.debug( textGenerator.getText(decisionTable));

		decisionTable.reduce();

		logger.debug( textGenerator.getText(decisionTable));
	}

	public void testReduction4() {
		final IDecisionTable decisionTable = new DecisionTable();

		final TextGenerator textGenerator = GeneratorFactorySPI.getAnyTextGenerator();

		ActionUtil.addCondition(decisionTable);
		ActionUtil.addCondition(decisionTable);
		ActionUtil.addAction(decisionTable);
		decisionTable.split();
		ActionUtil.setValue(decisionTable, 3, 1, BinaryActionValue.DO);
		ActionUtil.setValue(decisionTable, 3, 4, BinaryActionValue.DO);

		logger.debug( textGenerator.getText(decisionTable));

		decisionTable.reduce();

		logger.debug( textGenerator.getText(decisionTable));
	}

	public void testReduction5() {
		final IDecisionTable decisionTable = new DecisionTable();

		final TextGenerator textGenerator = GeneratorFactorySPI.getAnyTextGenerator();

		ActionUtil.addCondition(decisionTable);
		ActionUtil.addCondition(decisionTable);
		ActionUtil.addAction(decisionTable);
		decisionTable.split();
		ActionUtil.setValue(decisionTable, 3, 1, BinaryActionValue.DO);
		ActionUtil.setValue(decisionTable, 3, 2, BinaryActionValue.DO);
		ActionUtil.setValue(decisionTable, 3, 3, BinaryActionValue.DO);
		ActionUtil.setValue(decisionTable, 3, 4, BinaryActionValue.DO);

		logger.debug( textGenerator.getText(decisionTable));

		decisionTable.reduce();

		logger.debug( textGenerator.getText(decisionTable));
	}

	public void testReduction6() {
		final IDecisionTable decisionTable = new DecisionTable();

		final TextGenerator textGenerator = GeneratorFactorySPI.getAnyTextGenerator();

		ActionUtil.addCondition(decisionTable);
		ActionUtil.addCondition(decisionTable);
		ActionUtil.addCondition(decisionTable);
		ActionUtil.addAction(decisionTable);
		decisionTable.split();
		ActionUtil.setValue(decisionTable, 4, 1, BinaryActionValue.DO);
		ActionUtil.setValue(decisionTable, 4, 2, BinaryActionValue.DO);
		ActionUtil.setValue(decisionTable, 4, 3, BinaryActionValue.DO);
		ActionUtil.setValue(decisionTable, 4, 4, BinaryActionValue.DO);

		logger.debug( textGenerator.getText(decisionTable));

		decisionTable.reduce();

		logger.debug( textGenerator.getText(decisionTable));
	}

	public void testReduction7() {
		final IDecisionTable decisionTable = new DecisionTable();

		final TextGenerator textGenerator = GeneratorFactorySPI.getAnyTextGenerator();

		ActionUtil.addCondition(decisionTable);
		ActionUtil.addCondition(decisionTable);
		ActionUtil.addCondition(decisionTable);
		ActionUtil.addAction(decisionTable);
		ActionUtil.addAction(decisionTable);
		decisionTable.split();
		ActionUtil.setValue(decisionTable, 4, 1, BinaryActionValue.DO);
		ActionUtil.setValue(decisionTable, 4, 2, BinaryActionValue.DO);
		ActionUtil.setValue(decisionTable, 4, 3, BinaryActionValue.DO);
		ActionUtil.setValue(decisionTable, 4, 4, BinaryActionValue.DO);
		ActionUtil.setValue(decisionTable, 5, 1, BinaryActionValue.DO);
		ActionUtil.setValue(decisionTable, 5, 2, BinaryActionValue.DO);
		ActionUtil.setValue(decisionTable, 5, 5, BinaryActionValue.DO);
		ActionUtil.setValue(decisionTable, 5, 6, BinaryActionValue.DO);

		logger.debug( textGenerator.getText(decisionTable));

		decisionTable.reduce();

		logger.debug( textGenerator.getText(decisionTable));
	}

	public void testEmptyReduction() {
		final IDecisionTable decisionTable = new DecisionTable();

		final TextGenerator textGenerator = GeneratorFactorySPI.getAnyTextGenerator();

		ActionUtil.addCondition(decisionTable);
		ActionUtil.addAction(decisionTable);

		decisionTable.reduce();

		logger.debug( textGenerator.getText(decisionTable));
	}
}
