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
	 * type check시간에 타당성이 확인 됐을 때<tt>typeOf()</tt>에 의해 구해진 <tt>Type</tt>객체를 caching한다.
	 */
	protected Type type;


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
	 * Expression에서는 loop문 내부 여부를 신겅쓰지 않고 검사해도 무방하다.
	 * 따라서 <tt>V(HashMap<String, Init> declarationMap)</tt>메소드로 redirect한다.
	 *
	 * @param declarationMap 이 노드의 범위에서 사용가능한 변수의 map
	 * @param loopStatement  타당성 검사 할 반복자
	 */
	@Override
	protected final void V(HashMap<String, Init> declarationMap, Statement loopStatement) {
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

	/**
	 * <tt>Expression</tt>상속한 객체의 수행 후 타입을 구한다.
	 *
	 * @param declarationMap 변수의 map
	 * @return 이 객체를 수행 후의 타입
	 */
	abstract Type typeOf(HashMap<String, Init> declarationMap);

	/**
	 * type checking때 caching한 <tt>Type</tt>객체를 가져온다.
	 *
	 * @return 이 객체를 수행 후의 타입
	 */
	public Type getType() {
		check(type != null, "Compiler error. type can not be null");

		return type;
	}
}
