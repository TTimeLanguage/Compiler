/**
 * Created by 병훈 on 2015-05-09.
 */

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;

abstract class AbstractSyntax {
	/**
	 * 객체의 타당할경우 <tt>true</tt>
	 * 객체가 타당성을 <tt>V</tt>함수를 통해 확인 됐다면,
	 * 다음 <tt>V</tt>합수를 실행할때 다시 타당성을 확인하지 않는다.
	 */
	protected boolean valid = false;
	/**
	 * 전역변수 테이블
	 * 전역변수 이름(String)를 Key로, 전역변수 객체를 Init으로 가지는 map객체
	 */
	protected final static HashMap<String, Init> globalVariableMap = new HashMap<>();
	// todo
	protected final static HashSet<Global> globalFunctionMap = new HashSet<>();

	/**
	 * AST에서 노드(객체)의 정보를 표시함
	 *
	 * @param k 노드의 레벨
	 */
	abstract void display(int k);

	/**
	 * AST에서 노드(객체)의 타당성을 확인함
	 *
	 * @param declarationMap 이 노드의 범위에서 사용가능한 변수의 map
	 */
	abstract protected void V(HashMap<String, Init> declarationMap);    // validation

	/**
	 * <tt>test</tt>를 확인해서 false면 <tt>msg</tt>를 출력하고 종료함
	 *
	 * @param test 확인 하고 싶은 식이나 변수
	 * @param msg  <tt>test</tt>가 만족하지 못 할 경우 출력 할 메시지
	 */
	protected final void check(boolean test, String msg) {
		if (test) return;
		System.err.println(msg);
		System.exit(1);
	}
}

/**
 * AST의 투르 노드
 * <p>
 * Abstract Syntax :
 * Program = Global*; Statements
 *
 * @see AbstractSyntax
 */
class Program extends AbstractSyntax {
	/**
	 * Global들을 모아놓은 리스트
	 *
	 * @see Global
	 * @see ArrayList
	 */
	protected final ArrayList<Global> globals;
	/**
	 * int main()의 안에 적혀있는 코드들
	 *
	 * @see Statement
	 */
	protected final Statements statements;

	/**
	 * <tt>Program</tt>객체를 매개변수로 초기화한다.
	 *
	 * @param g <tt>Global</tt>의 <tt>ArrayList</tt>
	 *          전역변수와 함수의 정의들
	 * @param s int main()의 안에 적혀있는 코드들
	 */
	Program(ArrayList<Global> g, Statements s) {
		globals = g;
		statements = s;
	}

	private void mapGlobal() {
		for (Global global : globals) {

			if (global instanceof FunctionDeclaration) {
				FunctionDeclaration functionDeclaration = (FunctionDeclaration) global;
				functionDeclaration.mapParams();

				check(!globalFunctionMap.contains(global),
						"duplicated declared function " + functionDeclaration.name);

				globalFunctionMap.add(functionDeclaration);

			} else if (global instanceof Declaration) {

				for (Init init : ((Declaration) global).inits) {

					check(!globalVariableMap.containsKey(init.name),
							"duplicated declaration in global " + init.name);

					globalVariableMap.put(init.name, init);
				}

			} else {
				check(false, "never reach here");
			}
		}
	}

	@Override
	public void display(int k) {
		for (int w = 0; w < k; w++) {
			System.out.print("\t");
		}

		System.out.println("Program");
		for (Global g : globals) {
			g.display(k + 1);
		}
		statements.display(k + 1);
	}


	public void V() {
		V(globalVariableMap);
	}


	@Override
	protected void V(HashMap<String, Init> declarationMap) {
		mapGlobal();

		for (Global global : globals) {
			global.V(declarationMap);
		}

		statements.mapVariable();
		statements.V(declarationMap);
	}
}


/**
 * Abstract Syntax :
 * Global = FunctionDeclaration | Declarations
 */
abstract class Global extends AbstractSyntax {
}


/**
 * Abstract Syntax :
 * FunctionDeclaration = Type; String id; ParamDeclaration*; Statements
 */
class FunctionDeclaration extends Global {
	protected final Type type;
	protected final String name;
	protected final ArrayList<ParamDeclaration> params;
	protected final Statements statements;
	protected final HashMap<String, Init> paramMap = new HashMap<>();

