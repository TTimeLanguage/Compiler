package Syntax;

import java.util.HashMap;

/**
 * 타입을 나타내는 구문
 * <p>
 * Abstract Syntax :
 * Type = 'int' | 'bool' | 'void' | 'char' | 'float' | 'time' | 'date'
 */
public class Type extends AbstractSyntax {
	// Type = int | bool | char | float
	public final static Type INT = new Type("int", 1);
	public final static Type BOOL = new Type("bool", 1);
	public final static Type CHAR = new Type("char", 1);
	public final static Type FLOAT = new Type("float", 2);
	public final static Type VOID = new Type("void", 0);
	public final static Type TIME = new Type("time", 1);
	public final static Type DATE = new Type("date", 1);

	/**
	 * 타입을 문자열로 저장
	 */
	private String value;

	/**
	 * 이 타입을 u-code로 변환할때의 크기
	 */
	private int size;

	private Type(String t, int size) {
		value = t;
		this.size = size;
	}

	/**
	 * 현재 타입을 u-code로 변환했을때의 크기를 반환.
	 *
	 * @return 이 타입의 크기
	 */
	protected int sizeOf() {
		return size;
	}

	@Override
	public String toString() {
		return value;
	}

	@Override
	public boolean equals(Object obj) {
		if (obj instanceof Type) {
			Type tmp = (Type) obj;
			return tmp.value.equals(this.value);
		} else return false;
	}

	@Override
	void display(int lev) {
		for (int i = 0; i < lev; i++) {
			System.out.print("\t");
		}

		System.out.println("Type " + value);
	}

	@Override
	protected void V(HashMap<String, Init> declarationMap) {
		check(false, "Compiler error. never reach here. Type class");
	}

	@Override
	public void genCode() {
		check(false, "Compiler error. never reach here in code generating. Type class");
	}
}