package Syntax;

import CodeGenerator.CodeGenerator;

import java.util.HashMap;

/**
 * While문을 나타내는 구문
 * <p>
 * Abstract Syntax :
 * WhileStatement = Expression condition; Block
 */
public class WhileStatement extends Statement {
	/**
	 * while문의 반복 조건을 나타내 주는 변수
	 */
	protected final Expression condition;
	/**
	 * 반복시에 수행 할 block내의 실행문
	 */
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

	/**
	 * while문의 조건문이 타당한지 검사
	 * <p>
	 * 조건문의 반환 type이 bool인지 검사
	 * <p>
	 * while문 내부의 실행문의 타당성 검사
	 */
	@Override
	protected void V(HashMap<String, Init> declarationMap, Type functionType) {
		if (valid) return;

		condition.V(declarationMap);

		Type type = condition.typeOf(declarationMap);
		check(condition.typeOf(declarationMap).equals(Type.BOOL),
				"condition statement type must be boolean. condition type : " + type);

		statements.V(declarationMap, this, functionType);

		valid = true;
	}

	/**
	 * 반복문 내부의 while문의 타당성 검사
	 */
	@Override
	protected void V(HashMap<String, Init> declarationMap, Statement loopStatement) {
		innerV(declarationMap);

		statements.V(declarationMap, loopStatement);

		valid = true;
	}

	@Override
	protected void V(HashMap<String, Init> declarationMap, Statement loopStatement, Type functionType) {
		V(declarationMap, functionType);
	}

	@Override
	public void genCode() {
		// todo 확인

		branchNum = CodeGenerator.makeLoopStartBranch();

		condition.genCode();

		CodeGenerator.fjp(CodeGenerator.getLoopEndBranch(branchNum));

		statements.genCode();

		CodeGenerator.ujp(CodeGenerator.getLoopStartBranch(branchNum));

		CodeGenerator.makeLoopEndBranch(branchNum);
	}
}
