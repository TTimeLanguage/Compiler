package Syntax;

import java.util.HashMap;

/**
 * Abstract Syntax :
 * Syntax.DateValue = String year, month, day
 */
public class DateValue extends Value {
	protected final int year, month, day;

	public DateValue(String y, String m, String d) {
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
