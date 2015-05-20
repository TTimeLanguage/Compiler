package Syntax;

import java.util.ArrayList;
import java.util.HashMap;

/**
 * Created by 병훈 on 2015-05-20.
 */
/**
 * AST의 투르 노드
 * <p>
 * Abstract Syntax :
 * Syntax.Program = Syntax.Global*; Syntax.Statements
 *
 * @see AbstractSyntax
 */
public class Program extends AbstractSyntax {
	/**
	 * Global들을 모아놓은 리스트
	 *
	 * @see Global
	 * @see ArrayList
	 */
	protected final ArrayList<Global> globals;
	/**
	 * int main()의 안에 적혀있는 코드들
	 *
	 * @see Statement
	 */
	protected final Statements statements;

	/**
	 * <tt>Syntax.Program</tt>객체를 매개변수로 초기화한다.
	 *
	 * @param g <tt>Syntax.Global</tt>의 <tt>ArrayList</tt>
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

				check(!globalFunctionMap.contains(global),
						"duplicated declared function " + functionDeclaration.name);

				globalFunctionMap.add(functionDeclaration);

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
	public void display(int k) {
		for (int w = 0; w < k; w++) {
			System.out.print("\t");
		}

		System.out.println("Syntax.Program");
		for (Global g : globals) {
			g.display(k + 1);
		}
		statements.display(k + 1);
	}


	public void V() {
		V(globalVariableMap);
	}


	@Override
	public void V(HashMap<String, Init> declarationMap) {
		mapGlobal();

		for (Global global : globals) {
			global.V(declarationMap);
		}

		statements.mapVariable();
		statements.V(declarationMap);
	}
}