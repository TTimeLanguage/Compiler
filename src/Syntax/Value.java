package Syntax;

import java.util.HashMap;

/**
 * Abstract Syntax :
 * Value = IntValue | BoolValue | FloatValue | CharValue | TimeValue | DateValue
 */
public abstract class Value extends Expression {
	protected Type type;

	@Override
	protected void V(HashMap<String, Init> declarationMap) {
		valid = true;
	}

	@Override
	Type typeOf(HashMap<String, Init> declarationMap) {
		check(valid, "Compiler error. must check validation");

		return type;
	}
}


