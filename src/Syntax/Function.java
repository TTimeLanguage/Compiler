package Syntax;

import java.util.ArrayList;
import java.util.HashMap;

/**
 * 함수 호출을 나타내는 구문
 * <p>
 * Abstract Syntax :
 * Function = String name; Expression*
 */
public class Function extends Expression {
	/**
	 * 호출할 함수의 이름을 나타내는 변수
	 */
	protected final String name;
	/**
	 * 함수 호출 전달인자들을 저장하는 <tt>ArrayList</tt>
	 */
	protected final ArrayList<Expression> params;

	public Function(String id, ArrayList<Expression> params) {
		name = id;
		this.params = params;
	}

	@Override
	void display(int lev) {
		for (int i = 0; i < lev; i++) {
			System.out.print("\t");
		}

		System.out.println("Function " + name);

		for (Expression expression : params) {
			expression.display(lev + 1);
		}
	}

	@Override
	protected void V(HashMap<String, Init> declarationMap) {
		if (valid) return;
		// todo

		valid = true;
	}

	@Override
	Type typeOf(HashMap<String, Init> declarationMap) {
		// todo 확인

		check(valid, "Compiler error. must check validation");

		ArrayList<Type> types = new ArrayList<>();
		for (Expression expression : params) {
			expression.V(declarationMap);
			types.add(expression.typeOf(declarationMap));
		}
		Type functionType = globalFunctionMap.getFunctionType(name, types);
		check(functionType != null, "using not declared function. function : " + name);

		return functionType;
	}
}