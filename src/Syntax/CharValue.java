package Syntax;

import CodeGenerator.CodeGenerator;

/**
 * char 값을 나타내는 구문
 * <p>
 * Abstract Syntax :
 * CharValue = String
 */
public class CharValue extends Value {
	/**
	 * char 값을 나타내는 변수
	 */
	protected final char value;

	public CharValue(char v) {
		type = Type.CHAR;
		value = v;
	}

	char charValue() {
		return value;
	}

	@Override
	public int hashCode() {
		return Character.hashCode(value);
	}

	@Override
	public boolean equals(Object obj) {
		return obj instanceof CharValue && obj.hashCode() == this.hashCode();
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

		System.out.println("CharValue " + value);
	}

	@Override
	public void genCode() {
		CodeGenerator.ldc((int) value);
	}
}
