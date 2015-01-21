package dt.generators;

import java.util.ArrayList;
import java.util.List;
import java.util.ServiceLoader;


public abstract class GeneratorFactorySPI {
	public abstract List<Generator> getGenerators();

	public static Iterable<Generator> getAllGenerators() {
		final List<Generator> generators = new ArrayList<Generator>();

		final ServiceLoader<GeneratorFactorySPI> generatorFactories
	     = ServiceLoader.load(GeneratorFactorySPI.class);

		for( final GeneratorFactorySPI generatorFactory : generatorFactories) {
			generators.addAll( generatorFactory.getGenerators());
		}
		return generators;
	}

	// @todo use a predicate
	public static Iterable<Generator> getAllFileGenerators() {
		final List<Generator> generators = new ArrayList<Generator>();

		final ServiceLoader<GeneratorFactorySPI> generatorFactories
	     = ServiceLoader.load(GeneratorFactorySPI.class);

		for( final GeneratorFactorySPI generatorFactory : generatorFactories) {
			for( final Generator generator : generatorFactory.getGenerators()) {
				generators.add( generator);
			}
		}
		return generators;
	}

	public static Generator getAnyGenerator( final String shortDescription) {
		Generator generator = null;

		final ServiceLoader<GeneratorFactorySPI> generatorFactories
	     = ServiceLoader.load(GeneratorFactorySPI.class);

		for( final GeneratorFactorySPI generatorFactory : generatorFactories) {
			final List<Generator> generators = generatorFactory.getGenerators();

			for( final Generator generator2 : generators) {
				if( shortDescription.equals( generator2.getShortDescription())) {
					generator = generator2;
					break;
				}
			}
		}

		return generator;
	}

	public static TextGenerator getAnyTextGenerator() {
		TextGenerator textGenerator = null;

		final ServiceLoader<GeneratorFactorySPI> generatorFactories
	     = ServiceLoader.load(GeneratorFactorySPI.class);

		for( final GeneratorFactorySPI generatorFactory : generatorFactories) {
			final List<Generator> generators = generatorFactory.getGenerators();

			for( final Generator generator : generators) {
				if( generator instanceof TextGenerator) {
					textGenerator = (TextGenerator)generator;
					break;
				}
			}
		}

		return textGenerator;
	}

	public static TextGenerator getTextGenerator( final String shortDescription) {
		TextGenerator textGenerator = null;

		final ServiceLoader<GeneratorFactorySPI> generatorFactories
	     = ServiceLoader.load(GeneratorFactorySPI.class);

		for( final GeneratorFactorySPI generatorFactory : generatorFactories) {
			final List<Generator> generators = generatorFactory.getGenerators();

			for( final Generator generator : generators) {
				if( shortDescription.equals( generator.getShortDescription())
						&& (generator instanceof TextGenerator)) {
					textGenerator = (TextGenerator)generator;
					break;
				}
			}
		}

		return textGenerator;
	}

	public static List<TextGenerator> getTextGenerators() {
		List<TextGenerator> textGenerators = new ArrayList<TextGenerator>();

		final ServiceLoader<GeneratorFactorySPI> generatorFactories
	     = ServiceLoader.load(GeneratorFactorySPI.class);

		for( final GeneratorFactorySPI generatorFactory : generatorFactories) {
			final List<Generator> generators = generatorFactory.getGenerators();

			for( final Generator generator : generators) {
				if( generator instanceof TextGenerator) {
					textGenerators.add((TextGenerator)generator);
				}
			}
		}

		return textGenerators;
	}
}
