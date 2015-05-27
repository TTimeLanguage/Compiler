package Syntax;

import CodeGenerator.CodeGenerator;

import java.util.HashMap;
import java.util.function.*;

/**
 * 이항연산을 나타내는 구문
 * 연산자 <tt>Operator</tt>객체와 피연산자 <tt>Expression</tt>객체 2개를 저장한다.
 * <p>
 * Abstract Syntax :
 * Binary = Operator; Expression e1, e2
 */
public class Binary extends Expression {
	/**
	 * 연산자를 나타내는 변수
	 */
	protected Operator op;
	/**
	 * 두개의 항을 나타내는 변수
	 */
	protected Expression term1, term2;

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
		Type type1 = term1.typeOf(declarationMap);
		Type type2 = term2.typeOf(declarationMap);

		if (op.isAssignOP()) {
			check(term1 instanceof VariableRef,
					"left value of assignment must be l-value");
		}

		check(!type1.equals(Type.VOID) || !type2.equals(Type.VOID),
				"Compiler error. expression type can not be void.");

		if (type1.equals(Type.TIME)) {
			Operator operator = Operator.timeMap(op.value);

			check(operator != null,
					type1 + " value can not have " + op.value + " operator");

			switch (operator.value) {
				case Operator.TIME_PLUS:
				case Operator.TIME_MINUS:
				case Operator.TIME_PLUS_ASSIGN:
				case Operator.TIME_MINUS_ASSIGN:
				case Operator.TIME_LT:
				case Operator.TIME_LE:
				case Operator.TIME_EQ:
				case Operator.TIME_NE:
				case Operator.TIME_GT:
				case Operator.TIME_GE:
				case Operator.TIME_ASSIGN:
					check(type2.equals(Type.TIME),
							type1 + " value can not have " + op.value + " operator with " + type2 + "type term");
					op = operator;
					break;

				case Operator.TIME_DIV:
				case Operator.TIME_TIMES:
				case Operator.TIME_MOD:
				case Operator.TIME_TIMES_ASSIGN:
				case Operator.TIME_DIV_ASSIGN:
				case Operator.TIME_MOD_ASSIGN:
					check(type2.equals(Type.INT),
							type1 + " value can not have " + op.value + " operator with " + type2 + "type term");
					op = operator;
					break;

				default:
					check(false, "Compiler error. unknown operator in Binary");
			}

		} else if (type1.equals(Type.DATE)) {
			Operator operator = Operator.dateMap(op.value);

			check(operator != null,
					type1 + " value can not have " + op.value + " operator");

			switch (operator.value) {
				case Operator.DATE_PLUS:
				case Operator.DATE_MINUS:
				case Operator.DATE_PLUS_ASSIGN:
				case Operator.DATE_MINUS_ASSIGN:
				case Operator.DATE_LT:
				case Operator.DATE_LE:
				case Operator.DATE_EQ:
				case Operator.DATE_NE:
				case Operator.DATE_GT:
				case Operator.DATE_GE:
				case Operator.DATE_ASSIGN:
					check(type2.equals(Type.DATE),
							type1 + " value can not have " + op.value + " operator with " + type2 + " type term");
					op = operator;
					break;

				case Operator.DATE_DIV:
				case Operator.DATE_TIMES:
				case Operator.DATE_MOD:
				case Operator.DATE_TIMES_ASSIGN:
				case Operator.DATE_DIV_ASSIGN:
				case Operator.DATE_MOD_ASSIGN:
					check(false,
							type1 + " value can not have " + op.value + " operator with " + type2 + " type term");

				default:
					check(false, "Compiler error. unknown operator in Binary");
			}

		} else if (type1.equals(Type.INT)) {
			Operator operator = Operator.intMap(op.value);

			check(operator != null,
					type1 + " value can not have " + op.value + " operator");

			switch (operator.value) {
				case Operator.INT_PLUS:
				case Operator.INT_MINUS:
				case Operator.INT_LT:
				case Operator.INT_LE:
				case Operator.INT_EQ:
				case Operator.INT_NE:
				case Operator.INT_GT:
				case Operator.INT_GE:
					check(type2.equals(Type.INT) || type2.equals(Type.FLOAT),
							type1 + " value can not have " + op.value + " operator with " + type2 + " type term");

					if (type2.equals(Type.FLOAT)) {
						term1 = new TypeCast(Type.FLOAT, term1);
						operator = Operator.floatMap(op.value);
					}
					op = operator;
					break;

				case Operator.INT_PLUS_ASSIGN:
				case Operator.INT_MINUS_ASSIGN:
				case Operator.INT_TIMES_ASSIGN:
				case Operator.INT_DIV_ASSIGN:
				case Operator.INT_MOD_ASSIGN:
				case Operator.INT_ASSIGN:
					check(type2.equals(Type.INT) || type2.equals(Type.FLOAT),
							type1 + " value can not have " + op.value + " operator with " + type2 + " type term");

					if (type2.equals(Type.FLOAT)) {
						term2 = new TypeCast(Type.INT, term2);
					}
					op = operator;
					break;

				case Operator.INT_DIV:
				case Operator.INT_TIMES:
				case Operator.INT_MOD:
					check(type2.equals(Type.INT) || type2.equals(Type.FLOAT) || type2.equals(Type.TIME),
							type1 + " value can not have " + op.value + " operator with " + type2 + " type term");

					if (type2.equals(Type.FLOAT)) {
						term1 = new TypeCast(Type.FLOAT, term1);
						operator = Operator.floatMap(op.value);

					} else if (type2.equals(Type.TIME)) {
						operator = Operator.timeMap(op.value);
					}
					op = operator;
					break;

				default:
					check(false, "Compiler error. unknown operator in Binary");
			}

		} else if (type1.equals(Type.CHAR)) {
			Operator operator = Operator.charMap(op.value);

			check(operator != null,
					type1 + " value can not have " + op.value + " operator");

			check(type2.equals(Type.CHAR),
					type1 + " value can not have " + op.value + " operator with " + type2 + " type term");
			op = operator;

		} else if (type1.equals(Type.BOOL)) {
			Operator operator = Operator.boolMap(op.value);

			check(operator != null,
					type1 + " value can not have " + op.value + " operator");

			check(type2.equals(Type.BOOL),
					type1 + " value can not have " + op.value + " operator with " + type2 + " type term");
			op = operator;

		} else if (type1.equals(Type.FLOAT)) {
			Operator operator = Operator.floatMap(op.value);

			check(operator != null,
					type1 + " value can not have " + op.value + " operator");

			switch (operator.value) {
				case Operator.FLOAT_PLUS:
				case Operator.FLOAT_MINUS:
				case Operator.FLOAT_DIV:
				case Operator.FLOAT_TIMES:
				case Operator.FLOAT_MOD:
				case Operator.FLOAT_PLUS_ASSIGN:
				case Operator.FLOAT_MINUS_ASSIGN:
				case Operator.FLOAT_TIMES_ASSIGN:
				case Operator.FLOAT_DIV_ASSIGN:
				case Operator.FLOAT_MOD_ASSIGN:
				case Operator.FLOAT_ASSIGN:
				case Operator.FLOAT_LT:
				case Operator.FLOAT_LE:
				case Operator.FLOAT_EQ:
				case Operator.FLOAT_NE:
				case Operator.FLOAT_GT:
				case Operator.FLOAT_GE:
					check(type2.equals(Type.INT) || type2.equals(Type.FLOAT),
							type1 + " value can not have " + op.value + " operator with " + type2 + " type term");

					if (type2.equals(Type.INT)) {
						term2 = new TypeCast(Type.FLOAT, term2);
						operator = Operator.floatMap(op.value);
					}
					op = operator;
					break;

				default:
					check(false, "Compiler error. unknown operator in Binary");
			}


		} else throw new IllegalArgumentException("should never reach here BinaryOp error");

