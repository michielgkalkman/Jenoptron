package dt.generators;

import java.io.Serializable;

import org.taHjaj.wo.jenoptron.model.icore.IDecisionTable;


public interface TextGenerator extends Generator, Serializable {
	public String getText();
	public String getText( final IDecisionTable decisionTable);
}
