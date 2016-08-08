package Syntax;

import CodeGenerator.CodeGenerator;

import java.util.HashMap;

/**
 * 형변환을 나타내는 구문
 * <p>
 * Abstract Syntax :
 * TypeCast = Type type; Expression
 */
public class TypeCast extends Expression {
	/**
	 * 변환하고자 하는 타입을 나타내는 변수
	 */
	protected final Type type;

	/**
	 * 캐스트할 구문의 타입을 나타내는 변수
	 */
	protected Type expressionType;
	/**
	 * 변환하고자 하는 실행구문
	 */
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

	/**
	 * type cast를 하려는 식의 타당성을 확인
	 * <p>
	 * type cast의 변환 타입이 타당한지 확인
	 */
	@Override
	protected void V(HashMap<String, Init> declarationMap) {
		// todo 확인
		if (valid) return;

		expression.V(declarationMap);
		Type t = expression.typeOf(declarationMap);
		expressionType = t;

		if (type.equals(Type.INT)) {
			check(t.equals(Type.INT) || t.equals(Type.CHAR) || t.equals(Type.FLOAT) || t.equals(Type.BOOL),
					"can not cast type to " + type);

		} else if (type.equals(Type.FLOAT)) {
			check(type.equals(Type.FLOAT) || t.equals(Type.INT),
					"can not cast type to " + type);

		} else if (type.equals(Type.CHAR)) {
			check(t.equals(Type.CHAR) || type.equals(Type.INT),
					"can not cast type to " + type);

		} else if (type.equals(Type.BOOL)) {
			check(type.equals(Type.BOOL) || t.equals(Type.INT),
					"can not cast type to " + type);

		} else if (type.equals(Type.TIME)) {
			check(type.equals(Type.TIME),
					"can not cast type to " + type);

		} else if (type.equals(Type.DATE)) {
			check(type.equals(Type.DATE),
					"can not cast type to " + type);

		} else if (type.equals(Type.VOID)) {
			check(false, "expression can not cast type to void");

		} else {
			throw new IllegalArgumentException("should never reach here Syntax.TypeCast error");
		}

		valid = true;
	}

	@Override
	Type typeOf(HashMap<String, Init> declarationMap) {
		// todo 확인

		check(valid, "Compiler error. must check validation");

		return this.type;
	}

	@Override
	public void genCode() {
		if (expressionType.equals(Type.INT) && type.equals(Type.FLOAT)) {
			CodeGenerator.ldp();
			expression.genCode();
			CodeGenerator.call("I2F");

		} else if (expressionType.equals(Type.FLOAT) && type.equals(Type.INT)) {
			CodeGenerator.ldp();
			expression.genCode();
			CodeGenerator.call("F2I");

		} else {
			expression.genCode();
		}
	}
}