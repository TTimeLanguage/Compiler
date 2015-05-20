package Semantic;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;
import java.util.Set;


public class FunctionSet implements Set<FunctionInformation> {
	private ArrayList<FunctionInformation> array = new ArrayList<>();

	@Override
	public int size() {
		return array.size();
	}

	@Override
	public boolean isEmpty() {
		return array.isEmpty();
	}

	@Override
	public boolean contains(Object o) {
		if (!(o instanceof FunctionInformation)) return false;

		FunctionInformation tmp = (FunctionInformation) o;

		for (FunctionInformation information : array) {
			if (information.equals(tmp)) return true;
		}

		return false;
	}

	@Override
	public Iterator iterator() {
		return null;
	}

	@Override
	public Object[] toArray() {
		return array.toArray();
	}

	@Override
	public <T> T[] toArray(T[] a) {
		return null;
	}

	@Override
	public boolean add(FunctionInformation functionInformation) {
		if (contains(functionInformation)) return false;

		array.add(functionInformation);

		return true;
	}

	@Override
	public boolean remove(Object o) {
		return contains(o) && array.remove(o);

	}

	@Override
	public boolean addAll(Collection c) {
		return false;
	}

	@Override
	public void clear() {

	}

	@Override
	public boolean removeAll(Collection c) {
		return false;
	}

	@Override
	public boolean retainAll(Collection c) {
		return false;
	}

	@Override
	public boolean containsAll(Collection c) {
		return false;
	}

	@Override
	public String toString() {
		StringBuilder result = new StringBuilder("");

		for (FunctionInformation information : array) {
			result.append(information.toString()).append("\n");
		}

		return result.toString();
	}
}
