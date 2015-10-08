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

import jdt.core.binary.BinaryCondition;

/**
 * A description of a condition.
 * 
 * E.g. A flag to search for a category of items. BUT NOT THE VALUE. So, it does
 * not represent whether the flag to search for a category of items is set or
 * not.
 * 
 * @author Michiel Kalkman
 * 
 */
public interface ICondition extends Serializable {
	static final String CHANGE_EVENT = "CHANGE_EVENT";
	static final String PROP_SHORT_DESCRIPTION = "shortDescription";

	IConditionValue getIrrelevantValue();

	IConditionValue getDefaultValue();

	ICondition deepcopy();

	List<IConditionValue> getPossibleValues();

	String getShortDescription();

	BinaryCondition setShortDescription(String shortDescription);
}
