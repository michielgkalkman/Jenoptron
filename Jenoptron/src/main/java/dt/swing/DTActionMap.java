package dt.swing;

import java.awt.event.ActionEvent;
import java.awt.event.KeyEvent;

import javax.swing.ActionMap;
import javax.swing.InputMap;
import javax.swing.KeyStroke;

import dt.swing.action.AddActionAction;
import dt.swing.action.AddConditionAction;
import dt.swing.action.CopyDefaultTextAction;
import dt.swing.action.CopyTableExcelFormatAction;
import dt.swing.action.DeleteAction;
import dt.swing.action.DisplayAction;
import dt.swing.action.DoAction;
import dt.swing.action.DontAction;
import dt.swing.action.DownAction;
import dt.swing.action.InsertActionAction;
import dt.swing.action.InsertConditionAction;
import dt.swing.action.SaveAction;
import dt.swing.action.SplitAction;
import dt.swing.action.SplitAllAction;
import dt.swing.action.UpAction;

public class DTActionMap {
	private static final String SPLIT_ALL_ACTION_MAP_KEY = "splitAll";
	private static final String SPLIT_ACTION_MAP_KEY = "split";
	private static final String REDUCE_ACTION_MAP_KEY = "reduce";
	private static final String INSERT_ACTION_ACTION_MAP_KEY = "insertAction";
	private static final String ADD_ACTION_ACTION_MAP_KEY = "addAction";
	private static final String ADD_CONDITION_ACTION_MAP_KEY = "addCondition";
	private static final String INSERT_CONDITION_ACTION_MAP_KEY = "insertCondition";
	private static final String DOWN_ACTION_MAP_KEY = "down";
	private static final String UP_ACTION_MAP_KEY = "up";
	private static final String DELETE_ACTION_MAP_KEY = "delete";
	private static final String SAVE_ACTION_MAP_KEY = "save";
	private static final String DISPLAY_ACTION_MAP_KEY = "display";
	private static final String COPY_TEXT_ACTION_MAP_KEY = "text to clipboard";
	private static final String COPY_EXCEL_ACTION_MAP_KEY = "copy (excel)";
	private static final String DO_KEY = "do";
	private static final String DONT_KEY = "dont";
	private static final KeyStroke SPLIT_ALL_KEY_STROKE = KeyStroke.getKeyStroke( KeyEvent.VK_S, ActionEvent.ALT_MASK);
	private static final KeyStroke REDUCE_KEY_STROKE = KeyStroke.getKeyStroke( KeyEvent.VK_R, ActionEvent.CTRL_MASK);
	private static final KeyStroke INSERT_ACTION_KEY_STROKE = KeyStroke.getKeyStroke( KeyEvent.VK_X, ActionEvent.CTRL_MASK);
	private static final KeyStroke ADD_ACTION_KEY_STROKE = KeyStroke.getKeyStroke( KeyEvent.VK_Z, ActionEvent.CTRL_MASK);
	private static final KeyStroke ADD_CONDITION_KEY_STROKE = KeyStroke.getKeyStroke( KeyEvent.VK_W, ActionEvent.CTRL_MASK);
	private static final KeyStroke INSERT_CONDITION_KEY_STROKE = KeyStroke.getKeyStroke( KeyEvent.VK_E, ActionEvent.CTRL_MASK);
	private static final KeyStroke DOWN_KEY_STROKE = KeyStroke.getKeyStroke( KeyEvent.VK_DOWN, ActionEvent.CTRL_MASK);
	private static final KeyStroke UP_KEY_STROKE = KeyStroke.getKeyStroke( KeyEvent.VK_UP, ActionEvent.CTRL_MASK);
	private static final KeyStroke DELETE_KEY_STROKE = KeyStroke.getKeyStroke( KeyEvent.VK_DELETE, 0);
	private static final KeyStroke SAVE_KEY_STROKE = KeyStroke.getKeyStroke( KeyEvent.VK_S, ActionEvent.CTRL_MASK);
	private static final KeyStroke DISPLAY_TABLE_KEY_STROKE = KeyStroke.getKeyStroke( KeyEvent.VK_D, ActionEvent.CTRL_MASK);
	public static final KeyStroke COPY_TABLE_KEY_STROKE = KeyStroke.getKeyStroke( KeyEvent.VK_T, ActionEvent.CTRL_MASK);
	public static final KeyStroke COPY_EXCEL_ACTION_KEY_STROKE = KeyStroke.getKeyStroke( KeyEvent.VK_C, ActionEvent.CTRL_MASK, false);
	public static final KeyStroke DO_STROKE = KeyStroke.getKeyStroke( KeyEvent.VK_D, ActionEvent.SHIFT_MASK, false);
	public static final KeyStroke DONT_STROKE = KeyStroke.getKeyStroke( KeyEvent.VK_D, ActionEvent.ALT_MASK, false);

