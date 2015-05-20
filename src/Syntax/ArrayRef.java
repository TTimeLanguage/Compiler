package Syntax;


import java.util.HashMap;

/**
 * Abstract Syntax :
 * Syntax.ArrayRef = String id; Syntax.Expression index
 */
public class ArrayRef extends VariableRef {
	protected final String name;
	protected final Expression index;

	ArrayRef(String n, Expression index) {
		this.name = n;
		this.index = index;
	}

	@Override
	void display(int k) {
		for (int w = 0; w < k; w++) {
			System.out.print("\t");
		}

		System.out.println("Syntax.ArrayRef " + name);
		index.display(k + 1);
	}

	@Override
	protected void V(HashMap<String, Init> declarationMap) {
		// todo 확인
		if (valid) return;

		check(declarationMap.containsKey(name),
				"undeclared variable: " + name);

		Init declare = declarationMap.get(name);
		check(declare instanceof ArrayInit,
				"wrong reference. should remove array reference. in " + name);

		check(index.typeOf(declarationMap) == Type.INT,
				"index type must be integer. in " + name);

		valid = true;
	}

	@Override
	Type typeOf(HashMap<String, Init> declarationMap) {
		check(declarationMap.containsKey(this.name),
				"undefined variable: " + this.name);

		return declarationMap.get(this.name).type;
	}
}