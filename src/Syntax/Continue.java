package Syntax;

import java.util.HashMap;

/**
 * Abstract Syntax :
 * Syntax.Continue =
 */
public class Continue extends Statement {
	@Override
	public void display(int k) {
		for (int w = 0; w < k; w++) {
			System.out.print("\t");
		}

		System.out.println("Syntax.Continue");
	}

	@Override
	public void V(HashMap<String, Init> declarationMap) {
		// todo
	}

	@Override
	void V(HashMap<String, Init> declarationMap, Statement s) {
		check(s instanceof WhileStatement || s instanceof ForStatement,
				"can not reach here Continue");
	}
}
