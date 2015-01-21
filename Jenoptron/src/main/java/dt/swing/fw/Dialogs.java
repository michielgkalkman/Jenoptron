package dt.swing.fw;
/*
 * Copyright (C) 2001-2003 Colin Bell
 * colbell@users.sourceforge.net
 *
 * This library is free software; you can redistribute it and/or
 * modify it under the terms of the GNU Lesser General Public
 * License as published by the Free Software Foundation; either
 * version 2.1 of the License, or (at your option) any later version.
 *
 * This library is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the GNU
 * Lesser General Public License for more details.
 *
 * You should have received a copy of the GNU Lesser General Public
 * License along with this library; if not, write to the Free Software
 * Foundation, Inc., 59 Temple Place, Suite 330, Boston, MA  02111-1307  USA
 */
import java.awt.Component;

import javax.swing.JOptionPane;
/**
 * This class provides some methods for using standard JDK dialogs.
 *
 * @author <A HREF="mailto:colbell@users.sourceforge.net">Colin Bell</A>
 */
public class Dialogs
{
	/** Internationalized strings for this class. */
	public static void showNotYetImplemented( final Component owner) {
		JOptionPane.showMessageDialog(owner, "Not yet implemented",
										"", JOptionPane.INFORMATION_MESSAGE);
	}

	public static boolean showYesNo( final Component owner, final String msg) {
		return showYesNo(owner, msg, "");
	}

	public static boolean showYesNo( final Component owner, final String msg, final String title) {
		final int rc = JOptionPane.showConfirmDialog(owner, msg, title,
												JOptionPane.YES_NO_OPTION);
		return rc == JOptionPane.YES_OPTION;
	}

	public static void showOk( final Component owner, final String msg) {
		JOptionPane.showMessageDialog(owner, msg, "", JOptionPane.INFORMATION_MESSAGE);
	}
	
	private Dialogs() {
		super();
	}
}
