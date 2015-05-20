package Syntax;

import java.util.HashMap;

/**
 * Abstract Syntax :
 * Syntax.ParamDeclaration = Syntax.Type; String Id
 */
public class ParamDeclaration extends Init {
	public ParamDeclaration(Type t, String name) {
		type = t;
		this.name = name;
	}

	void display(int lev) {
		for (int i = 0; i < lev; i++) {
			System.out.print("\t");
		}

		System.out.println("ParamDeclaration " + type + " " + name);
	}

	@Override
	public void V(HashMap<String, Init> declarationMap) {
		// todo 확인
		check(!type.equals(Type.VOID),
				"variable type can not be void");
	}
}