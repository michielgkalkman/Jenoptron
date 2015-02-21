	package dt.fx;

import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

import org.controlsfx.control.spreadsheet.SpreadsheetCell;
import org.controlsfx.control.spreadsheet.SpreadsheetCellBase;
import org.controlsfx.control.spreadsheet.SpreadsheetCellEditor;
import org.controlsfx.control.spreadsheet.SpreadsheetCellType;
import org.controlsfx.control.spreadsheet.SpreadsheetView;

public class ImageCellType extends SpreadsheetCellType<Image>{


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
    * @param image
    *            the value to display
    * @return a {@link SpreadsheetCell}
    */
    public SpreadsheetCell createCell(final int row, final int column, final int rowSpan, final int columnSpan,
            final Image image) {
        final SpreadsheetCell cell = new SpreadsheetCellBase(row, column, rowSpan, columnSpan, this);
        cell.setGraphic(new ImageView(image));
        return cell;
    }

	@Override
	public SpreadsheetCellEditor createEditor(final SpreadsheetView view) {
		return null;
	}

	@Override
	public String toString(final Image object) {
		return "image";
	}

	@Override
	public boolean match(final Object value) {
		return value instanceof Image;
	}

	@Override
	public Image convertValue(final Object value) {
		final Image image;
		if (value instanceof Image) {
			image = (Image) value;
		}  else {
			image = null;
		}
		return image;
	}

}
