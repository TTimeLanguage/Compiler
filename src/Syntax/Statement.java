package Syntax;

/**
 * 프로그램에서 정의를 뺀 실행구문들의 abstract class
 * <p>
 * Abstract Syntax :
 * Statement = Skip | IfStatement | Block | WhileStatement | SwitchStatement
 * | ForStatement | Return | Expression | Break | Continue
 */
abstract public class Statement extends AbstractSyntax {
	protected int branchNum;
}


