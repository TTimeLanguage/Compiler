package Syntax;

import CodeGenerator.CodeGenerator;

import java.util.HashMap;

/**
 * 단항 실행문을 나타내는 구문
 * <p>
 * Abstract Syntax :
 * Unary = UnaryOperator; Expression
 */
public class Unary extends Expression {
	/**
	 * 연산자를 나타내는 변수
	 */
	protected Operator op;
	/**
	 * 실행문을 나타내는 변수
	 */
	protected final Expression term;

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

			op = Operator.mapping(op, type);

		} else if (op.NegateOp()) {
			check(type.equals(Type.INT) || type.equals(Type.FLOAT),
					"type error for NegateOp " + op);

			op = Operator.mapping(op, type);

		} else if (op.incOp()) {
			check(!(type.equals(Type.BOOL) && type.equals(Type.VOID)),
					"type error for increase or decrease Op");

			check(term instanceof VariableRef,
					"operator " + op + " must be l-value");

			op = Operator.mapping(op, type);

			// todo 주석
			CodeGenerator.addOperatorLink(op.value);

		} else if (op.refOp()) {
			check(term instanceof VariableRef,
					type + " type term can not have " + op + " operator");

		} else if (op.pointOp()) {
			check(term instanceof VariableRef,
					type + " type term can not have " + op + " operator");

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

	@Override
	public void genCode() {
		// todo time, date float 추가

		switch (op.value) {
			case Operator.INT_PP:
			case Operator.INT_MM:
				if (term instanceof Variable) {
					Variable temp = (Variable) term;

					CodeGenerator.lod(temp.name);
					if (op.value.equals(Operator.INT_PP)) {
						CodeGenerator.inc();
					} else {
						CodeGenerator.dec();
					}
					CodeGenerator.str(temp.name);

				} else if (term instanceof ArrayRef) {
					ArrayRef temp = (ArrayRef) term;

					CodeGenerator.lda(temp.name);
					temp.index.genCode();
					CodeGenerator.add();

					CodeGenerator.dup();

					CodeGenerator.ldi();
					if (op.value.equals(Operator.INT_PP)) {
						CodeGenerator.inc();
					} else {
						CodeGenerator.dec();
					}

					CodeGenerator.sti();
				}
				break;

			case Operator.INT_NEG:
				term.genCode();
				CodeGenerator.neg();
				break;

			case Operator.BOOL_COMP:
				term.genCode();
				CodeGenerator.notop();
				break;

			case Operator.TIME_PP:
			case Operator.TIME_MM:
				if (term instanceof Variable) {
					Variable temp = (Variable) term;

					CodeGenerator.ldp();

					CodeGenerator.lod(temp.name);
					if (op.value.equals(Operator.TIME_PP)) {
						CodeGenerator.inc();
					} else {
						CodeGenerator.dec();
					}

					CodeGenerator.call("validTime");

					CodeGenerator.str(temp.name);

				} else if (term instanceof ArrayRef) {
					ArrayRef temp = (ArrayRef) term;

					CodeGenerator.lda(temp.name);
					temp.index.genCode();
					CodeGenerator.add();


					CodeGenerator.ldp();

					CodeGenerator.lda(temp.name);
					temp.index.genCode();
					CodeGenerator.add();

					CodeGenerator.ldi();
					if (op.value.equals(Operator.TIME_PP)) {
						CodeGenerator.inc();
					} else {
						CodeGenerator.dec();
					}

					CodeGenerator.call("validTime");


					CodeGenerator.sti();
				}
				break;

			case Operator.DATE_PP:
			case Operator.DATE_MM:

				if (term instanceof Variable) {
					Variable temp = (Variable) term;

					CodeGenerator.lod(temp.name);
					if (op.value.equals(Operator.DATE_PP)) {
						CodeGenerator.inc();
					} else {
						CodeGenerator.dec();
					}

					CodeGenerator.str(temp.name);

				} else if (term instanceof ArrayRef) {
					ArrayRef temp = (ArrayRef) term;

					CodeGenerator.lda(temp.name);
					temp.index.genCode();
					CodeGenerator.add();

					CodeGenerator.dup();
					CodeGenerator.ldi();

					if (op.value.equals(Operator.DATE_PP)) {
						CodeGenerator.inc();
					} else {
						CodeGenerator.dec();
					}


					CodeGenerator.sti();
				}
				break;

			case Operator.FLOAT_PP:
			case Operator.FLOAT_MM:
				if (term instanceof Variable) {
					Variable temp = (Variable) term;

					CodeGenerator.ldp();

					CodeGenerator.lda(temp.name);
					CodeGenerator.ldc(Float.floatToRawIntBits(1.0f));

					if (op.value.equals(Operator.FLOAT_PP)) {
						CodeGenerator.call("addFloat");
					} else {
						CodeGenerator.call("subFloat");
					}

					CodeGenerator.str(temp.name);

				} else if (term instanceof ArrayRef) {
					ArrayRef temp = (ArrayRef) term;

					CodeGenerator.lda(temp.name);
					temp.index.genCode();
					CodeGenerator.add();


					CodeGenerator.ldp();

					CodeGenerator.lda(temp.name);
					temp.index.genCode();
					CodeGenerator.add();

					CodeGenerator.ldi();
					CodeGenerator.ldc(Float.floatToRawIntBits(1.0f));

					if (op.value.equals(Operator.FLOAT_PP)) {
						CodeGenerator.call("addFloat");
					} else {
						CodeGenerator.call("subFloat");
					}


					CodeGenerator.sti();
				}
				break;

			case Operator.FLOAT_NEG:
				CodeGenerator.ldp();
				term.genCode();
				CodeGenerator.call("negFloat");
				break;


			case Operator.REFERENCE:
				if (term instanceof Variable) {
					Variable temp = (Variable) term;

					CodeGenerator.lda(temp.name);

				} else if (term instanceof ArrayRef) {
					ArrayRef temp = (ArrayRef) term;

					CodeGenerator.lda(temp.name);
					temp.index.genCode();
					CodeGenerator.add();
				}
				break;

			case Operator.POINT:


			default:
				check(false, "can not find such operation");
		}
	}
}