import java.util.ArrayList;

public class Parser {
	Token token;          // current token from the input stream
	Lexer lexer;

	public Parser(Lexer ts) { // Open the C++Lite source program
		lexer = ts;                          // as a token stream, and
		token = lexer.next();            // retrieve its first Token
	}

	private String match(TokenType t) {
		String value = token.value();
		if (token.type().equals(t))
			token = lexer.next();
		else
			error(t);
		return value;
	}

	private void error(TokenType tok) {
		System.err.println("Syntax error: expecting: " + tok
				+ "; saw: " + token + " ");
		System.exit(1);
	}

	private void error(String tok) {
		System.err.println("Syntax error: expecting: " + tok
				+ "; saw: " + token + " ");
		System.exit(1);
	}

	/**
	 * ********************************************************************************************************
	 */
	public Program program() {
		// Program → { Global } 'int' 'main' '(' ')' Statements

		ArrayList<Global> globals = new ArrayList<Global>();
		Statements s = null;

		while (isType()) {
			Type t = type();

			if (t.equals(Type.INT) && isMain()) {
				match(TokenType.Main);            // int main
				match(TokenType.LeftParen);        // (
				match(TokenType.RightParen);    // )
				s = statements();                // Statements
			}

			String id = match(TokenType.Identifier);

			if (isLeftParen()) {
				globals.add(functionDeclaration(t, id));
			} else {
				globals.add(declaration());
			}
		}

		return new Program(globals, s);
	}


	private FunctionDeclaration functionDeclaration(Type type, String id) {
		// FunctionDeclaration → Type Id '(' ParamDeclarations ’)’ Statements

		ArrayList<ParamDeclaration> params = new ArrayList<ParamDeclaration>();

		match(TokenType.LeftParen);
		if (!isRightParen()) {

			params.add(paramDeclaration());

			while (isComma()) {
				match(TokenType.Comma);
				params.add(paramDeclaration());
			}
		}
		match(TokenType.RightParen);

		Statements s = statements();


		return new FunctionDeclaration(type, id, params, s);
	}

	private ParamDeclaration paramDeclaration() {
		// ParamDeclarations → ParamDeclaration { ',' ParamDeclaration } | ε

		Type type = type();
		String id = match(TokenType.Identifier);

		return new ParamDeclaration(type, id);
	}


	private Statements statements() {
		//Statements → ‘{‘ { Declarations } { Statement } ‘}’

		ArrayList<Declaration> declarations = new ArrayList<Declaration>();
		ArrayList<Statement> statements = new ArrayList<Statement>();


		match(TokenType.LeftBrace);

		while (isType()) {
			declarations.add(declaration());
		}

		// todo

		match(TokenType.RightBrace);

		return new Statements(declarations, statements);
	}


	private Declaration declaration() {
		// Declarations → Type Init { ',' Init } ';'

		Type type = type();
		String id = match(TokenType.Identifier);
		Declaration declaration = null;

		if (isLeftBracket()) {
			declaration = arrayInit(type, id);
		} else {
			declaration = noArrayInit(type, id);
		}

		return declaration;
	}

	private ArrayInit arrayInit(Type type, String id) {
		// ArrayInit → Id '[' Integer ']' [ '=' '{' Expression { ',' Expression } '}' ]

		match(TokenType.LeftBracket);
		int size = Integer.parseInt(match(TokenType.IntLiteral));
		match(TokenType.RightBracket);

		if (isAssign()) {
			ArrayList<Expression> expressions = new ArrayList<Expression>();

			match(TokenType.LeftBrace);
			expressions.add(expression());

			while (isComma()) {
				match(TokenType.Comma);
				expressions.add(expression());
			}
			match(TokenType.RightBrace);

			return new ArrayInit(type, id, size, expressions)
		}

		return new ArrayInit(type, id, size);
	}

	private NoArrayInit noArrayInit(Type type, String id) {
		// NoArrayInit → Id [ '=' Expression ]

		if (isAssign()) {
			return new NoArrayInit(type, id, expression());
		}
		return new NoArrayInit(type, id);
	}


