package org.taHjaj.wo.jenoptron.generators.spi;

import java.io.Serializable;

public interface GeneratorOptions extends Serializable {
	void copy(final GeneratorOptions generatorOptions);
}
