package Syntax;

import CodeGenerator.CodeGenerator;

import java.util.HashMap;

/**
 * continue 를 나타내는 구문
 * <p>
 * Abstract Syntax :
 * Continue =
 */
public class Continue extends Statement {
	private Statement parentStatement;

	@Override
	void display(int lev) {
		for (int i = 0; i < lev; i++) {
			System.out.print("\t");
		}

		System.out.println("Continue");
	}

	/**
	 * while, for문에서 continue가 쓰이는지 확인
	 */
	@Override
	protected void V(HashMap<String, Init> declarationMap, Type functionType) {
		check(false, "wrong statement. continue can not be here");
	}

	@Override
	protected void V(HashMap<String, Init> declarationMap, Statement loopStatement, Type functionType) {
		check(loopStatement instanceof WhileStatement
				|| loopStatement instanceof ForStatement
				, "continue must used in loop statement");

		parentStatement = loopStatement;
	}

	@Override
	public void genCode() {
		// todo

		CodeGenerator.ujp(CodeGenerator.getLoopStartBranch(parentStatement.branchNum));
	}
}
