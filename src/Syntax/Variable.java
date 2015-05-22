package Syntax;

import java.util.HashMap;


/**
 * Abstract Syntax :
 * Variable = String id
 */
public class Variable extends VariableRef {
	protected final String name;

	public Variable(String name) {
		this.name = name;
	}

	public String toString() {
		return name;
	}

	public boolean equals(Object obj) {
		if (obj instanceof Variable) {
			String s = ((Variable) obj).name;
			return name.equals(s); // case-sensitive identifiers
		} else return false;
	}

	public int hashCode() {
		return name.hashCode();
	}

	@Override
	void display(int lev) {
		for (int i = 0; i < lev; i++) {
			System.out.print("\t");
		}

		System.out.println("Variable " + name);
	}

	@Override
	protected void V(HashMap<String, Init> declarationMap) {
		// todo 확인
		if (valid) return;

		check(declarationMap.containsKey(name),
				"undeclared variable: " + name);

		check(!(declarationMap.get(name) instanceof ArrayInit),
				"wrong reference. should add array reference. in " + name);

		valid = true;
	}

	@Override
	Type typeOf(HashMap<String, Init> declarationMap) {
		check(valid, "Compiler error. must check validation");

		check(declarationMap.containsKey(name),
				"undefined variable: " + name);

		return declarationMap.get(name).type;
	}
}
