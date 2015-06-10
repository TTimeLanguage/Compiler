package Syntax;

import CodeGenerator.CodeGenerator;

import java.util.ArrayList;
import java.util.HashMap;

/**
 * 변수 정의들과 실행구문들의 모임으로 이뤄진 구문
 * <p>
 * Abstract Syntax :
 * Statements = Declaration*; Statement*
 */
public class Statements extends AbstractSyntax {
	/**
	 * 변수 정의를 해주는 구문들을 나타내는 <tt>ArrayList</tt>
	 *
	 * @see Declaration
	 */
	protected final ArrayList<Declaration> declarations;
	/**
	 * 실행 구문들을 나타내는 <tt>ArrayList</tt>
	 *
	 * @see Statement
	 */
	protected final ArrayList<Statement> statements;
	/**
	 * 이 <tt>Statements</tt>의 매개변수의 맵
	 * <p>
	 * 변수 정의의 중복을 확인하기 위해서 <tt>HashMap</tt>형식으로 저장.
	 * <p>
	 * 이 객체가 paser에 의해 생성될때는 비어있지만 type checker가 실행될 때 map을 채운다.
	 */
	protected final HashMap<String, Init> variableMap = new HashMap<>();

	public Statements(ArrayList<Declaration> d, ArrayList<Statement> s) {
		declarations = d;
		statements = s;
	}

	/**
	 * type checking시간에 호출됨.
	 * <p>
	 * <tt>ArrayList</tt>객체인 declarations를 보고 <tt>HashMap</tt>객체의 variableMap을 채운다.
	 */
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
	protected void V(HashMap<String, Init> declarationMap, Type functionType) {
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
				"duplicated declaration");

		for (Statement statement : statements) {
			statement.V(localMap, functionType);
		}

		valid = true;
	}


	/**
	 * <tt>declarations</tt>에 정의된 모든 변수의 크기를 반환한다.
	 *
	 * @return <tt>declarations</tt>에 정의된 모든 변수의 크기
	 */
	protected int variableSize() {
		int sum = 0;

		for (Declaration declaration : declarations) {
			sum += declaration.sizeOf();
		}

		return sum;
	}

	@Override
	public void genCode() {
		for (Declaration declaration : declarations) {
			declaration.genCode();
		}

		CodeGenerator.finishLocalDeclaration();

		for (Statement statement : statements) {
			statement.genCode();
		}
	}
}