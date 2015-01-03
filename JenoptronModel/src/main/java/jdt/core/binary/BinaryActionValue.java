package jdt.core.binary;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import jdt.icore.IValue;
import jdt.util.xmlencoder.EnumDelegate;
import jdt.util.xmlencoder.XMLEncoderFactory;

public class BinaryActionValue implements IValue {
	/**
	 *
	 */
	private static final long serialVersionUID = 8840365481949810899L;
	public static final BinaryActionValue DO = new BinaryActionValue( Values.DO);
	public static final BinaryActionValue DONT = new BinaryActionValue( Values.DONT);
	public static final BinaryActionValue UNKNOWN = new BinaryActionValue( Values.UNKNOWN);

	private static final BinaryActionValue binaryActionValues[] = {	DO, DONT, UNKNOWN };

	public static final BinaryActionValue INITIAL_VALUE = new BinaryActionValue( Values.UNKNOWN);
	public static final BinaryActionValue DEFAULT_VALUE = new BinaryActionValue( Values.DO);

	private static List<IValue> selectableValuesList;

	public enum Values { DO, DONT, UNKNOWN; }


	static {
		selectableValuesList = new ArrayList<IValue>();
		// @todo: report the following - it does not work !
		// Arrays.fill( binaryActionValues, possibleValuesList);
		for( final BinaryActionValue binaryActionValue : binaryActionValues) {
			selectableValuesList.add( binaryActionValue);
		}
		selectableValuesList = Collections.unmodifiableList( selectableValuesList);

		XMLEncoderFactory.add( BinaryActionValue.Values.class,
			EnumDelegate.create( BinaryActionValue.Values.class));
	}

	private Values value;

	public BinaryActionValue() {
		super();
	}

	public BinaryActionValue( final Values value) {
		super();
		this.value = value;
	}

	public Values getBinaryActionValue() {
		return value;
	}

	public IValue getDefaultValue() {
		return defaultValue();
	}

	public IValue getInitialValue() {
		return initialValue();
	}

	public static IValue defaultValue() {
		return DEFAULT_VALUE;
	}

	public static IValue initialValue() {
		return INITIAL_VALUE;
	}

	public Values getValue() {
		return value;
	}

	public void setValue( final Values values) {
		this.value = values;
	}

	public static List<IValue> selectableValues() {
		return selectableValuesList;
	}

	@Override
	public String toString() {
		String strValue;
		switch( value) {
		case DO: strValue = "Do"; break;
		case DONT: strValue = "Dont"; break;
		case UNKNOWN: strValue = "?"; break;
		default: strValue = "Unknown";
		}
		return strValue;
	}

	public boolean isInstanceOf(final IValue value) {
		return this.equals( value) || value.equals( defaultValue());
	}

	@Override
	public boolean equals(final Object object) {
		boolean fEquals = false;
		if( object instanceof BinaryActionValue) {
			final BinaryActionValue binaryActionValue = (BinaryActionValue) object;
			fEquals = this.getValue() == binaryActionValue.getValue();
		}
		return fEquals;
	}

	@Override
	public int hashCode() {
		return getValue().hashCode();
	}

	public static final IValue parse(final String string) {
		final IValue value;
		if( DO.value.toString().equalsIgnoreCase( string)) {
			value = DO;
		} else if( DONT.value.toString().equalsIgnoreCase( string)) {
			value = DONT;
		} else {
			value = UNKNOWN;
		}
		return value;
	}

	/*
	 * @param Object object because
	 * @see java.lang.Comparable#compareTo(java.lang.Object)
	 */
	public int compareTo(final Object object) {
		return 0;
	}
}