	FunctionDeclaration(Type t, String name, ArrayList<ParamDeclaration> p, Statements s) {
		type = t;
		this.name = name;
		params = p;
		statements = s;
	}

	protected void mapParams() {
		for (ParamDeclaration param : params) {

			check(!paramMap.containsKey(param.name),
					"duplicated declaration :" + param.name);

			paramMap.put(param.name, param);
		}

		statements.mapVariable();
	}

	@Override
	void display(int k) {
		for (int w = 0; w < k; w++) {
			System.out.print("\t");
		}

		System.out.println("FunctionDeclaration " + type + " " + name);
		for (ParamDeclaration declaration : params) {
			declaration.display(k + 1);
		}
		statements.display(k + 1);
	}

	@Override
	protected void V(HashMap<String, Init> declarationMap) {
		// todo
		if (valid) return;

		HashMap<String, Init> localMap = new HashMap<>(declarationMap);
		int globalLength = localMap.size();
		int localLength = paramMap.size();
		localMap.putAll(paramMap);

		check(globalLength + localLength == localMap.size(),
				"duplicated declaration in function :" + type + " " + name);

		statements.V(localMap);

		valid = true;
	}
}


/**
 * Abstract Syntax :
 * ParamDeclaration = Type; String Id
 */
class ParamDeclaration extends Init {
	ParamDeclaration(Type t, String name) {
		type = t;
		this.name = name;
	}

	void display(int k) {
		for (int w = 0; w < k; w++) {
			System.out.print("\t");
		}

		System.out.println("ParamDeclaration " + type + " " + name);
	}

	@Override
	protected void V(HashMap<String, Init> declarationMap) {
		// todo 아마 그대로
	}
}


/**
 * Abstract Syntax :
 * Statements = Declaration*; Statement*
 */
class Statements extends AbstractSyntax {
	protected final ArrayList<Declaration> declarations;
	protected final ArrayList<Statement> statements;
	protected final HashMap<String, Init> variableMap = new HashMap<>();

	Statements(ArrayList<Declaration> d, ArrayList<Statement> s) {
		declarations = d;
		statements = s;
	}

	protected void mapVariable() {
		for (Declaration declaration : declarations) {

			for (Init init : declaration.inits) {

				check(!variableMap.containsKey(init.name),
						"duplicated declaration " + init.name);

				variableMap.put(init.name, init);
			}

		}
	}

	@Override
	void display(int k) {
		for (int w = 0; w < k; w++) {
			System.out.print("\t");
		}

		System.out.println("Statements");
		for (Declaration declaration : declarations) {
			declaration.display(k + 1);
		}
		for (Statement statement : statements) {
			statement.display(k + 1);
		}
	}

	@Override
	protected void V(HashMap<String, Init> declarationMap) {
		// todo
		if (valid) return;

		for (Declaration declaration : declarations) {
			declaration.V(declarationMap);
		}

		HashMap<String, Init> localMap = new HashMap<>(declarationMap);
		int globalLength = localMap.size();
		int localLength = variableMap.size();
		localMap.putAll(variableMap);

		check(globalLength + localLength == localMap.size(),
				"duplicated declaration in main");

		for (Statement statement : statements) {
			statement.V(localMap);
		}

		valid = true;
	}
}


/**
 * Abstract Syntax :
 * Declaration = Init*
 */
class Declaration extends Global {
	protected final ArrayList<Init> inits;

	Declaration(ArrayList<Init> init) {
		this.inits = init;
	}

	@Override
	void display(int k) {
		for (int w = 0; w < k; w++) {
			System.out.print("\t");
		}

		System.out.println(this.getClass().getName());
		for (Init init : inits) {
			init.display(k + 1);
		}
	}

	@Override
	protected void V(HashMap<String, Init> declarationMap) {
		// todo 아마 그대로
	}
}


/**
 * Abstract Syntax :
 * Init = ArrayInit | NoArrayInit
 */
abstract class Init extends AbstractSyntax {
	protected Type type;
	protected String name;
}


/**
 * Abstract Syntax :
 * ArrayInit = Type; String id; int size; (Expression initList)*
 */
