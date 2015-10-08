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
package jdt.util.collections;

import java.util.Iterator;

import org.apache.commons.collections.IteratorUtils;
import org.apache.commons.collections.Predicate;

public class IterableWrapper<T> implements Iterable<T> {
	private final Iterable<T> iterable;
	private final Predicate predicate;

	public IterableWrapper(final Iterable<T> iterable, final Predicate predicate) {
		this.iterable = iterable;
		this.predicate = predicate;
	}

	public Iterator<T> iterator() {
		return IteratorUtils.filteredIterator(iterable.iterator(), predicate);
	}

	@Override
	public String toString() {
		final StringBuffer stringBuffer = new StringBuffer();

		for (final Object object : this) {
			stringBuffer.append("[]").append(object).append('\n');
		}

		return stringBuffer.toString();
	}
}
