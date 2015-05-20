package Syntax;

import java.util.HashMap;

/**
 * Abstract Syntax :
 * Syntax.CharValue = String
 */
public class CharValue extends Value {
	protected final char value;

	public CharValue(char v) {
		type = Type.CHAR;
		value = v;
	}

	char charValue() {
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

		System.out.println("CharValue " + value);
	}

	@Override
	protected void V(HashMap<String, Init> declarationMap) {
	}
}
