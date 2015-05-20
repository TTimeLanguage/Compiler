package Syntax;

import java.util.HashMap;

/**
 * Abstract Syntax :
 * Syntax.Type = 'int' | 'bool' | 'void' | 'char' | 'float' | 'time' | 'date'
 */
public class Type extends AbstractSyntax {
	// Syntax.Type = int | bool | char | float
	final static Type INT = new Type("int");
	public final static Type BOOL = new Type("bool");
	final static Type CHAR = new Type("char");
	final static Type FLOAT = new Type("float");
	final static Type VOID = new Type("void");
	final static Type TIME = new Type("time");
	final static Type DATE = new Type("date");

	private String value;

	private Type(String t) {
		value = t;
	}

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

	void display(int k) {
		for (int w = 0; w < k; w++) {
			System.out.print("\t");
		}

		System.out.println("Syntax.Type " + value);
	}

	@Override
	public void V(HashMap<String, Init> declarationMap) {
	}
}