package Syntax;

/**
 * Abstract Syntax :
 * Syntax.Operator = BooleanOp | RelationalOp | ArithmeticOp | UnaryOp | AssignOp
 */
public class Operator {
	// BooleanOp = && | ||
	final static String AND = "&&";
	final static String OR = "||";
	// RelationalOp = < | <= | == | != | >= | >
	final static String LT = "<";
	final static String LE = "<=";
	final static String EQ = "==";
	final static String NE = "!=";
	final static String GT = ">";
	final static String GE = ">=";
	// ArithmeticOp = + | - | * | /
	final static String PLUS = "+";
	final static String MINUS = "-";
	final static String TIMES = "*";
	final static String DIV = "/";
	final static String MOD = "%";
	// UnaryOp = !
	final static String NOT = "!";
	final static String NEG = "-";
	// CastOp = int | float | char
	final static String INT = "int";
	final static String FLOAT = "float";
	final static String CHAR = "char";
	// AssignOp
	final static String PLUSASSIGN = "+=";
	final static String MINUSASSIGN = "-=";
	final static String TIMESASSIGN = "*=";
	final static String DIVASSIGN = "/=";
	final static String MODASSIGN = "%=";
	final static String ASSIGN = "=";
	// DoubleOp
	final static String PLUSPLUS = "++";
	final static String MINUSMINUS = "--";

	// Typed Operators
	// RelationalOp = < | <= | == | != | >= | >
	final static String INT_LT = "INT<";
	final static String INT_LE = "INT<=";
	final static String INT_EQ = "INT==";
	final static String INT_NE = "INT!=";
	final static String INT_GT = "INT>";
	final static String INT_GE = "INT>=";
	// ArithmeticOp = + | - | * | / | % | ++ | --
	final static String INT_PLUS = "INT+";
	final static String INT_MINUS = "INT-";
	final static String INT_TIMES = "INT*";
	final static String INT_DIV = "INT/";
	final static String INT_MOD = "INT%";
	final static String INT_PP = "INT++";
	final static String INT_MM = "INT--";
	// UnaryOp = -
	final static String INT_NEG = "-";
	// AssignOp = += | -= | *= | /= | %=
	final static String INT_PLUS_ASSIGN = "INT+=";
	final static String INT_MINUS_ASSIGN = "INT-=";
	final static String INT_TIMES_ASSIGN = "INT*=";
	final static String INT_DIV_ASSIGN = "INT/=";
	final static String INT_MOD_ASSIGN = "INT%=";

	// RelationalOp = < | <= | == | != | >= | > |
	final static String FLOAT_LT = "FLOAT<";
	final static String FLOAT_LE = "FLOAT<=";
	final static String FLOAT_EQ = "FLOAT==";
	final static String FLOAT_NE = "FLOAT!=";
	final static String FLOAT_GT = "FLOAT>";
	final static String FLOAT_GE = "FLOAT>=";
	// ArithmeticOp = + | - | * | / | % | ++ | -
	final static String FLOAT_PLUS = "FLOAT+";
	final static String FLOAT_MINUS = "FLOAT-";
	final static String FLOAT_TIMES = "FLOAT*";
	final static String FLOAT_DIV = "FLOAT/";
	final static String FLOAT_MOD = "FLOAT%";
	final static String FLOAT_PP = "FLOAT++";
	final static String FLOAT_MM = "FLOAT--";
	// UnaryOp = -
	final static String FLOAT_NEG = "-";
	// AssignOp = += | -= | *= | /= | %=
	final static String FLOAT_PLUS_ASSIGN = "FLOAT+=";
	final static String FLOAT_MINUS_ASSIGN = "FLOAT-=";
	final static String FLOAT_TIMES_ASSIGN = "FLOAT*=";
	final static String FLOAT_DIV_ASSIGN = "FLOAT/=";
	final static String FLOAT_MOD_ASSIGN = "FLOAT%=";

	// RelationalOp = < | <= | == | != | >= | > |
	final static String TIME_LT = "TIME<";
	final static String TIME_LE = "TIME<=";
	final static String TIME_EQ = "TIME==";
	final static String TIME_NE = "TIME!=";
	final static String TIME_GT = "TIME>";
	final static String TIME_GE = "TIME>=";
	// ArithmeticOp = + | - | * | / | % | ++ | -
	final static String TIME_PLUS = "TIME+";
	final static String TIME_MINUS = "TIME-";
	final static String TIME_TIMES = "TIME*";
	final static String TIME_DIV = "TIME/";
	final static String TIME_MOD = "TIME%";
	final static String TIME_PP = "TIME++";
	final static String TIME_MM = "TIME--";
	// AssignOp = += | -= | *= | /= | %=
	final static String TIME_PLUS_ASSIGN = "TIME+=";
	final static String TIME_MINUS_ASSIGN = "TIME-=";
	final static String TIME_TIMES_ASSIGN = "TIME*=";
	final static String TIME_DIV_ASSIGN = "TIME/=";
	final static String TIME_MOD_ASSIGN = "TIME%=";

