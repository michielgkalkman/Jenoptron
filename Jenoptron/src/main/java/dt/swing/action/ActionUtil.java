package dt.swing.action;

import java.util.List;

import jdt.core.binary.BinaryAction;
import jdt.core.binary.BinaryCondition;
import jdt.icore.IAction;
import jdt.icore.ICondition;
import jdt.icore.IDecisionTable;
import jdt.icore.IRule;
import jdt.icore.IValue;

/**
 * Utilities supporting the action in the application.
 *
 * <p>
 * Variable <code>rowNr</code> refers to the row that needs to be accessed. It start at zero (0).
 * Row nr. 0 identifies the rules.
 * </p>
 *
 * <p>
 * Variable <code>columnNr</code> refers to the column that needs to be accessed. It start at one (0).
 * Column nr. 0 identifies conditions and actions. The cell at row nr. 0 and column nr. 0 is always empty.
 * </p>
 *
 * @author Michiel Kalkman
 *
 */
public class ActionUtil {
	private static final int INDEX_TEXT_COL_SIZE = 1;   // There is 1 column dedicated to texts for conditions and actions.
	private static final int INDEX_TEXT_COL_NR = 0;  // Column 0 is dedicated to texts for conditions and actions.
	private static final int RULE_TEXT_ROW_SIZE = 1;    // There is 1 column dedicated to texts identifying rules.
	private static final int RULE_TEXT_ROW_NR = 0;   // Row 0 is dedicated to texts identifying rules.

	private ActionUtil() {
		super();
	}

	public static final BinaryCondition createBinaryCondition( final IDecisionTable decisionTable) {
		final String shortDescription = "condition " + (decisionTable.getConditions().size()+1);
		return new BinaryCondition( shortDescription);
	}

	public static final BinaryAction createBinaryAction( final IDecisionTable decisionTable) {
		final String shortDescription = "action " + (decisionTable.getActions().size()+1);
		return new BinaryAction( shortDescription);
	}

	public static void addAction( final IDecisionTable decisionTable) {
		decisionTable.add( ActionUtil.createBinaryAction(decisionTable));
	}

	public static void addCondition( final IDecisionTable decisionTable) {
		decisionTable.add( ActionUtil.createBinaryCondition(decisionTable));
	}

	public static void addAction( final IDecisionTable decisionTable, final int rowNr) {
		// If no row has been selected, add an action to the bottom

		if( rowNr == -1) {
			// Add action to the bottom.
			decisionTable.insert(
					decisionTable.nrActions(), createBinaryAction(decisionTable));
		} else {
			checkrowNr( decisionTable, rowNr);
			decisionTable.insert( (rowNr-(decisionTable.nrConditions()+RULE_TEXT_ROW_SIZE))+1, createBinaryAction(decisionTable));
		}
	}

	public static void addCondition( final IDecisionTable decisionTable, final int rowNr) {
		if( rowNr == -1) {
			// Add action to the bottom.
			decisionTable.insert(
					decisionTable.nrConditions(), createBinaryCondition(decisionTable));
		} else {
			checkrowNr( decisionTable, rowNr);
			decisionTable.insert( (rowNr-RULE_TEXT_ROW_SIZE)+1, createBinaryCondition(decisionTable));
		}
	}

	public static void insertAction( final IDecisionTable decisionTable, final int rowNr) {
		checkrowNr( decisionTable, rowNr);
		decisionTable.insert( rowNr-(decisionTable.nrConditions()+RULE_TEXT_ROW_SIZE), createBinaryAction(decisionTable));
	}

	public static void insertCondition( final IDecisionTable decisionTable, final int rowNr) {
		checkrowNr( decisionTable, rowNr);
		decisionTable.insert( rowNr-RULE_TEXT_ROW_SIZE, createBinaryCondition(decisionTable));
	}

	public static void deleteRow( final IDecisionTable decisionTable, final int rowNr) {
		final int nrConditions = decisionTable.nrConditions();
		if( isActionRow( rowNr, nrConditions)) {
			final IAction action = getAction( decisionTable, rowNr);
			decisionTable.remove( action);
		} else if( isConditionRow( rowNr, nrConditions)) {
			final ICondition condition = getCondition( decisionTable, rowNr);
			decisionTable.remove( condition);
		}
	}

	public static void removeAction( final IDecisionTable decisionTable, final int rowNr) {
		checkrowNr( decisionTable, rowNr);
		final IAction action = getAction( decisionTable, rowNr);
		decisionTable.remove( action);
	}

