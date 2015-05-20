package Syntax;

import java.util.HashMap;

/**
 * Abstract Syntax :
 * Syntax.WhileStatement = Syntax.Expression condition; Syntax.Block
 */
public class WhileStatement extends Statement {
	protected final Expression condition;
	protected final Block statements;

	WhileStatement(Expression c, Block s) {
		condition = c;
		statements = s;
	}

	@Override
	void display(int k) {
		for (int w = 0; w < k; w++) {
			System.out.print("\t");
		}

		System.out.println("Syntax.WhileStatement");
		condition.display(k + 1);
		statements.display(k + 1);
	}

	@Override
	public void V(HashMap<String, Init> declarationMap) {
		if (valid) return;

		check(condition.typeOf(declarationMap) == Type.BOOL,
				"poorly typed test in while Loop in Conditional: " + condition);

		condition.V(declarationMap);
		statements.V(declarationMap);

		valid = true;
	}
}
