/*
 * Created on Jul 28, 2005
 *
 * TODO To change the template for this generated file go to
 * Window - Preferences - Java - Code Style - Code Templates
 */
package dt.swing.menu;

import java.awt.event.ActionEvent;
import java.awt.event.KeyEvent;

import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.KeyStroke;

import dt.swing.DTDesktop;
import dt.swing.DTTable;
import dt.swing.action.LoadAction;
import dt.swing.action.NewAction;
import dt.swing.action.NotImplementedYetAction;
import dt.swing.action.SaveAction;
import dt.swing.action.SaveAsAction;

/**
 * @author Michiel Kalkman
 * 
 * TODO To change the template for this generated type comment go to Window -
 * Preferences - Java - Code Style - Code Templates
 */
public class DTMenuBar extends JMenuBar {
	private static final long serialVersionUID = -1923594558628660827L;

	public DTMenuBar( final DTDesktop desktop) {
		super();
		{
			final JMenu menu = new JMenu("File");
			menu.setMnemonic(KeyEvent.VK_F);
			add(menu);
	
			{
				final JMenuItem menuItem = new JMenuItem("Save", KeyEvent.VK_S);
				menuItem.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_S,
						ActionEvent.CTRL_MASK));
				menuItem.addActionListener(new SaveAction( desktop));
				menu.add(menuItem);
			}

			{
				final JMenuItem menuItem = new JMenuItem("Load", KeyEvent.VK_L);
				menuItem.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_L,
						ActionEvent.CTRL_MASK));
				menuItem.addActionListener(new LoadAction( desktop));
				menu.add(menuItem);
			}
		}
		{
			final JMenu menu = new JMenu("Table");
			menu.setMnemonic(KeyEvent.VK_T);
			add(menu);
	
			{
				final JMenuItem menuItem = new JMenuItem("New", KeyEvent.VK_N);
				menuItem.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_N,
						ActionEvent.CTRL_MASK));
				menuItem.addActionListener( new NewAction( desktop));
				menu.add(menuItem);
			}
		}
		{
			final JMenu menu = new JMenu("Windows");
			menu.setMnemonic(KeyEvent.VK_W);
			add(menu);
	
			{
				final JMenuItem menuItem = new JMenuItem("Tile", KeyEvent.VK_T);
				menuItem.addActionListener( new NotImplementedYetAction( desktop));
				menu.add(menuItem);
			}
		}
	}

	public DTMenuBar(final DTTable table) {
		super();
		
		final JMenu menu = new JMenu("File");
		menu.setMnemonic(KeyEvent.VK_F);
		add(menu);

		{
			final JMenuItem menuItem = new JMenuItem("Save", KeyEvent.VK_S);
			menuItem.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_S,
					ActionEvent.CTRL_MASK));
			menuItem.addActionListener(new SaveAction( table));
			menu.add(menuItem);
		}
		{
			final JMenuItem menuItem = new JMenuItem("Save As ...");
			menuItem.addActionListener(new SaveAsAction(table));
			menu.add(menuItem);
		}
	}
}
