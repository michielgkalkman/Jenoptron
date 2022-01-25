package org.taHjaj.wo.jenoptron.generators.spi.ascii;

import org.apache.commons.collections.CollectionUtils;
import org.taHjaj.wo.jenoptron.generators.spi.Generator;
import org.taHjaj.wo.jenoptron.generators.spi.GeneratorFactorySPI;

import java.util.ArrayList;
import java.util.List;

public class AsciiGeneratorSPI extends GeneratorFactorySPI {

	@Override
	public List<Generator> getGenerators() {
		final Generator[] generators = new Generator[] { new AsciiGenerator() };
		final ArrayList<Generator> generatorArrayList = new ArrayList<>();
		CollectionUtils.addAll( generatorArrayList, generators);
		return generatorArrayList;
	}
}
