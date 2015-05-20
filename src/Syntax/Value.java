package Syntax;

import java.util.HashMap;

/**
 * Abstract Syntax :
 * Syntax.Value = Syntax.IntValue | Syntax.BoolValue | Syntax.FloatValue | Syntax.CharValue | Syntax.TimeValue | Syntax.DateValue
 */
public abstract class Value extends Expression {
	protected Type type;

	@Override
	Type typeOf(HashMap<String, Init> declarationMap) {
		return type;
	}
}


