package Syntax;

import java.util.ArrayList;
import java.util.HashMap;

/**
 * Abstract Syntax :
 * ArrayInit = Type; String id; int size; (Expression initList)*
 */
public class ArrayInit extends Init {
	protected final int size;
	protected final ArrayList<Expression> initList;

	public ArrayInit(Type t, String name, int s, ArrayList<Expression> i) {
		type = t;
		this.name = name;
		size = s;
		initList = i;
	}

	public ArrayInit(Type t, String name, int s) {
		this(t, name, s, null);
	}

	@Override
	void display(int lev) {
		for (int i = 0; i < lev; i++) {
			System.out.print("\t");
		}

		System.out.println("ArrayInit " + type + " " + name + "[" + size + "]");
		if (initList != null) {
			for (Expression expression : initList) {
				expression.display(lev + 1);
			}
		}
	}

	@Override
	void V(HashMap<String, Init> declarationMap) {
		// todo 확인
		if (valid) return;

		check(!type.equals(Type.VOID),
				"variable type can not be void");

		check(size >= 1,
				"array size must higher than 1. declared : " + type + " " + name + "[" + size + "]");

		if (initList != null) {
			for (Expression expression : initList) {
				expression.V(declarationMap);
				check(expression.typeOf(declarationMap).equals(type),
						"wrong type initializer in declaration : "
								+ type + " " + name + "[" + size + "],"
								+ " initialize : " + expression);
			}
		}

		valid = true;
	}
}
