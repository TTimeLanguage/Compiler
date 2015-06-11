package Syntax;

import CodeGenerator.CodeGenerator;

import java.util.Calendar;
import java.util.GregorianCalendar;
import java.util.HashMap;
import java.util.concurrent.TimeUnit;

/**
 * date 값을 나타내는 구문
 * <p>
 * Abstract Syntax :
 * DateValue = String year, month, day
 */
public class DateValue extends Value {
	/**
	 * 년, 월, 일을 나타내는 변수
	 */
	protected final int year, month, day;

	protected final static long base = new GregorianCalendar(1, 0, 3).getTimeInMillis() * -1;

	protected Calendar calendar;


	public DateValue(String y, String m, String d) {
		type = Type.DATE;
		year = Integer.parseInt(y);
		month = Integer.parseInt(m);
		day = Integer.parseInt(d);
	}

	@Override
	public int hashCode() {
		return Integer.hashCode(year * 10000 + month * 100 + day);
	}

	@Override
	public boolean equals(Object obj) {
		return obj instanceof DateValue && obj.hashCode() == this.hashCode();
	}

	@Override
	public String toString() {
		return "" + year + "/" + month + "/" + day;
	}

	@Override
	void display(int lev) {
		for (int i = 0; i < lev; i++) {
			System.out.print("\t");
		}

		System.out.println("DateValue " + year + "." + month + "." + day);
	}

	@Override
	protected void V(HashMap<String, Init> declarationMap) {
		// todo
		if (valid) return;

		check(year >= 0, "year can not be negative value");
		check(month >= 0, "month can not be negative value");
		check(day >= 0, "day can not be negative value");

		calendar = new GregorianCalendar(year, month - 1, day);

		int rightYear = calendar.get(Calendar.YEAR);
		int rightMonth = calendar.get(Calendar.MONTH);
		int rightDay = calendar.get(Calendar.DATE);

		check(rightYear == year && rightMonth + 1 == month && rightDay == day,
				"wrong date type declaration. should be : " + rightYear + "/" + rightMonth + "/" + rightDay);

		valid = true;
	}

	@Override
	public void genCode() {
		CodeGenerator.ldc(getDays());
	}

	private int getDays() {
		return (int) TimeUnit.MILLISECONDS.toDays(base + calendar.getTimeInMillis());
	}
}
