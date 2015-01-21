package dt.generators.excel;

import java.io.Serializable;

import jdt.core.binary.BinaryActionValue;
import jdt.core.binary.BinaryConditionValue;
import jdt.icore.IValue;

import org.apache.commons.lang.builder.ToStringBuilder;
import org.apache.commons.lang.builder.ToStringStyle;


public class ExcelTableOptionsWrapper implements Serializable {
	/**
	 *
	 */
	private static final long serialVersionUID = 4649208529586279970L;
	private final ExcelTableOptions tableOptions;

	public ExcelTableOptionsWrapper( final boolean fBorders, final boolean fConditionActionSeparator,
			final boolean fWrapping, final int wrapLength) {
		tableOptions = new ExcelTableOptions( fBorders);
	}

	public ExcelTableOptionsWrapper( final ExcelTableOptions asciiTableOptions) {
		this.tableOptions = asciiTableOptions;
	}

	public String getCrossing() {
		return tableOptions.isFBorders() ? "+" : "";
	}

	public String getVerticalSeparator() {
		return tableOptions.isFBorders() ? "|" : "";
	}

	public String getHorizontalSeparator() {
		return tableOptions.isFBorders() ? "-" : "";
	}

	public String getBackgroundChar() {
		return " ";
	}

	public String getConditionChar( final IValue value) {
		final String valueChar;
		if( value instanceof BinaryConditionValue) {
			final BinaryConditionValue binaryConditionValue = (BinaryConditionValue) value;
			switch( binaryConditionValue.getValue()) {
				case IRRELEVANT: valueChar = "-"; break;
				case YES: valueChar = "Y"; break;
				case NO: valueChar = "N"; break;
				default: valueChar = "?";
			}
		} else {
			valueChar = "?";
		}
		return valueChar;
	}

	public String getActionChar( final IValue value) {
		final String valueChar;
		if( value instanceof BinaryActionValue) {
			final BinaryActionValue binaryActionValue = (BinaryActionValue) value;
			switch( binaryActionValue.getValue()) {
				case DO: valueChar = "X"; break;
				case DONT: valueChar = " "; break;
				default: valueChar = "?";
			}
		} else {
			valueChar = "?";
		}
		return valueChar;
	}

	@Override
	public String toString() {
		return ToStringBuilder.reflectionToString( this, ToStringStyle.MULTI_LINE_STYLE);
	}

	public void setFBorders(final boolean borders) {
		tableOptions.setFBorders( borders);
	}

	public ExcelTableOptions getTableOptions() {
		return tableOptions;
	}
}
