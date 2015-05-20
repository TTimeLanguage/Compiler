package Syntax;

import java.util.HashMap;

/**
 * Abstract Syntax :
 * WhileStatement = Expression condition; Block
 */
public class WhileStatement extends Statement {
	protected final Expression condition;
	protected final Block statements;

	public WhileStatement(Expression c, Block s) {
		condition = c;
		statements = s;
	}

	@Override
	void display(int lev) {
		for (int i = 0; i < lev; i++) {
			System.out.print("\t");
		}

		System.out.println("WhileStatement");
		condition.display(lev + 1);
		statements.display(lev + 1);
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
