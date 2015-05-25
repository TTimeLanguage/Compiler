package Syntax;

import CodeGenerator.CodeGenerator;

/**
 * float 값을 나타내는 구문
 * <p>
 * Abstract Syntax :
 * FloatValue = float
 */
public class FloatValue extends Value {
	/**
	 * float 값을 저장하는 변수
	 */
	protected final double value;

	public FloatValue(double v) {
		type = Type.FLOAT;
		value = v;
	}

	double floatValue() {
		return value;
	}

	@Override
	public int hashCode() {
		return Double.hashCode(value);
	}

	@Override
	public boolean equals(Object obj) {
		return obj instanceof FloatValue && obj.hashCode() == this.hashCode();
	}

	@Override
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

	@Override
	public void genCode() {
		CodeGenerator.ldc((int) value);
		// todo
	}
}
