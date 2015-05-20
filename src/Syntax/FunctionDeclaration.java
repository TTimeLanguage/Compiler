package Syntax;

import java.util.ArrayList;
import java.util.HashMap;

/**
 * Abstract Syntax :
 * Syntax.FunctionDeclaration = Syntax.Type; String id; Syntax.ParamDeclaration*; Syntax.Statements
 */
public class FunctionDeclaration extends Global {
	protected final Type type;
	protected final String name;
	protected final ArrayList<ParamDeclaration> params;
	protected final Statements statements;
	protected final HashMap<String, Init> paramMap = new HashMap<>();

	public FunctionDeclaration(Type t, String name, ArrayList<ParamDeclaration> p, Statements s) {
		type = t;
		this.name = name;
		params = p;
		statements = s;
	}

	protected void mapParams() {
		for (ParamDeclaration param : params) {

			check(!paramMap.containsKey(param.name),
					"duplicated declaration :" + param.name);

			paramMap.put(param.name, param);
		}

		statements.mapVariable();
	}

	@Override
	void display(int lev) {
		for (int i = 0; i < lev; i++) {
			System.out.print("\t");
		}

		System.out.println("FunctionDeclaration " + type + " " + name);
		for (ParamDeclaration declaration : params) {
			declaration.display(lev + 1);
		}
		statements.display(lev + 1);
	}

	@Override
	public void V(HashMap<String, Init> declarationMap) {
		// todo
		if (valid) return;

		HashMap<String, Init> localMap = new HashMap<>(declarationMap);
		int globalLength = localMap.size();
		int localLength = paramMap.size();
		localMap.putAll(paramMap);

		check(globalLength + localLength == localMap.size(),
				"duplicated declaration in function :" + type + " " + name);

		statements.V(localMap);

		valid = true;
	}
}