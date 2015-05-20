package Syntax;

import java.util.HashMap;

/**
 * Abstract Syntax :
 * TimeValue = String hour, minute, second
 */
public class TimeValue extends Value {
	protected final int hour, minute, second;

	public TimeValue(String h, String m, String s) {
		type = Type.TIME;
		hour = Integer.parseInt(h);
		minute = Integer.parseInt(m);
		second = Integer.parseInt(s);
	}

	public String toString() {
		return "" + hour + "/" + minute + "/" + second;
	}

	@Override
	void display(int lev) {
		for (int i = 0; i < lev; i++) {
			System.out.print("\t");
		}

		System.out.println("TimeValue " + hour + ":" + minute + ":" + second);
	}

	@Override
	protected void V(HashMap<String, Init> declarationMap) {
		check(hour >= 0 && hour < 24, "hour value must be between 0 and 23");
		check(minute >= 0 && minute < 60, "minute value must be between 0 and 59");
		check(second >= 0 && second < 60, "second value must be between 0 and 59");
	}
}
