/**
 * Created by 병훈 on 2015-05-09.
 */

import java.util.ArrayList;
import java.util.HashMap;

/**
 * Abstract Syntax :
 * Program = Global*; Staments
 */
class Program {
	ArrayList<Global> globals;
	Statements statements;

	Program(ArrayList<Global> g, Statements s) {
		globals = g;
		statements = s;
	}
}


/**
 * Abstract Syntax :
 * Global = FunctionDeclaration | Declarations
 */
abstract class Global {}


/**
 * Abstract Syntax :
 * FunctionDeclaration = Type; String id; ParamDeclaration*; Statements
 */
class FunctionDeclaration extends Global {
	Type type;
	String name;
	ArrayList<ParamDeclaration> params;
	Statements statements;

	FunctionDeclaration(Type t, String name, ArrayList<ParamDeclaration> p, Statements s) {
		type = t;
		this.name = name;
		params = p;
		statements = s;
	}
}


/**
 * Abstract Syntax :
 * ParamDeclaration = Type; String Id
 */
class ParamDeclaration {
	Type type;
	String name;

	ParamDeclaration(Type t, String name) {
		type = t;
		this.name = name;
	}
}


/**
 * Abstract Syntax :
 * Statements = Declaration*; Statement*
 */
class Statements {
	ArrayList<Declaration> declarations;
	ArrayList<Statement> statements;

	Statements(ArrayList<Declaration> d, ArrayList<Statement> s) {
		declarations = d;
		statements = s;
	}
}


/**
 * Abstract Syntax :
 * Declaration = ArrayInit | NoArrayInit
 */
abstract class Declaration {
}


/**
 * Abstract Syntax :
 * ArrayInit = Type; String id; int size; (Expression initList)*
 */
class ArrayInit extends Declaration {
	Type type;
	String name;
	int size;
	ArrayList<Expression> initList;

	ArrayInit(Type t, String name, int s, ArrayList<Expression> i) {
		type = t;
		this.name = name;
		size = s;
		initList = i;
	}

	ArrayInit(Type t, String name, int s) {
		this(t, name, s, null);
	}
}


/**
 * Abstract Syntax :
 * ArrayInit = Type; String id; (Expression initList)*
 */
class NoArrayInit extends Declaration {
	Type type;
	String name;
	ArrayList<Expression> initList;

	NoArrayInit(Type t, String name, ArrayList<Expression> i) {
		type = t;
		this.name = name;
		initList = i;
	}

	NoArrayInit(Type t, String name) {
		this(t, name, null);
	}
}


/**
 * Abstract Syntax :
 * Statement = Skip | IfSatement | Block | WhileSatement | SwitchStatement
 * | ForStatement | Return | Expression | Break | Continue
 */
abstract class Statement {
}


/**
 * Abstract Syntax :
 * IfStatement = Expression condition; Block; (Expression elseif; Block)*; Block?
 */
class IfStatement extends Statement {
	Expression conditiion;
	Block statements;
	ArrayList<IfStatement> elseIfs;
	Block elses;

	IfStatement(Expression c, Block s, ArrayList<IfStatement> elseIf, Block e) {
		conditiion = c;
		statements = s;
		elseIfs = elseIf;
		elses = e;
	}

	IfStatement(Expression c, Block s, ArrayList<IfStatement> elseIfs) {
		this(c, s, elseIfs, null);
	}

	IfStatement(Expression c, Block s, Block e) {
		this(c, s, null, e);
	}

	IfStatement(Expression c, Block s) {
		this(c, s, null, null);
	}
}


/**
 * Abstract Syntax :
 * Block = Statement*
 */
class Block extends Statement {
	ArrayList<Statement> statements;

	Block(ArrayList<Statement> s) {
		statements = s;
	}
}


/**
 * Abstract Syntax :
 * WhileStatement = Expression condition; Block
 */
class WhileStatement extends Statement {
	Expression condition;
	Block statements;

	WhileStatement(Expression c, Block s) {
		condition = c;
		statements = s;
	}
}


/**
 * Abstract Syntax :
 * SwitchStatement = Expression condition; (Literal case; Statement*)*; (Statement*)?
 */
class SwitchStatement extends Statement {
	Expression condition;
	HashMap<Value, ArrayList<Statement>> cases = new HashMap<Value, ArrayList<Statement>>();
	ArrayList<Statement> defaults;

	void addCase(Value caseLiteral, ArrayList<Statement> statements) {
		cases.put(caseLiteral, statements);
	}
}


/**
 * Abstract Syntax :
 * ForStatement = Expression*; Expression; Expression*; Block
 */
class ForStatement extends Statement {
	ArrayList<Expression> preExpression = new ArrayList<Expression>();
	ArrayList<Expression> postExpression = new ArrayList<Expression>();
	Expression condition;
	Block statements;

	void addPreExpression(Expression expression) {
		preExpression.add(expression);
	}

	void addPostExpression(Expression expression) {
		postExpression.add(expression);
	}
}


/**
 * Abstract Syntax :
 * Return = Expression?
 */
class Return extends Statement {
	Expression returnValue;

	Return(Expression returnValue) {
		this.returnValue = returnValue;
	}
}


/**
 * Abstract Syntax :
 * Break =
 */
class Bread extends Statement {}


/**
 * Abstract Syntax :
 * Continue =
 */
class Continue extends Statement {}


/**
 * Abstract Syntax :
 * Skip =
 */
class Skip extends Statement {}


/**
 * Abstract Syntax :
 * Expression = VariableRef | Value | Binary | Unary | Assignment | Function
 */
abstract class Expression extends Statement{}


/**
 * Abstract Syntax :
 * VariableRef = Variable | ArrayRef
 */
abstract class VariableRef extends Expression{}


/**
 * Abstract Syntax :
 * Variable = String id
 */
