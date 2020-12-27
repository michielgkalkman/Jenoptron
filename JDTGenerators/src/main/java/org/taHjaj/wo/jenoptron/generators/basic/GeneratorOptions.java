package dt.generators;

import java.io.Serializable;

public interface GeneratorOptions extends Serializable {
	public void copy(final GeneratorOptions generatorOptions);
}
