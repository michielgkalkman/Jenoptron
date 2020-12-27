package org.taHjaj.wo.jenoptron.generators.basic.ascii;

import org.apache.commons.lang.StringUtils;
import org.apache.commons.lang.WordUtils;
import org.taHjaj.wo.jenoptron.model.core.binary.BinaryActionValue;
import org.taHjaj.wo.jenoptron.model.core.binary.BinaryConditionValue;
import org.taHjaj.wo.jenoptron.model.icore.*;

/**
 *
 * @author Michiel Kalkman
 */
class AsciiTable {
	private AsciiTable() {
		super();
	}

	public static final String toString(final IDecisionTable decisionTable, final boolean fBorders) {
		final AsciiTableOptionsWrapper asciiTableOptionsWrapper = new AsciiTableOptionsWrapper(
				fBorders, true, false, 20);
		return toString( decisionTable, asciiTableOptionsWrapper);
	}

	public static final String toString(final IDecisionTable decisionTable) {
		final AsciiTableOptionsWrapper asciiTableOptionsWrapper = new AsciiTableOptionsWrapper(
				true, true, false, 20);
		return toString( decisionTable, asciiTableOptionsWrapper);
	}

	public static final String toString(final IDecisionTable decisionTable, final AsciiTableOptions asciiTableOptions) {
		return toString( decisionTable, new AsciiTableOptionsWrapper( asciiTableOptions));
	}

	public static final String toString(final IDecisionTable decisionTable, final AsciiTableOptionsWrapper asciiTableOptionsWrapper) {
		final Iterable< IRule> rules = decisionTable.getLegalRulesIterable();

		final int[] columnWidths = determineColumnWidths(decisionTable, asciiTableOptionsWrapper);

		final StringBuffer stringBuffer = new StringBuffer();

		addConditions(decisionTable, rules, columnWidths, stringBuffer, asciiTableOptionsWrapper);

		if( asciiTableOptionsWrapper.hasConditionActionSeparator()) {
			addInterlacingLine( rules, columnWidths, stringBuffer,
				asciiTableOptionsWrapper.getConditionActionSeparator(), asciiTableOptionsWrapper.getConditionActionSeparator());
		}

		addActions(decisionTable, rules, columnWidths, stringBuffer, asciiTableOptionsWrapper);

		return stringBuffer.toString();
	}

	private static void addActions(final IDecisionTable decisionTable, final Iterable< IRule> rules,
		final int[] columnWidths, final StringBuffer stringBuffer, final AsciiTableOptionsWrapper asciiTableOptionsWrapper) {
		for( final IAction action : decisionTable.getActions()) {

			final String[] actionLines;
			if( asciiTableOptionsWrapper.isWrapping()) {
				final String wrapped = WordUtils.wrap( action.getShortDescription(),
						asciiTableOptionsWrapper.getWrapLength(),
						"\n",
						true);
				actionLines = org.apache.commons.lang.StringUtils.split( wrapped, '\n');
			} else {
				actionLines = new String[] { action.getShortDescription() };
			}

			boolean fShown = false;
			for( final String actionLine : actionLines) {
				addActionLine(rules, columnWidths, stringBuffer, asciiTableOptionsWrapper, action, actionLine, ! fShown);
				fShown = true;
			}

			addInterlacingLine( rules, columnWidths, stringBuffer, asciiTableOptionsWrapper);
		}
	}

	private static void addActionLine(final Iterable<IRule> rules, final int[] columnWidths, final StringBuffer stringBuffer, final AsciiTableOptionsWrapper asciiTableOptionsWrapper, final IAction action, final String actionText,
					final boolean fShowValues) {
		{
			// A Action Line. | blabla |X| |X| ...
			stringBuffer.append( asciiTableOptionsWrapper.getVerticalSeparator()) // edge.
				.append( processShortDescription(columnWidths, actionText));
			final String backgroundChar = asciiTableOptionsWrapper.getBackgroundChar();
			for( final IRule rule : rules) {
				final String value;
				if( fShowValues) {
					value = asciiTableOptionsWrapper.getActionChar( rule.getActionValue( action));
				} else {
					value = backgroundChar;
				}
				stringBuffer.append( asciiTableOptionsWrapper.getVerticalSeparator()).append( value);
			}

			stringBuffer.append( asciiTableOptionsWrapper.getVerticalSeparator() + "\n"); // other edge.
		}
	}

	private static String processShortDescription(final int[] columnWidths, final String actionText) {
		return StringUtils.rightPad( actionText, columnWidths[0]);
//		return StringUtils.center( actionText, columnWidths[0]);
	}

