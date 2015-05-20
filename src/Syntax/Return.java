package Syntax;

import java.util.HashMap;

/**
 * Abstract Syntax :
 * Syntax.Return = Syntax.Expression?
 */
public class Return extends Statement {
	protected final Expression returnValue;

	public Return(Expression returnValue) {
		this.returnValue = returnValue;
	}

	public Return() {
		this(null);
	}

	@Override
	void display(int lev) {
		for (int i = 0; i < lev; i++) {
			System.out.print("\t");
		}

		System.out.println("Return");
		if (returnValue != null) {
			returnValue.display(lev + 1);
		}
	}

	@Override
	protected void V(HashMap<String, Init> declarationMap) {
		// todo
	}
}
