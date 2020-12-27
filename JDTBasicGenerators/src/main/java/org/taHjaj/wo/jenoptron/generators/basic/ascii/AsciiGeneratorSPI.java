package org.taHjaj.wo.jenoptron.generators.basic.ascii;

import org.springframework.util.CollectionUtils;
import org.taHjaj.wo.jenoptron.generators.basic.Generator;
import org.taHjaj.wo.jenoptron.generators.basic.GeneratorFactorySPI;

import java.util.List;

public class AsciiGeneratorSPI extends GeneratorFactorySPI {

	@Override
	public List<Generator> getGenerators() {
		final Generator[] generators = new Generator[] { new AsciiGenerator() };
		return CollectionUtils.arrayToList( generators);
	}
}