	/**
	 * ********************************************************************************************************
	 */
	private Statement statement() {
		// Statement --> Skip | IfStatement | Block | WhileStatement | SwitchStatement
		// | ForStatement | Return | Expression | Break | Continue
		Statement s = null;
		//	System.out.println("starting statment()");
		if (token.type().equals(TokenType.Semicolon))
			s = new Skip();
		else if (token.type().equals(TokenType.If))            //if
			s = IfStatement();
			//		System.out.println("block data " + s);}
		else if (token.type().equals(TokenType.LeftBrace))    //block
			s = block();
		else if (token.type().equals(TokenType.While))        //while
			s = WhileStatement();
		else if (token.type().equals(TokenType.Switch))        //switch
			s = SwitchStatement();
		else if (token.type().equals(TokenType.For))        //for
			s = ForStatement();
		else if (token.type().equals(TokenType.Return))    //Return
			s = Return();
		else if (token.type().equals(TokenType.Break))        //break
			s = Break();
		else if (token.type().equals(TokenType.Continue))    //continue
			s = Continue();
		/*else if (token.type().equals(TokenType.))			//Expression
		todo
			s = expression();*/
		else error("Error in Statement construction");
		return s;
	}

	private Statement IfStatement() {
		// IfStatement → 'if' '(' Expression ')' Block 
		//{ 'else' 'if' '(' Expression ')' Block } [ 'else' Block ]
		match(TokenType.If);

		Expression test = expression();
		Block thenbranch = Block();

		ArrayList<IfStatement> elseifbranchs = new ArrayList<Expression>();
		while (!token.type().equals(TokenType.Else)) {
			if (token.type().equals(TokenType.ElseIf)) {
				elseifbranchs.add(elseifbranch);
				Block thenbranch2 = block();
			}

		}
		Statement elsebranch = new Skip();

		if (token.type().equals(TokenType.Else)) {
			token = lexer.next();
			elsebranch = statement();
		}
		return new Conditional(test, thenbranch, elseifbranch, elsebranch);// student exercise
	}

	private Statement block() {
		// Block → '{' { Statement } '}'

		ArrayList<Statement> statements = new ArrayList<Statement>();

		match(TokenType.LeftBracket);
		while (isStatement()) {
			statements.add(statement());
		}
		match(TokenType.RightBracket);

		return new Block(statements);
	}

	private Statement WhileStatement() {
		// WhileStatement --> while ( Expression ) Statement
		Statement body;
		Expression test;

		match(TokenType.While);
		match(TokenType.LeftParen);
		test = Expression();
		match(TokenType.RightParen);
		body = Block();
		return WhileStatement(test, body);
	}

	private Statement SwitchStatement() {
		//SwitchStatement → 'switch' '(' Expression ')'
		//'{' { 'case' Literal ':' { Statement } } [ 'default' ':' { Statement } ] '}'
		SwitchStatement sw = new SwitchStatement();
		//Expression ex = new Expression();
		match(TokenType.Switch);        // switch(?)
		match(TokenType.LeftParen);
		// expression
		match(TokenType.RightParen);
		match(TokenType.LeftBrace);        // {
		match(TokenType.Case);            //case x : ~~~;break;
		// Literal
		// : 
		// Statement
		// default
		// :
		// Statement
		match(TokenType.RightBrace);    // }
		return sw;
	}

	private Statement ForStatement() {
		// TODO Auto-generated method stub
		return null;
	}

	private Statement Return() {
		// Return → 'return' [ Expression ] ';'

		match(TokenType.Return);
		if (!isSemicolon()) {
			return new Return(expression());
		}
		return new Return();
	}

	private Statement Break() {
		// Break → 'break' ';'

		match(TokenType.Break);
		match(TokenType.Semicolon);
		return new Break();
	}

	private Statement Continue() {
		// Continue → 'continue' ';'

		match(TokenType.Continue);
		match(TokenType.Semicolon);
		return new Continue();
	}


	private Expression expression() {
		// Expression → [ Disjunction AssignmentOp ] Disjunction
		Expression e = disjunction();
		if (isAssignOp()) {
			Operator op = new Operator(match(token.type()));
			Expression term2 = disjunction();
			e = new Binary(op, term2, e);
		}
		return e;
	}

