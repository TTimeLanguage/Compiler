package Syntax;

/**
 * 연산자를 나타내는 구문
 * <p>
 * 연산자를 <tt>String</tt>객체로 나타낸다.
 * <p>
 * Abstract Syntax :
 * Operator = BooleanOp | RelationalOp | ArithmeticOp | UnaryOp | AssignOp
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
	final static String PLUSPLUS = "++";
	final static String MINUSMINUS = "--";
	// AssignOp
	final static String PLUSASSIGN = "+=";
	final static String MINUSASSIGN = "-=";
	final static String TIMESASSIGN = "*=";
	final static String DIVASSIGN = "/=";
	final static String MODASSIGN = "%=";
	final static String ASSIGN = "=";

	// Typed Operators
	// RelationalOp = < | <= | == | != | >= | >
	final static String INT_LT = "INT<";
	final static String INT_LE = "INT<=";
	final static String INT_EQ = "INT==";
	final static String INT_NE = "INT!=";
	final static String INT_GT = "INT>";
	final static String INT_GE = "INT>=";
	// ArithmeticOp = + | - | * | / | %
	final static String INT_PLUS = "INT+";
	final static String INT_MINUS = "INT-";
	final static String INT_TIMES = "INT*";
	final static String INT_DIV = "INT/";
	final static String INT_MOD = "INT%";
	// UnaryOp = - | ++ | --
	final static String INT_NEG = "-";
	final static String INT_PP = "INT++";
	final static String INT_MM = "INT--";
	// AssignOp = += | -= | *= | /= | %= | =
	final static String INT_PLUS_ASSIGN = "INT+=";
	final static String INT_MINUS_ASSIGN = "INT-=";
	final static String INT_TIMES_ASSIGN = "INT*=";
	final static String INT_DIV_ASSIGN = "INT/=";
	final static String INT_MOD_ASSIGN = "INT%=";
	final static String INT_ASSIGN = "INT=";

	// RelationalOp = < | <= | == | != | >= | > |
	final static String FLOAT_LT = "FLOAT<";
	final static String FLOAT_LE = "FLOAT<=";
	final static String FLOAT_EQ = "FLOAT==";
	final static String FLOAT_NE = "FLOAT!=";
	final static String FLOAT_GT = "FLOAT>";
	final static String FLOAT_GE = "FLOAT>=";
	// ArithmeticOp = + | - | * | / | %
	final static String FLOAT_PLUS = "FLOAT+";
	final static String FLOAT_MINUS = "FLOAT-";
	final static String FLOAT_TIMES = "FLOAT*";
	final static String FLOAT_DIV = "FLOAT/";
	final static String FLOAT_MOD = "FLOAT%";
	// UnaryOp = - | ++ | --
	final static String FLOAT_NEG = "-";
	final static String FLOAT_PP = "FLOAT++";
	final static String FLOAT_MM = "FLOAT--";
	// AssignOp = += | -= | *= | /= | %= | =
	final static String FLOAT_PLUS_ASSIGN = "FLOAT+=";
	final static String FLOAT_MINUS_ASSIGN = "FLOAT-=";
	final static String FLOAT_TIMES_ASSIGN = "FLOAT*=";
	final static String FLOAT_DIV_ASSIGN = "FLOAT/=";
	final static String FLOAT_MOD_ASSIGN = "FLOAT%=";
	final static String FLOAT_ASSIGN = "FLOAT=";

	// RelationalOp = < | <= | == | != | >= | > |
	final static String TIME_LT = "TIME<";
	final static String TIME_LE = "TIME<=";
	final static String TIME_EQ = "TIME==";
	final static String TIME_NE = "TIME!=";
	final static String TIME_GT = "TIME>";
	final static String TIME_GE = "TIME>=";
	// ArithmeticOp = + | - | * | / | %
	final static String TIME_PLUS = "TIME+";
	final static String TIME_MINUS = "TIME-";
	final static String TIME_TIMES = "TIME*";
	final static String TIME_DIV = "TIME/";
	final static String TIME_MOD = "TIME%";
	// UnaryOp = ++ | --
	final static String TIME_PP = "TIME++";
	final static String TIME_MM = "TIME--";
	// AssignOp = += | -= | *= | /= | %=
	final static String TIME_PLUS_ASSIGN = "TIME+=";
	final static String TIME_MINUS_ASSIGN = "TIME-=";
	final static String TIME_TIMES_ASSIGN = "TIME*=";
	final static String TIME_DIV_ASSIGN = "TIME/=";
	final static String TIME_MOD_ASSIGN = "TIME%=";
	final static String TIME_ASSIGN = "TIME=";

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
	// UnaryOp = ++ | --
	final static String DATE_PP = "DATE++";
	final static String DATE_MM = "DATE--";
	// AssignOp = += | -= | *= | /= | %=
	final static String DATE_PLUS_ASSIGN = "DATE+=";
	final static String DATE_MINUS_ASSIGN = "DATE-=";
	final static String DATE_TIMES_ASSIGN = "DATE*=";
	final static String DATE_DIV_ASSIGN = "DATE/=";
	final static String DATE_MOD_ASSIGN = "DATE%=";
	final static String DATE_ASSIGN = "DATE=";

	// RelationalOp = < | <= | == | != | >= | >
	final static String CHAR_LT = "CHAR<";
	final static String CHAR_LE = "CHAR<=";
	final static String CHAR_EQ = "CHAR==";
	final static String CHAR_NE = "CHAR!=";
	final static String CHAR_GT = "CHAR>";
	final static String CHAR_GE = "CHAR>=";
	// AssignOp = =
	final static String CHAR_ASSIGN = "CHAR=";

	// RelationalOp = < | <= | == | != | >= | >
	final static String BOOL_LT = "BOOL<";
	final static String BOOL_LE = "BOOL<=";
	final static String BOOL_EQ = "BOOL==";
	final static String BOOL_NE = "BOOL!=";
	final static String BOOL_GT = "BOOL>";
	final static String BOOL_GE = "BOOL>=";
	// UnaryOp = !
	final static String BOOL_COMP = "!";
	// AssignOp = =
	final static String BOOL_ASSIGN = "BOOL=";

	/**
	 * 연산자를 문자로 저장
	 */
	protected final String value;
	/**
	 * 연산자로 연산 후 결과의 type을 <tt>Type</tt>객체로 저장.
	 *
	 * @see Type
	 */
	protected Type type = null;

	public Operator(String s) {
		value = s;
	}

	public Operator(String s, Type type) {
		value = s;
		this.type = type;
	}

	public String toString() {
		return value;
	}

	public boolean equals(Object obj) {
		return obj instanceof Operator && value.equals(obj);
	}

	boolean isBooleanOp() {
		return value.equals(AND) || value.equals(OR);
	}

	boolean isRelationalOp() {
		return value.equals(LT) || value.equals(LE) || value.equals(EQ)
				|| value.equals(NE) || value.equals(GT) || value.equals(GE);
	}

	boolean isArithmeticOp() {
		return value.equals(PLUS) || value.equals(MINUS) || value.equals(TIMES)
				|| value.equals(DIV) || value.equals(MOD);
	}

	boolean isAssignOP() {
		return value.equals(PLUSASSIGN) || value.equals(MINUSASSIGN) || value.equals(TIMESASSIGN)
				|| value.equals(MODASSIGN) || value.equals(DIVASSIGN) || value.equals(ASSIGN);
	}

	boolean NotOp() {
		return value.equals(NOT);
	}

	boolean NegateOp() {
		return value.equals(NEG);
	}

	boolean incOp() {
		return value.equals(PLUSPLUS) || value.equals(MINUSMINUS);
	}

	/**
	 * 이 연산자의 연산 후 결과의 형을 <tt>Type</tt>객체로 반환
	 * <p>
	 * 만약 type변수가 null이라면 에러 출력
	 *
	 * @return 이 연산자의 연산 후 결과의 type
	 */
	protected Type getType() {
		if (type == null) {
			System.err.println("Compiler error. never reach here. in Operator. type is null");
			System.exit(1);
		}

		return type;
	}

	final static String intMap[][] = {
			{PLUS, INT_PLUS}, {MINUS, INT_MINUS}, {TIMES, INT_TIMES}, {DIV, INT_DIV}, {MOD, INT_MOD},
			{PLUSPLUS, INT_PP}, {MINUSMINUS, INT_MM},
			{PLUSASSIGN, INT_PLUS_ASSIGN}, {MINUSASSIGN, INT_MINUS_ASSIGN}, {TIMESASSIGN, INT_TIMES_ASSIGN},
			{DIVASSIGN, INT_DIV_ASSIGN}, {MODASSIGN, INT_MOD_ASSIGN}, {ASSIGN, INT_ASSIGN},
			{EQ, INT_EQ}, {NE, INT_NE}, {LT, INT_LT}, {LE, INT_LE}, {GT, INT_GT}, {GE, INT_GE},
			{NEG, INT_NEG}
	};

	final static String floatMap[][] = {
			{PLUS, FLOAT_PLUS}, {MINUS, FLOAT_MINUS}, {TIMES, FLOAT_TIMES}, {DIV, FLOAT_DIV}, {MOD, FLOAT_MOD},
			{PLUSPLUS, FLOAT_PP}, {MINUSMINUS, FLOAT_MM},
			{PLUSASSIGN, FLOAT_PLUS_ASSIGN}, {MINUSASSIGN, FLOAT_MINUS_ASSIGN}, {TIMESASSIGN, FLOAT_TIMES_ASSIGN},
			{DIVASSIGN, FLOAT_DIV_ASSIGN}, {MODASSIGN, FLOAT_MOD_ASSIGN}, {ASSIGN, FLOAT_ASSIGN},
			{EQ, FLOAT_EQ}, {NE, FLOAT_NE}, {LT, FLOAT_LT}, {LE, FLOAT_LE}, {GT, FLOAT_GT}, {GE, FLOAT_GE},
			{NEG, FLOAT_NEG}
	};

	final static String charMap[][] = {
			{ASSIGN, CHAR_ASSIGN},
			{EQ, CHAR_EQ}, {NE, CHAR_NE}, {LT, CHAR_LT}, {LE, CHAR_LE}, {GT, CHAR_GT}, {GE, CHAR_GE}
	};

	final static String boolMap[][] = {
			{ASSIGN, BOOL_ASSIGN},
			{EQ, BOOL_EQ}, {NE, BOOL_NE}, {LT, BOOL_LT}, {LE, BOOL_LE}, {GT, BOOL_GT}, {GE, BOOL_GE},
			{AND, AND}, {OR, OR},
			{NOT, BOOL_COMP}
	};

	final static String timeMap[][] = {
			{PLUS, TIME_PLUS}, {MINUS, TIME_MINUS}, {TIMES, TIME_TIMES}, {DIV, TIME_DIV}, {MOD, TIME_MOD},
			{PLUSPLUS, TIME_PP}, {MINUSMINUS, TIME_MM},
			{PLUSASSIGN, TIME_PLUS_ASSIGN}, {MINUSASSIGN, TIME_MINUS_ASSIGN}, {TIMESASSIGN, TIME_TIMES_ASSIGN},
			{DIVASSIGN, TIME_DIV_ASSIGN}, {MODASSIGN, TIME_MOD_ASSIGN}, {ASSIGN, TIME_ASSIGN},
			{EQ, TIME_EQ}, {NE, TIME_NE}, {LT, TIME_LT}, {LE, TIME_LE}, {GT, TIME_GT}, {GE, TIME_GE}
	};

	final static String dateMap[][] = {
			{PLUS, DATE_PLUS}, {MINUS, DATE_MINUS}, {TIMES, DATE_TIMES}, {DIV, DATE_DIV}, {MOD, DATE_MOD},
			{PLUSPLUS, DATE_PP}, {MINUSMINUS, DATE_MM},
			{PLUSASSIGN, DATE_PLUS_ASSIGN}, {MINUSASSIGN, DATE_MINUS_ASSIGN}, {TIMESASSIGN, DATE_TIMES_ASSIGN},
			{DIVASSIGN, DATE_DIV_ASSIGN}, {MODASSIGN, DATE_MOD_ASSIGN}, {ASSIGN, DATE_ASSIGN},
			{EQ, DATE_EQ}, {NE, DATE_NE}, {LT, DATE_LT}, {LE, DATE_LE}, {GT, DATE_GT}, {GE, DATE_GE}
	};

	/**
	 * 매개변수로 주어진 tmap에서 op을 찾아서 알맞은 type의 operator를 반환
	 * <p>
	 * 찾지못한경우 에러 출력
	 *
	 * @param tmap 주어진 map
	 * @param op   찾을 연산자
	 * @param type 연산자의 type
	 * @return 매개변수에 맞는 알맞은 연산자 <tt>Operator</tt>객체
	 */
	static private Operator map(String[][] tmap, String op, Type type) {
		for (String[] aTmap : tmap) {

			if (aTmap[0].equals(op)) {
				switch (op) {
					case PLUSASSIGN:
					case MINUSASSIGN:
					case TIMESASSIGN:
					case DIVASSIGN:
					case MODASSIGN:
					case ASSIGN:
						return new Operator(aTmap[1], Type.VOID);

					case EQ:
					case NE:
					case LT:
					case LE:
					case GT:
					case GE:
					case OR:
					case AND:
						return new Operator(aTmap[1], Type.BOOL);

					default:
						return new Operator(aTmap[1], type);
				}
			}
		}

		System.err.println("Compiler error. never reach here. in Operator. wrong operation");
		System.exit(1);
		return null;
	}

	/**
	 * 매개변수에 해당하는 연산자를 int형 연산자를 반환한다.
	 *
	 * @param op 변한될 매개변수
	 * @return 변환한 int형 매개변수
	 */
	static public Operator intMap(String op) {
		return map(intMap, op, Type.INT);
	}

	/**
	 * 매개변수에 해당하는 연산자를 float형 연산자를 반환한다.
	 *
	 * @param op 변한될 매개변수
	 * @return 변환한 float형 매개변수
	 */
	static public Operator floatMap(String op) {
		return map(floatMap, op, Type.FLOAT);
	}

	/**
	 * 매개변수에 해당하는 연산자를 char형 연산자를 반환한다.
	 *
	 * @param op 변한될 매개변수
	 * @return 변환한 char형 매개변수
	 */
	static public Operator charMap(String op) {
		return map(charMap, op, Type.CHAR);
	}

	/**
	 * 매개변수에 해당하는 연산자를 bool형 연산자를 반환한다.
	 *
	 * @param op 변한될 매개변수
	 * @return 변환한 bool형 매개변수
	 */
	static public Operator boolMap(String op) {
		return map(boolMap, op, Type.BOOL);
	}

	/**
	 * 매개변수에 해당하는 연산자를 time형 연산자를 반환한다.
	 *
	 * @param op 변한될 매개변수
	 * @return 변환한 time형 매개변수
	 */
	static public Operator timeMap(String op) {
		return map(timeMap, op, Type.TIME);
	}

	/**
	 * 매개변수에 해당하는 연산자를 date형 연산자를 반환한다.
	 *
	 * @param op 변한될 매개변수
	 * @return 변환한 date형 매개변수
	 */
	static public Operator dateMap(String op) {
		return map(dateMap, op, Type.DATE);
	}

	void display(int lev) {
		for (int i = 0; i < lev; i++) {
			System.out.print("\t");
		}

		System.out.println("Operator " + value);
	}

}
