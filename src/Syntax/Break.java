package Syntax;

import java.util.HashMap;

/**
 * Abstract Syntax :
 * Syntax.Break =
 */
public class Break extends Statement {
	@Override
	public void display(int k) {
		for (int w = 0; w < k; w++) {
			System.out.print("\t");
		}

		System.out.println("Syntax.Break");
	}

	@Override
	public void V(HashMap<String, Init> declarationMap) {
		// todo
	}

	@Override
	void V(HashMap<String, Init> declarationMap, Statement s) {
		
		check(s instanceof WhileStatement || s instanceof ForStatement || s instanceof SwitchStatement || s instanceof IfStatement,
				"can not reach here Break ");
		// TODO Auto-generated method stub
		
	}
}
