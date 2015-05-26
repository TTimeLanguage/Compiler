package Syntax;

import CodeGenerator.CodeGenerator;

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
	 * @see    Type
	 */
	protected Type type;
	/**
	 * 변수의 이름
	 */
	protected String name;


	/**
	 * 이 객체를 u-code로 변환했을때의 크기를 반환.
	 *
	 * @return 이 객체를 u-code로 변환했을때의 크기
	 */
	abstract public int sizeOf();


	/**
	 * 이 객체를 U-code로 변활할때 변수 초기화를 처리함.
	 */
	protected abstract void init();


	@Override
	public void genCode() {
		int block = 2;
		int size = sizeOf();

		if (CodeGenerator.isInGlobalDeclaration()) {
			block = 1;
		}

		CodeGenerator.sym(name, block, size);

		init();
	}
}