package Syntax;

/**
 * boolean 변수의 값을 나타내는 구문
 * <p>
 * Abstract Syntax :
 * BoolValue = bool
 */
public class BoolValue extends Value {
	/**
	 * bool 값을 나타내는 변수
	 */
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
	void display(int lev) {
		for (int i = 0; i < lev; i++) {
			System.out.print("\t");
		}

		System.out.println("BoolValue " + value);
	}
}
