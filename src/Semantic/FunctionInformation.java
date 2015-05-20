package Semantic;

import Syntax.FunctionDeclaration;
import Syntax.ParamDeclaration;
import Syntax.Type;

import java.util.ArrayList;

public class FunctionInformation {
	protected Type type;
	protected String name;
	protected ArrayList<Type> paramType = new ArrayList<>();

	public FunctionInformation(FunctionDeclaration declaration) {
		type = declaration.getType();
		name = declaration.getName();
		for (ParamDeclaration param : declaration.getParams()) {
			paramType.add(param.getType());
		}
	}

	@Override
	public boolean equals(Object obj) {
		if (!(obj instanceof FunctionInformation)) return false;

		FunctionInformation tmp = (FunctionInformation) obj;

		return type.equals(tmp.type) && name.equals(tmp.name) && paramType.equals(tmp.paramType);
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
