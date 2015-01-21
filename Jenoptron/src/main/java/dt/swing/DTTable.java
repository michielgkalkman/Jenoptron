/*
 * Created on Jul 28, 2005
 *
 * TODO To change the template for this generated file go to
 * Window - Preferences - Java - Code Style - Code Templates
 */
package dt.swing;

import java.awt.Color;
import java.awt.Component;
import java.awt.Container;
import java.awt.Point;
import java.awt.Rectangle;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.util.Arrays;

import javax.swing.DefaultCellEditor;
import javax.swing.JComboBox;
import javax.swing.JComponent;
import javax.swing.JTable;
import javax.swing.JViewport;
import javax.swing.ListSelectionModel;
import javax.swing.table.TableCellEditor;
import javax.swing.table.TableCellRenderer;

import jdt.core.DecisionTable;
import jdt.core.binary.BinaryActionValue;
import jdt.icore.IAction;
import jdt.icore.IDecisionTable;
import jdt.icore.IValue;

import org.apache.log4j.Logger;

import com.jgoodies.binding.beans.Observable;

import dt.swing.action.ActionUtil;
import dt.swing.dnd.DTTableDragMouseAdapter;
import dt.swing.dnd.DTTableTransferHandler;
import dt.swing.util.TableFramePopupListener;


/**
 * @author Michiel Kalkman
 *
 * TODO To change the template for this generated type comment go to Window -
 * Preferences - Java - Code Style - Code Templates
 */
public class DTTable extends JTable implements PropertyChangeListener, Observable {
    static final long serialVersionUID = -6829728416977461250L;
    private static final Logger logger = Logger.getLogger( DTTable.class);

	private IDecisionTable decisionTable;
	private int rowSelected = -1;
	private int columnSelected = -1;
	private DTTableModel tableModel;
	private final transient DTCellEditorModel cellEditorModel = new DTCellEditorModel();

	public DTTable() {
		super();

		setupEditors();
	}

	public DTTable(final IDecisionTable decisionTable) {
		super();

		tableModel = new DTTableModel( decisionTable);

		setModel( tableModel);

		this.decisionTable = decisionTable;

		setTableHeader(null);

		addPopupMenu();

		DTActionMap.setActionMap( getActionMap(), this);
		DTActionMap.setInputMap( getInputMap());

		decisionTable.addPropertyChangeListener( this);

		setupEditors();

		// Enable scrolling
		setAutoResizeMode(JTable.AUTO_RESIZE_OFF);

		setVisible( true);

		setColumnSelectionAllowed( false);
		setCellSelectionEnabled( true);
		setRowSelectionAllowed( true);

		setSelectionMode( ListSelectionModel.MULTIPLE_INTERVAL_SELECTION);

		setDragEnabled(true);
		setTransferHandler( new DTTableTransferHandler());

		final MouseListener listener = new DTTableDragMouseAdapter();
		addMouseListener(listener);

		logger.debug( "Row selection allowed = " + getRowSelectionAllowed());
	}

	private void addPopupMenu() {
		final MouseListener popupListener = new TableFramePopupListener( this);
		// add the listener specifically to the header
		addMouseListener(popupListener);
	}

	public void setupEditors() {
		for( final IAction action : decisionTable.getActions()) {
			final JComboBox comboBox = new JComboBox();

			for( final IValue value : action.getSelectableValues()) {
				comboBox.addItem( value.toString());
			}

			cellEditorModel.addEditor( action.getClass(), new DefaultCellEditor(comboBox));
		}

//	    TableColumnModel cmodel = getColumnModel();
//	    TextAreaRenderer textAreaRenderer = new TextAreaRenderer();
//	    TextAreaEditor textEditor = new TextAreaEditor();
//
//	    cmodel.getColumn(0).setCellRenderer(textAreaRenderer);
//	    cmodel.getColumn(0).setCellEditor(textEditor);
	}

