package Syntax;

import java.util.ArrayList;
import java.util.HashMap;

/**
 * Abstract Syntax :
 * Block = Statement*
 */
public class Block extends Statement {
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
	void V(HashMap<String, Init> declarationMap, Type functionType) {
		if (valid) return;

		for (Statement statement : statements) {
			statement.V(declarationMap, functionType);
		}

		valid = true;
	}

	@Override
	void V(HashMap<String, Init> declarationMap, Statement loopStatement) {
		if (valid) return;

		for (Statement statement : statements) {
			statement.V(declarationMap, loopStatement);
		}

		valid = true;
	}

	@Override
	void V(HashMap<String, Init> declarationMap, Statement loopStatement, Type functionType) {
		if (valid) return;

		for (Statement statement : statements) {
			statement.V(declarationMap, loopStatement, functionType);
		}

		valid = true;
	}
}
