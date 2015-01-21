package dt.swing.util.jtree;

import java.util.Hashtable;
import java.util.Vector;

import javax.swing.JTree;
import javax.swing.tree.TreeModel;
import javax.swing.tree.TreeNode;

public class NXTree extends JTree {
	/**
	 * 
	 */
	private static final long serialVersionUID = 7829792271152411022L;

	public void expandAll() {
		final int rowCount = getRowCount();
		for (int i = 0; i < rowCount; i++) {
			this.expandRow(i);
		}
	}

	public void collapseAll() {
		for (int i = getRowCount() - 1; i >= 0; i--) {
	         collapseRow(i);
		}
	}
	
	public NXTree() {
		super();
		// TODO Auto-generated constructor stub
	}

	public NXTree(final Hashtable<?, ?> value) {
		super(value);
		// TODO Auto-generated constructor stub
	}

	public NXTree(final Object[] value) {
		super(value);
		// TODO Auto-generated constructor stub
	}

	public NXTree(final TreeModel newModel) {
		super(newModel);
		// TODO Auto-generated constructor stub
	}

	public NXTree(final TreeNode root, final boolean asksAllowsChildren) {
		super(root, asksAllowsChildren);
		// TODO Auto-generated constructor stub
	}

	public NXTree(final TreeNode root) {
		super(root);
		// TODO Auto-generated constructor stub
	}

	public NXTree(final Vector<?> value) {
		super(value);
		// TODO Auto-generated constructor stub
	}

}
