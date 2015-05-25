package Syntax;

import CodeGenerator.CodeGenerator;
import CodeGenerator.SymbolTableElement;
import Semantic.FunctionInfo;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedHashMap;

/**
 * 함수를 정의하는 구문
 * <p>
 * Abstract Syntax :
 * FunctionDeclaration = Type; String id; ParamDeclaration*; Statements
 */
public class FunctionDeclaration extends Global {
	/**
	 * 함수의 return 타입을 저장하는 변수
	 */
	protected final Type type;
	/**
	 * 함수의 이름을 저장하는 변수
	 */
	protected final String name;
	/**
	 * 함수의 전달인자를 저장하는 <tt>ParamDeclaration</tt>객체의 <tt>ArrayList</tt>
	 *
	 * @see ParamDeclaration
	 */
	protected final ArrayList<ParamDeclaration> params;
	/**
	 * 함수내의 실행 부분을 나타내는 구문
	 */
	protected final Statements statements;
	/**
	 * 이 함수의 매개변수의 맵
	 * <p>
	 * 전달인자의 중복을 확인하기 위해서 <tt>LinkedHashMap</tt>형식으로 저장.
	 * <p>
	 * 이 객체가 paser에 의해 생성될때는 비어있지만 type checker가 실행될 때 map을 채운다.
	 */
	protected final LinkedHashMap<String, Init> paramMap = new LinkedHashMap<>();
	protected FunctionInfo information;


	public FunctionDeclaration(Type t, String name, ArrayList<ParamDeclaration> p, Statements s) {
		type = t;
		this.name = name;
		params = p;
		statements = s;
	}

	public Type getType() {
		return type;
	}

	public String getName() {
		return name;
	}

	public ArrayList<ParamDeclaration> getParams() {
		return params;
	}

	protected void setInfo(FunctionInfo info) {
		information = info;
	}

	/**
	 * type checking시간에 호출됨.
	 * <p>
	 * <tt>ArrayList</tt>객체인 params를 보고 <tt>HashMap</tt>객체의 paramMap을 채운다.
	 */
	protected void mapParams() {
		for (ParamDeclaration param : params) {

			check(!paramMap.containsKey(param.name),
					"duplicated declaration :" + param.name);

			paramMap.put(param.name, param);
		}

		statements.mapVariable();
	}

	@Override
	void display(int lev) {
		for (int i = 0; i < lev; i++) {
			System.out.print("\t");
		}

		System.out.println("FunctionDeclaration " + type + " " + name);
		for (ParamDeclaration declaration : params) {
			declaration.display(lev + 1);
		}
		statements.display(lev + 1);
	}

	@Override
	protected void V(HashMap<String, Init> declarationMap) {
		// todo
		if (valid) return;

		HashMap<String, Init> localMap = new HashMap<>(declarationMap);
		int globalLength = localMap.size();
		int localLength = paramMap.size();
		localMap.putAll(paramMap);

		check(globalLength + localLength == localMap.size(),
				"duplicated declaration in function :" + type + " " + name);

		statements.V(localMap, type);

		valid = true;
	}

	@Override
	public void genCode() {
		LinkedHashMap<String, SymbolTableElement> localMap = new LinkedHashMap<>();
		int localVariableSize = 0;

		for (ParamDeclaration param : params) {
			localVariableSize += param.sizeOf();
		}
		localVariableSize += statements.variableSize();

		String realName = name + overloadMap.get(name).indexOf(information);

		CodeGenerator.proc(realName, localVariableSize);


		for (ParamDeclaration param : params) {
			param.genCode();
		}

		statements.genCode();

		CodeGenerator.end();
	}
}