package dt.swing;

import javax.help.HelpBroker;
import javax.help.HelpSet;
import javax.swing.JComponent;

public class DTHelp {
	private HelpSet m_hs;
	private HelpBroker m_hb;
	private static DTHelp help;
	
	private static final String HELPSETNAME = "/Help/Layout.hs";

	static {
		help = new DTHelp();
	}

	private DTHelp() {
		super();
		final ClassLoader classLoader = this.getClass().getClassLoader();
		try {
			m_hs = new HelpSet( classLoader, getClass().getResource( HELPSETNAME));
			m_hb = m_hs.createHelpBroker();
		} catch ( final Exception exception) {
		
			exception.printStackTrace();
		}
	}
	
	public static void createHelp( final JComponent component) {
		help.m_hb.enableHelpKey( component, help.m_hb.getHelpSet().getHomeID().id,
				null);
	}
}
