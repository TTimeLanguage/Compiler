package Semantic;

import Syntax.FunctionDeclaration;
import Syntax.ParamDeclaration;
import Syntax.Type;

import java.util.ArrayList;

/**
 * 함수 하나의 완전한 정보(함수의 반환 타입, 이름, 파라미터들의 타입)를 저장함.
 * <p>
 * 함수 정의 타당성 확인을 위해 사용됨.
 *
 * @see	Parser.Parser
 * @see FunctionSet
 */
public class FunctionInformation {
	/**
	 * 함수의 반환 타입
	 */
	protected Type type;
	/**
	 * 함수의 이름
	 */
	protected String name;
	/**
	 * 파라미터들의 타입을 <tt>Type</tt>형으로 저장
	 */
	protected ArrayList<Type> paramType;

	public FunctionInformation(FunctionDeclaration declaration) {
		type = declaration.getType();
		name = declaration.getName();
		paramType = new ArrayList<>();

		for (ParamDeclaration param : declaration.getParams()) {
			paramType.add(param.getType());
		}
	}

	@Override
	public boolean equals(Object obj) {
		if (!(obj instanceof FunctionInformation)) return false;

		FunctionInformation tmp = (FunctionInformation) obj;

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
