package dt.swing.props;

import java.awt.Dimension;
import java.awt.GridLayout;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.swing.JInternalFrame;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JSplitPane;
import javax.swing.JTree;
import javax.swing.event.InternalFrameEvent;
import javax.swing.event.InternalFrameListener;
import javax.swing.event.TreeSelectionEvent;
import javax.swing.event.TreeSelectionListener;
import javax.swing.tree.DefaultMutableTreeNode;
import javax.swing.tree.TreeNode;
import javax.swing.tree.TreePath;
import javax.swing.tree.TreeSelectionModel;

import jdt.core.category.IGroup;
import jdt.icore.IAction;
import jdt.icore.ICondition;
import jdt.icore.IDecisionTable;
import jdt.util.xmlencoder.XMLEncoderFactory;

import org.taHjaj.wo.swingk.util.NXUtil;

import dt.swing.util.DTInternalFrame;
import dt.swing.util.jtree.NXTree;

public class DTProps extends DTInternalFrame implements InternalFrameListener,
		TreeSelectionListener, PropertyChangeListener {

	private static final long serialVersionUID = 2879561790604700261L;

//	private static final Logger logger = Logger.getLogger(DTProps.class);

	private JPanel panel;
	private JPanel treePanel;

	private final Map<Object, JPanel> panels = new HashMap<Object, JPanel>();

	private IDecisionTable decisionTable;

	static {
		XMLEncoderFactory.add(JInternalFrame.class, getPersistenceDelegate());
	}

	public DTProps() {
		// Just for use with XMLEncoder.
		super();
	}

	public DTProps(final IDecisionTable decisionTable) {
		super("Properties: " + decisionTable.getShortDescription(), true, true,
				true, true);
		this.decisionTable = decisionTable;

		setLayout(new GridLayout(1, 0));

		final JTree tree = createTree(decisionTable);

		treePanel = new JPanel();
		treePanel.add( tree);

		panel = panels.get( decisionTable.getShortDescription());

		// Create the scroll pane and add the tree to it.
		final JScrollPane treeView = new JScrollPane(treePanel);
		final JScrollPane dataView = new JScrollPane(panel);

		// Add the scroll panes to a split pane.
		final JSplitPane splitPane = new JSplitPane(JSplitPane.HORIZONTAL_SPLIT);
		splitPane.setTopComponent(treeView);
		splitPane.setBottomComponent(dataView);

		final Dimension minimumSize = new Dimension(100, 50);
		dataView.setMinimumSize(minimumSize);
		treeView.setMinimumSize(minimumSize);
		splitPane.setDividerLocation(100); // XXX: ignored in some releases
		// of Swing. bug 4101306
		// workaround for bug 4101306:
		// treeView.setPreferredSize(new Dimension(100, 100));

		splitPane.setPreferredSize(new Dimension(500, 300));

		// Add the split pane to this panel.
		add(splitPane);

		addInternalFrameListener(this);
		decisionTable.addPropertyChangeListener( this);

		pack();
		setVisible(true);
	}

	private JTree createTree(final IDecisionTable decisionTable) {
		final DefaultMutableTreeNode top = new DefaultMutableTreeNode(decisionTable
				.getShortDescription());
		createNodes(top, decisionTable);

		final NXTree tree = new NXTree(top);
		tree.getSelectionModel().setSelectionMode(
				TreeSelectionModel.SINGLE_TREE_SELECTION);
		tree.expandAll();

		tree.addTreeSelectionListener( this);
		return tree;
	}

	// From Javaalmanac
	public TreePath getPath( final TreeNode node) {
		final List<TreeNode> list = new ArrayList<TreeNode>();

		addParents(list, node);

		Collections.reverse(list);

		// Convert array of nodes to TreePath
		return new TreePath(list.toArray());
	}

	private void addParents( final List<TreeNode> list, final TreeNode node) {
		if( node != null) {
			list.add( node);
			addParents( list, node.getParent());
		}
	}

	public TreePath[] getPaths(final JTree tree, final boolean expanded) {
		final TreeNode root = (TreeNode) tree.getModel().getRoot();

		// Create array to hold the treepaths
		final List<TreePath> list = new ArrayList<TreePath>();

		// Traverse tree from root adding treepaths for all nodes to list
		getPaths(tree, new TreePath(root), expanded, list);

		// Convert list to array
		return list.toArray(new TreePath[list.size()]);
	}

	// From Javaalmanac
	public void getPaths(final JTree tree, final TreePath parent, final boolean expanded,
			final List<TreePath> list) {
		// Return if node is not expanded
		if (expanded && !tree.isVisible(parent)) {
			return;
		}

		// Add node to list
		list.add(parent);

		// Create paths for all children
		final TreeNode node = (TreeNode) parent.getLastPathComponent();
		if (node.getChildCount() >= 0) {
			for (final Enumeration<TreeNode> e = node.children(); e.hasMoreElements();) {
				final TreeNode n = e.nextElement();
				final TreePath path = parent.pathByAddingChild(n);
				getPaths(tree, path, expanded, list);
			}
		}
	}

	private void createNodes(final DefaultMutableTreeNode top,
			final IDecisionTable decisionTable) {
		panels.clear();

		final DefaultMutableTreeNode conditions = new DefaultMutableTreeNode(
			"conditions");
		top.add(conditions);

		panels.put( top.getUserObject(), new DTDecisionTableProps(
				decisionTable));

		for (final ICondition condition : decisionTable.getConditions()) {
			final DefaultMutableTreeNode aCondition = new DefaultMutableTreeNode(
					condition.getShortDescription());
			conditions.add(aCondition);
			panels.put( condition.getShortDescription(), new DTConditionProps(
					condition));
		}

		final DefaultMutableTreeNode actions = new DefaultMutableTreeNode(
			"actions");
		top.add(actions);
		for (final IAction action : decisionTable.getActions()) {
			final DefaultMutableTreeNode anAction = new DefaultMutableTreeNode(action
					.getShortDescription());
			actions.add(anAction);
			panels.put( action.getShortDescription(), new DTActionProps(
					action));
		}

//		final DefaultMutableTreeNode rules = new DefaultMutableTreeNode(
//			"rules");
//		top.add(rules);
//		for (final IRule rule : decisionTable.getRules()) {
//			final DefaultMutableTreeNode aGroup = new DefaultMutableTreeNode( rule
//					.getShortDescription());
//			rules.add(aGroup);
//			panels.put( rule.getShortDescription(), new RulePanel( rule));
//		}
//		panels.put( "rules", new RulesCentral( decisionTable));

		final DefaultMutableTreeNode groups = new DefaultMutableTreeNode(
			"categories");
		top.add( groups);
		for (final IGroup group : decisionTable.getGroups()) {
			final DefaultMutableTreeNode aGroup = new DefaultMutableTreeNode(group
					.getShortDescription());
			groups.add(aGroup);
			panels.put( group.getShortDescription(), new CategoryPanel( group));
		}
		panels.put( "categories", new CategoriesCentral( decisionTable));
	}

	public void valueChanged(final TreeSelectionEvent treeSelectionEvent) {
		final DefaultMutableTreeNode node = (DefaultMutableTreeNode)
			((JTree)treeSelectionEvent.getSource()).getLastSelectedPathComponent();

		if (node == null) {
			return;
		}

		final Object nodeInfo = node.getUserObject();
		final JPanel dataPanel = panels.get(nodeInfo);

		NXUtil.invokeLater(new Runnable() {
			public void run() {
				panel.removeAll();
				if (dataPanel == null) {
					panel.add(new DTDefaultProps(nodeInfo.toString()));
				} else {
					panel.add(dataPanel);
				}
				panel.setVisible(false);
				panel.setVisible(true);
			}
		});
	}

	public void propertyChange( final PropertyChangeEvent propertyChangeEvent) {
		NXUtil.invokeLater(new Runnable() {
			public void run() {
				setTitle( "Properties: " + decisionTable.getShortDescription());
				treePanel.removeAll();
				treePanel.add( createTree(decisionTable));
				treePanel.setVisible(false);
				treePanel.setVisible(true);
			}
		});
	}

	public void internalFrameOpened(final InternalFrameEvent e) {
		// TODO Auto-generated method stub

	}

	public void internalFrameClosing(final InternalFrameEvent e) {
		// TODO Auto-generated method stub

	}

	public void internalFrameClosed(final InternalFrameEvent e) {
		// TODO Auto-generated method stub

	}

	public void internalFrameIconified(final InternalFrameEvent e) {
		// TODO Auto-generated method stub

	}

	public void internalFrameDeiconified(final InternalFrameEvent e) {
		// TODO Auto-generated method stub

	}

	public void internalFrameActivated(final InternalFrameEvent e) {
		// TODO Auto-generated method stub

	}

	public void internalFrameDeactivated(final InternalFrameEvent e) {
		// TODO Auto-generated method stub

	}
}
