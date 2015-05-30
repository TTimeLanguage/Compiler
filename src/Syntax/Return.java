package Syntax;

import CodeGenerator.CodeGenerator;

import java.util.HashMap;

/**
 * return 을 나타내는 구문
 * <p>
 * Abstract Syntax :
 * Return = Expression?
 */
public class Return extends Statement {
	/**
	 * return 해주는 값을 나타내는 구문
	 * <p>
	 * null일경우 반환값이 없는 경우
	 */
	protected final Expression returnValue;

	/**
	 * return 값이 있을 때의 생성자
	 */
	public Return(Expression returnValue) {
		this.returnValue = returnValue;
	}

	/**
	 * return 값이 없을 때의 생성자
	 */
	public Return() {
		this(null);
	}

	@Override
	void display(int lev) {
		for (int i = 0; i < lev; i++) {
			System.out.print("\t");
		}

		System.out.println("Return");
		if (returnValue != null) {
			returnValue.display(lev + 1);
		}
	}

	/**
	 * 반복문 내에 있는 return문의 타당성 확인
	 */
	@Override
	protected void V(HashMap<String, Init> declarationMap, Statement loopStatement, Type functionType) {
		V(declarationMap, functionType);
	}

	/**
	 * return value가 있는지 확인
	 * <p>
	 * return value가 있다면 type이 void인지 확인, return 식이 타당한지 확인
	 * <p>
	 * return value가 없다면 type이 void인지 확인
	 */
	@Override
	protected void V(HashMap<String, Init> declarationMap, Type functionType) {
		if (returnValue == null) {
			check(functionType.equals(Type.VOID),
					"must not have return value in void function.");
		} else {
			 check(!functionType.equals(Type.VOID),
					 "void function can not have return value");

			returnValue.V(declarationMap);

			Type expressionType = returnValue.typeOf(declarationMap);
			check(expressionType.equals(functionType),
					"return value's type is not match with declared function return type." +
							" return type : [" + expressionType + "], function type : [" + functionType + "]");
		}
	}

	@Override
	public void genCode() {
		// todo

		if (returnValue != null) {
			returnValue.genCode();
			CodeGenerator.retv();

		} else {
			CodeGenerator.ret();
		}
	}
}
