package dt.fx;

import javafx.scene.control.CheckBox;

import org.controlsfx.control.spreadsheet.SpreadsheetCellEditor;
import org.controlsfx.control.spreadsheet.SpreadsheetView;

public class CheckboxEditor extends SpreadsheetCellEditor {
	private final CheckBox checkBox;
	
	public CheckboxEditor(final SpreadsheetView spreadsheetView) {
		super(spreadsheetView);
		checkBox = new CheckBox();
	}

	@Override
	public void startEdit(final Object item) {
		// TODO Auto-generated method stub

	}

	@Override
	public CheckBox getEditor() {
		return checkBox;
	}

	@Override
	public String getControlValue() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void end() {
		// TODO Auto-generated method stub

	}

}
