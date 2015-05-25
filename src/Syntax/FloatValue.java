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

	@Override
	public void genCode() {
		CodeGenerator.ldc((int) value);
		// todo
	}
}
