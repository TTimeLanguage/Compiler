package Semantic;

import Syntax.Type;

import java.util.ArrayList;


public class FunctionSet {
	private ArrayList<FunctionInformation> array = new ArrayList<>();

	public boolean contains(FunctionInformation tmp) {
		for (FunctionInformation information : array) {
			if (information.equals(tmp)) return true;
		}

		return false;
	}

	public boolean add(FunctionInformation functionInformation) {
		if (contains(functionInformation)) return false;

		array.add(functionInformation);

		return true;
	}

	public Type getFunctionType(String name, ArrayList<Type> paramType) {
		for (FunctionInformation function : array) {
			if (function.name.equals(name) && function.paramType.equals(paramType)) {
				return function.type;
			}
		}

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
