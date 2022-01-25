package org.taHjaj.wo.jenoptron.generators.spi;

import org.taHjaj.wo.jenoptron.model.icore.IDecisionTable;

import java.io.FileFilter;
import java.io.IOException;
import java.io.Writer;

public interface Generator {
	Generator setDecisionTable(final IDecisionTable decisionTable);
	String getShortDescription();
	GeneratorOptions getGeneratorOptions();

	FileFilter getFileFilter();
	String getSuffix();
	boolean write( final Writer writer) throws IOException;
}
