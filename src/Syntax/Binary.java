package Syntax;

import java.util.HashMap;

/**
 * Abstract Syntax :
 * Syntax.Binary = Syntax.Operator; Syntax.Expression e1, e2
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
	void display(int k) {
		for (int w = 0; w < k; w++) {
			System.out.print("\t");
		}

		System.out.println("Syntax.Binary");

		op.display(k + 1);
		term1.display(k + 1);
		term2.display(k + 1);
	}

	@Override
	protected void V(HashMap<String, Init> declarationMap) {
		if (valid) return;

		Type typ1 = term1.typeOf(declarationMap);
		Type typ2 = term2.typeOf(declarationMap);
		term1.V(declarationMap);
		term2.V(declarationMap);

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
			term1.V(declarationMap);
			term2.V(declarationMap);

			Type target = term1.typeOf(declarationMap); //ttype = target type; targets are only variables in Clite which are defined in the TypeMap
			Type source = term2.typeOf(declarationMap); //scrtype = source type; sources are Expressions or Syntax.Statements which are not in the TypeMap

			if (target != source) {
				if (target == Type.FLOAT) {
					check(source == Type.INT || source == Type.FLOAT
							, "mixed mode assignment to " + term1 + " with " + term2);
				} else if (target == Type.INT) {
					check(source != Type.DATE || source != Type.TIME || source != Type.VOID,
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