package Syntax;

/**
 * Abstract Syntax :
 * FloatValue = float
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
	void display(int lev) {
		for (int i = 0; i < lev; i++) {
			System.out.print("\t");
		}

		System.out.println("FloatValue " + value);
	}
}
