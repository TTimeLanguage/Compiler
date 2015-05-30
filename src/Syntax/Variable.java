package Syntax;

import CodeGenerator.CodeGenerator;

import java.util.HashMap;

/**
 * 일반 변수참조를 나타내는 구문
 * <p>
 * Abstract Syntax :
 * Variable = String id
 */
public class Variable extends VariableRef {
	/**
	 * 변수의 이름을 저장하는 변수
	 */
	protected final String name;

	public Variable(String name) {
		this.name = name;
	}

	@Override
	public String toString() {
		return name;
	}

	@Override
	public boolean equals(Object obj) {
		if (obj instanceof Variable) {
			String s = ((Variable) obj).name;
			return name.equals(s);
		} else return false;
	}

	@Override
	public int hashCode() {
		return name.hashCode();
	}

	@Override
	void display(int lev) {
		for (int i = 0; i < lev; i++) {
			System.out.print("\t");
		}

		System.out.println("Variable " + name);
	}

	/**
	 * table에 정의되어 있는 변수인지 확안
	 * <p>
	 * 배열이 table에 정의 되어있는지 확인
	 */
	@Override
	protected void V(HashMap<String, Init> declarationMap) {
		// todo 확인
		if (valid) return;

		check(declarationMap.containsKey(name),
				"undeclared variable: " + name);

		check(!(declarationMap.get(name) instanceof ArrayInit),
				"wrong reference. should add array reference. in " + name);

		valid = true;
	}

	@Override
	Type typeOf(HashMap<String, Init> declarationMap) {
		check(valid, "Compiler error. must check validation");

		check(declarationMap.containsKey(name),
				"undefined variable: " + name);

		type = declarationMap.get(name).type;

		return type;
	}

	@Override
	public void genCode() {
		CodeGenerator.lod(name);
	}
}
