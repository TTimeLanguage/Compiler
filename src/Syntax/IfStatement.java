package Syntax;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;

/**
 * Abstract Syntax :
 * IfStatement = Expression condition; Block; (Expression elseif; Block)*; Block?
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
	void display(int lev) {
		for (int i = 0; i < lev; i++) {
			System.out.print("\t");
		}

		System.out.println("IfStatement");
		condition.display(lev + 1);
		statements.display(lev + 1);
		if (elseIfs != null) {
			for (IfStatement statement : elseIfs) {
				statement.display(lev + 1);
			}
		}
		if (elses != null) {
			elses.display(lev + 1);
		}
	}

	@Override
	protected void V(HashMap<String, Init> declarationMap, Type functionType) {
		// todo 확인
		if (valid) return;

		condition.V(declarationMap);

		check(condition.typeOf(declarationMap) == Type.BOOL,
				"\'if\'\'s condition must boolean type. declared : " + condition.typeOf(declarationMap));

		statements.V(declarationMap, functionType);

		if (elseIfs != null) {
			HashSet<Expression> conditions = new HashSet<>();
			conditions.add(condition);

			for (IfStatement statement : elseIfs) {

				check(!conditions.contains(statement.condition),
						"duplicated else if condition");

				conditions.add(statement.condition);
				statement.V(declarationMap, functionType);
			}
		}

		if (elses != null) {
			elses.V(declarationMap, functionType);
		}

		valid = true;
	}

	@Override
	protected void V(HashMap<String, Init> declarationMap, Statement loopStatement) {
		// todo 확인
		if (valid) return;

		condition.V(declarationMap);

		check(condition.typeOf(declarationMap) == Type.BOOL,
				"\'if\'\'s condition must boolean type. declared : " + condition.typeOf(declarationMap));

		statements.V(declarationMap, loopStatement);

		if (elseIfs != null) {
			HashSet<Expression> conditions = new HashSet<>();
			conditions.add(condition);

			for (IfStatement statement : elseIfs) {

				check(conditions.contains(statement.condition),
						"duplicated else if condition");

				conditions.add(statement.condition);
				statement.V(declarationMap, loopStatement);
			}
		}

		if (elses != null) {
			elses.V(declarationMap, loopStatement);
		}

		valid = true;
	}

	@Override
	protected void V(HashMap<String, Init> declarationMap, Statement loopStatement, Type functionType) {
		// todo 확인
		if (valid) return;

		condition.V(declarationMap);

		check(condition.typeOf(declarationMap) == Type.BOOL,
				"\'if\'\'s condition must boolean type. declared : " + condition.typeOf(declarationMap));

		statements.V(declarationMap, loopStatement, functionType);

		if (elseIfs != null) {
			HashSet<Expression> conditions = new HashSet<>();
			conditions.add(condition);

			for (IfStatement statement : elseIfs) {

				check(conditions.contains(statement.condition),
						"duplicated else if condition");

				conditions.add(statement.condition);
				statement.V(declarationMap, loopStatement, functionType);
			}
		}

		if (elses != null) {
			elses.V(declarationMap, loopStatement, functionType);
		}

		valid = true;
	}
}
