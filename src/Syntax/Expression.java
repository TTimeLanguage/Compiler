package Syntax;

import java.util.HashMap;

/**
 * Abstract Syntax :
 * Expression = VariableRef | Value | Binary | Unary | Function
 */
public abstract class Expression extends Statement {
	@Override
	protected final void V(HashMap<String, Init> declarationMap, Type functionType) {
		V(declarationMap);
	}

	@Override
	protected final void V(HashMap<String, Init> declarationMap, Statement loopStatement) {
		V(declarationMap);
	}

	@Override
	protected final void V(HashMap<String, Init> declarationMap, Statement loopStatement, Type functionType) {
		V(declarationMap);
	}

	abstract Type typeOf(HashMap<String, Init> declarationMap);
}
