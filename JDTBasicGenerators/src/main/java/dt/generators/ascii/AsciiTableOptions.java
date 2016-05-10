package dt.generators.ascii;

import org.apache.commons.lang.builder.ToStringBuilder;
import org.apache.commons.lang.builder.ToStringStyle;

import dt.generators.GeneratorOptions;

public class AsciiTableOptions implements GeneratorOptions {
	@Override
	public String toString() {
		return ToStringBuilder.reflectionToString(this, ToStringStyle.MULTI_LINE_STYLE);
	}

	private void fire() {
	}

	/**
	 *
	 */
	private static final long serialVersionUID = 353838050855412550L;

	@Override
	public void copy(final GeneratorOptions generatorOptions) {
		if (generatorOptions instanceof AsciiTableOptions) {
			setOptions((AsciiTableOptions) generatorOptions);
		}
	}

	public void setOptions(final AsciiTableOptions asciiTableOptions) {
		this.fBorders = asciiTableOptions.fBorders;
		this.fConditionActionSeparator = asciiTableOptions.fConditionActionSeparator;
		this.fWrapping = asciiTableOptions.fWrapping;
		this.wrapLength = asciiTableOptions.wrapLength;
	}

	private boolean fBorders = true;
	private boolean fConditionActionSeparator = true;
	private boolean fWrapping = false;
	private int wrapLength = 20;

	public AsciiTableOptions(final boolean fBorders, final boolean fConditionActionSeparator, final boolean fWrapping,
			final int wrapLength) {
		this.fBorders = fBorders;
		this.fConditionActionSeparator = fConditionActionSeparator;
		this.fWrapping = fWrapping;
		this.wrapLength = wrapLength;
	}

	public boolean isFBorders() {
		return fBorders;
	}

	public void setFBorders(final boolean borders) {
		fBorders = borders;
		fire();
	}

	public boolean isFConditionActionSeparator() {
		return fConditionActionSeparator;
	}

	public void setFConditionActionSeparator(final boolean conditionActionSeparator) {
		fConditionActionSeparator = conditionActionSeparator;
		fire();
	}

	public boolean isFWrapping() {
		return fWrapping;
	}

	public void setFWrapping(final boolean wrapping) {
		fWrapping = wrapping;
		fire();
	}

	public int getWrapLength() {
		return wrapLength;
	}

	public void setWrapLength(final int wrapLength) {
		this.wrapLength = wrapLength;
		fire();
	}
}
