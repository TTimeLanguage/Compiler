package Syntax;

import java.util.HashMap;

/**
 * Abstract Syntax :
 * TypeCast = Type type; Expression
 */
public class TypeCast extends Expression {
	protected final Type type;
	protected final Expression expression;

	public TypeCast(Type t, Expression e) {
		type = t;
		expression = e;
	}

	@Override
	void display(int lev) {
		for (int i = 0; i < lev; i++) {
			System.out.print("\t");
		}

		System.out.println("TypeCast " + type);
		expression.display(lev + 1);
	}

	@Override
	protected void V(HashMap<String, Init> declarationMap) {
		// todo 추가
		if (valid) return;

		expression.V(declarationMap);
		Type t = expression.typeOf(declarationMap);

		if (type == Type.INT) {
			check(t == Type.INT || t == Type.CHAR,
					"can not transform type to " + type);

		} else if (type == Type.FLOAT) {
			check(t == Type.FLOAT || t == Type.INT || t == Type.CHAR,
					"can not cast type to " + type);

		} else if (type == Type.CHAR) {

		} else if (type == Type.BOOL) {

		} else {
			throw new IllegalArgumentException("should never reach here Syntax.TypeCast error");
		}

		valid = true;
	}

	@Override
	Type typeOf(HashMap<String, Init> declarationMap) {
		return this.type;
	}
}