package Syntax;

import java.util.HashMap;

/**
 * Abstract Syntax :
 * Return = Expression?
 */
public class Return extends Statement {
	protected final Expression returnValue;

	public Return(Expression returnValue) {
		this.returnValue = returnValue;
	}

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

	@Override
	void V(HashMap<String, Init> declarationMap, Statement loopStatement, Type functionType) {
		V(declarationMap, functionType);
	}

	@Override
	void V(HashMap<String, Init> declarationMap, Type functionType) {
		if (returnValue == null) {
			check(functionType.equals(Type.VOID),
					"must not have return value in void function.");
		} else {
			returnValue.V(declarationMap);

			Type expressionType = returnValue.typeOf(declarationMap);
			check(expressionType.equals(functionType),
					"return value's type is not match with declared function return type." +
							" return type : [" + expressionType + "], function type : [" + functionType + "]");
		}
	}
}
