/*
 * ******************************************************************************
 *  * Copyright 2020 Michiel Kalkman
 *  *
 *  * Licensed under the Apache License, Version 2.0 (the "License");
 *  * you may not use this file except in compliance with the License.
 *  * You may obtain a copy of the License at
 *  *
 *  *     http://www.apache.org/licenses/LICENSE-2.0
 *  *
 *  * Unless required by applicable law or agreed to in writing, software
 *  * distributed under the License is distributed on an "AS IS" BASIS,
 *  * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 *  * See the License for the specific language governing permissions and
 *  * limitations under the License.
 *  ******************************************************************************
 */
package org.taHjaj.wo.jenoptron.model.core.binary;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import org.taHjaj.wo.jenoptron.model.icore.IConditionValue;
import org.taHjaj.wo.jenoptron.model.icore.IValue;
import org.taHjaj.wo.jenoptron.model.util.xmlencoder.EnumDelegate;
import org.taHjaj.wo.jenoptron.model.util.xmlencoder.XMLEncoderFactory;

public class BinaryConditionValue implements IConditionValue {
	/**
	 *
	 */
	private static final long serialVersionUID = 4080273622232089465L;

	public enum Values { YES, NO, IRRELEVANT; }

	public static final BinaryConditionValue YES = new BinaryConditionValue( Values.YES);
	public static final BinaryConditionValue NO = new BinaryConditionValue( Values.NO);
	public static final BinaryConditionValue IRRELEVANT = new BinaryConditionValue( Values.IRRELEVANT);

	private static final BinaryConditionValue binaryConditionValues[] = { YES, NO };

	public static final BinaryConditionValue INITIAL_VALUE = IRRELEVANT;
	public static final BinaryConditionValue DEFAULT_VALUE = YES;

	private static List<IConditionValue> possibleValuesList;

	static {
		possibleValuesList = new ArrayList<IConditionValue>();
		for( final BinaryConditionValue binaryConditionValue : binaryConditionValues) {
			possibleValuesList.add( binaryConditionValue);
		}
		possibleValuesList = Collections.unmodifiableList( possibleValuesList);

		XMLEncoderFactory.add( BinaryConditionValue.Values.class,
				EnumDelegate.create( BinaryConditionValue.Values.class));
	}


	private Values value;

	public BinaryConditionValue() {
		super();
	}

	public BinaryConditionValue( final Values value) {
		super();
		this.value = value;
	}

	public Values getBinaryConditionValue() {
		return value;
	}

	public IConditionValue getDefaultValue() {
		return defaultValue();
	}

	public IConditionValue getInitialValue() {
		return initialValue();
	}

	public IConditionValue getIrrelevantValue() {
		return IRRELEVANT;
	}

	public static IConditionValue defaultValue() {
		return DEFAULT_VALUE;
	}

	public static IConditionValue initialValue() {
		return INITIAL_VALUE;
	}

	public Values getValue() {
		return value;
	}

	public static List<IConditionValue> getPossibleValues() {
		return possibleValuesList;
	}

	public boolean isInstanceOf( final IValue value) {
		return this.equals( value) || value.equals( getIrrelevantValue());
	}

	@Override
	public String toString() {
		String strValue;
		switch( value) {
		case YES: strValue = "Yes"; break;
		case NO: strValue = "No"; break;
		case IRRELEVANT: strValue = "Irrelevant"; break;
		default: strValue = "";
		}
		return strValue;
	}

	@Override
	public boolean equals(final Object object) {
		return compareTo(object) == 0;
	}

	@Override
	public int hashCode() {
		return getValue().hashCode();
	}

	public static final IValue parse(final String string) {
		final IValue value;
		if( YES.value.toString().equalsIgnoreCase( string)) {
			value = YES;
		} else if( NO.value.toString().equalsIgnoreCase( string)) {
			value = NO;
		} else if( IRRELEVANT.value.toString().equalsIgnoreCase( string)) {
			value = IRRELEVANT;
		} else {
			value = defaultValue();
		}
		return value;
	}

	public int compareTo( final Object object) {
		int result = -1;

		if( object instanceof BinaryConditionValue) {
			final BinaryConditionValue binaryConditionValue = (BinaryConditionValue) object;
			final Values otherValue = binaryConditionValue.getValue();
			final int ordinal = value.ordinal();
			final int otherOrdinal = otherValue.ordinal();
			if( ordinal > otherOrdinal) {
				result = 1;
			} else if( ordinal == otherOrdinal) {
				result = 0;
			}
		}

		return result;
	}

	public void setValue(final Values value) {
		this.value = value;
	}
}