class ArrayInit extends Init {
	protected final int size;
	protected final ArrayList<Expression> initList;

	ArrayInit(Type t, String name, int s, ArrayList<Expression> i) {
		type = t;
		this.name = name;
		size = s;
		initList = i;
	}

	ArrayInit(Type t, String name, int s) {
		this(t, name, s, null);
	}

	@Override
	void display(int k) {
		for (int w = 0; w < k; w++) {
			System.out.print("\t");
		}

		System.out.println("ArrayInit " + type + " " + name + "[" + size + "]");
		if (initList != null) {
			for (Expression expression : initList) {
				expression.display(k + 1);
			}
		}
	}

	@Override
	protected void V(HashMap<String, Init> declarationMap) {
		// todo 확인
		if (valid) return;

		check(size >= 1,
				"array size must higher than 1. declared : " + type + " " + name + "[" + size + "]");

		if (initList != null) {
			for (Expression expression : initList) {
				check(expression.typeOf(declarationMap).equals(type),
						"wrong type initializer in declaration : " + type + " " + name + "[" + size + "]");
			}
		}

		valid = true;
	}
}


/**
 * Abstract Syntax :
 * ArrayInit = Type; String id; (Expression initList)*
 */
class NoArrayInit extends Init {
	protected final Expression initial;

	NoArrayInit(Type t, String name, Expression i) {
		type = t;
		this.name = name;
		initial = i;
	}

	NoArrayInit(Type t, String name) {
		this(t, name, null);
	}

	@Override
	void display(int k) {
		for (int w = 0; w < k; w++) {
			System.out.print("\t");
		}

		System.out.println("NoArrayInit " + type + " " + name);

		if (initial != null) {
			initial.display(k + 1);
		}
	}

	@Override
	protected void V(HashMap<String, Init> declarationMap) {
		// todo 확인
		if (valid) return;

		check(initial.typeOf(declarationMap).equals(type),
				"wrong type initializer in declaration : " + type + " " + name);

		valid = true;
	}
}


/**
 * Abstract Syntax :
 * Statement = Skip | IfStatement | Block | WhileStatement | SwitchStatement
 * | ForStatement | Return | Expression | Break | Continue
 */
abstract class Statement extends AbstractSyntax {
}


/**
 * Abstract Syntax :
 * IfStatement = Expression condition; Block; (Expression elseif; Block)*; Block?
 */
class IfStatement extends Statement {
	protected final Expression condition;
	protected final Block statements;
	protected final ArrayList<IfStatement> elseIfs;
	protected final Block elses;

	IfStatement(Expression c, Block s, ArrayList<IfStatement> elseIf, Block e) {
		condition = c;
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

	@Override
	void display(int k) {
		for (int w = 0; w < k; w++) {
			System.out.print("\t");
		}

		System.out.println("IfStatement");
		condition.display(k + 1);
		statements.display(k + 1);
		if (elseIfs != null) {
			for (IfStatement statement : elseIfs) {
				statement.display(k + 1);
			}
		}
		if (elses != null) {
			elses.display(k + 1);
		}
	}

	@Override
	protected void V(HashMap<String, Init> declarationMap) {
		// todo 확인
		if (valid) return;

		check(condition.typeOf(declarationMap) == Type.BOOL,
				"\'if\'\'s condition must boolean type. declared : " + condition.typeOf(declarationMap));

		condition.V(declarationMap);
		statements.V(declarationMap);

		if (elseIfs != null) {
			HashSet<Expression> conditions = new HashSet<>();
			conditions.add(condition);

			for (IfStatement statement : elseIfs) {

				check(conditions.contains(statement.condition),
						"duplicated else if condition");

				conditions.add(statement.condition);
				statement.V(declarationMap);
			}
		}

		if (elses != null) {
			elses.V(declarationMap);
		}

		valid = true;
	}
}


/**
 * Abstract Syntax :
 * Block = Statement*
 */
class Block extends Statement {
	protected final ArrayList<Statement> statements;

	Block(ArrayList<Statement> s) {
		statements = s;
	}

	@Override
	void display(int k) {
		for (int w = 0; w < k; w++) {
			System.out.print("\t");
		}

		System.out.println("Block");
		for (Statement statement : statements) {
			statement.display(k + 1);
		}
	}

