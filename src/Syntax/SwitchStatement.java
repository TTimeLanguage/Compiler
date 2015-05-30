package Syntax;

import CodeGenerator.CodeGenerator;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedHashMap;

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
	 * case 문에 값을 저장하는 <tt>LinkedHashMap</tt>, 해당 실행문을 나타내는 <tt>ArrayList</tt>
	 */
	protected final LinkedHashMap<Value, ArrayList<Statement>> cases = new LinkedHashMap<>();
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

		for (int i = 0; i < lev; i++) {
			System.out.print("\t");
		}
		System.out.println("Condition :");
		condition.display(lev + 1);

		if (cases.size() > 0) {
			for (int i = 0; i < lev; i++) {
				System.out.print("\t");
			}
			System.out.println("Cases :");

			for (Value value : cases.keySet()) {
				value.display(lev + 1);
				for (Statement statement : cases.get(value)) {
					statement.display(lev + 2);
				}
			}
		}

		if (defaults != null) {
			for (int i = 0; i < lev; i++) {
				System.out.print("\t");
			}
			System.out.println("Default :");

			for (Statement statement : defaults) {
				statement.display(lev + 1);
			}
		}
	}

	/**
	 * switch의 조건이 타당한지 확인
	 * <p>
	 * 해당 case들의 값이 switch type과 맞는지 확인
	 * <p>
	 * case내부의 실행문이 타당한지 확인
	 * <p>
	 * default가 존재한다면 실행문이 타당한지 확인
	 */
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

	/**
	 * 반복문 내부에 switch문이 존재할 때 호출하여 타당성 확인
	 */
	@Override
	protected void V(HashMap<String, Init> declarationMap, Statement loopStatement, Type functionType) {
		// todo 확인
		if (valid) return;

		Statement nextLoop = this;
		if (loopStatement instanceof WhileStatement || loopStatement instanceof ForStatement) {
			nextLoop = loopStatement;
		}

		condition.V(declarationMap);
		switchType = condition.typeOf(declarationMap);

		for (Value key : cases.keySet()) {
			key.V(declarationMap);
			check(key.typeOf(declarationMap).equals(switchType),
					"different type of case literal in switch. case : " + key.typeOf(declarationMap));

			for (Statement statement : cases.get(key)) {

				statement.V(declarationMap, nextLoop, functionType);
			}
		}

		if (defaults != null) {
			for (Statement statement : defaults) {
				statement.V(declarationMap, nextLoop, functionType);
			}
		}

		valid = true;
	}

	@Override
	public void genCode() {
		// todo

		branchNum = CodeGenerator.getIfNum();

		condition.genCode();

		int len = cases.size();
		int i = 0;
		for (Value value : cases.keySet()) {

			if (i < len - 1) {
				CodeGenerator.dup();
			}
			value.genCode();
			CodeGenerator.eq();

			if (i < len - 1) {
				CodeGenerator.fjp(CodeGenerator.getElseIfBranch(branchNum, i++));
			} else {
				CodeGenerator.fjp(CodeGenerator.getElseBranch(branchNum));
			}
		}

		for (Value value : cases.keySet()) {
			CodeGenerator.makeElseIfBranch(branchNum);

			for (Statement statement : cases.get(value)) {
				statement.genCode();
			}
		}

		CodeGenerator.makeElseBranch(branchNum);

		for (Statement statement : defaults) {
			statement.genCode();
		}

		CodeGenerator.makeIfExitBranch(branchNum);
	}
}
