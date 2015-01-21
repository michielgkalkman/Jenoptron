package dt.generators.excel;

import java.util.List;

import org.springframework.util.CollectionUtils;

import dt.generators.Generator;
import dt.generators.GeneratorFactorySPI;

public class ExcelGeneratorSPI extends GeneratorFactorySPI {

	@Override
	public List<Generator> getGenerators() {
		final Generator[] generators = new Generator[] { new ExcelGenerator() };
		return CollectionUtils.arrayToList( generators);
	}
}
