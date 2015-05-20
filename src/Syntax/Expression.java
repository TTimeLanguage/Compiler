package Syntax;

import java.util.HashMap;

/**
 * Abstract Syntax :
 * Expression = VariableRef | Value | Binary | Unary | Function
 */
public abstract class Expression extends Statement {
	abstract Type typeOf(HashMap<String, Init> declarationMap);
}
