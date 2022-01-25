package org.taHjaj.wo.jenoptron.generators.spi.ascii;

import org.apache.commons.lang.builder.ToStringBuilder;
import org.apache.commons.lang.builder.ToStringStyle;
import org.taHjaj.wo.jenoptron.generators.spi.Generator;
import org.taHjaj.wo.jenoptron.generators.spi.GeneratorOptions;
import org.taHjaj.wo.jenoptron.generators.spi.TextGenerator;
import org.taHjaj.wo.jenoptron.model.icore.IDecisionTable;

import java.io.FileFilter;
import java.io.IOException;
import java.io.Writer;

public class AsciiGenerator implements TextGenerator {

	/**
	 *
	 */
	private static final long serialVersionUID = -670292186612685316L;

	@Override
	public String getText(final IDecisionTable decisionTable) {
		return AsciiTable.toString( decisionTable, asciiTableOptionsWrapper);
	}

	@Override
	public String getSuffix() {
		return ".txt";
	}

	public boolean write(final Writer writer) throws IOException {
		writer.write( getText());
		return true;
	}

	@Override
	public FileFilter getFileFilter() {
		return null;
	}

	private final AsciiTableOptionsWrapper asciiTableOptionsWrapper;
	private IDecisionTable decisionTable;

	@Override
	public String getText() {
		return getText( this.decisionTable);
	}

	@Override
	public GeneratorOptions getGeneratorOptions() {
		return asciiTableOptionsWrapper.getAsciiTableOptions();
	}

//	public Component getDisplay() {
//		final String text = AsciiTable.toString( decisionTable, asciiTableOptionsWrapper);
//		final JTextArea textArea = new JTextArea( text);
//		textArea.setColumns( text.indexOf( '\n'));
//		textArea.setRows( StringUtils.countMatches( text,"\n") + 1);
//		textArea.setDragEnabled( true);
//
//		final Font font = new Font( "Monospaced", Font.PLAIN, textArea.getFont().getSize());
//		textArea.setFont( font);
//
//		return textArea;
//	}

	public AsciiGenerator() {
		super();
		asciiTableOptionsWrapper = new AsciiTableOptionsWrapper( false, false, false, 20);
	}

	@Override
	public String toString() {
		return ToStringBuilder.reflectionToString( this, ToStringStyle.MULTI_LINE_STYLE);
	}

	public String getShortDescription() {
		return "ASCII";
	}

	public Generator setDecisionTable(final IDecisionTable decisionTable) {
		this.decisionTable = decisionTable;
		return this;
	}
}

