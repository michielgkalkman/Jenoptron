package dt.swing.display;

import org.taHjaj.wo.events.Observable;

public class PropertyList implements Observable {

	private final Observable observable;
	
	public PropertyList( final Observable observable) {
		super();
		this.observable = observable;
	}

	public int getNrProperties() {
		return 0;
	}

	public Observable getObservable() {
		return observable;
	}

}
