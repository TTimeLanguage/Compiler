package Syntax;

import Semantic.FunctionInfo;
import Semantic.FunctionSet;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedHashMap;

public abstract class AbstractSyntax {
	/**
	 * 객체의 타당할경우 <tt>true</tt>
	 * 객체가 타당성을 <tt>V</tt>함수를 통해 확인 됐다면,
	 * 다음 <tt>V</tt>합수를 실행할때 다시 타당성을 확인하지 않는다.
	 * 또한 Expression객체일 때 타당성을 확인하지않고 타입을 가져오는<tt>typeOf</tt>함수를 사용하는지 확인하는데 쓰인다.
	 * <p>
	 * Type Check시간에 사용됨.
	 */
	protected boolean valid = false;

	/**
	 * 전역변수 테이블.
	 * 전역변수 이름(<tt>String</tt>)를 Key로, 전역변수 객체를 <tt>Init</tt>으로 가지는 map객체
	 * <p>
	 * Type Check시간에 사용됨.
	 *
	 * @see Init
	 */
	protected final static LinkedHashMap<String, Init> globalVariableMap = new LinkedHashMap<>();

	/**
	 * 함수 테이블.
	 * 함수의 정보(<tt>FunctionInfo</tt>)의 set.
	 * <p>
	 * Type Check시간에 사용됨.
	 *
	 * @see FunctionInfo
	 */
	protected final static FunctionSet globalFunctionMap = new FunctionSet();

	/**
	 * code generate시간에 overload된 함수들의 중복된 함수이름 라벨을 피하기위해 type check시간에 맵을 채움.
	 */
	protected final static HashMap<String, ArrayList<FunctionInfo>> overloadMap = new HashMap<>();


	/**
	 * AST에서 노드(객체)의 정보를 표시함
	 *
	 * @param lev 노드의 레벨
	 */
	abstract void display(int lev);


	/**
	 * 자신의 객체에 해당하는 U-code를 생성한다.
	 */
	public abstract void genCode();

	/**
	 * AST에서 노드(객체)의 타당성을 확인함
	 *
	 * @param declarationMap 이 노드의 범위에서 사용가능한 변수의 map
	 */
	protected void V(HashMap<String, Init> declarationMap) {
		check(false, "Compiler error. never reach here. empty V()");
	}

	/**
	 * AST에서 노드(객체)의 타당성을 <tt>Type</tt>의 타입을 가지는 함수 안에서 확인함
	 *
	 * @param declarationMap 이 노드의 범위에서 사용가능한 변수의 map
	 * @param functionType   타당성 검사 할 함수의 <tt>Type</tt>
	 */
	protected void V(HashMap<String, Init> declarationMap, Type functionType) {
		check(false, "Compiler error. never reach here. empty V()");
	}

	/**
	 * AST에서 노드(객체)의 타당성을 반복자의 범위와 <tt>Type</tt>의 타입을 가지는 함수안에서 확인함
	 *
	 * @param declarationMap 이 노드의 범위에서 사용가능한 변수의 map
	 * @param loopStatement  타당성 검사 할 반복자
	 * @param functionType   타당성 검사 할 함수의 <tt>Type</tt>
	 */
	protected void V(HashMap<String, Init> declarationMap, Statement loopStatement, Type functionType) {
		check(false, "Compiler error. never reach here. empty V()");
	}

	/**
	 * <tt>test</tt>를 확인해서 false면 <tt>msg</tt>를 출력하고 종료함
	 *
	 * @param test 확인 하고 싶은 식이나 변수
	 * @param msg  <tt>test</tt>가 만족하지 못 할 경우 출력 할 메시지
	 */
	protected final void check(boolean test, String msg) {
		if (test) return;
		System.err.println(msg);
		System.exit(1);
	}
}