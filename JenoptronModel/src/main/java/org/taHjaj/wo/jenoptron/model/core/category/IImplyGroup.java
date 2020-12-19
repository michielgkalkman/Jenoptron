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
package org.taHjaj.wo.jenoptron.model.core.category;

import org.taHjaj.wo.jenoptron.model.icore.IAction;
import org.taHjaj.wo.jenoptron.model.icore.ICondition;
import org.taHjaj.wo.jenoptron.model.icore.IValue;

public interface IImplyGroup extends IGroup {
	void add( final ICondition condition);
	void add( final IAction action);
	void add( final ICondition condition, final IValue value);
	void add( final IAction action, final IValue value);
	void implies( final ICondition condition);
	void implies( final IAction action);
	void implies( final ICondition condition, final IValue value);
	void implies( final IAction action, final IValue value);
}
