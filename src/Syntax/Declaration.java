package Syntax;

import java.util.ArrayList;
import java.util.HashMap;

/**
 * 변수 선언들을 나타내는 구문
 * 한 줄의 변수 선언에 하나의 <tt>Declaration</tt>객체를 가진다.
 * <tt>Init</tt>객체들을 하나의 선언으로 가지고있다.
 * <p>
 * Abstract Syntax :
 * Declaration = Init*
 *
 * @see Init
 */
public class Declaration extends Global {
	/**
	 * 초기값을 나타내는 <tt>ArrayList</tt> 배열
	 */
	protected final ArrayList<Init> inits;

	public Declaration(ArrayList<Init> init) {
		this.inits = init;
	}

	@Override
	void display(int lev) {
		for (int i = 0; i < lev; i++) {
			System.out.print("\t");
		}

		System.out.println("Declaration");
		for (Init init : inits) {
			init.display(lev + 1);
		}
	}

	@Override
	protected void V(HashMap<String, Init> declarationMap) {
		for (Init init : inits) {
			init.V(declarationMap);
		}
	}

	@Override
	public void genCode() {
		for (Init init : inits) {
			init.genCode();
		}
	}

	/**
	 * 이 객체에서 선언한 모든 변수의 크기를 반환한다.
	 *
	 * @return 이 객체에서 선언한 모든 변수의 크기
	 */
	protected int sizeOf() {
		int sum = 0;

		for (Init init : inits) {
			sum += init.sizeOf();
		}

		return sum;
	}
}