	private static void addConditions(final IDecisionTable decisionTable, final Iterable< IRule> rules,
		final int[] columnWidths, final StringBuffer stringBuffer, final AsciiTableOptionsWrapper asciiTableOptionsWrapper) {
		for( final ICondition condition : decisionTable.getConditions()) {
			addInterlacingLine( rules, columnWidths, stringBuffer, asciiTableOptionsWrapper);

			final String[] conditionLines;
			if( asciiTableOptionsWrapper.isWrapping()) {
				final String wrapped = WordUtils.wrap( condition.getShortDescription(),
						asciiTableOptionsWrapper.getWrapLength(),
						"\n",
						true);
				conditionLines = StringUtils.split( wrapped, '\n');
			} else {
				conditionLines = new String[] { condition.getShortDescription() };
			}

			boolean fShown = false;
			for( final String conditionLine : conditionLines) {
				addConditionLine(rules, columnWidths, stringBuffer, asciiTableOptionsWrapper, condition, conditionLine, ! fShown);
				fShown = true;
			}
		}
	}

	private static void addConditionLine(final Iterable<IRule> rules, final int[] columnWidths, final StringBuffer stringBuffer, final AsciiTableOptionsWrapper asciiTableOptionsWrapper, final ICondition condition, final String conditionText,
					final boolean fShowValues) {
		{
			// A Condition Line. | blabla |Y|-|N| ...
			stringBuffer.append( asciiTableOptionsWrapper.getVerticalSeparator()) // edge.
				.append( processShortDescription(columnWidths, conditionText));
			final String backgroundChar = asciiTableOptionsWrapper.getBackgroundChar();
			for( final IRule rule : rules) {
				final String value;
				if( fShowValues) {
					value = asciiTableOptionsWrapper.getConditionChar( rule.getConditionValue( condition));
				} else {
					value = backgroundChar;
				}
				stringBuffer.append( asciiTableOptionsWrapper.getVerticalSeparator()).append( value);
			}

			stringBuffer.append( asciiTableOptionsWrapper.getVerticalSeparator() + "\n"); // other edge.
		}
	}

	private static int[] determineColumnWidths(final IDecisionTable decisionTable,
				final AsciiTableOptionsWrapper asciiTableOptionsWrapper) {
		final int[] columnWidths = new int[decisionTable.getRules().size()+1];

		for( final ICondition condition : decisionTable.getConditions()) {
			final int length = condition.getShortDescription().length();
			if( length > columnWidths[0]) {
				columnWidths[0] = length;
			}
		}


		for( final IAction action : decisionTable.getActions()) {
			final int length = action.getShortDescription().length();
			if( length > columnWidths[0]) {
				columnWidths[0] = length;
			}
		}

		if( asciiTableOptionsWrapper.isFWrapping()) {
			final int wrapLength = asciiTableOptionsWrapper.getWrapLength();
			if( columnWidths[0] > wrapLength) {
				columnWidths[0] = wrapLength;
			}
		}

		for( int i=1;i<columnWidths.length; i++) {
			columnWidths[i]=1;
		}
		return columnWidths;
	}

	private static void addInterlacingLine(final Iterable< IRule> rules,
			final int[] columnWidths, final StringBuffer stringBuffer, final AsciiTableOptionsWrapper asciiTableOptionsWrapper) {
		addInterlacingLine( rules, columnWidths, stringBuffer, asciiTableOptionsWrapper.getHorizontalSeparator(),
				asciiTableOptionsWrapper.getCrossing());
	}

	private static void addInterlacingLine( final Iterable< IRule> rules,
			final int[] columnWidths, final StringBuffer stringBuffer,
			final String separator, final String crossing) {
		{
			int column = 0;
			// Line 1. +---+------+---+ ....
			stringBuffer.append( crossing) // corner.
				.append( StringUtils.repeat( separator, columnWidths[column]));
			for(final IRule rule : rules) {
				// TODO: volgende regel is alleen weggehaald omdat anders een fout wordt gerapporteerd
				// omdat de variabele rule niet gebruikt wordt.
				rule.getClass();
				column++;
				stringBuffer.append( crossing).append( StringUtils.repeat( separator, columnWidths[column]));
			}

			stringBuffer.append( crossing + "\n"); // other corner.
		}
	}

	public static final String toString( final IValue value) {
		String valueString = "?";

		if( value instanceof BinaryConditionValue) {
			final BinaryConditionValue binaryConditionValue = (BinaryConditionValue) value;
			if( binaryConditionValue.getValue() == BinaryConditionValue.Values.IRRELEVANT) {
				valueString = "-";
			} else if( binaryConditionValue.getValue() == BinaryConditionValue.Values.NO) {
				valueString = "N";
			} else if( binaryConditionValue.getValue() == BinaryConditionValue.Values.YES) {
				valueString = "Y";
			}
		} else if( value instanceof BinaryActionValue) {
			final BinaryActionValue binaryActionValue = (BinaryActionValue) value;
			if( binaryActionValue.getValue() == BinaryActionValue.Values.DO) {
				valueString = "X";
			} else if( binaryActionValue.getValue() == BinaryActionValue.Values.DONT) {
				valueString = " ";
			}
		}

		return valueString;
	}
}
