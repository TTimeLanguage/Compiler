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

/**
 * Abstract Syntax :
 * Syntax.IntValue = int
 */
class IntValue extends Value {
	protected final int value;

	IntValue(int v) {
		type = Type.INT;
		value = v;
	}

	int intValue() {
		return value;
	}

	public String toString() {
		return "" + value;
	}

	@Override
	void display(int k) {
		for (int w = 0; w < k; w++) {
			System.out.print("\t");
		}

		System.out.println("Syntax.IntValue " + value);
	}

	@Override
	protected void V(HashMap<String, Init> declarationMap) {
	}
}


/**
 * Abstract Syntax :
 * Syntax.BoolValue = bool
 */
class BoolValue extends Value {
	protected final boolean value;

	BoolValue(boolean v) {
		type = Type.BOOL;
		value = v;
	}

	boolean boolValue() {
		return value;
	}

	int intValue() {
		return value ? 1 : 0;
	}

	public String toString() {
		return "" + value;
	}

	@Override
	void display(int k) {
		for (int w = 0; w < k; w++) {
			System.out.print("\t");
		}

		System.out.println("Syntax.BoolValue " + value);
	}

	@Override
	protected void V(HashMap<String, Init> declarationMap) {
	}
}


/**
 * Abstract Syntax :
 * Syntax.CharValue = String
 */
class CharValue extends Value {
	protected final char value;

	CharValue(char v) {
		type = Type.CHAR;
		value = v;
	}

	char charValue() {
		return value;
	}

	public String toString() {
		return "" + value;
	}

	@Override
	void display(int k) {
		for (int w = 0; w < k; w++) {
			System.out.print("\t");
		}

		System.out.println("Syntax.CharValue " + value);
	}

	@Override
	protected void V(HashMap<String, Init> declarationMap) {
	}
}


/**
 * Abstract Syntax :
 * Syntax.FloatValue = float
 */
class FloatValue extends Value {
	protected final float value;

	FloatValue(float v) {
		type = Type.FLOAT;
		value = v;
	}

	float floatValue() {
		return value;
	}

	public String toString() {
		return "" + value;
	}

	@Override
	void display(int k) {
		for (int w = 0; w < k; w++) {
			System.out.print("\t");
		}

		System.out.println("Syntax.FloatValue " + value);
	}

	@Override
	protected void V(HashMap<String, Init> declarationMap) {
	}
}


/**
 * Abstract Syntax :
 * Syntax.DateValue = String year, month, day
 */
class DateValue extends Value {
	protected final int year, month, day;

	DateValue(String y, String m, String d) {
		type = Type.DATE;
		year = Integer.parseInt(y);
		month = Integer.parseInt(m);
		day = Integer.parseInt(d);
	}

	public String toString() {
		return "" + year + "/" + month + "/" + day;
	}

	@Override
	void display(int k) {
		for (int w = 0; w < k; w++) {
			System.out.print("\t");
		}

		System.out.println("Syntax.DateValue " + year + "." + month + "." + day);
	}

	@Override
	protected void V(HashMap<String, Init> declarationMap) {
		// todo
		check(year >= 0, "year can not be negative value");
		check(month >= 1 && month <= 12, "month value must be between 1 and 12");
		check(month >= 1 && month <= 12, "month value must be between 1 and 12");
	}
}

/**
 * Abstract Syntax :
 * Syntax.TimeValue = String hour, minute, second
 */
class TimeValue extends Value {
	protected final int hour, minute, second;

	TimeValue(String h, String m, String s) {
		type = Type.TIME;
		hour = Integer.parseInt(h);
		minute = Integer.parseInt(m);
		second = Integer.parseInt(s);
	}

	public String toString() {
		return "" + hour + "/" + minute + "/" + second;
	}

	@Override
	void display(int k) {
		for (int w = 0; w < k; w++) {
			System.out.print("\t");
		}

		System.out.println("Syntax.TimeValue " + hour + ":" + minute + ":" + second);
	}

	@Override
	protected void V(HashMap<String, Init> declarationMap) {
		check(hour >= 0 && hour < 24, "hour value must be between 0 and 23");
		check(minute >= 0 && minute < 60, "minute value must be between 0 and 59");
		check(second >= 0 && second < 60, "second value must be between 0 and 59");
	}
}