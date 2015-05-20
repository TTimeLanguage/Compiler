package Syntax;

import java.util.HashMap;

import Syntax.AbstractSyntax;

/**
 * Abstract Syntax :
 * Syntax.Statement = Syntax.Skip | Syntax.IfStatement | Syntax.Block | Syntax.WhileStatement | Syntax.SwitchStatement
 * | Syntax.ForStatement | Syntax.Return | Syntax.Expression | Syntax.Break | Syntax.Continue
 */
abstract public class Statement extends AbstractSyntax {	
	abstract void V(HashMap<String, Init> declarationMap, Statement s);    // validation

	/**
	 * <tt>test</tt>를 확인해서 false면 <tt>msg</tt>를 출력하고 종료함
	 *
	 * @param test 확인 하고 싶은 식이나 변수
	 * @param msg  <tt>test</tt>가 만족하지 못 할 경우 출력 할 메시지
	 */

}

