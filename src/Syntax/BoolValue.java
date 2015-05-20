package Syntax;

import java.util.HashMap;

/**
 * Abstract Syntax :
 * BoolValue = bool
 */
public class BoolValue extends Value {
	protected final boolean value;

	public BoolValue(boolean v) {
		type = Type.BOOL;
		value = v;
	}

	boolean boolValue() {
		return value;
	}

	int intValue() {
		return value ? 1 : 0;
	}

	public String toString() {
		return "" + value;
	}

	@Override
	void display(int lev) {
		for (int i = 0; i < lev; i++) {
			System.out.print("\t");
		}

		System.out.println("BoolValue " + value);
	}

	@Override
	protected void V(HashMap<String, Init> declarationMap) {
	}
}