		valid = true;
	}


	@Override
	Type typeOf(HashMap<String, Init> declarationMap) {
		// todo 확인
		check(valid, "Compiler error. must check validation");

		type = op.type;

		return type;
	}

	@Override
	public void genCode() {
		// todo

		switch (op.value) {
			case Operator.CHAR_EQ:
			case Operator.INT_EQ:
				makeCondition(CodeGenerator::eq);
				break;

			case Operator.INT_NE:
			case Operator.CHAR_NE:
				makeCondition(CodeGenerator::ne);
				break;

			case Operator.INT_GT:
			case Operator.CHAR_GT:
				makeCondition(CodeGenerator::gt);
				break;

			case Operator.INT_LT:
			case Operator.CHAR_LT:
				makeCondition(CodeGenerator::lt);
				break;

			case Operator.INT_GE:
			case Operator.CHAR_GE:
				makeCondition(CodeGenerator::ge);
				break;

			case Operator.INT_LE:
			case Operator.CHAR_LE:
				makeCondition(CodeGenerator::le);
				break;

			case Operator.BOOL_ASSIGN:
			case Operator.CHAR_ASSIGN:
			case Operator.INT_ASSIGN:
			case Operator.TIME_ASSIGN:
			case Operator.DATE_ASSIGN:
				if (term1 instanceof Variable) {
					Variable temp = (Variable) term1;

					term2.genCode();

					CodeGenerator.str(temp.name);

				} else if (term1 instanceof ArrayRef) {
					ArrayRef temp = (ArrayRef) term1;

					CodeGenerator.lda(temp.name);
					temp.index.genCode();
					CodeGenerator.add();

					term2.genCode();

					CodeGenerator.sti();

				} else {
					check(false, "Compiler error. never reach here. Binary genCode()");
				}
				break;

			case Operator.INT_PLUS_ASSIGN:
			case Operator.INT_MINUS_ASSIGN:
			case Operator.INT_TIMES_ASSIGN:
			case Operator.INT_DIV_ASSIGN:
			case Operator.INT_MOD_ASSIGN:
				if (term1 instanceof Variable) {
					Variable temp = (Variable) term1;

					CodeGenerator.lod(temp.name);
					term2.genCode();

					switch (op.value) {
						case Operator.INT_PLUS_ASSIGN:
							CodeGenerator.add();
							break;
						case Operator.INT_MINUS_ASSIGN:
							CodeGenerator.sub();
							break;
						case Operator.INT_TIMES_ASSIGN:
							CodeGenerator.multi();
							break;
						case Operator.INT_DIV_ASSIGN:
							CodeGenerator.div();
							break;
						case Operator.INT_MOD_ASSIGN:
							CodeGenerator.mod();
					}

					CodeGenerator.str(temp.name);

				} else if (term1 instanceof ArrayRef) {
					ArrayRef temp = (ArrayRef) term1;

					CodeGenerator.lda(temp.name);
					temp.index.genCode();
					CodeGenerator.add();

					CodeGenerator.dup();
					CodeGenerator.ldi();

					term2.genCode();

					switch (op.value) {
						case Operator.INT_PLUS_ASSIGN:
							CodeGenerator.add();
							break;
						case Operator.INT_MINUS_ASSIGN:
							CodeGenerator.sub();
							break;
						case Operator.INT_TIMES_ASSIGN:
							CodeGenerator.multi();
							break;
						case Operator.INT_DIV_ASSIGN:
							CodeGenerator.div();
							break;
						case Operator.INT_MOD_ASSIGN:
							CodeGenerator.mod();
					}

					CodeGenerator.sti();

				} else {
					check(false, "Compiler error. never reach here. Binary genCode()");
				}
				break;

		}
	}

	private void makeCondition(Runnable runnable) {
		term1.genCode();
		term2.genCode();

		CodeGenerator.sub();

		CodeGenerator.ldc(0);
		runnable.run();
	}
}