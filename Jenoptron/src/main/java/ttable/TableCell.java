package ttable;
public class TableCell {

	public int row;
	public int column;

	public TableCell() {
	}

	public TableCell(int row, int column) {
		this.row = row;
		this.column = column;
	}

	@Override
	public boolean equals(Object obj) {
		if (obj == null) {
			return false;
		}
		if (getClass() != obj.getClass()) {
			return false;
		}
		final TableCell other = (TableCell) obj;
		if (this.row != other.row) {
			return false;
		}
		if (this.column != other.column) {
			return false;
		}
		return true;
	}

	@Override
	public int hashCode() {
		int hash = 7;
		hash = 67 * hash + this.row;
		hash = 67 * hash + this.column;
		return hash;
	}
}
