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
	public final static Type INT = new Type("int");
	public final static Type BOOL = new Type("bool");
	public final static Type CHAR = new Type("char");
	public final static Type FLOAT = new Type("float");
	public final static Type VOID = new Type("void");
	public final static Type TIME = new Type("time");
	public final static Type DATE = new Type("date");

	/**
	 * 타입을 문자열로 저장
	 */
	private String value;

	private Type(String t) {
		value = t;
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