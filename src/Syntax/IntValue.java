package Syntax;

import CodeGenerator.CodeGenerator;

/**
 * int 값을 나타내는 구문
 * <p>
 * Abstract Syntax :
 * IntValue = int
 */
public class IntValue extends Value {
	/**
	 * int 값은 저장하는 변수
	 */
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
	public void genCode() {
		CodeGenerator.ldc(value);
	}
}
