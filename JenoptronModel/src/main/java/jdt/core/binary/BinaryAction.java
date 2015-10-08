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
package jdt.core.binary;

import java.util.List;

import org.apache.commons.lang.StringUtils;

import jdt.icore.IAction;
import jdt.icore.IValue;

public class BinaryAction extends jdt.core.Model implements IAction {
	/**
	 * 
	 */
	private static final long serialVersionUID = -1617158798961613116L;
	private final String shortDescription;

	public BinaryAction() {
		this("BinaryAction");
	}

	public BinaryAction(final String shortDescription) {
		super();

		this.shortDescription = shortDescription;
	}

	@Override
	public IAction deepcopy() {
		return this;
	}

	@Override
	public IValue getDefaultValue() {
		return BinaryActionValue.defaultValue();
	}

	@Override
	public IValue getUnknownValue() {
		return BinaryActionValue.initialValue();
	}

	@Override
	public List<IValue> getSelectableValues() {
		return BinaryActionValue.selectableValues();
	}

	@Override
	public String toString() {
		return getShortDescription();
	}

	@Override
	public IValue parse(final String string) {
		return BinaryActionValue.parse(string);
	}

	@Override
	public String getShortDescription() {
		return shortDescription;
	}

	@Override
	public BinaryAction setShortDescription(final String shortDescription) {
		final BinaryAction binaryAction;
		if (StringUtils.equals(shortDescription, this.shortDescription)) {
			binaryAction = this;
		} else {
			binaryAction = new BinaryAction(shortDescription);
		}
		return binaryAction;
	}
}
