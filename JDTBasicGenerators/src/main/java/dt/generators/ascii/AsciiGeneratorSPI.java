package dt.generators.ascii;

import java.util.List;

import org.springframework.util.CollectionUtils;

import dt.generators.Generator;
import dt.generators.GeneratorFactorySPI;

public class AsciiGeneratorSPI extends GeneratorFactorySPI {

	@Override
	public List<Generator> getGenerators() {
		final Generator[] generators = new Generator[] { new AsciiGenerator() };
		return CollectionUtils.arrayToList( generators);
	}
}
