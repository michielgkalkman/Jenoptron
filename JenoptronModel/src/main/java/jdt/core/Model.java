package jdt.core;

import java.beans.PropertyChangeEvent;
import java.io.Serializable;

public abstract class Model extends rx.Observable<PropertyChangeEvent>implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 4355813285118680429L;
	private static rx.Observable.OnSubscribe<PropertyChangeEvent> onSubscribe;

	public Model() {
		super(onSubscribe);
	}
}