class Variable extends VariableRef{
	String name;

	Variable(String name) {
		this.name = name;
	}

	public String toString() {
		return name;
	}

	public boolean equals(Object obj) {
		String s = ((Variable) obj).name;
		return name.equals(s); // case-sensitive identifiers
	}

	public int hashCode() {
		return name.hashCode();
	}
}


/**
 * Abstract Syntax :
 * ArrayRef = String id; Expression index
 */
class ArrayRef extends VariableRef{
	String name;
	Expression index;

	ArrayRef(String n, Expression index) {
		this.name = n;
		this.index = index;
	}
}


/**
 * Abstract Syntax :
 * Value = IntValue | BoolValue | FloatValue | CharValue | TimeValue | DateValue
 */
abstract class Value extends Expression {
	Type type;
}


/**
 * Abstract Syntax :
 * Binary = Operator; Expression e1, e2
 */
class Binary extends Expression {
	Operator op;
	Expression term1, term2;

	Binary(Operator o, Expression l, Expression r) {
		op = o;
		term1 = l;
		term2 = r;
	}
}


/**
 * Abstract Syntax :
 * Unary = UnaryOperator; Expression
 */
class Unary extends Expression {
	Operator op;
	Expression term;

	Unary(Operator o, Expression e) {
		op = o;
		term = e;
	}
}


/**
 * Abstract Syntax :
 * Assignments = Assignment | ArrayAssign
 */
abstract class Assignments extends Expression {
	Variable var;
	Operator operator;
	Expression expression;
}


/**
 * Abstract Syntax :
 * Assignment = String Id; Operator; Expression
 */
class Assignment extends Assignments {
	Assignment(Variable v, Operator o, Expression e) {
		var = v;
		operator = o;
		expression = e;
	}
}


/**
 * Abstract Syntax :
 * ArrayAssign = String Id; Expression index; Operator; Expression
 */
class ArrayAssign  extends Assignments {
	Expression index;

	ArrayAssign (Variable v, Expression index,Operator o, Expression e) {
		var = v;
		this.index = index;
		operator = o;
		expression = e;
	}
}


/**
 * Abstract Syntax :
 * Function = String name; Expression*
 */
class Function extends Expression {
	String name;
	ArrayList<Expression> params;

	void addParams(Expression param) {
		params.add(param);
	}
}


/**
 * Abstract Syntax :
 * Type = 'int' | 'bool' | 'void' | 'char' | 'float' | 'time' | 'date'
 */
class Type {
	// Type = int | bool | char | float
	final static Type INT = new Type("int");
	final static Type BOOL = new Type("bool");
	final static Type CHAR = new Type("char");
	final static Type FLOAT = new Type("float");
	final static Type VOID = new Type("void");
	final static Type TIME = new Type("time");
	final static Type DATE = new Type("date");

	private String value;

	private Type(String t) {
		value = t;
	}

	public String toString() {
		return value;
	}
}


/**
 * Abstract Syntax :
 * IntValue = int
 */
class IntValue extends Value {
	private int value = 0;

	IntValue() {
		type = Type.INT;
	}

	IntValue(int v) {
		this();
		value = v;
	}

	int intValue() {
		return value;
	}

	public String toString() {
		return "" + value;
	}
}


/**
 * Abstract Syntax :
 * BoolValue = bool
 */
class BoolValue extends Value {
	private boolean value = false;

	BoolValue() {
		type = Type.BOOL;
	}

	BoolValue(boolean v) {
		this();
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
}


/**
 * Abstract Syntax :
 * CharValue = String
 */
class CharValue extends Value {
	private char value = ' ';

	CharValue() {
		type = Type.CHAR;
	}

	CharValue(char v) {
		this();
		value = v;
	}

	char charValue() {
		return value;
	}

	public String toString() {
		return "" + value;
	}
}


/**
 * Abstract Syntax :
 * FloatValue = float
 */
class FloatValue extends Value {
	private float value = 0;

	FloatValue() {
		type = Type.FLOAT;
	}

	FloatValue(float v) {
		this();
		value = v;
	}

	float floatValue() {
		return value;
	}

	public String toString() {
		return "" + value;
	}
}


/**
 * Abstract Syntax :
 * DateValue = String year, month, day
 */
class DateValue extends Value {
	private String year, month, day;

	DateValue() {
		type = Type.DATE;
	}

	DateValue(String y, String m, String d) {
		this();
		year = y;
		month = m;
		day = d;
	}

	public String toString() {
		return "" + year + "/" + month + "/" + day;
	}
}


/**
 * Abstract Syntax :
 * TimeValue = String hour, minute, second
 */
class TimeValue extends Value {
	private String hour, minute, second;

	TimeValue() {
		type = Type.TIME;
	}

	TimeValue(String h, String m, String s) {
		this();
		hour = h;
		minute = m;
		second = s;
	}

	public String toString() {
		return "" + hour + "/" + minute + "/" + second;
	}
}


/**
 * Abstract Syntax :
 * Operator = BooleanOp | RelationalOp | ArithmeticOp | UnaryOp | AssignOp
 */
class Operator {
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
	
	// Type specific cast
	final static String I2F = "I2F";
	final static String F2I = "F2I";
	final static String C2I = "C2I";
	final static String I2C = "I2C";

	String value;

	Operator(String s) {
		value = s;
	}

	public String toString() {
		return value;
	}

	public boolean equals(Object obj) {
		return value.equals(obj);
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
				|| value.equals(MODASSIGN) || value.equals(DIVASSIGN);
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
			{EQ, FLOAT_EQ}, {NE, FLOAT_NE}, {LT, FLOAT_LT},	{LE, FLOAT_LE}, {GT, FLOAT_GT}, {GE, FLOAT_GE},
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
}
