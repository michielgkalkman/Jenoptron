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
package org.taHjaj.wo.jenoptron.model.icore;

import java.beans.PropertyChangeListener;

public interface IPropertyChangeSupport extends PropertyChangeListener {
	void addPropertyChangeListener(String propertyName,
            PropertyChangeListener listener);
	
	void removePropertyChangeListener(String propertyName,
            PropertyChangeListener listener);
	
	void firePropertyChange(String propertyName,
            Object oldValue,
            Object newValue);
}
