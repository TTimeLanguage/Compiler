package Syntax;

import java.util.HashMap;

/**
 * Abstract Syntax :
 * Syntax.BoolValue = bool
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
	void display(int k) {
		for (int w = 0; w < k; w++) {
			System.out.print("\t");
		}

		System.out.println("Syntax.BoolValue " + value);
	}

	@Override
	protected void V(HashMap<String, Init> declarationMap) {
	}
}