	public static void removeCondition( final IDecisionTable decisionTable, final int rowNr) {
		checkrowNr( decisionTable, rowNr);
		final ICondition condition = getCondition( decisionTable, rowNr);
		decisionTable.remove( condition);
	}

	public static final String getValue( final IDecisionTable decisionTable, final int rowNr, final int columnNr) {
		String value;

		try {
			checkrowNr( decisionTable, rowNr);
			checkcolumnNr( decisionTable, columnNr);

			if (rowNr == RULE_TEXT_ROW_NR) {
				if (columnNr == INDEX_TEXT_COL_NR) {
					value = "";
				} else {
					value = Integer.toString( toRuleNr( decisionTable, columnNr));
				}
			} else {
				final List<ICondition> conditions = decisionTable.getConditions();
				final int nrConditions = conditions.size();

				if (columnNr == INDEX_TEXT_COL_NR) {
					if ( isActionRow(rowNr, nrConditions)) {
						value = getAction(decisionTable, rowNr).getShortDescription();
					} else if( isConditionRow( rowNr, nrConditions)) {
						value = getCondition(decisionTable, rowNr).getShortDescription();
					} else {
						value = "";
					}
				} else {
					if ( isActionRow(rowNr, nrConditions)) {
						value = getRule(decisionTable, columnNr)
								.getActionValue( getAction(decisionTable, rowNr))
								.toString();
					} else {
						final ICondition condition = getCondition(decisionTable, rowNr);
						final IValue conditionValue = getRule( decisionTable, columnNr)
								.getConditionValue( condition);
						value = conditionValue.toString();
					}
				}
			}
		} catch( final Exception exception) {
			value = "";
		}

		return value;
	}
	public static final void setValue( final IDecisionTable decisionTable, final int rowNr, final Object aValue) {
		final int nrConditions = decisionTable.getConditions().size();
		if( isActionRow(rowNr, nrConditions)) {
			for( int columnNr=1; columnNr<=decisionTable.getRules().size(); columnNr++) {
				setValue(decisionTable, rowNr, columnNr, aValue);
			}
		}
	}
	public static final void setValue( final IDecisionTable decisionTable, final int rowNr, final int columnNr, final Object aValue) {
		checkrowNr( decisionTable, rowNr);
		checkcolumnNr( decisionTable, columnNr);

		final int nrConditions = decisionTable.nrConditions();

		if( (columnNr > INDEX_TEXT_COL_NR) && isActionRow(rowNr, nrConditions)) {
			// An action.
			final IRule rule = getRule(decisionTable, columnNr);
			final IAction action = getAction(decisionTable, rowNr);

			rule.setActionValue( action, createValue( action, aValue));
		} else if( columnNr == INDEX_TEXT_COL_NR) {
			// An action or condition text.
			if( isActionRow(rowNr, nrConditions)) {
				getAction( decisionTable, rowNr).setShortDescription( (String) aValue);
			} else if( isConditionRow(rowNr, nrConditions)) {
				getCondition( decisionTable, rowNr).setShortDescription( (String) aValue);
			}
		}
	}

	public static final IAction getAction( final IDecisionTable decisionTable, final int rowNr) {
		final List<ICondition> conditions = decisionTable.getConditions();
		final int nrConditions = conditions.size();

		return getAction(decisionTable, rowNr, nrConditions);
	}

	private static final IAction getAction( final IDecisionTable decisionTable, final int rowNr, final int nrConditions) {
		return decisionTable.getActions().get( (rowNr - nrConditions) - RULE_TEXT_ROW_SIZE);
	}

	private static final ICondition getCondition( final IDecisionTable decisionTable, final int rowNr) {
		return getCondition( decisionTable, rowNr, decisionTable.nrConditions());
	}

	private static final ICondition getCondition( final IDecisionTable decisionTable,  final int rowNr, final int nrConditions) {
		checkConditionRowNr(rowNr, nrConditions);
		return decisionTable.getConditions().get( rowNr-RULE_TEXT_ROW_SIZE);
	}

	private static void checkConditionRowNr(final int rowNr, final int nrConditions) {
		if( (rowNr < RULE_TEXT_ROW_NR) || (rowNr > RULE_TEXT_ROW_SIZE + nrConditions)) {
			throw new IllegalArgumentException( "rowNr = " + rowNr +", must be > " + RULE_TEXT_ROW_SIZE
								+  " and <= "
						+ nrConditions + '.');
		}
	}

