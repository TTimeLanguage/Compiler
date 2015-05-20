package Syntax;

import java.util.HashMap;

/**
 * Abstract Syntax :
 * Syntax.Unary = UnaryOperator; Syntax.Expression
 */
public class Unary extends Expression {
	protected final Operator op;
	protected final Expression term;

	public Unary(Operator o, Expression e) {
		op = o;
		term = e;
	}

	@Override
	void display(int k) {
		for (int w = 0; w < k; w++) {
			System.out.print("\t");
		}

		System.out.println("Syntax.Unary");

		op.display(k + 1);
		term.display(k + 1);
	}

	@Override
	protected void V(HashMap<String, Init> declarationMap) {
		if (valid) return;

		Type type = term.typeOf(declarationMap); //start here
		term.V(declarationMap);
		if (op.NotOp()) {
			check((type == Type.BOOL), "type error for NotOp " + op);

		} else if (op.NegateOp()) {
			check((type.equals(Type.INT) || type == (Type.FLOAT)),
					"type error for NegateOp " + op);

		} else if (op.incOp()) {
			if(type.equals(Type.BOOL)) check(!(type.equals(Type.BOOL)),"type error for increase or decrease Op");
			else if(type.equals(Type.VOID))check(!(type.equals(Type.VOID)),"type error for increase or decrease Op");
			
		} else {
			throw new IllegalArgumentException("should never reach here UnaryOp error");
		}

		valid = true;
	}

	@Override
	Type typeOf(HashMap<String, Init> declarationMap) {
		// todo 수정
		if (op.NotOp()){
			if(term.typeOf(declarationMap)==Type.BOOL) return (Type.BOOL);
			else return null;
		}
		else if (op.NegateOp()){
			if(term.typeOf(declarationMap)==Type.INT || 
			term.typeOf(declarationMap)==Type.FLOAT) return term.typeOf(declarationMap); 
			else return null;
		}
		else if (op.incOp()){
			if(term.typeOf(declarationMap)==Type.INT) return (Type.INT);
			else if(term.typeOf(declarationMap)==Type.FLOAT) return (Type.FLOAT);
			else if(term.typeOf(declarationMap)==Type.TIME) return (Type.TIME);
			else if(term.typeOf(declarationMap)==Type.DATE) return (Type.DATE);
			else return null;
		}
		else return null;
	}

}