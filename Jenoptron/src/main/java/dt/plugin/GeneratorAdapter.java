package dt.plugin;

import java.awt.Component;
import java.awt.Font;

import javax.swing.JTextArea;

import org.apache.commons.lang.StringUtils;

import dt.generators.Generator;

public abstract class GeneratorAdapter implements Generator {

	public Component getComponent() {
		return getTextArea( null);
	}

	private Component getTextArea(final String text) {
		final JTextArea textArea = new JTextArea( text);
		textArea.setColumns( text.indexOf( '\n'));
		textArea.setRows( StringUtils.countMatches( text,"\n") + 1);
		textArea.setDragEnabled( true);

		final Font font = new Font( "Monospaced", Font.PLAIN, textArea.getFont().getSize());
		textArea.setFont( font);

		return textArea;
	}
}
