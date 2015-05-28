package Syntax;

import CodeGenerator.CodeGenerator;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;

/**
 * if 를 나타내는 구문
 * <p>
 * Abstract Syntax :
 * IfStatement = Expression condition; Block; (Expression elseif; Block)*; Block?
 */
public class IfStatement extends Statement {
	/**
	 * if의 조건문을 나타내는 변수
	 */
	protected final Expression condition;
	/**
	 * if내의 실행 부분을 나타내는 변수
	 */
	protected final Block statements;
	/**
	 * else if 구문들을 나타내는 <tt>ArrayList</tt>
	 */
	protected final ArrayList<IfStatement> elseIfs;
	/**
	 * else내의 실행 부분을 <tt>Block</tt>객체로 저장
	 */
	protected final Block elses;

	/**
	 * if, else if, else 구문을 포함하고 있는 if구문 생성자
	 */
	public IfStatement(Expression c, Block s, ArrayList<IfStatement> elseIf, Block e) {
		condition = c;
		statements = s;
		elseIfs = elseIf;
		elses = e;
	}

	/**
	 * if, else if 구문을 포함하고 있는 if구문 생성자
	 */
	public IfStatement(Expression c, Block s, ArrayList<IfStatement> elseIfs) {
		this(c, s, elseIfs, null);
	}

	/**
	 * if, else 구문을 포함하고 있는 if구문 생성자
	 */
	public IfStatement(Expression c, Block s, Block e) {
		this(c, s, null, e);
	}

	/**
	 * if구문을 포함하고 있는 if구문 생성자
	 */
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
			for (int i = 0; i < lev; i++) {
				System.out.print("\t");
			}
			System.out.println("ElseIfStatement");
			for (IfStatement statement : elseIfs) {
				statement.display(lev + 1);
			}
		}
		if (elses != null) {
			for (int i = 0; i < lev; i++) {
				System.out.print("\t");
			}
			System.out.println("ElseStatement");
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

				check(!conditions.contains(statement.condition),
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

	@Override
	public void genCode() {
		// todo tjp, tjp 유동적 기능 추가

		int ifNum = CodeGenerator.getIfNum();

		condition.genCode();

		if (elseIfs != null) {
			CodeGenerator.fjp(CodeGenerator.getElseIfBranch(ifNum));

		} else if (elses != null) {
			CodeGenerator.fjp(CodeGenerator.getElseBranch(ifNum));

		} else {
			CodeGenerator.fjp(CodeGenerator.getIfExitBranch(ifNum));
		}

		statements.genCode();

		CodeGenerator.ujp(CodeGenerator.getIfExitBranch(ifNum));

		if (elseIfs != null) {
			int len = elseIfs.size();
			for (int i = 0; i < len; i++) {
				CodeGenerator.makeElseIfBranch(ifNum);

				elseIfs.get(i).condition.genCode();

				if (i  == len - 1) {
					if (elses != null) {
						CodeGenerator.fjp(CodeGenerator.getElseBranch(ifNum));

					} else {
						CodeGenerator.fjp(CodeGenerator.getIfExitBranch(ifNum));
					}
				} else {
					CodeGenerator.fjp(CodeGenerator.getElseIfBranch(ifNum));
				}

				elseIfs.get(i).statements.genCode();

				CodeGenerator.ujp(CodeGenerator.getIfExitBranch(ifNum));
			}
		}

		if (elses != null) {
			CodeGenerator.makeElseBranch(ifNum);

			elses.genCode();
		}

		CodeGenerator.makeIfExitBranch(ifNum);
	}
}