	private DTActionMap() {
		super();
	}

	public static void setActionMap( final ActionMap actionMap, final DTTable table) {
		actionMap.clear();

		actionMap.put( COPY_TEXT_ACTION_MAP_KEY, new CopyDefaultTextAction( table));
		actionMap.put( DISPLAY_ACTION_MAP_KEY, new DisplayAction( table));
		actionMap.put( SAVE_ACTION_MAP_KEY, new SaveAction( table));
		actionMap.put( DELETE_ACTION_MAP_KEY, new DeleteAction( table));
		actionMap.put( UP_ACTION_MAP_KEY, new UpAction( table));
		actionMap.put( DOWN_ACTION_MAP_KEY, new DownAction( table));
		actionMap.put( INSERT_CONDITION_ACTION_MAP_KEY, new InsertConditionAction( table));
		actionMap.put( ADD_CONDITION_ACTION_MAP_KEY, new AddConditionAction( table));
		actionMap.put( ADD_ACTION_ACTION_MAP_KEY, new AddActionAction( table));
		actionMap.put( INSERT_ACTION_ACTION_MAP_KEY, new InsertActionAction( table));
		actionMap.put( REDUCE_ACTION_MAP_KEY, new InsertActionAction( table));
		actionMap.put( SPLIT_ACTION_MAP_KEY, new SplitAction( table));
		actionMap.put( SPLIT_ALL_ACTION_MAP_KEY, new SplitAllAction( table));
		actionMap.put( COPY_EXCEL_ACTION_MAP_KEY, new CopyTableExcelFormatAction( table));
		actionMap.put( DO_KEY, new DoAction( table));
		actionMap.put( DONT_KEY, new DontAction( table));
	}

	public static void setInputMap( final InputMap inputMap) {
		inputMap.clear();

		inputMap.put( COPY_TABLE_KEY_STROKE, COPY_TEXT_ACTION_MAP_KEY);
		inputMap.put( DISPLAY_TABLE_KEY_STROKE, DISPLAY_ACTION_MAP_KEY);
		inputMap.put( SAVE_KEY_STROKE, SAVE_ACTION_MAP_KEY);
		inputMap.put( DELETE_KEY_STROKE, DELETE_ACTION_MAP_KEY);
		inputMap.put( UP_KEY_STROKE, UP_ACTION_MAP_KEY);
		inputMap.put( DOWN_KEY_STROKE, DOWN_ACTION_MAP_KEY);
		inputMap.put( INSERT_CONDITION_KEY_STROKE, INSERT_CONDITION_ACTION_MAP_KEY);
		inputMap.put( ADD_CONDITION_KEY_STROKE, ADD_CONDITION_ACTION_MAP_KEY);
		inputMap.put( ADD_ACTION_KEY_STROKE, ADD_ACTION_ACTION_MAP_KEY);
		inputMap.put( INSERT_ACTION_KEY_STROKE, INSERT_ACTION_ACTION_MAP_KEY);
		inputMap.put( REDUCE_KEY_STROKE, REDUCE_ACTION_MAP_KEY);
		inputMap.put( SAVE_KEY_STROKE, SPLIT_ACTION_MAP_KEY);
		inputMap.put( SPLIT_ALL_KEY_STROKE, SPLIT_ALL_ACTION_MAP_KEY);
		inputMap.put( COPY_EXCEL_ACTION_KEY_STROKE, COPY_EXCEL_ACTION_MAP_KEY);
		inputMap.put( DO_STROKE, DO_KEY);
		inputMap.put( DONT_STROKE, DONT_KEY);
	}
}
