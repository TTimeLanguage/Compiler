package Syntax;

import java.util.ArrayList;
import java.util.HashMap;

/**
 * switch를 나타내는 구문
 * <p>
 * Abstract Syntax :
 * SwitchStatement = Expression condition; (Literal case; Statement*)*; (Statement*)?
 */
public class SwitchStatement extends Statement {
	/**
	 * switch 조건에 쓰이는 변수의 type을 저장하는 변수
	 */
	protected Type switchType;
	/**
	 * 조건을 나타내는 변수
	 */
	protected final Expression condition;
	/**
	 * case 문에 값을 저장하는 HashMap, 해당 실행문을 나타내는 ArrayList
	 */
	protected final HashMap<Value, ArrayList<Statement>> cases = new HashMap<>();
	/**
	 * default 구문을 나타내는 ArrayList
	 */
	protected ArrayList<Statement> defaults = null;


	public SwitchStatement(Expression condition) {
		this.condition = condition;
	}

	/**
	 * case 구문을 추가하는 함수
	 * <p>
	 * 두번 실행도리 경우(구문이 덮어씌여질 경우) 에러가난다.
	 *
	 * @param caseLiteral 추가 할 case의 literal
	 * @param statements  추가 할 case의 실행 부분
	 */
	public void addCase(Value caseLiteral, ArrayList<Statement> statements) {
		check(!cases.containsKey(caseLiteral),
				"duplicated case literal in switch");

		cases.put(caseLiteral, statements);
	}

	/**
	 * default을 설정하는 함수
	 * <p>
	 * 두번 실행도리 경우(구문이 덮어씌여질 경우) 에러가난다.
	 *
	 * @param defaults	설정할 default의 <tt>Statement</tt>들의 <tt>ArrayList</tt>
	 */
	public void setDefault(ArrayList<Statement> defaults) {
		check(defaults != null, "duplicated default in switch");
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
	protected void V(HashMap<String, Init> declarationMap, Type functionType) {
		// todo 확인
		if (valid) return;

		condition.V(declarationMap);
		switchType = condition.typeOf(declarationMap);

		for (Value key : cases.keySet()) {
			key.V(declarationMap);
			check(key.typeOf(declarationMap).equals(switchType),
					"different type of case literal in switch. case : " + key.typeOf(declarationMap));

			for (Statement statement : cases.get(key)) {
				statement.V(declarationMap, this, functionType);
			}
		}

		if (defaults != null) {
			for (Statement statement : defaults) {
				statement.V(declarationMap, this, functionType);
			}
		}

		valid = true;
	}

	@Override
	protected void V(HashMap<String, Init> declarationMap, Statement loopStatement) {
		// todo 확인
		if (valid) return;

		condition.V(declarationMap);
		switchType = condition.typeOf(declarationMap);

		for (Value key : cases.keySet()) {
			key.V(declarationMap);
			check(key.typeOf(declarationMap) != switchType,
					"different type of case literal in switch. case : " + key.typeOf(declarationMap));

			for (Statement statement : cases.get(key)) {
				statement.V(declarationMap, this);
			}
		}

		if (defaults != null) {
			for (Statement statement : defaults) {
				statement.V(declarationMap, this);
			}
		}

		valid = true;
	}

	@Override
	protected void V(HashMap<String, Init> declarationMap, Statement loopStatement, Type functionType) {
		V(declarationMap, functionType);
	}
}
