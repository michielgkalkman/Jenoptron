package org.taHjaj.wo.jenoptron.generators.spi.excel;

import org.apache.commons.collections.CollectionUtils;
import org.taHjaj.wo.jenoptron.generators.spi.Generator;
import org.taHjaj.wo.jenoptron.generators.spi.GeneratorFactorySPI;

import java.util.ArrayList;
import java.util.List;

public class ExcelGeneratorSPI extends GeneratorFactorySPI {

	@Override
	public List<Generator> getGenerators() {
		final Generator[] generators = new Generator[] { new ExcelGenerator() };
		final ArrayList<Generator> generatorArrayList = new ArrayList<>();
		CollectionUtils.addAll( generatorArrayList, generators);
		return generatorArrayList;
	}
}
