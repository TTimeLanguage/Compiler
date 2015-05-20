package Syntax;

import java.util.HashMap;

/**
 * Abstract Syntax :
 * Syntax.ParamDeclaration = Syntax.Type; String Id
 */
public class ParamDeclaration extends Init {
	ParamDeclaration(Type t, String name) {
		type = t;
		this.name = name;
	}

	void display(int k) {
		for (int w = 0; w < k; w++) {
			System.out.print("\t");
		}

		System.out.println("Syntax.ParamDeclaration " + type + " " + name);
	}

	@Override
	public void V(HashMap<String, Init> declarationMap) {
		// todo 아마 그대로
	}
}