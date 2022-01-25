package org.taHjaj.wo.jenoptron.generators.spi;

import org.taHjaj.wo.jenoptron.model.icore.IDecisionTable;

import java.io.Serializable;


public interface TextGenerator extends Generator, Serializable {
	String getText();
	String getText( final IDecisionTable decisionTable);
}
