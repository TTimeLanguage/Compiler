package Syntax;

import java.util.HashMap;

/**
 * 변수가 아닌 리터럴을 나타내는 구문
 *
 * Abstract Syntax :
 * Value = IntValue | BoolValue | FloatValue | CharValue | TimeValue | DateValue
 */
public abstract class Value extends Expression {
	/**
	 * 리터럴의 타입
	 */
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


