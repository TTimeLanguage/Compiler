package Syntax;

import java.util.ArrayList;
import java.util.HashMap;

/**
 * Abstract Syntax :
 * Statements = Declaration*; Statement*
 */
public class Statements extends AbstractSyntax {
	protected final ArrayList<Declaration> declarations;
	protected final ArrayList<Statement> statements;
	protected final HashMap<String, Init> variableMap = new HashMap<>();

	public Statements(ArrayList<Declaration> d, ArrayList<Statement> s) {
		declarations = d;
		statements = s;
	}

	protected void mapVariable() {
		for (Declaration declaration : declarations) {

			for (Init init : declaration.inits) {

				check(!variableMap.containsKey(init.name),
						"duplicated declaration " + init.name);

				variableMap.put(init.name, init);
			}

		}
	}

	@Override
	void display(int lev) {
		for (int i = 0; i < lev; i++) {
			System.out.print("\t");
		}

		System.out.println("Statements");
		for (Declaration declaration : declarations) {
			declaration.display(lev + 1);
		}
		for (Statement statement : statements) {
			statement.display(lev + 1);
		}
	}

	@Override
	void V(HashMap<String, Init> declarationMap, Type functionType) {
		// todo
		if (valid) return;

		for (Declaration declaration : declarations) {
			declaration.V(declarationMap);
		}

		HashMap<String, Init> localMap = new HashMap<>(declarationMap);
		int globalLength = localMap.size();
		int localLength = variableMap.size();
		localMap.putAll(variableMap);

		check(globalLength + localLength == localMap.size(),
				"duplicated declaration in main");

		for (Statement statement : statements) {
			statement.V(localMap, functionType);
		}

		valid = true;
	}
}