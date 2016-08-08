package Syntax;

import CodeGenerator.CodeGenerator;

import java.util.HashMap;

/**
 * time 값을 나타내는 구문
 * <p>
 * Abstract Syntax :
 * TimeValue = String hour, minute, second
 */
public class TimeValue extends Value {
	/**
	 * 시간, 분, 초 를 저장하는 변수
	 */
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
		if (valid) return;

		check(hour >= 0 && hour < 24, "hour value must be between 0 and 23");
		check(minute >= 0 && minute < 60, "minute value must be between 0 and 59");
		check(second >= 0 && second < 60, "second value must be between 0 and 59");

		valid = true;
	}

	@Override
	public void genCode() {
		CodeGenerator.ldc(hour * 3600 + minute * 60 + second);
	}
}
