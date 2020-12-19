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
package org.taHjaj.wo.jenoptron.model.util.xmlencoder;

import java.beans.Encoder;
import java.beans.Expression;
import java.beans.PersistenceDelegate;

public class EnumDelegate<T extends Enum<T>>
extends PersistenceDelegate
{
    public static <TT extends Enum<TT>> EnumDelegate<TT> create(final Class<TT> type)
    {
        return new EnumDelegate<TT>(type);
    }
 
    private final Class<T> type;
 
    public EnumDelegate(final Class<T> type)
    {
    	super();
        this.type = type;
    }
 
    @Override
	protected boolean mutatesTo(final Object oldInstance, final Object newInstance)
    {
        return oldInstance == newInstance;
    }
 
    @Override
    /*
     * @param out
     * @param Enoder
     */
	protected Expression instantiate(final Object oldInstance, final Encoder out)
    {
        return new Expression(oldInstance, type, "valueOf", new Object[]{type.cast(oldInstance).name()});
    }
}

