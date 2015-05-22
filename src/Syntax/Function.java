package Syntax;

import java.util.ArrayList;
import java.util.HashMap;

/**
 * Abstract Syntax :
 * Function = String name; Expression*
 */
public class Function extends Expression {
	protected final String name;
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