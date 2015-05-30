package Syntax;


import CodeGenerator.CodeGenerator;

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

	/**
	 * 선언이 되어있는 변수의 이름인지 확인
	 * <p>
	 * <tt>ArrayInit</tt>의 객체인지 확인
	 * <p>
	 * 참조하려는 배열의 index가 Int인지 확인
	 */
	@Override
	protected void V(HashMap<String, Init> declarationMap) {
		// todo 확인 인덱스 범위 확인
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

		check(declarationMap.containsKey(name),
				"undefined variable: " + name);

		type = declarationMap.get(name).type;

		return type;
	}

	@Override
	public void genCode() {
		// todo 확인

		CodeGenerator.lda(name);
		index.genCode();
		CodeGenerator.add();
		CodeGenerator.ldi();
	}
}