package dt.swing.display;

import java.awt.Component;
import java.awt.Font;
import java.beans.PropertyChangeListener;

import javax.swing.JTextArea;

import org.apache.commons.lang.StringUtils;
import org.taHjaj.wo.events.ObservableMediator;

import dt.generators.Generator;
import dt.generators.TextGenerator;
import dt.swing.DTPanel;

public class DTDisplayPanel extends DTPanel implements PropertyChangeListener {
	/**
	 *
	 */
	private static final long serialVersionUID = 4105467948577172683L;
//	private static final Logger logger = Logger.getLogger( DTDisplayPanel.class);
	private final DTDisplayBean displayBean;

	public DTDisplayPanel( final DTDisplayBean displayBean) {
		super();

		this.displayBean = displayBean;

		fill();

		ObservableMediator.getInstance().register( displayBean.getCurrentGenerator().getGeneratorOptions(), this);
	}

	private Component getDisplay( final String text) {
		final JTextArea textArea = new JTextArea( text);
		textArea.setColumns( text.indexOf( '\n'));
		textArea.setRows( StringUtils.countMatches( text,"\n") + 1);
		textArea.setDragEnabled( true);

		final Font font = new Font( "Monospaced", Font.PLAIN, textArea.getFont().getSize());
		textArea.setFont( font);

		return textArea;
	}

	@Override
	protected void fill() {
		final Generator generator = displayBean.getCurrentGenerator();

		final Component component;
		if( generator instanceof TextGenerator) {
			component = getDisplay( ((TextGenerator)generator).getText());
		} else {
			component = getDisplay( "No display");
		}

		add( component);





//		} else if( displayOptionBean instanceof DTAsciiDisplayBean) {
//			displayOptionBean.addPropertyChangeListener( this);
//		} else if( displayOptionBean instanceof DTXhtmlBean) {
//			final DTXhtmlBean xhtmlBean = (DTXhtmlBean) displayOptionBean;
//			if( xhtmlBean.getIsShownAsText()) {
//				final String text = XHTMLTableGenerator.generate( displayBean.getDecisionTable());
//				final JTextArea textArea = new JTextArea( text);
//				textArea.setColumns( text.indexOf( '\n'));
//				textArea.setRows( StringUtils.countMatches( text,"\n") + 1);
//
//				final Font font = new Font( "Monospaced", Font.PLAIN, textArea.getFont().getSize());
//				textArea.setFont( font);
//
//				add( textArea);
//			} else {
//				final String text = XHTMLTableGenerator.generate( displayBean.getDecisionTable());
//				final JEditorPane editorPane = new JEditorPane( "text/html", text);
//				add( editorPane);
//			}
//
//			displayOptionBean.addPropertyChangeListener( this);
	}
}