	/*
	 * (non-Javadoc)
	 *
	 * @see javax.swing.JTable#prepareRenderer(javax.swing.table.TableCellRenderer,
	 *      int, int)
	 */
	@Override
	public synchronized Component prepareRenderer(final TableCellRenderer renderer, final int row,
			final int column) {
		final Component component = super.prepareRenderer(renderer, row, column);

		if( Arrays.binarySearch( getSelectedRows(), row) < 0) {
			if ((row == 0) || (column == 0)) {
				component.setBackground(Color.ORANGE);
			} else if (row > decisionTable.getConditions().size()) {
				if( BinaryActionValue.UNKNOWN.toString() == ActionUtil.getValue(
						decisionTable, row, column)) {
					component.setBackground(Color.RED);
				} else {
					component.setBackground(Color.YELLOW);
				}
			} else {
				component.setBackground(Color.PINK);
			}
		}

		return component;
	}

	/*
	 * (non-Javadoc)
	 *
	 * @see javax.swing.JTable#getColumnClass(int)
	 */
	@Override
	public Class< ? > getColumnClass(final int column) {
		// TODO Auto-generated method stub
		return String.class;
	}

	/*
	 * (non-Javadoc)
	 *
	 * @see javax.swing.JTable#isCellEditable(int, int)
	 */
	@Override
	public boolean isCellEditable(final int row, final int column) {
		return ((column == 0) && (row > 0)) || (row > decisionTable.nrConditions());
	}

	/* (non-Javadoc)
	 * @see javax.swing.JTable#getCellEditor(int, int)
	 */
	@Override
	public TableCellEditor getCellEditor(final int row, final int column) {
		TableCellEditor tableCellEditor = null;

		final int nrConditions = decisionTable.nrConditions();

		logger.debug( "row=" + row + ", column=" + column);

		if( column > 0) {
			if( row > nrConditions) {
				logger.debug( "Action Editor");
				tableCellEditor = cellEditorModel.getEditor( decisionTable.getActions().get((row - nrConditions) - 1).getClass());
			} else if( row >= 1) {
				tableCellEditor = cellEditorModel.getEditor( decisionTable.getConditions().get(row - 1).getClass());
			}
		}

		if( tableCellEditor == null) {
			tableCellEditor = super.getCellEditor( row, column);
		}

		return tableCellEditor;
	}
	/**
	 * @return Returns the decisionTable.
	 */
	public IDecisionTable getDecisionTable() {
		return decisionTable;
	}
	/**
	 * @param decisionTable The decisionTable to set.
	 */
	public void setDecisionTable(final DecisionTable decisionTable) {
		this.decisionTable = decisionTable;
	}

	/**
	 * @return Returns the columnSelected.
	 */
	public int getColumnSelected() {
		return columnSelected;
	}
	/**
	 * @param columnSelected The columnSelected to set.
	 */
	public void setColumnSelected(final int columnSelected) {
		this.columnSelected = columnSelected;
	}
	/**
	 * @return Returns the rowSelected.
	 */
	public int getRowSelected() {
		logger.debug( "getRowSelected " + rowSelected);
		return rowSelected;
	}
	/**
	 * @param rowSelected The rowSelected to set.
	 */
	public void setRowSelected(final int rowSelected) {
		this.rowSelected = rowSelected;
		logger.debug( "setRowSelected " + rowSelected);
	}

	public void refresh() {
		tableModel.fireTableStructureChanged();
	}

	//
	// Take care that JTable encompasses entire viewport. From Santosh.

	@Override
	public boolean getScrollableTracksViewportHeight() {
	    // fetch the table's parent
	    final Container viewport = getParent();

	    // if the parent is not a viewport, calling this isn't useful
	    if (! (viewport instanceof JViewport)) {
	        return false;
	    }

	    // return true if the table's preferred height is smaller
	    // than the viewport height, else false
	    return getPreferredSize().height < viewport.getHeight();
	}