	@Override
	protected void V(HashMap<String, Init> declarationMap) {
		if (valid) return;

		for (Statement i : statements) {
			i.V(declarationMap);
		}

		valid = true;
	}
}


/**
 * Abstract Syntax :
 * WhileStatement = Expression condition; Block
 */
class WhileStatement extends Statement {
	protected final Expression condition;
	protected final Block statements;

	WhileStatement(Expression c, Block s) {
		condition = c;
		statements = s;
	}

	@Override
	void display(int k) {
		for (int w = 0; w < k; w++) {
			System.out.print("\t");
		}

		System.out.println("WhileStatement");
		condition.display(k + 1);
		statements.display(k + 1);
	}

	@Override
	protected void V(HashMap<String, Init> declarationMap) {
		if (valid) return;

		check(condition.typeOf(declarationMap) == Type.BOOL,
				"poorly typed test in while Loop in Conditional: " + condition);

		condition.V(declarationMap);
		statements.V(declarationMap);

		valid = true;
	}
}


/**
 * Abstract Syntax :
 * SwitchStatement = Expression condition; (Literal case; Statement*)*; (Statement*)?
 */
class SwitchStatement extends Statement {
	protected Type switchType;
	protected final Expression condition;
	protected final HashMap<Value, ArrayList<Statement>> cases = new HashMap<>();
	protected ArrayList<Statement> defaults = null;

	SwitchStatement(Expression condition) {
		this.condition = condition;
	}

	void addCase(Value caseLiteral, ArrayList<Statement> statements) {
		check(cases.containsKey(caseLiteral),
				"duplicated case literal in switch");

		cases.put(caseLiteral, statements);
	}

	@Override
	void display(int k) {
		for (int w = 0; w < k; w++) {
			System.out.print("\t");
		}

		System.out.println("SwitchStatement");
		for (Value value : cases.keySet()) {
			value.display(k + 1);
			for (Statement statement : cases.get(value)) {
				statement.display(k + 1);
			}
		}
	}

	@Override
	protected void V(HashMap<String, Init> declarationMap) {
		// todo 확인
		if (valid) return;

		switchType = condition.typeOf(declarationMap);

		for (Value key : cases.keySet()) {
			check(key.typeOf(declarationMap) != switchType,
					"different type of case literal in switch. case : " + key.typeOf(declarationMap));

			for (Statement statement : cases.get(key)) {
				statement.V(declarationMap);
			}
		}

		if (defaults != null) {
			for (Statement statement : defaults) {
				statement.V(declarationMap);
			}
		}

		valid = true;
	}
}


/**
 * Abstract Syntax :
 * ForStatement = Expression*; Expression; Expression*; Block
 */
class ForStatement extends Statement {
	protected final ArrayList<Expression> preExpression = new ArrayList<>();
	protected final ArrayList<Expression> postExpression = new ArrayList<>();
	protected Expression condition;
	protected Block statements;

	void addPreExpression(Expression expression) {
		preExpression.add(expression);
	}

	void addPostExpression(Expression expression) {
		postExpression.add(expression);
	}

	@Override
	void display(int k) {
		for (int w = 0; w < k; w++) {
			System.out.print("\t");
		}

		System.out.println("ForStatement");

		for (Expression expression : preExpression) {
			expression.display(k + 1);
		}
		condition.display(k + 1);
		for (Expression expression : postExpression) {
			expression.display(k + 1);
		}
	}

	@Override
	protected void V(HashMap<String, Init> declarationMap) {
		// todo 확인
		if (valid) return;

		check(condition.typeOf(declarationMap) == Type.BOOL,
				"condition type must boolean in for. condition type : " + condition.typeOf(declarationMap));

		for (Expression pre : preExpression) {
			pre.V(declarationMap);
		}

		for (Expression post : postExpression) {
			post.V(declarationMap);
		}

		statements.V(declarationMap);

		valid = true;
	}
}


/**
 * Abstract Syntax :
 * Return = Expression?
 */
class Return extends Statement {
	protected final Expression returnValue;

	Return(Expression returnValue) {
		this.returnValue = returnValue;
	}

