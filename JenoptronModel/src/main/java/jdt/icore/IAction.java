/*******************************************************************************
 * Copyright 2015 Michiel Kalkman
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 *******************************************************************************/
package jdt.icore;

import java.io.Serializable;
import java.util.List;

import jdt.core.binary.BinaryAction;

/**
 * Describes a kind of action.
 * 
 * E.g. a flag signifying whether something must be printed or not. But not a
 * flag that signifies that something must be printed (i.e. a value).
 * 
 * @author Michiel Kalkman
 * 
 */
public interface IAction extends Serializable {
	static final String CHANGE_EVENT = "CHANGE_EVENT";
	static final String PROP_SHORT_DESCRIPTION = "shortDescription";

	IAction deepcopy();

	IValue getDefaultValue();

	IValue getUnknownValue();

	List<IValue> getSelectableValues();

	String getShortDescription();

	BinaryAction setShortDescription(String shortDescription);

	IValue parse(String string);
}
