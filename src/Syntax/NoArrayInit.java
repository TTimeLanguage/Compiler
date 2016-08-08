package Syntax;

import CodeGenerator.CodeGenerator;

import java.util.HashMap;

/**
 * 배열이 아닌 변수의 초기화를 나타내는 구문
 * <p>
 * Abstract Syntax :
 * ArrayInit = Type; String id; (Expression initList)*
 */
public class NoArrayInit extends Init {
	/**
	 * 초기화 부분을 나타내는 변수
	 * <p>
	 * null일경우 초기화되지않은 변수의 선언이다.
	 */
	protected Expression initial;

	/**
	 * 초기화를 해주는 변수의 구문을 나타내는 생성자
	 */
	public NoArrayInit(Type t, String name, Expression i) {
		type = t;
		this.name = name;
		initial = i;
	}

	/**
	 * 초기화를 하지 않는 변수의 구문을 나타내는 생성자
	 */
	public NoArrayInit(Type t, String name) {
		this(t, name, null);
	}


	public int sizeOf() {
		return 1;
	}

	@Override
	void display(int lev) {
		for (int i = 0; i < lev; i++) {
			System.out.print("\t");
		}

		System.out.println("NoArrayInit " + type + " " + name);

		if (initial != null) {
			initial.display(lev + 1);
		}
	}

	/**
	 * void 타입의 변수가 선언되었는지 확인
	 * <p>
	 * 초기화 식이 존재한다면 초기화 식 확인
	 * <p>
	 * 초기화 식의 type을 확인
	 */
	@Override
	protected void V(HashMap<String, Init> declarationMap) {
		// todo 확인
		if (valid) return;

		check(!type.equals(Type.VOID),
				"variable type can not be void");

		if (initial != null) {
			initial.V(declarationMap);

			Type declaredType = initial.typeOf(declarationMap);

			checkInitType(initial, declaredType);
		}

		valid = true;
	}

	@Override
	protected void init() {
		if (initial != null) {
			CodeGenerator.addInit(name, initial);
		}
	}
}