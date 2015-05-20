package Syntax;

import java.util.ArrayList;
import java.util.HashMap;

/**
 * Abstract Syntax :
 * Syntax.ForStatement = Syntax.Expression*; Syntax.Expression; Syntax.Expression*; Syntax.Block
 */
public class ForStatement extends Statement {
	protected final ArrayList<Expression> preExpression = new ArrayList<>();
	protected final ArrayList<Expression> postExpression = new ArrayList<>();
	protected Expression condition = null;
	protected Block statements = null;

	public void addPreExpression(Expression expression) {
		preExpression.add(expression);
	}

	public void addPostExpression(Expression expression) {
		postExpression.add(expression);
	}

	public void setCondition(Expression condition) {
		check(this.condition == null,
				"duplicated declaration condition in for");

		this.condition = condition;
	}

	public void setStatements(Block statements) {
		check(this.statements == null,
				"duplicated declaration block in for");

		this.statements = statements;
	}

	@Override
	public void display(int k) {
		for (int w = 0; w < k; w++) {
			System.out.print("\t");
		}

		System.out.println("Syntax.ForStatement");

		for (Expression expression : preExpression) {
			expression.display(k + 1);
		}
		condition.display(k + 1);
		for (Expression expression : postExpression) {
			expression.display(k + 1);
		}
	}

	@Override
	public void V(HashMap<String, Init> declarationMap) {
		// todo 확인
		if (valid) return;

		check(condition != null,
				"condition can not be null");
		check(condition.typeOf(declarationMap) == Type.BOOL,
				"condition type must boolean in for. condition type : " + condition.typeOf(declarationMap));

		for (Expression pre : preExpression) {
			pre.V(declarationMap);
		}

		for (Expression post : postExpression) {
			post.V(declarationMap);
		}

		statements.V(declarationMap);

		valid = true;
	}
}
