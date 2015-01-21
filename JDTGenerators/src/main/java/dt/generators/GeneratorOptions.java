package dt.generators;

import java.io.Serializable;

import org.taHjaj.wo.events.Observable;

public interface GeneratorOptions extends Serializable, Observable {
	public void copy( final GeneratorOptions generatorOptions);
}