	private Expression conjunction() {
		// Conjunction → Equality { ‘&&’ Equality }
		Expression e = equality();
		while (token.type().equals(TokenType.And)) {
			Operator op = new Operator(match(token.type()));
			Expression term2 = conjunction();
			e = new Binary(op, e, term2);
		}
		return e;  // student exercise
	}

	private Expression equality() {
		// Equality → Relation [ EquOp Relation ]z
		Expression e = relation();
		while (isEqualityOp()) {
			Operator op = new Operator(match(token.type()));
			Expression term2 = relation();
			e = new Binary(op, e, term2);
		}
		return e;  // student exercise
	}

	private Expression relation() {
		// Relation → Addition [ RelOp Addition ]
		Expression e = addition();
		while (isRelationalOp()) {
			Operator op = new Operator(match(token.type()));
			Expression term2 = addition();
			e = new Binary(op, e, term2);
		}
		return e;  // student exercise
	}

	private Expression addition() {
		// Addition → Term { AddOp  Term }
		Expression e = term();
		while (isAddOp()) {
			Operator op = new Operator(match(token.type()));
			Expression term2 = term();
			e = new Binary(op, e, term2);
		}
		return e;
	}

	private Expression term() {
		// Term → Double { MulOp Double }
		Expression e = Double();
		while (isMultiplyOp()) {
			Operator op = new Operator(match(token.type()));
			Expression term2 = Double();
			e = new Binary(op, e, term2);
		}
		return e;
	}

	private Expression Double() {
		// Double → Factor [ DouOp ]
		Expression term = factor();
		if (isDouOp()) {
			Operator op = new Operator(match(token.type()));
			term = new Unary(op, term);
		}
		return term;
	}

	private Expression factor() {
		// Factor → [ UnaryOp ] Primary
		if (isUnaryOp()) {
			Operator op = new Operator(match(token.type()));
			Expression term = primary();
			return new Unary(op, term);
		} else return primary();
	}

	private Expression primary() {
		// Primary → VariableRef | Literal | ‘(‘ Expression ‘)’ | Function | ‘(‘ Type ’)’ Expression

		if (token.type().equals(TokenType.LeftParen)) {
			match(TokenType.LeftParen);
			if (isType()) {
				Type type = type();
				match(TokenType.RightParen);

				return new TypeCast(type, expression());
			} else {
				Expression expression = expression();
				match(TokenType.RightParen);

				return expression;
			}
		} else if (isLiteral()) {
			return literal();
		} else if (isIdentifier()) {

		}

		// todo
		return null;
	}

	private Expression variableRef() {
		String id = match(TokenType.Identifier);

		if (isLeftBracket()) {
			match(TokenType.LeftBracket);

		}
	}

	/**
	 * *************************************OK****************************************
	 */
	private Type type() {
		// Type  -->  int | bool | float | char 
		// look up enum in API make sure that this is working
		Type t = null;
		if (token.type().equals(TokenType.Int)) {
			t = Type.INT;
		} else if (token.type().equals(TokenType.Bool)) {
			t = Type.BOOL;
		} else if (token.type().equals(TokenType.Float)) {
			t = Type.FLOAT;
		} else if (token.type().equals(TokenType.Char)) {
			t = Type.CHAR;
		} else if (token.type().equals(TokenType.Void)) {
			t = Type.VOID;
		} else if (token.type().equals(TokenType.Time)) {
			t = Type.TIME;
		} else if (token.type().equals(TokenType.Date)) {
			t = Type.DATE;
		} else error("Error in Type construction");
		token = lexer.next();
		return t;
	}

