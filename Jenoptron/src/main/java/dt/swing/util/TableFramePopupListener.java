/*
 * Created on Jul 31, 2005
 *
 * TODO To change the template for this generated file go to
 * Window - Preferences - Java - Code Style - Code Templates
 */
package dt.swing.util;

import java.awt.Point;
import java.awt.event.ActionEvent;
import java.awt.event.KeyEvent;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

import javax.swing.JMenuItem;
import javax.swing.JPopupMenu;
import javax.swing.KeyStroke;

import jdt.icore.IDecisionTable;
import dt.swing.DTTable;
import dt.swing.action.AddActionAction;
import dt.swing.action.AddCategoriesAction;
import dt.swing.action.AddConditionAction;
import dt.swing.action.AddRestrictionAction;
import dt.swing.action.CopyDefaultTextAction;
import dt.swing.action.DeleteAction;
import dt.swing.action.DisplayAction;
import dt.swing.action.DoAction;
import dt.swing.action.DontAction;
import dt.swing.action.DownActionAction;
import dt.swing.action.DownConditionAction;
import dt.swing.action.InsertActionAction;
import dt.swing.action.InsertConditionAction;
import dt.swing.action.JenoptronAction;
import dt.swing.action.PropertyAction;
import dt.swing.action.ReduceAction;
import dt.swing.action.SaveAction;
import dt.swing.action.SaveAsAction;
import dt.swing.action.SplitAction;
import dt.swing.action.SplitAllAction;
import dt.swing.action.UpActionAction;
import dt.swing.action.UpConditionAction;

/**
 * @author Michiel Kalkman
 *
 * TODO To change the template for this generated type comment go to
 * Window - Preferences - Java - Code Style - Code Templates
 */
public class TableFramePopupListener extends MouseAdapter {
	private final DTTable table;
	private JPopupMenu popupMenu;
	private final JMenuItem DISPLAY = new JMenuItem( "Display Table");
	private final JMenuItem SAVE = new JMenuItem( "Save Table");
	private final JMenuItem SAVE_AS = new JMenuItem( "Save Table As");
	private final JMenuItem COPY_TEXT = new JMenuItem( "Text To Clipboard");
	private final JMenuItem REDUCE = new JMenuItem( "Reduce Table");
	private final JMenuItem SPLIT_RULE = new JMenuItem( "Split Rule Table");
	private final JMenuItem INSERT_CONDITION = new JMenuItem( "Insert condition");
	private final JMenuItem ADD_CONDITION = new JMenuItem( "Add condition");
	private final JMenuItem UP_CONDITION = new JMenuItem( "Up");
	private final JMenuItem DOWN_CONDITION = new JMenuItem( "Down");
	private final JMenuItem INSERT_ACTION = new JMenuItem( "Insert action");
	private final JMenuItem ADD_ACTION = new JMenuItem( "Add action", KeyEvent.VK_Z);
	private final JMenuItem UP_ACTION = new JMenuItem( "Up");
	private final JMenuItem DOWN_ACTION = new JMenuItem( "Down");
	private final JMenuItem DELETE_ACTION = new JMenuItem( "Delete");
	private final JMenuItem SPLIT_ALL_RULES = new JMenuItem( "Split all rules");
	private final JMenuItem ADD_RESTRICTION = new JMenuItem( "Add Restriction");
	private final JMenuItem ADD_CATEGORIES = new JMenuItem( "Add Categories");
	private final JMenuItem PROPERTIES = new JMenuItem( "Properties");
	private final JMenuItem DO_ACTION = new JMenuItem( "Do");
	private final JMenuItem DONT_ACTION = new JMenuItem( "Don't");

