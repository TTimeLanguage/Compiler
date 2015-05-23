package Syntax;

/**
 * 변수선언에서 변수 하나를 나타내는 구문의 abstract class
 * <p>
 * Abstract Syntax :
 * Init = ArrayInit | NoArrayInit
 */
abstract public class Init extends AbstractSyntax {
	/**
	 * 변수의 type <tt>Type</tt>객체로 저장한다.
	 *
	 * @see	Type
	 */
	protected Type type;
	/**
	 * 변수의 이름
	 */
	protected String name;
}