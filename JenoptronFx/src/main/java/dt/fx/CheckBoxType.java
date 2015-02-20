package dt.fx;

import javafx.util.converter.BooleanStringConverter;

import org.controlsfx.control.spreadsheet.SpreadsheetCell;
import org.controlsfx.control.spreadsheet.SpreadsheetCellBase;
import org.controlsfx.control.spreadsheet.SpreadsheetCellEditor;
import org.controlsfx.control.spreadsheet.SpreadsheetCellType;
import org.controlsfx.control.spreadsheet.SpreadsheetView;

public class CheckBoxType extends SpreadsheetCellType<Boolean> {
    public CheckBoxType() {
        this(new DefaultCheckBoxConverter());
    }

    public CheckBoxType(final BooleanStringConverter converter) {
        super(converter);
    }

    @Override
    public String toString() {
        return "checkbox"; //$NON-NLS-1$
    }

    @Override
    public boolean match(final Object value) {
        return Boolean.TRUE.toString().equalsIgnoreCase((String)value) || Boolean.FALSE.toString().equalsIgnoreCase((String)value); 
    }

    /**
    * Creates a cell that hold a String at the specified position, with the
    * specified row/column span.
    * 
    * @param row
    *            row number
    * @param column
    *            column number
    * @param rowSpan
    *            rowSpan (1 is normal)
    * @param columnSpan
    *            ColumnSpan (1 is normal)
    * @param value
    *            the value to display
    * @return a {@link SpreadsheetCell}
    */
    public SpreadsheetCell createCell(final int row, final int column, final int rowSpan, final int columnSpan,
            final Boolean value) {
        final SpreadsheetCell cell = new SpreadsheetCellBase(row, column, rowSpan, columnSpan, this);
        cell.setItem(value);
        return cell;
    }

    @Override
    public SpreadsheetCellEditor createEditor(final SpreadsheetView view) {
        return new CheckboxEditor(view);
    }

	@Override
	public String toString(final Boolean object) {
        return "";
	}

	@Override
	public Boolean convertValue(final Object value) {
		final Boolean aBoolean;
		if( value instanceof Boolean) {
			aBoolean = (Boolean) value;
		} else if (value instanceof String) {
			aBoolean = Boolean.valueOf((String)value);
		} else {
			aBoolean = false;
		}
		return aBoolean;
	}
}
