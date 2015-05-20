package Syntax;

import Syntax.Init;
import Syntax.Statement;
import Syntax.Type;

import java.util.HashMap;

/**
 * Abstract Syntax :
 * Syntax.Expression = Syntax.VariableRef | Syntax.Value | Syntax.Binary | Syntax.Unary | Syntax.Function
 */
public abstract class Expression extends Statement {
	abstract Type typeOf(HashMap<String, Init> declarationMap);
}
