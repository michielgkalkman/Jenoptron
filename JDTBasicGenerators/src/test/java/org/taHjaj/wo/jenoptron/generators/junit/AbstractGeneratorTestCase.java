package org.taHjaj.wo.jenoptron.generators.junit;

import org.taHjaj.wo.jenoptron.generators.basic.GeneratorFactorySPI;
import org.taHjaj.wo.jenoptron.generators.basic.TextGenerator;
import org.taHjaj.wo.jenoptron.generators.basic.ascii.AsciiTableOptions;
import org.taHjaj.wo.jenoptron.model.icore.IDecisionTable;
import org.taHjaj.wo.jenoptron.model.junit.AbstractTestCase;

public abstract class AbstractGeneratorTestCase extends AbstractTestCase {
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
