package Syntax;

import java.util.HashMap;

/**
 * Abstract Syntax :
 * Syntax.FloatValue = float
 */
public class FloatValue extends Value {
	protected final float value;

	public FloatValue(float v) {
		type = Type.FLOAT;
		value = v;
	}

	float floatValue() {
		return value;
	}

	public String toString() {
		return "" + value;
	}

	@Override
	void display(int k) {
		for (int w = 0; w < k; w++) {
			System.out.print("\t");
		}

		System.out.println("Syntax.FloatValue " + value);
	}

	@Override
	protected void V(HashMap<String, Init> declarationMap) {
	}
}
