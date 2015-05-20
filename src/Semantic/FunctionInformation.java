package Semantic;

import Syntax.FunctionDeclaration;
import Syntax.ParamDeclaration;
import Syntax.Type;

import java.util.ArrayList;

public class FunctionInformation {
	protected Type type;
	protected String name;
	protected ArrayList<Type> paramType;

	public FunctionInformation(Type type, String name, ArrayList<Type> paramType) {
		this.type = type;
		this.name = name;
		this.paramType = paramType;
	}

	public FunctionInformation(FunctionDeclaration declaration) {
		type = declaration.getType();
		name = declaration.getName();
		for (ParamDeclaration param : declaration.getParams()) {
			paramType.add(param.getType());
		}
	}
}
