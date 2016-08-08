package Syntax;

import CodeGenerator.CodeGenerator;

import java.util.ArrayList;
import java.util.HashMap;

/**
 * 배열을 선언 또는 초기화 하는 구문
 * <p>
 * Abstract Syntax :
 * ArrayInit = Type; String id; int size; (Expression initList)*
 */
public class ArrayInit extends Init {
	/**
	 * 배열의 크기를 나타내는 변수
	 */
	protected final int size;
	/**
	 * 배열을 초기화 했을 때 초기값을 표현해주는 <tt>ArrayList</tt>
	 * <p>
	 * null일경우 초기화되지않은 배열의 선언이다.
	 */
	protected final ArrayList<Expression> initList;

	/**
	 * 초기화를 해준 배열 생성자
	 */
	public ArrayInit(Type t, String name, int s, ArrayList<Expression> i) {
		type = t;
		this.name = name;
		size = s;
		initList = i;
	}

	/**
	 * 초기화를 해주지 않은 배열 생성자
	 */
	public ArrayInit(Type t, String name, int s) {
		this(t, name, s, null);
	}


	@Override
	public int sizeOf() {
		return size;
	}

	@Override
	void display(int lev) {
		for (int i = 0; i < lev; i++) {
			System.out.print("\t");
		}

		System.out.println("ArrayInit " + type + " " + name + "[" + size + "]");
		if (initList != null) {
			for (Expression expression : initList) {
				expression.display(lev + 1);
			}
		}
	}

	/**
	 * void type의 변수가 선언되었는지 확인
	 * <p>
	 * 배열의 크기가 양수인지 확인
	 * <p>
	 * 초기화가 되어있는 배열이라면 해당 초기화 식을 확인
	 * <p>
	 * 배열 크기와 초기화 식의 수를 확인, 초기화 식을 확인하고 type을 확인
	 */
	@Override
	protected void V(HashMap<String, Init> declarationMap) {
		// todo 확인
		if (valid) return;

		check(!type.equals(Type.VOID),
				"variable type can not be void");

		check(size >= 1,
				"array size must higher than 1. declared : " + type + " " + name + "[" + size + "]");

		if (initList != null) {
			check(size >= initList.size(),
					"initializes must less than array size. declared : " + type + " " + name + "[" + size + "]");

			for (Expression expression : initList) {
				expression.V(declarationMap);

				Type declaredType = expression.typeOf(declarationMap);

				checkInitType(expression, declaredType);
			}
		}

		valid = true;
	}

	@Override
	protected void init() {
		if (initList != null) {
			for (Expression init : initList) {
				CodeGenerator.addInit(name, init);
			}
		}
	}
}