	private Value literal() { // take the stringy part and convert it to the correct return new  typed value. cast it to the correct value
		Value value = null;
		String stval = token.value();

		if (token.type().equals(TokenType.IntLiteral)) {
			value = new IntValue(Integer.parseInt(stval));
			token = lexer.next();
		} else if (token.type().equals(TokenType.FloatLiteral)) {
			value = new FloatValue(Float.parseFloat(stval));
			token = lexer.next();
		} else if (token.type().equals(TokenType.CharLiteral)) {
			value = new CharValue(stval.charAt(0));
			token = lexer.next();
		} else if (token.type().equals(TokenType.TimeLiteral)) {
			value = new TimeValue();
			token = lexer.next();
		} else if (token.type().equals(TokenType.DateLiteral)) {
			value = new DateValue();
			token = lexer.next();
		} else if (token.type().equals(TokenType.True)) {
			value = new BoolValue(true);
			token = lexer.next();
		} else if (token.type().equals(TokenType.False)) {
			value = new BoolValue(false);
			token = lexer.next();
		} else error("Error in literal value contruction");

		return value;
	}

	private boolean isBooleanOp() {
		return token.type().equals(TokenType.And) ||
				token.type().equals(TokenType.Or);
	}

	private boolean isAddOp() {
		return token.type().equals(TokenType.Plus) ||
				token.type().equals(TokenType.Minus);
	}

	private boolean isMultiplyOp() {
		return token.type().equals(TokenType.Multiply) ||
				token.type().equals(TokenType.Divide);
	}

	private boolean isAssignOp() {
		return token.type().equals(TokenType.PlusAssign)
				|| token.type().equals(TokenType.MinusAssign)
				|| token.type().equals(TokenType.MultiplyAssign)
				|| token.type().equals(TokenType.DivideAssign)
				|| token.type().equals(TokenType.ModAssign)
				|| token.type().equals(TokenType.Assign)
				|| token.type().equals(TokenType.PlusPlus)
				|| token.type().equals(TokenType.MinusMinus);
	}

	private boolean isUnaryOp() {
		return token.type().equals(TokenType.Not) ||
				token.type().equals(TokenType.Minus);
	}

	private boolean isEqualityOp() {
		return token.type().equals(TokenType.Equals) ||
				token.type().equals(TokenType.NotEqual);
	}

	private boolean isRelationalOp() {
		return token.type().equals(TokenType.Less) ||
				token.type().equals(TokenType.LessEqual) ||
				token.type().equals(TokenType.Greater) ||
				token.type().equals(TokenType.GreaterEqual);
	}

	private boolean isType() {
		return token.type().equals(TokenType.Int)
				|| token.type().equals(TokenType.Bool)
				|| token.type().equals(TokenType.Float)
				|| token.type().equals(TokenType.Char)
				|| token.type().equals(TokenType.Time)
				|| token.type().equals(TokenType.Date)
				|| token.type().equals(TokenType.Void);
	}

	private boolean isLiteral() {
		return token.type().equals(TokenType.IntLiteral) ||
				isBooleanLiteral() ||
				token.type().equals(TokenType.FloatLiteral) ||
				token.type().equals(TokenType.CharLiteral);
	}

	private boolean isIdentifier() {
		return token.type().equals(TokenType.Identifier);
	}

	private boolean isBooleanLiteral() {
		return token.type().equals(TokenType.True) ||
				token.type().equals(TokenType.False);
	}

	private boolean isComma() {
		return token.type().equals(TokenType.Comma);
	}

	private boolean isSemicolon() {
		return token.type().equals(TokenType.Semicolon);
	}

	private boolean isLeftParen() {
		return token.type().equals(TokenType.LeftParen);
	}

	private boolean isRightParen() {
		return token.type().equals(TokenType.RightParen);
	}

	private boolean isLeftBrace() {
		return token.type().equals(TokenType.LeftBrace);
	}

	private boolean isAssign() {
		return token.type().equals(TokenType.Assign);
	}

	private boolean isRightBrace() {
		return token.type().equals(TokenType.RightBrace);
	}

	private boolean isLeftBracket() {
		return token.type().equals(TokenType.LeftBracket);
	}

	private boolean isRightBracket() {
		return token.type().equals(TokenType.RightBracket);
	}

	private boolean isElse() {
		return token.type().equals(TokenType.Else);
	}

	private boolean isMain() {
		return token.type().equals(TokenType.Main);
	}

	private boolean isStatement() {
		return isSemicolon() ||
				isLeftBrace() ||
				token.type().equals(TokenType.If) ||
				token.type().equals(TokenType.While) ||
				token.type().equals(TokenType.Identifier);
	}


}