	Return() {
		this(null);
	}

	@Override
	void display(int k) {
		for (int w = 0; w < k; w++) {
			System.out.print("\t");
		}

		System.out.println("Return");
		if (returnValue != null) {
			returnValue.display(k + 1);
		}
	}

	@Override
	protected void V(HashMap<String, Init> declarationMap) {
		// todo
	}
}


/**
 * Abstract Syntax :
 * Break =
 */
class Break extends Statement {
	@Override
	void display(int k) {
		for (int w = 0; w < k; w++) {
			System.out.print("\t");
		}

		System.out.println("Break");
	}

	@Override
	protected void V(HashMap<String, Init> declarationMap) {
		// todo
	}
}


/**
 * Abstract Syntax :
 * Continue =
 */
class Continue extends Statement {
	@Override
	void display(int k) {
		for (int w = 0; w < k; w++) {
			System.out.print("\t");
		}

		System.out.println("Continue");
	}

	@Override
	protected void V(HashMap<String, Init> declarationMap) {
		// todo
	}
}


/**
 * Abstract Syntax :
 * Skip =
 */
class Skip extends Statement {
	@Override
	void display(int k) {
	}

	@Override
	protected void V(HashMap<String, Init> declarationMap) {
	}
}


/**
 * Abstract Syntax :
 * Expression = VariableRef | Value | Binary | Unary | Function
 */
abstract class Expression extends Statement {
	abstract Type typeOf(HashMap<String, Init> declarationMap);
}


/**
 * Abstract Syntax :
 * VariableRef = Variable | ArrayRef
 */
abstract class VariableRef extends Expression {
}


/**
 * Abstract Syntax :
 * Variable = String id
 */
class Variable extends VariableRef {
	protected final String name;

	Variable(String name) {
		this.name = name;
	}

	public String toString() {
		return name;
	}

	public boolean equals(Object obj) {
		if (obj instanceof Variable) {
			String s = ((Variable) obj).name;
			return name.equals(s); // case-sensitive identifiers
		} else return false;
	}

	public int hashCode() {
		return name.hashCode();
	}

	@Override
	void display(int k) {
		for (int w = 0; w < k; w++) {
			System.out.print("\t");
		}

		System.out.println("Variable " + name);
	}

	@Override
	protected void V(HashMap<String, Init> declarationMap) {
		// todo 확인
		if (valid) return;

		check(declarationMap.containsKey(name),
				"undeclared variable: " + name);

		check(declarationMap.get(name) instanceof NoArrayInit,
				"wrong reference. should add array reference. in " + name);

		valid = true;
	}

	@Override
	Type typeOf(HashMap<String, Init> declarationMap) {
		check(declarationMap.containsKey(this.name),
				"undefined variable: " + this.name);

		return declarationMap.get(this.name).type;
	}
}


/**
 * Abstract Syntax :
 * ArrayRef = String id; Expression index
 */
class ArrayRef extends VariableRef {
	protected final String name;
	protected final Expression index;

	ArrayRef(String n, Expression index) {
		this.name = n;
		this.index = index;
	}

	@Override
	void display(int k) {
		for (int w = 0; w < k; w++) {
			System.out.print("\t");
		}

		System.out.println("ArrayRef " + name);
		index.display(k + 1);
	}

	@Override
	protected void V(HashMap<String, Init> declarationMap) {
		// todo 확인
		if (valid) return;

		check(declarationMap.containsKey(name),
				"undeclared variable: " + name);

		Init declare = declarationMap.get(name);
		check(declare instanceof ArrayInit,
				"wrong reference. should remove array reference. in " + name);

		check(index.typeOf(declarationMap) == Type.INT,
				"index type must be integer. in " + name);

		valid = true;
	}

	@Override
	Type typeOf(HashMap<String, Init> declarationMap) {
		check(declarationMap.containsKey(this.name),
				"undefined variable: " + this.name);

		return declarationMap.get(this.name).type;
	}
}


/**
 * Abstract Syntax :
 * Value = IntValue | BoolValue | FloatValue | CharValue | TimeValue | DateValue
 */
abstract class Value extends Expression {
	protected Type type;

