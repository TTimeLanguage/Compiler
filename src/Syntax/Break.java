package Syntax;

import java.util.HashMap;

/**
 * Abstract Syntax :
 * Syntax.Break =
 */
public class Break extends Statement {
	@Override
	void display(int lev) {
		for (int i = 0; i < lev; i++) {
			System.out.print("\t");
		}

		System.out.println("Break");
	}

	@Override
	public void V(HashMap<String, Init> declarationMap) {
		// todo
	}
}
