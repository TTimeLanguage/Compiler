package Syntax;

import java.util.ArrayList;
import java.util.HashMap;

/**
 * 중괄호로 둘러쌓인 문장들을 나타내는 구문
 * 여러 <tt>Statement</tt>객체들을 가지고있다.
 * <p>
 * Abstract Syntax :
 * Block = Statement*
 */
public class Block extends Statement {
	/**
	 * 실행문들을 나타내는 <tt>ArrayList</tt> 변수
	 */
	protected final ArrayList<Statement> statements;

	public Block(ArrayList<Statement> s) {
		statements = s;
	}

	@Override
	void display(int lev) {
		for (int i = 0; i < lev; i++) {
			System.out.print("\t");
		}

		System.out.println("Block");
		for (Statement statement : statements) {
			statement.display(lev + 1);
		}
	}

	@Override
	protected void V(HashMap<String, Init> declarationMap, Type functionType) {
		if (valid) return;

		for (Statement statement : statements) {
			statement.V(declarationMap, functionType);
		}

		valid = true;
	}

	@Override
	protected void V(HashMap<String, Init> declarationMap, Statement loopStatement) {
		if (valid) return;

		for (Statement statement : statements) {
			statement.V(declarationMap, loopStatement);
		}

		valid = true;
	}

	@Override
	protected void V(HashMap<String, Init> declarationMap, Statement loopStatement, Type functionType) {
		if (valid) return;

		for (Statement statement : statements) {
			statement.V(declarationMap, loopStatement, functionType);
		}

		valid = true;
	}

	@Override
	public void genCode() {
		for (Statement statement : statements) {
			statement.genCode();
		}
	}
}
