package Syntax;

import java.util.ArrayList;
import java.util.HashMap;

/**
 * Abstract Syntax :
 * Syntax.Block = Syntax.Statement*
 */
public class Block extends Statement {
	protected final ArrayList<Statement> statements;

	Block(ArrayList<Statement> s) {
		statements = s;
	}

	@Override
	public void display(int k) {
		for (int w = 0; w < k; w++) {
			System.out.print("\t");
		}

		System.out.println("Syntax.Block");
		for (Statement statement : statements) {
			statement.display(k + 1);
		}
	}

	@Override
	void V(HashMap<String, Init> declarationMap) {
		if (valid) return;

		for (Statement i : statements) {
			i.V(declarationMap);
		}

		valid = true;
	}
}
