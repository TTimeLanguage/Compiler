package Syntax;

import java.util.ArrayList;
import java.util.HashMap;

/**
 * Abstract Syntax :
 * Declaration = Init*
 */
public class Declaration extends Global {
	protected final ArrayList<Init> inits;

	public Declaration(ArrayList<Init> init) {
		this.inits = init;
	}

	@Override
	void display(int lev) {
		for (int i = 0; i < lev; i++) {
			System.out.print("\t");
		}

		System.out.println("Declaration");
		for (Init init : inits) {
			init.display(lev + 1);
		}
	}

	@Override
	public void V(HashMap<String, Init> declarationMap) {
		// todo 아마 그대로
	}
}