	// RelationalOp = < | <= | == | != | >= | > |
	final static String DATE_LT = "DATE<";
	final static String DATE_LE = "DATE<=";
	final static String DATE_EQ = "DATE==";
	final static String DATE_NE = "DATE!=";
	final static String DATE_GT = "DATE>";
	final static String DATE_GE = "DATE>=";
	// ArithmeticOp = + | - | * | / | % | ++ | -
	final static String DATE_PLUS = "DATE+";
	final static String DATE_MINUS = "DATE-";
	final static String DATE_TIMES = "DATE*";
	final static String DATE_DIV = "DATE/";
	final static String DATE_MOD = "DATE%";
	final static String DATE_PP = "DATE++";
	final static String DATE_MM = "DATE--";
	// AssignOp = += | -= | *= | /= | %=
	final static String DATE_PLUS_ASSIGN = "DATE+=";
	final static String DATE_MINUS_ASSIGN = "DATE-=";
	final static String DATE_TIMES_ASSIGN = "DATE*=";
	final static String DATE_DIV_ASSIGN = "DATE/=";
	final static String DATE_MOD_ASSIGN = "DATE%=";

	// RelationalOp = < | <= | == | != | >= | >
	final static String CHAR_LT = "CHAR<";
	final static String CHAR_LE = "CHAR<=";
	final static String CHAR_EQ = "CHAR==";
	final static String CHAR_NE = "CHAR!=";
	final static String CHAR_GT = "CHAR>";
	final static String CHAR_GE = "CHAR>=";

	// RelationalOp = < | <= | == | != | >= | >
	final static String BOOL_LT = "BOOL<";
	final static String BOOL_LE = "BOOL<=";
	final static String BOOL_EQ = "BOOL==";
	final static String BOOL_NE = "BOOL!=";
	final static String BOOL_GT = "BOOL>";
	final static String BOOL_GE = "BOOL>=";
	// UnaryOp = !
	final static String BOOL_COMP = "!";

	// Syntax.Type specific cast
	final static String I2F = "I2F";
	final static String F2I = "F2I";
	final static String C2I = "C2I";
	final static String I2C = "I2C";

	protected final String value;

	public Operator(String s) {
		value = s;
	}

	public String toString() {
		return value;
	}

	public boolean equals(Object obj) {
		return obj instanceof Operator && value.equals(obj);
	}

	boolean BooleanOp() {
		return value.equals(AND) || value.equals(OR);
	}

	boolean RelationalOp() {
		return value.equals(LT) || value.equals(LE) || value.equals(EQ)
				|| value.equals(NE) || value.equals(GT) || value.equals(GE);
	}

	boolean ArithmeticOp() {
		return value.equals(PLUS) || value.equals(MINUS) || value.equals(TIMES)
				|| value.equals(DIV) || value.equals(MOD);
	}

	boolean AssignOP() {
		return value.equals(PLUSASSIGN) || value.equals(MINUSASSIGN) || value.equals(TIMESASSIGN)
				|| value.equals(MODASSIGN) || value.equals(DIVASSIGN) || value.equals(ASSIGN);
	}

	boolean NotOp() {
		return value.equals(NOT);
	}

	boolean NegateOp() {
		return value.equals(NEG);
	}

	boolean intOp() {
		return value.equals(INT);
	}

	boolean floatOp() {
		return value.equals(FLOAT);
	}

	boolean charOp() {
		return value.equals(CHAR);
	}

	boolean incOp() {
		return value.equals(PLUSPLUS) || value.equals(MINUSMINUS);
	}

	final static String intMap[][] = {
			{PLUS, INT_PLUS}, {MINUS, INT_MINUS}, {TIMES, INT_TIMES}, {DIV, INT_DIV}, {MOD, INT_MOD},
			{PLUSPLUS, INT_PP}, {MINUSMINUS, INT_MM},
			{PLUSASSIGN, INT_PLUS_ASSIGN}, {MINUSASSIGN, INT_MINUS_ASSIGN}, {TIMESASSIGN, INT_TIMES_ASSIGN},
			{DIVASSIGN, INT_DIV_ASSIGN}, {MODASSIGN, INT_MOD_ASSIGN},
			{EQ, INT_EQ}, {NE, INT_NE}, {LT, INT_LT}, {LE, INT_LE}, {GT, INT_GT}, {GE, INT_GE},
			{NEG, INT_NEG}, {FLOAT, I2F}, {CHAR, I2C}
	};

