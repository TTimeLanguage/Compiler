package Syntax;

import java.util.HashMap;


/**
 * Abstract Syntax :
 * Syntax.Variable = String id
 */
public class Variable extends VariableRef {
	protected final String name;

	Variable(String name) {
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
	void display(int k) {
		for (int w = 0; w < k; w++) {
			System.out.print("\t");
		}

		System.out.println("Syntax.Variable " + name);
	}

	@Override
	protected void V(HashMap<String, Init> declarationMap) {
		// todo 확인
		if (valid) return;

		check(declarationMap.containsKey(name),
				"undeclared variable: " + name);

		check(declarationMap.get(name) instanceof NoArrayInit,
				"wrong reference. should add array reference. in " + name);

		valid = true;
	}

	@Override
	Type typeOf(HashMap<String, Init> declarationMap) {
		check(declarationMap.containsKey(this.name),
				"undefined variable: " + this.name);

		return declarationMap.get(this.name).type;
	}
}
