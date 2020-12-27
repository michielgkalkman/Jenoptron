package org.taHjaj.wo.jenoptron.generators.basic;

import java.io.FileFilter;
import java.io.IOException;
import java.io.Writer;

import org.taHjaj.wo.jenoptron.model.icore.IDecisionTable;

public interface Generator {
	public Generator setDecisionTable( final IDecisionTable decisionTable);
	public String getShortDescription();
	public GeneratorOptions getGeneratorOptions();

	public FileFilter getFileFilter();
	public String getSuffix();
	public boolean write( final Writer writer) throws IOException;
}
