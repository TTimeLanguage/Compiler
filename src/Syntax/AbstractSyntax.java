package Syntax;

import Semantic.FunctionInformation;

import java.util.HashMap;
import java.util.HashSet;

public abstract class AbstractSyntax {
	/**
	 * 객체의 타당할경우 <tt>true</tt>
	 * 객체가 타당성을 <tt>V</tt>함수를 통해 확인 됐다면,
	 * 다음 <tt>V</tt>합수를 실행할때 다시 타당성을 확인하지 않는다.
	 */
	protected boolean valid = false;
	/**
	 * 전역변수 테이블
	 * 전역변수 이름(String)를 Key로, 전역변수 객체를 Init으로 가지는 map객체
	 */
	protected final static HashMap<String, Init> globalVariableMap = new HashMap<>();
	// todo
	protected final static HashSet<FunctionInformation> globalFunctionMap = new HashSet<>();

	/**
	 * AST에서 노드(객체)의 정보를 표시함
	 *
	 * @param k 노드의 레벨
	 */
	abstract void display(int k);

	/**
	 * AST에서 노드(객체)의 타당성을 확인함
	 *
	 * @param declarationMap 이 노드의 범위에서 사용가능한 변수의 map
	 */
	abstract void V(HashMap<String, Init> declarationMap);    // validation

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