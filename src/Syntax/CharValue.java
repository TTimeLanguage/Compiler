package Syntax;

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
}
