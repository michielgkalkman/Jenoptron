

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.Writer;

import jdt.core.DecisionTable;
import jdt.core.binary.BinaryActionValue;
import jdt.core.junit.AbstractTestCase;
import jdt.icore.IDecisionTable;

import org.apache.commons.io.IOUtils;

import dt.generators.Generator;
import dt.generators.GeneratorFactorySPI;
import dt.swing.action.ActionUtil;

public class TestJavaClassGenerator extends AbstractTestCase {
//	private static final Logger logger = Logger.getLogger( TestJavaClassGenerator.class);

	public void testInsertCondition() {
		final IDecisionTable decisionTable = new DecisionTable();

		final Generator generator = GeneratorFactorySPI.getAnyGenerator( "java");

		ActionUtil.addCondition(decisionTable);
		decisionTable.reduce();
		ActionUtil.insertCondition(decisionTable, 1);
		ActionUtil.getValue( decisionTable, 0, 0);

		ActionUtil.setValue(decisionTable, 1, 1, BinaryActionValue.DO);

		ActionUtil.addAction(decisionTable);

		ActionUtil.setValue(decisionTable, 3, 1, BinaryActionValue.DO);

		decisionTable.split();

		ActionUtil.setValue(decisionTable, 3, 1, BinaryActionValue.DONT);

		decisionTable.split();

		decisionTable.reduce();

		decisionTable.split();

		decisionTable.reduce();

		generator.setDecisionTable( decisionTable);

		Writer writer = null;

		try {
			writer = new FileWriter( File.createTempFile( "test", generator.getSuffix()));
			assertEquals( "could not write java file", true, generator.write( writer));
		} catch( final IOException exception) {
			fail( exception.getLocalizedMessage());
		} finally {
			if( writer != null) {
				IOUtils.closeQuietly( writer);
			}
		}

	}
}
