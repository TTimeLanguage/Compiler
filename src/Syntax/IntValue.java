package Syntax;

import java.util.HashMap;

/**
 * Abstract Syntax :
 * Syntax.IntValue = int
 */
public class IntValue extends Value {
	protected final int value;

	public IntValue(int v) {
		type = Type.INT;
		value = v;
	}

	int intValue() {
		return value;
	}

	public String toString() {
		return "" + value;
	}

	@Override
	void display(int lev) {
		for (int i = 0; i < lev; i++) {
			System.out.print("\t");
		}

		System.out.println("IntValue " + value);
	}

	@Override
	protected void V(HashMap<String, Init> declarationMap) {
	}
}
