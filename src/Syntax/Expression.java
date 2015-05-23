package Syntax;

import java.util.HashMap;

/**
 * 수식과 관계식등을 나타내는 구문
 * <p>
 * Abstract Syntax :
 * Expression = VariableRef | Value | Binary | Unary | Function | TypeCast
 */
public abstract class Expression extends Statement {
	/**
	 * Expression에서는 함수의 Type을 신겅쓰지 않고 검사해도 무방하다.
	 * 따라서 <tt>V(HashMap<String, Init> declarationMap)</tt>메소드로 redirect한다.
	 *
	 * @param declarationMap 이 노드의 범위에서 사용가능한 변수의 map
	 * @param functionType   타당성 검사 할 함수의 <tt>Type</tt>
	 */
	@Override
	protected final void V(HashMap<String, Init> declarationMap, Type functionType) {
		V(declarationMap);
	}

	/**
	 * Expression에서는 함수의 Type이나 loop문 내부 여부를 신겅쓰지 않고 검사해도 무방하다.
	 * 따라서 <tt>V(HashMap<String, Init> declarationMap)</tt>메소드로 redirect한다.
	 *
	 * @param declarationMap 이 노드의 범위에서 사용가능한 변수의 map
	 * @param loopStatement  타당성 검사 할 반복자
	 * @param functionType   타당성 검사 할 함수의 <tt>Type</tt>
	 */
	@Override
	protected final void V(HashMap<String, Init> declarationMap, Statement loopStatement, Type functionType) {
		V(declarationMap);
	}

	abstract Type typeOf(HashMap<String, Init> declarationMap);
}
