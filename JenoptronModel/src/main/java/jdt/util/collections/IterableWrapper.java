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
