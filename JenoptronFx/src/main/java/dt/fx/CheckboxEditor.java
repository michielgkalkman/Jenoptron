package dt.fx;

import javafx.beans.value.ChangeListener;
import javafx.event.EventHandler;
import javafx.scene.control.CheckBox;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;

import org.controlsfx.control.spreadsheet.SpreadsheetCellEditor;
import org.controlsfx.control.spreadsheet.SpreadsheetView;

public class CheckboxEditor extends SpreadsheetCellEditor {
	private final CheckBox checkBox;
    private ChangeListener<Boolean> cl;
    /**
     * This is needed because "endEdit" will call our "end" method too late
     * when pressing enter, so several "endEdit" will be called. So this
     * prevent that to happen.
     */
    private final boolean ending = false;

	
	public CheckboxEditor(final SpreadsheetView spreadsheetView) {
		super(spreadsheetView);
		checkBox = new CheckBox();
	}

	@Override
	public void startEdit(final Object value) {

        if (value instanceof Boolean || value == null) {
        	checkBox.setSelected((boolean)value);
        }
        attachEnterEscapeEventHandler();

        checkBox.requestFocus();
    }

	@Override
	public CheckBox getEditor() {
		return checkBox;
	}

	@Override
	public String getControlValue() {
		return Boolean.toString(checkBox.isSelected());
	}

	@Override
	public void end() {
        checkBox.setOnKeyPressed(null);

	}


    /***************************************************************************
     * * Private Methods * *
     **************************************************************************/

    private void attachEnterEscapeEventHandler() {

            checkBox.setOnKeyPressed(new EventHandler<KeyEvent>() {
                @Override
                public void handle(final KeyEvent t) {
                    if (t.getCode() == KeyCode.ENTER) {
                        endEdit(true);
                    } else if (t.getCode() == KeyCode.ESCAPE) {
                        endEdit(false);
                    }
                }
            });

    	
        /**
         * We need to add an EventFilter because otherwise the DatePicker
         * will block "escape" and "enter". But when "enter" is hit, we need
         * to runLater the commit because the value has not yet hit the
         * DatePicker itself.
         */
//        eh = new EventHandler<KeyEvent>() {
//            @Override
//            public void handle(final KeyEvent t) {
//                if (t.getCode() == KeyCode.ENTER) {
//                    ending = true;
//                    endEdit(true);
//                    ending = false;
//				} else if (t.getCode() == KeyCode.ESCAPE) {
//                    endEdit(false);
//                }
//            }
//        };
//
//        datePicker.addEventFilter(KeyEvent.KEY_PRESSED, eh);

//        cl = new ChangeListener<Boolean>() {
//            @Override
//            public void changed(final ObservableValue<? extends Boolean> arg0, final Boolean arg1, final Boolean arg2) {
//                if (!ending)
//                    endEdit(true);
//            }
//        };
//        
//        checkBox.selectedProperty().addListener(cl);
    }
}