	@Override
	public boolean getScrollableTracksViewportWidth() {
	    // fetch the table's parent
	    final Container viewport = getParent();

	    // if the parent is not a viewport, calling this isn't useful
	    if (! (viewport instanceof JViewport)) {
	        return false;
	    }

	    // return true if the table's preferred height is smaller
	    // than the viewport height, else false
	    return getPreferredSize().width < viewport.getWidth();
	}

	// Show popups for texts bigger than a cell.
	// From: http://www.jroller.com/page/santhosh/20050625#partially_visible_tablecells
	// Well, almost. Sometimes hitRowIndex became -1.

    // JTree has convertValueToText(), but why not JTable ???
    public String convertValueToText(final Object value, final int row, final int column){
        if(value != null) {
            final String sValue = value.toString();
            if (sValue != null) {
				return sValue;
			}
        }
        return "";
    }

    @Override
	public String getToolTipText(final MouseEvent event) {
        String tip = null;
        final Point p = event.getPoint();

        // Locate the renderer under the event location
        final int hitColumnIndex = columnAtPoint(p);
        final int hitRowIndex = rowAtPoint(p);

        if ((hitColumnIndex != -1) && (hitRowIndex != -1)) {
            final TableCellRenderer renderer = getCellRenderer(hitRowIndex, hitColumnIndex);
            final Component component = prepareRenderer(renderer, hitRowIndex, hitColumnIndex);

            // Now have to see if the component is a JComponent before
            // getting the tip
            if (component instanceof JComponent) {
                // Convert the event to the renderer's coordinate system
                final Rectangle cellRect = getCellRect(hitRowIndex, hitColumnIndex, false);
                if(cellRect.width>=component.getPreferredSize().width) {
					return null;
				}
                p.translate(-cellRect.x, -cellRect.y);
                final MouseEvent newEvent = new MouseEvent(component, event.getID(),
                                          event.getWhen(), event.getModifiers(),
                                          p.x, p.y, event.getClickCount(),
                                          event.isPopupTrigger());

                tip = ((JComponent)component).getToolTipText(newEvent);
            }

	        // No tip from the renderer, see whether any tooltip is set on JTable
	        if (tip == null) {
				tip = getToolTipText();
			}

	        // calculate tooltip from cell value
	        if(tip==null){
	            final Object value = getValueAt(hitRowIndex, hitColumnIndex);
	            tip = convertValueToText(value, hitRowIndex, hitColumnIndex);
	            if(tip.length()==0) {
					tip = null; // don't show empty tooltips
				}
	        }
        }

        return tip;
    }

    // makes the tooltip's location to match table cell location
    // also avoids showing empty tooltips
    @Override
	public Point getToolTipLocation(final MouseEvent event){
        final int row = rowAtPoint( event.getPoint() );
        if(row==-1) {
			return null;
		}
        final int col = columnAtPoint( event.getPoint() );
        if(col==-1) {
			return null;
		}

        // to avoid empty tooltips - return null location
        final boolean hasTooltip = getToolTipText()==null
                ? getToolTipText(event)!=null
                : true;

        return hasTooltip
                ? getCellRect(row, col, false).getLocation()
                : null;
    }

	public void propertyChange(final PropertyChangeEvent evt) {
		logger.debug( "Repaint: DTTable");

		setupEditors();
	}

	public DTTableModel getTableModel() {
		return tableModel;
	}

	public void deleteSelectedRows() {
		final int nrSelectedRows = getSelectedRowCount();

		if( nrSelectedRows > 0) {
			final int[] selectedRows = getSelectedRows();

			for( int i=nrSelectedRows-1; i>=0; --i) {
				final int row = selectedRows[i];
				ActionUtil.deleteRow( getDecisionTable(), row);
			}
		} else {
			final int currentSelectedRow = getRowSelected();
			if( currentSelectedRow != -1) {
				ActionUtil.deleteRow( getDecisionTable(), getRowSelected());
			}
		}
	}
}
