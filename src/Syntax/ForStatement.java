package Syntax;

import CodeGenerator.CodeGenerator;

import java.util.ArrayList;
import java.util.HashMap;

/**
 * For 를 나타내는 구문
 * <p>
 * Abstract Syntax :
 * ForStatement = Expression*; Expression; Expression*; Block
 */
public class ForStatement extends Statement {
	/**
	 * 조건1 초기값을 지정해주는 구문
	 * <p>
	 * for문 실행전에 한번만 실행될 구문들
	 */
	protected final ArrayList<Expression> preExpression = new ArrayList<>();
	/**
	 * 조건3 반복후에 실행하는 구문
	 */
	protected final ArrayList<Expression> postExpression = new ArrayList<>();
	/**
	 * 반복문의 반복 여부를 결정하는 구문
	 * <p>
	 * for문 반복시마다 실행될 구문들
	 */
	protected Expression condition = null;
	/**
	 * for문 내부의 statement들을 <tt>Block</tt>객체로 저장
	 */
	protected Block statements = null;

	/**
	 * 조건 1에 구문을 추가하는 함수
	 *
	 * @param expression 추가될 <tt>Expression</tt>객체
	 */
	public void addPreExpression(Expression expression) {
		preExpression.add(expression);
	}

	/**
	 * 조건 3에 구문을 추가하는 함수
	 *
	 * @param expression 추가될 <tt>Expression</tt>객체
	 */
	public void addPostExpression(Expression expression) {
		postExpression.add(expression);
	}

	/**
	 * 반복을 결정할 조건문 지정.
	 * <p>
	 * 두번 실행될 경우(조건문이 덮어씌여질 경우) 에러가난다.
	 *
	 * @param condition 추가할 <tt>Expression</tt>객체
	 */
	public void setCondition(Expression condition) {
		check(this.condition == null,
				"duplicated declaration condition in for");

		this.condition = condition;
	}

	/**
	 * for문 내부의 구문들을 지정.
	 * <p>
	 * 두번 실행도리 경우(구문이 덮어씌여질 경우) 에러가난다.
	 *
	 * @param statements 추가할 <tt>Block</tt>객체
	 */
	public void setStatements(Block statements) {
		check(this.statements == null,
				"duplicated declaration block in for");

		this.statements = statements;
	}

	@Override
	void display(int lev) {
		for (int i = 0; i < lev; i++) {
			System.out.print("\t");
		}

		System.out.println("ForStatement");

		for (Expression expression : preExpression) {
			expression.display(lev + 1);
		}
		condition.display(lev + 1);
		for (Expression expression : postExpression) {
			expression.display(lev + 1);
		}

		statements.display(lev + 1);
	}

	@Override
	protected void V(HashMap<String, Init> declarationMap, Type functionType) {
		if (valid) return;

		check(condition != null,
				"condition can not be null");
		condition.V(declarationMap);

		Type conditionType = condition.typeOf(declarationMap);
		check(conditionType.equals(Type.BOOL),
				"condition type must boolean in for. condition type : " + conditionType);

		for (Expression pre : preExpression) {
			pre.V(declarationMap);
		}

		for (Expression post : postExpression) {
			post.V(declarationMap);
		}

		statements.V(declarationMap, this, functionType);

		valid = true;
	}

	@Override
	protected void V(HashMap<String, Init> declarationMap, Statement loopStatement, Type functionType) {
		V(declarationMap, functionType);
	}

	@Override
	public void genCode() {
		// todo 확인

		for (Expression pre : preExpression) {
			pre.genCode();
		}

		branchNum = CodeGenerator.makeLoopStartBranch();

		condition.genCode();
		CodeGenerator.fjp(CodeGenerator.getLoopEndBranch(branchNum));

		statements.genCode();

		for (Expression post : postExpression) {
			post.genCode();
		}

		CodeGenerator.ujp(CodeGenerator.getLoopStartBranch(branchNum));

		CodeGenerator.makeLoopEndBranch(branchNum);
	}
}
