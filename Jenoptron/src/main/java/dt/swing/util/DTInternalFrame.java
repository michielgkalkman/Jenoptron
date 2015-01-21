/*
 * Created on Jul 28, 2005
 *
 * TODO To change the template for this generated file go to
 * Window - Preferences - Java - Code Style - Code Templates
 */
package dt.swing.util;

import java.beans.DefaultPersistenceDelegate;
import java.beans.PersistenceDelegate;
import java.beans.Statement;

import javax.swing.JInternalFrame;

import org.apache.commons.lang.builder.ToStringBuilder;
import org.apache.commons.lang.builder.ToStringStyle;

/**
 * @author Michiel Kalkman
 *
 * TODO To change the template for this generated type comment go to
 * Window - Preferences - Java - Code Style - Code Templates
 */
public class DTInternalFrame extends JInternalFrame {
	private static final long serialVersionUID = 8183883624862847564L;
    
	public DTInternalFrame() {
		super();
		// Auto-generated constructor stub
	}

	public DTInternalFrame(final String title, final boolean resizable, final boolean closable, final boolean maximizable, final boolean iconifiable) {
		super(title, resizable, closable, maximizable, iconifiable);
		// Auto-generated constructor stub
	}

	public DTInternalFrame(final String title, final boolean resizable, final boolean closable, final boolean maximizable) {
		super(title, resizable, closable, maximizable);
		// Auto-generated constructor stub
	}

	public DTInternalFrame(final String title, final boolean resizable, final boolean closable) {
		super(title, resizable, closable);
		// Auto-generated constructor stub
	}

	public DTInternalFrame(final String title, final boolean resizable) {
		super(title, resizable);
		// Auto-generated constructor stub
	}

	public DTInternalFrame(final String title) {
		super(title);
		// Auto-generated constructor stub
	}

	public static PersistenceDelegate getPersistenceDelegate() {
		  return new DefaultPersistenceDelegate() {
		    @Override
			protected void initialize(final Class type, final Object oldInstance, final Object newInstance, final java.beans.Encoder out) {
		      super.initialize(type, oldInstance, newInstance, out);
		      // bounds (java 1.4 work-around)
		      final JInternalFrame frame = (JInternalFrame) oldInstance;
		      final Statement boundsStatement = new Statement(oldInstance, "setBounds", new Object[] {frame.getBounds()});
		      out.writeStatement(boundsStatement);
		    }
		  };
		}


    @Override
	public String toString() {
		// TODO Auto-generated method stub
		return ToStringBuilder.reflectionToString( this, ToStringStyle.MULTI_LINE_STYLE);
	}

}
