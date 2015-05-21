package Syntax;

import java.util.HashMap;

/**
 * Abstract Syntax :
 * Continue =
 */
public class Continue extends Statement {
	@Override
	void display(int lev) {
		for (int i = 0; i < lev; i++) {
			System.out.print("\t");
		}

		System.out.println("Continue");
	}

	@Override
	protected void V(HashMap<String, Init> declarationMap, Statement loopStatement, Type functionType) {
		V(declarationMap, loopStatement);
	}

	@Override
	protected void V(HashMap<String, Init> declarationMap, Statement loopStatement) {
		check(loopStatement instanceof WhileStatement
				|| loopStatement instanceof ForStatement
				, "continue must used in loop statement");
	}
}
