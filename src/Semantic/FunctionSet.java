package Semantic;

import Syntax.Type;

import java.util.ArrayList;


/**
 * 함수 정보를 담는 <tt>FunctionInformation</tt>형 객체들의 container이다.
 * Type checking때 함수가 올바르게 정의 되었는지 확인을 위해 쓰인다.
 *
 * @see Syntax.Program
 */
public class FunctionSet {
	/**
	 * 함수정보인 <tt>FunctionInformation</tt>를 담고있는 <tt>ArrayList</tt>
	 */
	private ArrayList<FunctionInformation> array = new ArrayList<>();

	/**
	 * 인자인 <tt>infoToCheck</tt>이 이 set에 있는지 여부를 반환.
	 *
	 * @param infoToCheck 포함 여부를 확인할 <tt>FunctionInformation</tt>객체
	 * @return set에 포함 되어있는지 여부
	 */
	public boolean contains(FunctionInformation infoToCheck) {
		for (FunctionInformation information : array) {
			if (information.equals(infoToCheck)) return true;
		}

		return false;
	}

	/**
	 * set에 <tt>functionInformation</tt>객체를 추가.
	 * <p>
	 * 만악 이미 있다면 추가하지 않음 (set의 특징).
	 *
	 * @param functionInformation set에 추가할 <tt>FunctionInformation</tt>객체
	 */
	public void add(FunctionInformation functionInformation) {
		if (contains(functionInformation)) return;

		array.add(functionInformation);
	}

	/**
	 * 찾는 함수의 이름이 같고, 매개변수의 형들이 일치하는 함수의 Type을 반환한다.
	 *
	 * @param name      찾을 함수의 이름
	 * @param paramType 찾을 함수의 매개변수 형들
	 * @return 찾는 함수의 반환형
	 */
	public Type getFunctionType(String name, ArrayList<Type> paramType) {
		for (FunctionInformation function : array) {
			if (function.name.equals(name) && function.paramType.equals(paramType)) {
				return function.type;
			}
		}

		System.err.println("Compiler error in FunctionSet. can not find such function");
		System.exit(1);
		return null;
	}

	@Override
	public String toString() {
		StringBuilder result = new StringBuilder("");

		for (FunctionInformation information : array) {
			result.append(information.toString()).append("\n");
		}

		return result.toString();
	}
}