	private static IRule getRule(final IDecisionTable decisionTable, final int columnNr) {
		checkRuleNr(decisionTable, columnNr);

		return decisionTable.getRule(columnNr - INDEX_TEXT_COL_SIZE);
	}

	private static boolean isActionRow(final int rowNr, final int nrConditions) {
		return rowNr >= RULE_TEXT_ROW_SIZE + nrConditions;
	}

	private static boolean isConditionRow(final int rowNr, final int nrConditions) {
		return (rowNr > RULE_TEXT_ROW_NR) && (rowNr <= RULE_TEXT_ROW_NR + nrConditions);
	}

	private static int toRuleNr( final IDecisionTable decisionTable, final int columnNr) {
		checkRuleNr(decisionTable, columnNr);
		return columnNr;
	}

	private static void checkRuleNr(final IDecisionTable decisionTable, final int columnNr) {
		if( columnNr <= INDEX_TEXT_COL_NR) {
			throw new IllegalArgumentException( "columnNr = " + columnNr +", must be larger than zero.");
		}
		if( columnNr > decisionTable.nrRules()) {
			throw new IllegalArgumentException( "columnNr = " + columnNr +", must be > " + INDEX_TEXT_COL_NR
						+ " and <= "
						+ decisionTable.nrRules() + '.');
		}
	}

	private static void checkcolumnNr( final IDecisionTable decisionTable, final int columnNr) {
		if( columnNr < INDEX_TEXT_COL_NR) {
			throw new IllegalArgumentException( "columnNr = " + columnNr +", must be larger than or equal to zero.");
		}
		final int maxColumns = decisionTable.nrRules()+INDEX_TEXT_COL_SIZE;
		if( columnNr > maxColumns ) {
			throw new IllegalArgumentException( "columnNr = " + columnNr +", can currently not be greater than " + maxColumns);
		}
	}

	private static void checkrowNr( final IDecisionTable decisionTable, final int rowNr) {
		if( rowNr < RULE_TEXT_ROW_NR) {
			throw new IllegalArgumentException( "rowNr = " + rowNr +", must be larger than or equal to zero.");
		}
		final int maxRows = decisionTable.nrConditions()+decisionTable.nrActions()+RULE_TEXT_ROW_SIZE-1;
		if( rowNr > maxRows) {
			throw new IllegalArgumentException( "rowNr = " + rowNr +", can currently not be greater than " + maxRows);
		}
	}

	private static IValue createValue( final IAction action, final Object aValue) {
		final IValue value;

		if (aValue instanceof String) {
			final String string = (String) aValue;
			value = action.parse( string);
		} else if (aValue instanceof IValue) {
			value = (IValue) aValue;
		} else {
			value = action.getUnknownValue();
		}

		return value;
	}

	public static void down( final IDecisionTable decisionTable, final int rowNr) {
		checkrowNr( decisionTable, rowNr);
		if( isActionRow( rowNr, decisionTable.nrConditions())) {
			final IAction action = getAction(decisionTable, rowNr);
			decisionTable.down( action);
		} else {
			final ICondition condition = getCondition( decisionTable, rowNr);
			decisionTable.down( condition);
		}
	}

	public static void up( final IDecisionTable decisionTable, final int rowNr) {
		checkrowNr( decisionTable, rowNr);
		if( isActionRow( rowNr, decisionTable.nrConditions())) {
			final IAction action = getAction(decisionTable, rowNr);
			decisionTable.up( action);
		} else {
			final ICondition condition = getCondition( decisionTable, rowNr);
			decisionTable.up( condition);
		}
	}

	public static IDecisionTable subtable( final IDecisionTable decisionTable, final int[] rows) {
		int nrConditions = 0;

		for (final int element : rows) {
			if( element < decisionTable.nrConditions()+1) {
				nrConditions++;
			}
		}

		final int[] conditionRows = new int[nrConditions];
		final int[] actionRows = new int[rows.length - nrConditions];

		return decisionTable.getSubtable( conditionRows, actionRows);
	}

	public static void deleteUnselectedRows( final int[] selectedRows, final IDecisionTable decisionTable) {

		int selectedRowIndex = selectedRows.length-1;

		final int rowCount = decisionTable.getConditions().size() + decisionTable.getActions().size();
		for( int i=rowCount; i>0; i--) {
			if( (selectedRowIndex >= 0) && (selectedRows[selectedRowIndex] == i)) {
				// row found the has been selected.
				selectedRowIndex--;
			} else {
				// row found that has not been selected.
				deleteRow( decisionTable, i);
			}
		}
	}
}
