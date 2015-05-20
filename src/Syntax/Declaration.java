package Syntax;

import java.util.ArrayList;
import java.util.HashMap;

/**
 * Abstract Syntax :
 * Syntax.Declaration = Syntax.Init*
 */
public class Declaration extends Global {
	protected final ArrayList<Init> inits;

	Declaration(ArrayList<Init> init) {
		this.inits = init;
	}

	@Override
	void display(int k) {
		for (int w = 0; w < k; w++) {
			System.out.print("\t");
		}

		System.out.println(this.getClass().getName());
		for (Init init : inits) {
			init.display(k + 1);
		}
	}

	@Override
	public void V(HashMap<String, Init> declarationMap) {
		// todo 아마 그대로
	}
}