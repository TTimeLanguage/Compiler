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
	void display(int lev) {
		for (int i = 0; i < lev; i++) {
			System.out.print("\t");
		}

		System.out.println("NoArrayInit " + type + " " + name);

		if (initial != null) {
			initial.display(lev + 1);
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