	@Override
	Type typeOf(HashMap<String, Init> declarationMap) {
		return type;
	}
}


/**
 * Abstract Syntax :
 * Binary = Operator; Expression e1, e2
 */
class Binary extends Expression {
	protected final Operator op;
	protected final Expression term1, term2;

	Binary(Operator o, Expression l, Expression r) {
		op = o;
		term1 = l;
		term2 = r;
	}

	@Override
	void display(int k) {
		for (int w = 0; w < k; w++) {
			System.out.print("\t");
		}

		System.out.println("Binary");

		op.display(k + 1);
		term1.display(k + 1);
		term2.display(k + 1);
	}

	@Override
	protected void V(HashMap<String, Init> declarationMap) {
		if (valid) return;

		Type typ1 = term1.typeOf(declarationMap);
		Type typ2 = term2.typeOf(declarationMap);
		term1.V(declarationMap);
		term2.V(declarationMap);

		if (op.ArithmeticOp()) {
			check(typ1 == typ2 &&
					(typ1 == Type.INT || typ1 == Type.FLOAT)
					, "type error for " + op);

		} else if (op.RelationalOp()) {
			check(typ1 == typ2, "type error for " + op);

		} else if (op.BooleanOp()) {
			check(typ1 == Type.BOOL && typ2 == Type.BOOL, op + ": non-bool operand");

		} else if (op.AssignOP()) {
			// todo 추가
			check(term1 instanceof VariableRef, term1 + "is not l-value");
			term1.V(declarationMap);
			term2.V(declarationMap);

			Type target = term1.typeOf(declarationMap); //ttype = target type; targets are only variables in Clite which are defined in the TypeMap
			Type source = term2.typeOf(declarationMap); //scrtype = source type; sources are Expressions or Statements which are not in the TypeMap

			if (target != source) {
				if (target == Type.FLOAT) {
					check(source == Type.INT || source == Type.FLOAT
							, "mixed mode assignment to " + term1 + " with " + term2);
				} else if (target == Type.INT) {
					check(source != Type.DATE || source != Type.TIME || source != Type.VOID,
							"mixed mode assignment to " + term1 + " with " + term2);
				} else {
					check(false, "mixed mode assignment to " + term1 + " with " + term2);
				}
			}
		} else throw new IllegalArgumentException("should never reach here BinaryOp error");

		valid = true;
	}


	@Override
	Type typeOf(HashMap<String, Init> declarationMap) {
		// todo 추가
		if (op.ArithmeticOp())
			if (term1.typeOf(declarationMap) == Type.FLOAT)
				return (Type.FLOAT);
			else return (Type.INT);
		if (op.RelationalOp() || op.BooleanOp())
			return (Type.BOOL);
		else
			return null;
	}
}


/**
 * Abstract Syntax :
 * Unary = UnaryOperator; Expression
 */
class Unary extends Expression {
	protected final Operator op;
	protected final Expression term;

	Unary(Operator o, Expression e) {
		op = o;
		term = e;
	}

	@Override
	void display(int k) {
		for (int w = 0; w < k; w++) {
			System.out.print("\t");
		}

		System.out.println("Unary");

		op.display(k + 1);
		term.display(k + 1);
	}

	@Override
	protected void V(HashMap<String, Init> declarationMap) {
		if (valid) return;

		Type type = term.typeOf(declarationMap); //start here
		term.V(declarationMap);
		if (op.NotOp()) {
			check((type == Type.BOOL), "type error for NotOp " + op);

		} else if (op.NegateOp()) {
			check((type == (Type.INT) || type == (Type.FLOAT)),
					"type error for NegateOp " + op);

		} else if (op.incOp()) {
			check(type != Type.BOOL || type != Type.VOID,
					"type error for increase or decrease Op");
		} else {
			throw new IllegalArgumentException("should never reach here UnaryOp error");
		}

		valid = true;
	}

	@Override
	Type typeOf(HashMap<String, Init> declarationMap) {
		// todo 수정
		if (op.NotOp()) return (Type.BOOL);
		else if (op.NegateOp()) return term.typeOf(declarationMap);
		else if (op.intOp()) return (Type.INT);
		else return null;
	}
}


/**
 * Abstract Syntax :
 * Function = String name; Expression*
 */
class Function extends Expression {
	protected final String name;
	protected final ArrayList<Expression> params;

