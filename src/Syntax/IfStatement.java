package Syntax;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;

/**
 * Abstract Syntax :
 * Syntax.IfStatement = Syntax.Expression condition; Syntax.Block; (Syntax.Expression elseif; Syntax.Block)*; Syntax.Block?
 */
public class IfStatement extends Statement {
	protected final Expression condition;
	protected final Block statements;
	protected final ArrayList<IfStatement> elseIfs;
	protected final Block elses;

	public IfStatement(Expression c, Block s, ArrayList<IfStatement> elseIf, Block e) {
		condition = c;
		statements = s;
		elseIfs = elseIf;
		elses = e;
	}

	public IfStatement(Expression c, Block s, ArrayList<IfStatement> elseIfs) {
		this(c, s, elseIfs, null);
	}

	public IfStatement(Expression c, Block s, Block e) {
		this(c, s, null, e);
	}

	public IfStatement(Expression c, Block s) {
		this(c, s, null, null);
	}

	@Override
	void display(int k) {
		for (int w = 0; w < k; w++) {
			System.out.print("\t");
		}

		System.out.println("Syntax.IfStatement");
		condition.display(k + 1);
		statements.display(k + 1);
		if (elseIfs != null) {
			for (IfStatement statement : elseIfs) {
				statement.display(k + 1);
			}
		}
		if (elses != null) {
			elses.display(k + 1);
		}
	}

	@Override
	protected void V(HashMap<String, Init> declarationMap) {
		// todo 확인
		if (valid) return;

		check(condition.typeOf(declarationMap) == Type.BOOL,
				"\'if\'\'s condition must boolean type. declared : " + condition.typeOf(declarationMap));

		condition.V(declarationMap);
		statements.V(declarationMap);

		if (elseIfs != null) {
			HashSet<Expression> conditions = new HashSet<>();
			conditions.add(condition);

			for (IfStatement statement : elseIfs) {

				check(conditions.contains(statement.condition),
						"duplicated else if condition");

				conditions.add(statement.condition);
				statement.V(declarationMap);
			}
		}

		if (elses != null) {
			elses.V(declarationMap);
		}

		valid = true;
	}
}
