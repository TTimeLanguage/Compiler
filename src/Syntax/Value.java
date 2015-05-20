package Syntax;

import java.util.HashMap;

/**
 * Abstract Syntax :
 * Value = IntValue | BoolValue | FloatValue | CharValue | TimeValue | DateValue
 */
public abstract class Value extends Expression {
	protected Type type;

	@Override
	Type typeOf(HashMap<String, Init> declarationMap) {
		return type;
	}
}


