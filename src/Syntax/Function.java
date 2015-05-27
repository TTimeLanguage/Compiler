package Syntax;

import CodeGenerator.CodeGenerator;
import Semantic.FunctionInfo;

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

	/**
	 * 이 객체가 호출 할 함수의 파라미터들의 타입의 리스트
	 */
	protected final ArrayList<Type> paramTypes;

	/**
	 * 이 객체가 호출 할 함수를 가리키는 <tt>FunctionInfo</tt>객체.
	 * <p>
	 * type checking할때 생성하며, code generate할때 사용한다.
	 */
	protected FunctionInfo functionInfo;

	public Function(String id, ArrayList<Expression> params) {
		name = id;
		this.params = params;
		this.paramTypes = new ArrayList<>();
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
		// todo 확인

		for (Expression expression : params) {
			expression.V(declarationMap);
			paramTypes.add(expression.typeOf(declarationMap));
		}

		functionInfo = globalFunctionMap.getFunctionInfo(name, paramTypes);

		check(functionInfo != null, "can not find function name : " + name);

		type = functionInfo.getType();

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
		// todo 개선
		CodeGenerator.ldp();

		String realName = name + '$' +  overloadMap.get(name).indexOf(functionInfo);

		CodeGenerator.call(realName);
	}
}