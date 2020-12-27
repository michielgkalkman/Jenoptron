package org.taHjaj.wo.jenoptron.generators.basic.ascii;

import org.apache.commons.lang.builder.ToStringBuilder;
import org.apache.commons.lang.builder.ToStringStyle;
import org.taHjaj.wo.jenoptron.model.core.binary.BinaryActionValue;
import org.taHjaj.wo.jenoptron.model.core.binary.BinaryConditionValue;
import org.taHjaj.wo.jenoptron.model.icore.IValue;

import java.io.Serializable;


public class AsciiTableOptionsWrapper implements Serializable {
	/**
	 *
	 */
	private static final long serialVersionUID = 4649208529586279970L;
	private final AsciiTableOptions asciiTableOptions;

	public AsciiTableOptionsWrapper( final boolean fBorders, final boolean fConditionActionSeparator,
			final boolean fWrapping, final int wrapLength) {
		asciiTableOptions = new AsciiTableOptions( fBorders, fConditionActionSeparator, fWrapping, wrapLength);
	}

	public AsciiTableOptionsWrapper( final AsciiTableOptions asciiTableOptions) {
		this.asciiTableOptions = asciiTableOptions;
	}

	public String getCrossing() {
		return asciiTableOptions.isFBorders() ? "+" : "";
	}

	public String getConditionActionSeparator() {
		return asciiTableOptions.isFConditionActionSeparator() ? "=" : "";
	}

	public boolean hasConditionActionSeparator() {
		return asciiTableOptions.isFConditionActionSeparator();
	}

	public String getVerticalSeparator() {
		return asciiTableOptions.isFBorders() ? "|" : "";
	}

	public String getHorizontalSeparator() {
		return asciiTableOptions.isFBorders() ? "-" : "";
	}

	public boolean isWrapping() {
		return asciiTableOptions.isFWrapping();
	}

	public int getWrapLength() {
		return asciiTableOptions.getWrapLength();
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
		asciiTableOptions.setFBorders( borders);
	}

	public boolean isFConditionActionSeparator() {
		return asciiTableOptions.isFConditionActionSeparator();
	}

	public void setFConditionActionSeparator(final boolean conditionActionSeparator) {
		asciiTableOptions.setFConditionActionSeparator( conditionActionSeparator);
	}

	public boolean isFWrapping() {
		return asciiTableOptions.isFWrapping();
	}

	public void setFWrapping(final boolean wrapping) {
		asciiTableOptions.setFWrapping(wrapping);
	}

	public void setWrapLength(final int wrapLength) {
		asciiTableOptions.setWrapLength( wrapLength);
	}

	public AsciiTableOptions getAsciiTableOptions() {
		return asciiTableOptions;
	}
}
