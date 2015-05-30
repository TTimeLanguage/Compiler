package Syntax;

import java.util.HashMap;

/**
 * 함수 정의에서 전달인자 하나를 나타내는 구문
 * <p>
 * Abstract Syntax :
 * ParamDeclaration = Type; String Id
 */
public class ParamDeclaration extends Init {
	public ParamDeclaration(Type t, String name) {
		type = t;
		this.name = name;
	}

	public Type getType() {
		return type;
	}

	void display(int lev) {
		for (int i = 0; i < lev; i++) {
			System.out.print("\t");
		}

		System.out.println("ParamDeclaration " + type + " " + name);
	}

	/**
	 * 파라미터에 void type이 있는지 확인
	 */
	@Override
	protected void V(HashMap<String, Init> declarationMap) {
		// todo 확인
		check(!type.equals(Type.VOID),
				"variable type can not be void");
	}

	@Override
	public int sizeOf() {
		return 1;
	}

	@Override
	protected void init() {
	}
}