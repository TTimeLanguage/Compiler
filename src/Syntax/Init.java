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
	 * @see Type
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

	/**
	 * 좌변 우변의 type이 타당한지 검사
	 * <p>
	 * 자동 형변환 까지 가능한 경우까지 검사
	 *
	 * @param init
	 * @param variableType
	 */
	protected void checkInitType(Expression init, Type variableType) {
		if (variableType.equals(Type.FLOAT)) {
			check(type.equals(Type.FLOAT) || type.equals(Type.INT),
					"float type variable can not initialize with " + type + " value");

			if (type.equals(Type.INT)) {
				init = new TypeCast(Type.FLOAT, init);
			}

		} else if (variableType.equals(Type.BOOL)) {
			check(type.equals(Type.BOOL),
					"bool type variable can not initialize with " + type + " value");

		} else if (variableType.equals(Type.CHAR)) {
			check(type.equals(Type.CHAR),
					"char type variable can not initialize with " + type + " value");

		} else if (variableType.equals(Type.DATE)) {
			check(type.equals(Type.DATE),
					"date type variable can not initialize with " + type + " value");

		} else if (variableType.equals(Type.TIME)) {
			check(type.equals(Type.TIME),
					"time type variable can not initialize with " + type + " value");

		} else if (variableType.equals(Type.INT)) {
			check(type.equals(Type.FLOAT) || type.equals(Type.INT),
					"int type variable can not initialize with " + type + " value");

			if (type.equals(Type.FLOAT)) {
				init = new TypeCast(Type.INT, init);
			}

		} else {
			check(false, "Compiler error. never reach here. unrecognized type.");
		}
	}
}