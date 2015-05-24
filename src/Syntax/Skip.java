package Syntax;

import java.util.HashMap;

/**
 * 빈 줄을 나타내는 구문
 * <p>
 * Abstract Syntax :
 * Skip =
 */
public class Skip extends Statement {
	@Override
	void display(int lev) {
	}

	@Override
	protected void V(HashMap<String, Init> declarationMap) {
	}

	@Override
	protected void V(HashMap<String, Init> declarationMap, Type functionType) {
	}

	@Override
	protected void V(HashMap<String, Init> declarationMap, Statement loopStatement, Type functionType) {
	}
}