	final static String floatMap[][] = {
			{PLUS, FLOAT_PLUS}, {MINUS, FLOAT_MINUS}, {TIMES, FLOAT_TIMES}, {DIV, FLOAT_DIV}, {MOD, FLOAT_MOD},
			{PLUSPLUS, FLOAT_PP}, {MINUSMINUS, FLOAT_MM},
			{PLUSASSIGN, FLOAT_PLUS_ASSIGN}, {MINUSASSIGN, FLOAT_MINUS_ASSIGN}, {TIMESASSIGN, FLOAT_TIMES_ASSIGN},
			{DIVASSIGN, FLOAT_DIV_ASSIGN}, {MODASSIGN, FLOAT_MOD_ASSIGN},
			{EQ, FLOAT_EQ}, {NE, FLOAT_NE}, {LT, FLOAT_LT}, {LE, FLOAT_LE}, {GT, FLOAT_GT}, {GE, FLOAT_GE},
			{NEG, FLOAT_NEG}, {INT, F2I}
	};

	final static String charMap[][] = {
			{EQ, CHAR_EQ}, {NE, CHAR_NE}, {LT, CHAR_LT}, {LE, CHAR_LE}, {GT, CHAR_GT}, {GE, CHAR_GE},
			{INT, C2I}
	};

	final static String boolMap[][] = {
			{EQ, BOOL_EQ}, {NE, BOOL_NE}, {LT, BOOL_LT}, {LE, BOOL_LE}, {GT, BOOL_GT}, {GE, BOOL_GE},
			{NOT, BOOL_COMP}
	};

	final static String timeMap[][] = {
			{PLUS, TIME_PLUS}, {MINUS, TIME_MINUS}, {TIMES, TIME_TIMES}, {DIV, TIME_DIV}, {MOD, TIME_MOD},
			{PLUSPLUS, TIME_PP}, {MINUSMINUS, TIME_MM},
			{PLUSASSIGN, TIME_PLUS_ASSIGN}, {MINUSASSIGN, TIME_MINUS_ASSIGN}, {TIMESASSIGN, TIME_TIMES_ASSIGN},
			{DIVASSIGN, TIME_DIV_ASSIGN}, {MODASSIGN, TIME_MOD_ASSIGN},
			{EQ, TIME_EQ}, {NE, TIME_NE}, {LT, TIME_LT}, {LE, TIME_LE}, {GT, TIME_GT}, {GE, TIME_GE}
	};

	final static String dateMap[][] = {
			{PLUS, DATE_PLUS}, {MINUS, DATE_MINUS}, {TIMES, DATE_TIMES}, {DIV, DATE_DIV}, {MOD, DATE_MOD},
			{PLUSPLUS, DATE_PP}, {MINUSMINUS, DATE_MM},
			{PLUSASSIGN, DATE_PLUS_ASSIGN}, {MINUSASSIGN, DATE_MINUS_ASSIGN}, {TIMESASSIGN, DATE_TIMES_ASSIGN},
			{DIVASSIGN, DATE_DIV_ASSIGN}, {MODASSIGN, DATE_MOD_ASSIGN},
			{EQ, DATE_EQ}, {NE, DATE_NE}, {LT, DATE_LT}, {LE, DATE_LE}, {GT, DATE_GT}, {GE, DATE_GE}
	};

	static private Operator map(String[][] tmap, String op) {
		for (String[] aTmap : tmap) {
			if (aTmap[0].equals(op)) {
				return new Operator(aTmap[1]);
			}
		}
		assert false : "should never reach here";
		return null;
	}

	static public Operator intMap(String op) {
		return map(intMap, op);
	}

	static public Operator floatMap(String op) {
		return map(floatMap, op);
	}

	static public Operator charMap(String op) {
		return map(charMap, op);
	}

	static public Operator boolMap(String op) {
		return map(boolMap, op);
	}

	static public Operator timeMap(String op) {
		return map(timeMap, op);
	}

	static public Operator dateMap(String op) {
		return map(dateMap, op);
	}

	void display(int k) {
		for (int w = 0; w < k; w++) {
			System.out.print("\t");
		}

		System.out.println("Syntax.Operator " + value);
	}

}
