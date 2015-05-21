package Syntax;

import java.util.HashMap;

/**
 * Abstract Syntax :
 * Skip =
 */
public class Skip extends Statement {
	@Override
	void display(int lev) {
	}

	@Override
	void V(HashMap<String, Init> declarationMap, Type functionType) {
	}

	@Override
	void V(HashMap<String, Init> declarationMap, Statement loopStatement) {
	}

	@Override
	void V(HashMap<String, Init> declarationMap, Statement loopStatement, Type functionType) {
	}

	@Override
	protected void V(HashMap<String, Init> declarationMap) {
	}
}
