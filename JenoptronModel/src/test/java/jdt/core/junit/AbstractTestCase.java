package jdt.core.junit;

import java.util.ArrayList;
import java.util.List;

import jdt.core.binary.BinaryCondition;
import jdt.icore.ICondition;
import jdt.icore.IDecisionTable;
import junit.framework.TestCase;

import org.apache.log4j.BasicConfigurator;

public abstract class AbstractTestCase extends TestCase {

	public AbstractTestCase() {
		super();
	}

	public AbstractTestCase(final String arg0) {
		super(arg0);
	}

	@Override
	protected void setUp() throws Exception {
		super.setUp();
		BasicConfigurator.configure();
	}

	@Override
	protected void tearDown() throws Exception {
		super.tearDown();
		BasicConfigurator.resetConfiguration();
	}

	protected List<? extends ICondition> getConditionList(final String ... conditions) {
		final List<BinaryCondition> binaryConditions = new ArrayList<BinaryCondition>();
		for( final String condition : conditions) {
			binaryConditions.add( new BinaryCondition( condition));
		}

		return binaryConditions;
	}
	
	protected String dump( final IDecisionTable decisionTable) {
		return decisionTable.simpleDump().toString();
	}
}
