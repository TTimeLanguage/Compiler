package Syntax;

import CodeGenerator.CodeGenerator;
import Semantic.FunctionInformation;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedHashMap;


/**
 * AST의 투르 노드
 * <p>
 * Abstract Syntax :
 * Program = Global*; Statements
 *
 * @see AbstractSyntax
 */
public class Program extends AbstractSyntax {
	/**
	 * <tt>Global</tt>객체들을 모아놓은 리스트
	 *
	 * @see Global
	 */
	protected final ArrayList<Global> globals;
	/**
	 * int main()의 안에 적혀있는 코드들
	 *
	 * @see Statement
	 */
	protected final Statements statements;

	/**
	 * <tt>Program</tt>객체를 매개변수로 초기화한다.
	 *
	 * @param g <tt>Global</tt>의 <tt>ArrayList</tt>
	 *          전역변수와 함수의 정의들
	 * @param s int main()의 안에 적혀있는 코드들
	 */
	public Program(ArrayList<Global> g, Statements s) {
		globals = g;
		statements = s;
	}

	private void mapGlobal() {
		for (Global global : globals) {

			if (global instanceof FunctionDeclaration) {
				FunctionDeclaration functionDeclaration = (FunctionDeclaration) global;
				functionDeclaration.mapParams();

				FunctionInformation tmp = new FunctionInformation(functionDeclaration);

				check(!globalFunctionMap.contains(tmp),
						"duplicated declared function " + functionDeclaration.name);

				globalFunctionMap.add(new FunctionInformation(functionDeclaration));
				tmp = null;        // for garbage collection

			} else if (global instanceof Declaration) {

				for (Init init : ((Declaration) global).inits) {

					check(!globalVariableMap.containsKey(init.name),
							"duplicated declaration in global " + init.name);

					globalVariableMap.put(init.name, init);
				}

			} else {
				check(false, "never reach here");
			}
		}
	}


	@Override
	public void display(int lev) {
		for (int i = 0; i < lev; i++) {
			System.out.print("\t");
		}

		System.out.println("Program");
		for (Global g : globals) {
			g.display(lev + 1);
		}
		statements.display(lev + 1);
	}


	/**
	 * 전체 AST의 타당성을 확인.
	 * <p>
	 * 실행 중 타당성이 성립되지 않는다면 중간에 프로그램이 종료됨.
	 */
	public void validation() {
		mapGlobal();
		statements.mapVariable();
		V(globalVariableMap);
	}


	/**
	 * <tt>globalVariableMap</tt>객체를 코드 생성을 위해 반환.
	 *
	 * @return 전역변수의 맵
	 */
	public LinkedHashMap<String, Init> getGlobalVariableMap() {
		return globalVariableMap;
	}


	@Override
	protected void V(HashMap<String, Init> declarationMap) {
		for (Global global : globals) {
			global.V(declarationMap);
		}

		statements.V(declarationMap, Type.INT);
	}

	@Override
	public void genCode() {
		for (Global global : globals) {
			if (global instanceof Declaration) {
				global.genCode();
			}
		}

		CodeGenerator.bgn();
		CodeGenerator.finishGlobalDeclaration();
		CodeGenerator.ldp();
		CodeGenerator.call("main");
		CodeGenerator.end();

		for (Global global : globals) {
			if (global instanceof FunctionDeclaration) {
				global.genCode();
			}
		}

		CodeGenerator.proc("main", statements.variableSize());
		statements.genCode();
	}
}