	public TableFramePopupListener( final DTTable table) {
		super();
		this.table = table;
		popupMenu = new JPopupMenu();

		final DisplayAction displayAction = new DisplayAction( table);
		DISPLAY.addActionListener( displayAction);
		DISPLAY.setAccelerator( KeyStroke.getKeyStroke( KeyEvent.VK_D, ActionEvent.CTRL_MASK));
		DISPLAY.setEnabled( displayAction.isEnabled()); 

		SAVE.addActionListener( new SaveAction( table));
		SAVE.setAccelerator( KeyStroke.getKeyStroke( KeyEvent.VK_S, ActionEvent.CTRL_MASK));

		SAVE_AS.addActionListener( new SaveAsAction( table));

		COPY_TEXT.addActionListener( new CopyDefaultTextAction( table));
		COPY_TEXT.setAccelerator( KeyStroke.getKeyStroke( KeyEvent.VK_T, ActionEvent.CTRL_MASK));

		ADD_CONDITION.addActionListener( new AddConditionAction( table));
		ADD_CONDITION.setAccelerator( KeyStroke.getKeyStroke( KeyEvent.VK_W, ActionEvent.CTRL_MASK));

		UP_CONDITION.addActionListener( new UpConditionAction( table));
		UP_CONDITION.setAccelerator( KeyStroke.getKeyStroke( KeyEvent.VK_U, ActionEvent.ALT_MASK));

		DOWN_CONDITION.addActionListener( new DownConditionAction( table));
		DOWN_CONDITION.setAccelerator( KeyStroke.getKeyStroke( KeyEvent.VK_D, ActionEvent.ALT_MASK));

		INSERT_CONDITION.addActionListener( new InsertConditionAction( table));
		INSERT_CONDITION.setAccelerator( KeyStroke.getKeyStroke( KeyEvent.VK_E, ActionEvent.CTRL_MASK));

		ADD_ACTION.addActionListener( new AddActionAction( table));
		ADD_ACTION.setAccelerator( KeyStroke.getKeyStroke( KeyEvent.VK_Z, ActionEvent.CTRL_MASK));

		UP_ACTION.addActionListener( new UpActionAction( table));
		UP_ACTION.setAccelerator( KeyStroke.getKeyStroke( KeyEvent.VK_U, ActionEvent.ALT_MASK));

		DOWN_ACTION.addActionListener( new DownActionAction( table));
		DOWN_ACTION.setAccelerator( KeyStroke.getKeyStroke( KeyEvent.VK_D, ActionEvent.ALT_MASK));

		INSERT_ACTION.addActionListener( new InsertActionAction( table));
		INSERT_ACTION.setAccelerator( KeyStroke.getKeyStroke( KeyEvent.VK_X, ActionEvent.CTRL_MASK));

		REDUCE.addActionListener( new ReduceAction( table));

		DELETE_ACTION.addActionListener( new DeleteAction( table));

		SPLIT_RULE.addActionListener( new SplitAction( table));
		SPLIT_ALL_RULES.addActionListener( new SplitAllAction( table));

		ADD_RESTRICTION.addActionListener( new AddRestrictionAction( table));
		ADD_CATEGORIES.addActionListener( new AddCategoriesAction( table));

		PROPERTIES.addActionListener( new PropertyAction( table));

		DO_ACTION.addActionListener( new DoAction( table));
		DO_ACTION.setAccelerator( KeyStroke.getKeyStroke( KeyEvent.VK_D, ActionEvent.SHIFT_MASK));

		DONT_ACTION.addActionListener( new DontAction( table));
		DONT_ACTION.setAccelerator( KeyStroke.getKeyStroke( KeyEvent.VK_D, ActionEvent.ALT_MASK));
	}

	@Override
	public void mousePressed(final MouseEvent e) {
	    showPopup(e);
	}

	@Override
	public void mouseReleased(final MouseEvent e) {
		showPopup(e);
	}

