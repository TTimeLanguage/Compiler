package Syntax;

import java.util.HashMap;

/**
 * Abstract Syntax :
 * Unary = UnaryOperator; Expression
 */
public class Unary extends Expression {
	protected final Operator op;
	protected final Expression term;
	private Type type;

	public Unary(Operator o, Expression e) {
		op = o;
		term = e;
	}

	@Override
	void display(int lev) {
		for (int i = 0; i < lev; i++) {
			System.out.print("\t");
		}

		System.out.println("Unary");

		op.display(lev + 1);
		term.display(lev + 1);
	}

	@Override
	protected void V(HashMap<String, Init> declarationMap) {
		// todo 확인

		if (valid) return;

		term.V(declarationMap);
		type = term.typeOf(declarationMap);

		if (op.NotOp()) {
			check(type.equals(Type.BOOL), "type error for NotOp " + op);

		} else if (op.NegateOp()) {
			check(type.equals(Type.INT) || type.equals(Type.FLOAT),
					"type error for NegateOp " + op);

		} else if (op.incOp()) {
			check(!(type.equals(Type.BOOL) && type.equals(Type.VOID)),
					"type error for increase or decrease Op");
		} else {
			throw new IllegalArgumentException("should never reach here UnaryOp error");
		}

		valid = true;
	}

	@Override
	Type typeOf(HashMap<String, Init> declarationMap) {
		// todo 확인

		check(valid, "Compiler error. must check validation");

		return type;
	}
}