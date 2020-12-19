package dt.generators.excel;

import java.io.FileFilter;
import java.io.IOException;
import java.io.Writer;

import org.taHjaj.wo.jenoptron.model.icore.IAction;
import org.taHjaj.wo.jenoptron.model.icore.ICondition;
import org.taHjaj.wo.jenoptron.model.icore.IConditionValue;
import org.taHjaj.wo.jenoptron.model.icore.IDecisionTable;
import org.taHjaj.wo.jenoptron.model.icore.IRule;
import org.taHjaj.wo.jenoptron.model.icore.IValue;
import dt.generators.Generator;
import dt.generators.GeneratorOptions;
import dt.generators.TextGenerator;

public class ExcelGenerator implements TextGenerator {

	/**
	 *
	 */
	private static final long serialVersionUID = -670292186612685316L;

	@Override
	public String getText(final IDecisionTable decisionTable) {

		final StringBuffer stringBuffer = new StringBuffer();
		
		for( final ICondition condition : decisionTable.getConditions()) {
			stringBuffer.append( condition.getShortDescription());
			for( final IRule rule : decisionTable.getRules()) {
				final IConditionValue conditionValue = rule.getConditionValue( condition);
				stringBuffer.append( '\t').append( conditionValue);
			}
			stringBuffer.append( '\n');
		}

		for( final IAction action : decisionTable.getActions()) {
			stringBuffer.append( action.getShortDescription());
			for( final IRule rule : decisionTable.getRules()) {
				final IValue value = rule.getActionValue(action);
				stringBuffer.append( '\t').append( value);
			}
			stringBuffer.append( '\n');
		}

		return stringBuffer.toString();
	}

	@Override
	public String getSuffix() {
		return ".txt";
	}

	public boolean write(final Writer writer) throws IOException {
		writer.write( getText());
		return true;
	}

	@Override
	public FileFilter getFileFilter() {
		return null;
	}

	private final ExcelTableOptionsWrapper excelTableOptionsWrapper;
	private IDecisionTable decisionTable;

	@Override
	public String getText() {
		return getText( this.decisionTable);
	}

	@Override
	public GeneratorOptions getGeneratorOptions() {
		return excelTableOptionsWrapper.getTableOptions();
	}

	public ExcelGenerator() {
		super();
		excelTableOptionsWrapper = new ExcelTableOptionsWrapper( false, false, false, 20);
	}

	public String getShortDescription() {
		return "Excel";
	}

	public Generator setDecisionTable( final IDecisionTable decisionTable) {
		this.decisionTable = decisionTable;
		return this;
	}
}

