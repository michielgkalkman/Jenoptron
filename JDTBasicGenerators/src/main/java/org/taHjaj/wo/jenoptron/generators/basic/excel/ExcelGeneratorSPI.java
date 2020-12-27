package org.taHjaj.wo.jenoptron.generators.basic.excel;

import org.springframework.util.CollectionUtils;
import org.taHjaj.wo.jenoptron.generators.basic.Generator;
import org.taHjaj.wo.jenoptron.generators.basic.GeneratorFactorySPI;

import java.util.List;

public class ExcelGeneratorSPI extends GeneratorFactorySPI {

	@Override
	public List<Generator> getGenerators() {
		final Generator[] generators = new Generator[] { new ExcelGenerator() };
		return CollectionUtils.arrayToList( generators);
	}
}
