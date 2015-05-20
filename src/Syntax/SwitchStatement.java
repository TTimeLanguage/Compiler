package Syntax;

import java.util.ArrayList;
import java.util.HashMap;

/**
 * Abstract Syntax :
 * SwitchStatement = Expression condition; (Literal case; Statement*)*; (Statement*)?
 */
public class SwitchStatement extends Statement {
	protected Type switchType;
	protected final Expression condition;
	protected final HashMap<Value, ArrayList<Statement>> cases = new HashMap<>();
	protected ArrayList<Statement> defaults = null;

	public SwitchStatement(Expression condition) {
		this.condition = condition;
	}

	public void addCase(Value caseLiteral, ArrayList<Statement> statements) {
		check(!cases.containsKey(caseLiteral),
				"duplicated case literal in switch");

		cases.put(caseLiteral, statements);
	}

	public void setDefault(ArrayList<Statement> defaults) {
		check(defaults == null, "duplicated default in switch");
		this.defaults = defaults;
	}

	@Override
	void display(int lev) {
		for (int i = 0; i < lev; i++) {
			System.out.print("\t");
		}

		System.out.println("SwitchStatement");
		for (Value value : cases.keySet()) {
			value.display(lev + 1);
			for (Statement statement : cases.get(value)) {
				statement.display(lev + 1);
			}
		}
	}

	@Override
	public void V(HashMap<String, Init> declarationMap) {
		// todo 확인
		if (valid) return;

		switchType = condition.typeOf(declarationMap);

		for (Value key : cases.keySet()) {
			check(key.typeOf(declarationMap) != switchType,
					"different type of case literal in switch. case : " + key.typeOf(declarationMap));

			for (Statement statement : cases.get(key)) {
				statement.V(declarationMap);
			}
		}

		if (defaults != null) {
			for (Statement statement : defaults) {
				statement.V(declarationMap);
			}
		}

		valid = true;
	}
}
