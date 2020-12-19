package jdt.core.generators.junit;

import dt.generators.GeneratorFactorySPI;
import dt.generators.TextGenerator;
import dt.generators.ascii.AsciiTableOptions;
import jdt.core.junit.AbstractTestCase;
import org.taHjaj.wo.jenoptron.model.icore.IDecisionTable;

public abstract class AbstractGeneratorTestCase extends AbstractTestCase {

	public AbstractGeneratorTestCase() {
		super();
	}

	public AbstractGeneratorTestCase(final String arg0) {
		super(arg0);
	}

	@Override
	protected void setUp() throws Exception {
		super.setUp();
	}

	@Override
	protected void tearDown() throws Exception {
		super.tearDown();
	}

	protected static TextGenerator getGenerator( final IDecisionTable decisionTable) {
		final TextGenerator textGenerator =
			GeneratorFactorySPI.getTextGenerator( "ASCII");

		textGenerator.setDecisionTable( decisionTable);
		textGenerator.getGeneratorOptions().copy(
				new AsciiTableOptions( true, true, false, 20));

		return textGenerator;
	}

	protected static TextGenerator getGenerator( final IDecisionTable decisionTable,
				final AsciiTableOptions asciiTableOptions) {
		final TextGenerator textGenerator =
			GeneratorFactorySPI.getAnyTextGenerator();

		textGenerator.setDecisionTable( decisionTable);
		textGenerator.getGeneratorOptions().copy( asciiTableOptions);

		return textGenerator;
	}
}
