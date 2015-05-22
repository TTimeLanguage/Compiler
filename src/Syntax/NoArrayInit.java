package Syntax;

import java.util.HashMap;

/**
 * Abstract Syntax :
 * ArrayInit = Type; String id; (Expression initList)*
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
	protected void V(HashMap<String, Init> declarationMap) {
		// todo 확인
		if (valid) return;

		check(!type.equals(Type.VOID),
				"variable type can not be void");

		if (initial != null) {
			initial.V(declarationMap);
			check(initial.typeOf(declarationMap).equals(type),
					"wrong type initializer in declaration : " + type + " " + name);
		}

		valid = true;
	}
}