	private void showPopup(final MouseEvent e) {
		if (e.isPopupTrigger()) {
			popupMenu.removeAll();

			popupMenu.add( DISPLAY);
			popupMenu.add( SAVE);
			popupMenu.add( SAVE_AS);
			popupMenu.add( COPY_TEXT);
			popupMenu.add( DELETE_ACTION);

			final IDecisionTable decisionTable = table.getDecisionTable();

			if( decisionTable.nrConditions() > 0) {
				if( ! decisionTable.showsAllRules()) {
					popupMenu.add( SPLIT_ALL_RULES);
				}

				popupMenu.add( REDUCE);
			}

			final Point point = e.getPoint();

			final int row = table.rowAtPoint( point);
			final int column = table.columnAtPoint( point);

			table.setRowSelected( row);
			table.setColumnSelected( column);

			final int nrConditions = decisionTable.nrConditions();
			final int nrActions = decisionTable.nrActions();

			if( isConditionOrActionRow(row)) {
				popupMenu.addSeparator();

				if( isConditionRow(row, nrConditions)) {
					// an condition;
					popupMenu.add( INSERT_CONDITION);
					popupMenu.add( ADD_CONDITION);
					if( ! isFirstCondition(row)) {
						popupMenu.add( UP_CONDITION);
					}
					if( isLastCondition(row, nrConditions)) {
						popupMenu.addSeparator();
						popupMenu.add( ADD_ACTION);
					} else {
						popupMenu.add( DOWN_CONDITION);
					}
				} else {
					// an action.
					if( isFirstAction(row, nrConditions)) {
						popupMenu.add( INSERT_CONDITION);
						popupMenu.add( ADD_CONDITION);
						popupMenu.addSeparator();
					}
					popupMenu.add( INSERT_ACTION);
					popupMenu.add( ADD_ACTION);
					if( ! isFirstAction(row, nrConditions)) {
						popupMenu.add( UP_ACTION);
					}
					if( ! isLastAction(row, nrConditions, nrActions)) {
						popupMenu.add( DOWN_ACTION);
					}
				}
			} else if( isLeadingRow(row)) {
				popupMenu.addSeparator();
				popupMenu.add( ADD_CONDITION);
				if( nrConditions == 0) {
					popupMenu.add( ADD_ACTION);
				}
			} else {
				// Popup is under the table; remember the table fills the entire window !
				if( nrActions == 0) {
					popupMenu.addSeparator();
					popupMenu.add( ADD_CONDITION);
				}
				popupMenu.addSeparator();
				popupMenu.add( ADD_ACTION);
			}

			if( (column > 0) && ! decisionTable.showsAllRules()) {
				popupMenu.addSeparator();
				popupMenu.add( SPLIT_RULE);
			}

			if( nrConditions > 0) {
				popupMenu.addSeparator();
				popupMenu.add( ADD_RESTRICTION);
				popupMenu.add( ADD_CATEGORIES);
			}

			popupMenu.addSeparator();
			popupMenu.add( PROPERTIES);

			popupMenu.addSeparator();
			popupMenu.add( DO_ACTION);
			popupMenu.add( DONT_ACTION);

			popupMenu.show(e.getComponent(), e.getX(), e.getY());
		}
	}

	private boolean isFirstCondition(final int row) {
		return row == 1;
	}

	private boolean isLastCondition(final int row, final int nrConditions) {
		return row == nrConditions;
	}

	private boolean isLastAction(final int row, final int nrConditions, final int nrActions) {
		return row == nrConditions + nrActions;
	}

	private boolean isFirstAction(final int row, final int nrConditions) {
		return row == nrConditions+1;
	}

//	private boolean isActionRow(final int row, final int nrConditions) {
//		return row > nrConditions;
//	}

	private boolean isConditionRow(final int row, final int nrConditions) {
		return (row != 0) && (row <= nrConditions);
	}

	private boolean isConditionOrActionRow(final int row) {
		return row > 0;
	}

	private boolean isLeadingRow(final int row) {
		return row == 0;
	}
	/**
	 * @return Returns the popupMenu.
	 */
	public JPopupMenu getPopupMenu() {
		return popupMenu;
	}
	/**
	 * @param popupMenu The popupMenu to set.
	 */
	public void setPopupMenu(final JPopupMenu popupMenu) {
		this.popupMenu = popupMenu;
	}
}