	Function(String id, ArrayList<Expression> params) {
		name = id;
		this.params = params;
	}

	@Override
	void display(int k) {
		for (int w = 0; w < k; w++) {
			System.out.print("\t");
		}

		System.out.println("Function " + name);

		for (Expression expression : params) {
			expression.display(k + 1);
		}
	}

	@Override
	protected void V(HashMap<String, Init> declarationMap) {
		if (valid) return;
		// todo

		valid = true;
	}

	@Override
	Type typeOf(HashMap<String, Init> declarationMap) {
		// todo
		return null;
	}
}


/**
 * Abstract Syntax :
 * TypeCast = Type type; Expression
 */
class TypeCast extends Expression {
	protected final Type type;
	protected final Expression expression;

	TypeCast(Type t, Expression e) {
		type = t;
		expression = e;
	}

	@Override
	void display(int k) {
		for (int w = 0; w < k; w++) {
			System.out.print("\t");
		}

		System.out.println("TypeCast " + type);
		expression.display(k + 1);
	}

	@Override
	protected void V(HashMap<String, Init> declarationMap) {
		// todo 추가
		if (valid) return;

		expression.V(declarationMap);
		Type t = expression.typeOf(declarationMap);

		if (type == Type.INT) {
			check(t == Type.INT || t == Type.CHAR,
					"can not transform type to " + type);

		} else if (type == Type.FLOAT) {
			check(t == Type.FLOAT || t == Type.INT || t == Type.CHAR,
					"can not cast type to " + type);

		} else if (type == Type.CHAR) {

		} else if (type == Type.BOOL) {

		} else {
			throw new IllegalArgumentException("should never reach here TypeCast error");
		}

		valid = true;
	}

	@Override
	Type typeOf(HashMap<String, Init> declarationMap) {
		return this.type;
	}
}

/**
 * Abstract Syntax :
 * Type = 'int' | 'bool' | 'void' | 'char' | 'float' | 'time' | 'date'
 */
class Type extends AbstractSyntax {
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

	@Override
	public boolean equals(Object obj) {
		if (obj instanceof Type) {
			Type tmp = (Type) obj;
			return tmp.value.equals(this.value);
		} else return false;
	}

	void display(int k) {
		for (int w = 0; w < k; w++) {
			System.out.print("\t");
		}

		System.out.println("Type " + value);
	}

	@Override
	protected void V(HashMap<String, Init> declarationMap) {
	}
}


/**
 * Abstract Syntax :
 * IntValue = int
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

		System.out.println("IntValue " + value);
	}

	@Override
	protected void V(HashMap<String, Init> declarationMap) {
	}
}


/**
 * Abstract Syntax :
 * BoolValue = bool
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

		System.out.println("BoolValue " + value);
	}

	@Override
	protected void V(HashMap<String, Init> declarationMap) {
	}
}


/**
 * Abstract Syntax :
 * CharValue = String
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

		System.out.println("CharValue " + value);
	}

	@Override
	protected void V(HashMap<String, Init> declarationMap) {
	}
}


/**
 * Abstract Syntax :
 * FloatValue = float
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

		System.out.println("FloatValue " + value);
	}

	@Override
	protected void V(HashMap<String, Init> declarationMap) {
	}
}


/**
 * Abstract Syntax :
 * DateValue = String year, month, day
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

		System.out.println("DateValue " + year + "." + month + "." + day);
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
 * TimeValue = String hour, minute, second
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

		System.out.println("TimeValue " + hour + ":" + minute + ":" + second);
	}

	@Override
	protected void V(HashMap<String, Init> declarationMap) {
		check(hour >= 0 && hour < 24, "hour value must be between 0 and 23");
		check(minute >= 0 && minute < 60, "minute value must be between 0 and 59");
		check(second >= 0 && second < 60, "second value must be between 0 and 59");
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

	// Type specific cast
	final static String I2F = "I2F";
	final static String F2I = "F2I";
	final static String C2I = "C2I";
	final static String I2C = "I2C";

	protected final String value;

	Operator(String s) {
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

		System.out.println("Operator " + value);
	}

}
