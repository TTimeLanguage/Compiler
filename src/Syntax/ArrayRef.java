package Syntax;


import java.util.HashMap;

/**
 * 배열의 특정 인덱스 참조를 나타내는 구문
 * <p>
 * Abstract Syntax :
 * ArrayRef = String id; Expression index
 */
public class ArrayRef extends VariableRef {
	/**
	 * 배열의 이름
	 */
	protected final String name;
	/**
	 * 배열의 index를 나타내는 변수
	 */
	protected final Expression index;

	public ArrayRef(String n, Expression index) {
		this.name = n;
		this.index = index;
	}

	@Override
	void display(int lev) {
		for (int i = 0; i < lev; i++) {
			System.out.print("\t");
		}

		System.out.println("ArrayRef " + name);
		index.display(lev + 1);
	}

	@Override
	protected void V(HashMap<String, Init> declarationMap) {
		// todo 확인
		if (valid) return;

		check(declarationMap.containsKey(name),
				"undeclared variable: " + name);

		Init declare = declarationMap.get(name);
		check(declare instanceof ArrayInit,
				"wrong reference. should remove array reference. in " + name);

		index.V(declarationMap);
		check(index.typeOf(declarationMap) == Type.INT,
				"index type must be integer. in " + name);

		valid = true;
	}

	@Override
	Type typeOf(HashMap<String, Init> declarationMap) {
		check(valid, "Compiler error. must check validation");

		check(declarationMap.containsKey(this.name),
				"undefined variable: " + this.name);

		return declarationMap.get(this.name).type;
	}

	@Override
	public void genCode() {

	}
}