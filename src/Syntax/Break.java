package Syntax;

import CodeGenerator.CodeGenerator;

import java.util.HashMap;

/**
 * break 를 나타내는 구문
 * <p>
 * Abstract Syntax :
 * Break =
 */
public class Break extends Statement {
	private Statement parentStatement;

	@Override
	void display(int lev) {
		for (int i = 0; i < lev; i++) {
			System.out.print("\t");
		}

		System.out.println("Break");
	}

	@Override
	protected void V(HashMap<String, Init> declarationMap, Type functionType) {
		check(false, "wrong statement. break keyword is not to be here");
	}

	@Override
	protected void V(HashMap<String, Init> declarationMap, Statement loopStatement, Type functionType) {
		check(loopStatement instanceof WhileStatement
				|| loopStatement instanceof ForStatement
				|| loopStatement instanceof SwitchStatement
				, "break must used in loop or switch statement");

		parentStatement = loopStatement;
	}

	@Override
	public void genCode() {
		// todo

		CodeGenerator.ujp(CodeGenerator.getLoopEndBranch(parentStatement.branchNum));
	}
}
