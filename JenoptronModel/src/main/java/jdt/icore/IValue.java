package jdt.icore;

import java.io.Serializable;

/**
 * Base interface for all value classes.
 * @author Michiel Kalkman
 */
public interface IValue extends Comparable<Object>, Serializable{
	boolean isInstanceOf( IValue value);
	IValue getDefaultValue();
	IValue getInitialValue();
}
