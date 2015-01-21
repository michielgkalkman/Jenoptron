package ttable;

import java.awt.Component;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseMotionListener;

import javax.swing.JComponent;
import javax.swing.JTable;
import javax.swing.SwingUtilities;
import javax.swing.table.TableCellRenderer;
import javax.swing.table.TableColumnModel;

public class ActiveJComponentTableMouseListener extends MouseAdapter implements
		MouseMotionListener {

	private JTable table;
	private JComponent oldComponent = null;
	private TableCell oldTableCell = new TableCell();

	public ActiveJComponentTableMouseListener(JTable table) {
		this.table = table;
	}

	@Override
	public void mouseMoved(MouseEvent e) {
		TableCell cell = new TableCell(getRow(e), getColumn(e));

		if (alreadyVisited(cell)) {
			return;
		}
		save(cell);

		if (oldComponent != null) {
			dispatchEvent(createMouseEvent(e, MouseEvent.MOUSE_EXITED),
					oldComponent);
			oldComponent = null;
		}

		JComponent component = getComponent(cell);
		if (component == null) {
			return;
		}
		dispatchEvent(createMouseEvent(e, MouseEvent.MOUSE_ENTERED), component);
		saveComponent(component);
		save(cell);
	}

	@Override
	public void mouseExited(MouseEvent e) {
		TableCell cell = new TableCell(getRow(e), getColumn(e));

		if (alreadyVisited(cell)) {
			return;
		}
		if (oldComponent != null) {
			dispatchEvent(createMouseEvent(e, MouseEvent.MOUSE_EXITED),
					oldComponent);
			oldComponent = null;
		}
	}

	@Override
	public void mouseEntered(MouseEvent e) {
		forwardEventToComponent(e);
	}

	private void forwardEventToComponent(MouseEvent e) {
		TableCell cell = new TableCell(getRow(e), getColumn(e));
		save(cell);
		JComponent component = getComponent(cell);
		if (component == null) {
			return;
		}
		dispatchEvent(e, component);
		saveComponent(component);
	}

	private void dispatchEvent(MouseEvent componentEvent, JComponent component) {
		MouseEvent convertedEvent = (MouseEvent) SwingUtilities
				.convertMouseEvent(table, componentEvent, component);
		component.dispatchEvent(convertedEvent);
		// This is necessary so that when a button is pressed and released
		// it gets rendered properly. Otherwise, the button may still appear
		// pressed down when it has been released.
		table.repaint();
	}

	private JComponent getComponent(TableCell cell) {
		if (rowOrColumnInvalid(cell)) {
			return null;
		}
		TableCellRenderer renderer = table.getCellRenderer(cell.row,
				cell.column);

		if (!(renderer instanceof ActiveJComponentTableCellRenderer)) {
			return null;
		}
		ActiveJComponentTableCellRenderer activeComponentRenderer = (ActiveJComponentTableCellRenderer) renderer;

		return activeComponentRenderer.getComponent(cell);
	}

	private int getColumn(MouseEvent e) {
		TableColumnModel columnModel = table.getColumnModel();
		int column = columnModel.getColumnIndexAtX(e.getX());
		return column;
	}

	private int getRow(MouseEvent e) {
		int row = e.getY() / table.getRowHeight();
		return row;
	}

	private boolean rowInvalid(int row) {
		return row >= table.getRowCount() || row < 0;
	}

	private boolean rowOrColumnInvalid(TableCell cell) {
		return rowInvalid(cell.row) || columnInvalid(cell.column);
	}

	private boolean alreadyVisited(TableCell cell) {
		return oldTableCell.equals(cell);
	}

	private boolean columnInvalid(int column) {
		return column >= table.getColumnCount() || column < 0;
	}

	private MouseEvent createMouseEvent(MouseEvent e, int eventID) {
		return new MouseEvent((Component) e.getSource(), eventID, e.getWhen(),
				e.getModifiers(), e.getX(), e.getY(), e.getClickCount(), e
						.isPopupTrigger(), e.getButton());
	}

	private void save(TableCell cell) {
		oldTableCell = cell;
	}

	private void saveComponent(JComponent component) {
		oldComponent = component;
	}
}
