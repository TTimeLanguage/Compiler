package Syntax;

import java.util.HashMap;

/**
 * Abstract Syntax :
 * Binary = Operator; Expression e1, e2
 */
public class Binary extends Expression {
	protected final Operator op;
	protected final Expression term1, term2;

	public Binary(Operator o, Expression l, Expression r) {
		op = o;
		term1 = l;
		term2 = r;
	}

	@Override
	void display(int lev) {
		for (int i = 0; i < lev; i++) {
			System.out.print("\t");
		}

		System.out.println("Binary");

		op.display(lev + 1);
		term1.display(lev + 1);
		term2.display(lev + 1);
	}

	@Override
	protected void V(HashMap<String, Init> declarationMap) {
		if (valid) return;

		term1.V(declarationMap);
		term2.V(declarationMap);
		Type typ1 = term1.typeOf(declarationMap);
		Type typ2 = term2.typeOf(declarationMap);

		if (op.ArithmeticOp()) {
			check(typ1 == typ2 &&
					(typ1 == Type.INT || typ1 == Type.FLOAT)
					, "type error for " + op);

		} else if (op.RelationalOp()) {
			check(typ1 == typ2, "type error for " + op);

		} else if (op.BooleanOp()) {
			check(typ1 == Type.BOOL && typ2 == Type.BOOL, op + ": non-bool operand");

		} else if (op.AssignOP()) {
			// todo 추가
			check(term1 instanceof VariableRef, term1 + "is not l-value");

			if (typ1 != typ2) {
				if (typ1 == Type.FLOAT) {
					check(typ2 == Type.INT || typ2 == Type.FLOAT
							, "mixed mode assignment to " + term1 + " with " + term2);
				} else if (typ1 == Type.INT) {
					check(typ2 != Type.DATE || typ2 != Type.TIME || typ2 != Type.VOID,
							"mixed mode assignment to " + term1 + " with " + term2);
				} else {
					check(false, "mixed mode assignment to " + term1 + " with " + term2);
				}
			}
		} else throw new IllegalArgumentException("should never reach here BinaryOp error");

		valid = true;
	}


	@Override
	Type typeOf(HashMap<String, Init> declarationMap) {
		// todo 추가
		check(valid, "Compiler error. must check validation");

		if (op.ArithmeticOp())
			if (term1.typeOf(declarationMap) == Type.FLOAT)
				return (Type.FLOAT);
			else return (Type.INT);
		if (op.RelationalOp() || op.BooleanOp())
			return (Type.BOOL);
		else
			return null;
	}
}