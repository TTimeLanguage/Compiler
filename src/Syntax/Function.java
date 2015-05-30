package Syntax;

import CodeGenerator.CodeGenerator;
import CodeGenerator.DefinedFunction;
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

	/**
	 * params에 해당하는 식이 타당한지 검사
	 * <p>
	 * 타당한 parmas를 paramsType에 추가저장
	 * <p>
	 * 해당 함수의 table의 정보를 가져옴
	 * <p>
	 * 함수의 리턴타입을 저장
	 */
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

		if (DefinedFunction.predefinedFunc.contains(name)) {
			CodeGenerator.addFunctionLink(name);
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
		// todo 개선
		if (!name.equals("lf")) {
			CodeGenerator.ldp();
		}

		for (Expression param : params) {
			param.genCode();
		}

		String realName = name;

		if (name.equals("write") && paramTypes.size() == 1) {
			Type t = paramTypes.get(0);

			if (t.equals(Type.FLOAT)) {
				realName = "writeF";

			} else if (t.equals(Type.CHAR)) {
				realName = "writeC";

			} else if (t.equals(Type.TIME)) {
				realName = "writeT";

			} else if (t.equals(Type.DATE)) {
				realName = "writeD";
			}

		} else {
			int index = overloadMap.get(name).indexOf(functionInfo);

			if (index != 0) {
				realName += '$' + index;
			}
		}

		CodeGenerator.call(realName);
	}
}