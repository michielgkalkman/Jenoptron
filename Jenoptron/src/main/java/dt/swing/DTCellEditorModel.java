/*
 * Created on Aug 1, 2005
 *
 * TODO To change the template for this generated file go to
 * Window - Preferences - Java - Code Style - Code Templates
 */
package dt.swing;

import java.util.HashMap;
import java.util.Map;

import javax.swing.table.TableCellEditor;

/**
 * @author Michiel Kalkman
 *
 * TODO To change the template for this generated type comment go to
 * Window - Preferences - Java - Code Style - Code Templates
 */
public class DTCellEditorModel {
	private final Map<Class,TableCellEditor> cellEditors = new HashMap<Class,TableCellEditor>();
	
	public DTCellEditorModel() {
		super();
	}
	
	public void addEditor( final Class someClass, final TableCellEditor tableCellEditor) {
		cellEditors.put( someClass, tableCellEditor);
	}
	
	public TableCellEditor getEditor( final Class someClass) {
		return cellEditors.get( someClass);
	}
}
