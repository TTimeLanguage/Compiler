package Syntax;

import java.util.HashMap;

/**
 * Abstract Syntax :
 * Break =
 */
public class Break extends Statement {
	@Override
	void display(int lev) {
		for (int i = 0; i < lev; i++) {
			System.out.print("\t");
		}

		System.out.println("Break");
	}

	@Override
	void V(HashMap<String, Init> declarationMap, Statement loopStatement) {
		check(loopStatement instanceof WhileStatement
				|| loopStatement instanceof ForStatement
				|| loopStatement instanceof SwitchStatement
				, "break must used in loop or switch statement");
	}

	@Override
	void V(HashMap<String, Init> declarationMap, Statement loopStatement, Type functionType) {
		V(declarationMap, loopStatement);
	}
}
