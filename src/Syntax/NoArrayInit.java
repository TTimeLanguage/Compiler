package Syntax;

import java.util.HashMap;

/**
 * Abstract Syntax :
 * Syntax.ArrayInit = Syntax.Type; String id; (Syntax.Expression initList)*
 */
public class NoArrayInit extends Init {
	protected final Expression initial;

	public NoArrayInit(Type t, String name, Expression i) {
		type = t;
		this.name = name;
		initial = i;
	}

	public NoArrayInit(Type t, String name) {
		this(t, name, null);
	}

	@Override
	void display(int k) {
		for (int w = 0; w < k; w++) {
			System.out.print("\t");
		}

		System.out.println("Syntax.NoArrayInit " + type + " " + name);

		if (initial != null) {
			initial.display(k + 1);
		}
	}

	@Override
	public void V(HashMap<String, Init> declarationMap) {
		// todo 확인
		if (valid) return;

		check(!type.equals(Type.VOID),
				"variable type can not be void");

		check(initial.typeOf(declarationMap).equals(type),
				"wrong type initializer in declaration : " + type + " " + name);

		valid = true;
	}
}