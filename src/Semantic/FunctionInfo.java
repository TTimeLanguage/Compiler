package Semantic;

import Syntax.FunctionDeclaration;
import Syntax.ParamDeclaration;
import Syntax.Type;

import java.util.ArrayList;

/**
 * �Լ� �ϳ��� ������ ����(�Լ��� ��ȯ Ÿ��, �̸�, �Ķ���͵��� Ÿ��)�� ������.
 * <p>
 * �Լ� ���� Ÿ�缺 Ȯ���� ���� ����.
 *
 * @see Parser.Parser
 * @see FunctionSet
 */
public class FunctionInfo {
	/**
	 * �Լ��� ��ȯ Ÿ��
	 */
	protected Type type;
	/**
	 * �Լ��� �̸�
	 */
	protected String name;
	/**
	 * �Ķ���͵��� Ÿ���� <tt>Type</tt>������ ����
	 */
	protected ArrayList<Type> paramType;

	public FunctionInfo(FunctionDeclaration declaration) {
		type = declaration.getType();
		name = declaration.getName();
		paramType = new ArrayList<>();

		for (ParamDeclaration param : declaration.getParams()) {
			paramType.add(param.getType());
		}
	}


	/**
	 * 이 객체가 가리키는 함수의 반환형을 <tt>Type</tt>형으로 반환한다.
	 *
	 * @return 이 객체가 가리키는 함수의 반환형
	 */
	public Type getType() {
		return type;
	}

	@Override
	public boolean equals(Object obj) {
		if (!(obj instanceof FunctionInfo)) return false;

		FunctionInfo tmp = (FunctionInfo) obj;

		return name.equals(tmp.name) && paramType.equals(tmp.paramType);
	}

	@Override
	public String toString() {
		StringBuilder result = new StringBuilder("");

		result.append(type).append(" ").append(name).append(" ");
		for (Type type : paramType) {
			result.append(type).append(" ");
		}

		return result.toString();
	}
}
