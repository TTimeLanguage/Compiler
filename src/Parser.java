import java.util.ArrayList;

public class Parser {
	Token token;          // current token from the input stream
    Lexer lexer;
  
    public Parser(Lexer ts) { // Open the C++Lite source program
        lexer = ts;                          // as a token stream, and
        token = lexer.next();            // retrieve its first Token
    }
  
    private String match (TokenType t) {
        String value = token.value();
        if (token.type().equals(t))
            token = lexer.next();
        else
            error(t);
        return value;
    }
  
    private void error(TokenType tok) {
        System.err.println("Syntax error: expecting: " + tok 
                           + "; saw: " + token + " " );
        System.exit(1);
    }
  
    private void error(String tok) {
        System.err.println("Syntax error: expecting: " + tok 
                           + "; saw: " + token+ " " );
        System.exit(1);
    }
    /************************************************************************************************************/
    public Program program() {
		// Program → { Global } 'int' 'main' '(' ')' Statements

		ArrayList<Global> globals = new ArrayList<Global>();
		Statements s = null;

		while (isType()) {
			Type t = type();

			if (t.equals(Type.INT) && isMain()) {
				match(TokenType.Main);			// int main
				match(TokenType.LeftParen);		// (
				match(TokenType.RightParen);	// )
				s = state();            // Statements
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

		Statements s = state();


		return new FunctionDeclaration(type, id, params, s);
	}
    
	private ParamDeclaration paramDeclaration() {
		// ParamDeclarations → ParamDeclaration { ',' ParamDeclaration } | ε

		Type type = type();
		String id = match(TokenType.Identifier);

		return new ParamDeclaration(type, id);
	}
	
	
	
	private Statements state() {
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

		if (isLeftBrace())



		return null;
	}
    
    private Statement statement(){
    	
    	return null;
    }
    
    
    /*
    public Program program(){
    	//Program → { Global } 'int' 'main' '(' ')' Statements
    	//Global → FunctionDeclaration | Declarations
    	//FunctionDeclaration → Type Id '(' ParamDeclarations ’)’ Statements
    	if(token.type().equals(TokenType.Int))	//int이면 main인지 변수,함수인지
    	{
    		match(TokenType.Int);
    		if(token.type().equals(TokenType.Main))
    		//Main
    		{
    			match(TokenType.Main);			//int main
    			match(TokenType.LeftParen);		// (
    			match(TokenType.RightParen);	// )
    			match(TokenType.LeftBrace);		// {
    			Statements s = state();			// Statements
    			match(TokenType.RightBrace);	// }
    			return null;//new Program(b);
    		}
    		else	//전역 or 함수
    		{
    			//Id
    			if(token.type().equals(TokenType.LeftParen))	// (
    			// 함수
    			{
        			//Id							// int Id(~~){~~}
        			match(TokenType.LeftParen);		// (
        			//매개변수
        			match(TokenType.RightParen);	// )
        			match(TokenType.LeftBrace);		// {
        			// 함수내용
        			match(TokenType.RightBrace);	// }
    			}		
    			else
    			// 전역변수
    			{
        			// Id
        			// 뒤에 문자가 있으면 '='
    				// 변수 초기화
    				// 뒤에 ,이면 변수초기화값 다시.
    			}
    		}
    		
    	}
    	else	//int 아닌 전역 or 함수
    	{
    		//type
			//Id
			if(token.type().equals(TokenType.LeftParen))	// ( 있으면 함수
			{
				match(TokenType.LeftParen);		// (
				
				match(TokenType.RightParen);	// )
				
			}		// 함수
			else
			{
				
				
			}		// 전역변수
    	}
		return null;
    }
    */

    /************************************************************************************************************/
    private Statement statement() {
        // Statement --> Skip | IfStatement | Block | WhileStatement | SwitchStatement
    	// | ForStatement | Return | Expression | Break | Continue
    	Statement s = null;
    	//	System.out.println("starting statment()");
    	if (token.type().equals(TokenType.Semicolon))
    		s = new Skip();
    	else if (token.type().equals(TokenType.If))    		//if
    		s = IfStatement();
    	//		System.out.println("block data " + s);}
    	else if (token.type().equals(TokenType.LeftBrace)) 	//block
    		s = Block();
    	else if (token.type().equals(TokenType.While))		//while
    		s = WhileStatement();
    	else if (token.type().equals(TokenType.Switch)) 	//switch
    		s = SwitchStatement();
    	else if (token.type().equals(TokenType.For)) 		//for
    		s = ForStatement();
    	//else if (token.type().equals(TokenType.)) 		//Return
    	//	s = Return();
    	//else if (token.type().equals(TokenType.)) //Expression
    	//	s = Expression();
    	//else if (token.type().equals(TokenType.)) 		//break
    	//	s = Bread();
    	//else if (token.type().equals(TokenType.)) 	//continue
    	//	s = Continue();
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
        while (! token.type().equals(TokenType.Else) )
        {
           if (token.type().equals(TokenType.ElseIf)){
            elseifbranchs.add(elseifbranch);
            Block thenbranch2 = Block();
        }
   
        }        
        Statement elsebranch = new Skip();
        
        if (token.type().equals(TokenType.Else)){
            token = lexer.next();
            elsebranch = statement();
        }
        return new Conditional(test, thenbranch, elseifbranch,elsebranch);// student exercise
	}
    
    private Statement Block() {
    	Block b = new Block(null);
    	Statement s;
    	match(TokenType.LeftBrace);
    	//	System.out.println(" left brace matched");
    	while (isStatement()) {
    		s = state();
    		b.member.add(s);
    	}
        match(TokenType.RightBrace);// end of the block 
        return b;
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
		match(TokenType.Switch);		// switch(?)
		match(TokenType.LeftParen);		
		// expression
		match(TokenType.RightParen);
		match(TokenType.LeftBrace);		// {
		match(TokenType.Case);			//case x : ~~~;break;
		// Literal
		// : 
		// Statement
		// default
		// :
		// Statement
		match(TokenType.RightBrace);	// }
    	return sw;    
	}
    
    private Statement ForStatement() {
		// TODO Auto-generated method stub
		return null;
	}
    
    private Statement Return() {
		// Return → 'return' [ Expression ] ';'
		return null;
	}
    
    private Statement Expression() {
    	// Expression → Disjunction | Assignment 
		return null;
	}
    
    private Statement Bread() {
		// Break → 'break' ';'
    	// break
    	match(TokenType.Semicolon);
		return null;
	}
    
    private Statement Continue() {
		// Continue → 'continue' ';'
		return null;
	}	
	/****************************************OK*****************************************/
	private Type type () {
        // Type  -->  int | bool | float | char 
    	// look up enum in API amke sure that this is working 
    	Type t = null;
    	if (token.type().equals(TokenType.Int)) {
            t = Type.INT;		
            //System.out.println(" Type  is int");
    	} else if (token.type().equals(TokenType.Bool)) {
			t = Type.BOOL;
			//System.out.println(" Type  is bool");
    	} else if (token.type().equals(TokenType.Float)) {
			t = Type.FLOAT;
			//System.out.println(" Type  is float");
    	} else if (token.type().equals(TokenType.Char)) {
			t = Type.CHAR;
			//System.out.println(" Type  is char");
    	} else if (token.type().equals(TokenType.Void)) {
			t = Type.VOID;
			//System.out.println(" Type  is char");
    	} else if (token.type().equals(TokenType.Time)) {
				t = Type.TIME;
				//System.out.println(" Type  is char");
    	} else if (token.type().equals(TokenType.Date)) {
			t = Type.DATE;
			//System.out.println(" Type  is char");
    	}else error ("Error in Type construction");
    	token = lexer.next();
    	return t;          
    }
  
    private Value literal( ) { // take the stringy part and convert it to the correct return new  typed value. cast it to the correct value
    	Value value = null;
		String stval = token.value();
		if (token.type().equals(TokenType.IntLiteral)) {
			value = new IntValue (Integer.parseInt(stval));
			token = lexer.next();
			//System.out.println("found int literal");
		}
		else if (token.type().equals(TokenType.FloatLiteral))  {
			value = new FloatValue(Float.parseFloat(stval));
			token = lexer.next();
		}
		else if (token.type().equals(TokenType.CharLiteral))  {
			value = new CharValue(stval.charAt(0));
			token = lexer.next();
		}
		else if (token.type().equals(TokenType.TimeLiteral))  {
			value = new TimeValue();
			token = lexer.next();
		}
		else if (token.type().equals(TokenType.DateLiteral))  {
			value = new DateValue();
			token = lexer.next();
		}
	    else if (token.type().equals(TokenType.True))  {
	        value = new BoolValue(true);
	        token = lexer.next();
	    }
	    else if (token.type().equals(TokenType.False))  {
	        value = new BoolValue(false);
	        token = lexer.next();
	    }
	    else error ("Error in literal value contruction");
		return value;
    }
    
    private boolean isBooleanOp() {
    	return token.type().equals(TokenType.And) || 
    	    token.type().equals(TokenType.Or);
    } 

    private boolean isAddOp( ) {
        return token.type().equals(TokenType.Plus) ||
               token.type().equals(TokenType.Minus);
    }
        
    private boolean isMultiplyOp( ) {
        return token.type().equals(TokenType.Multiply) ||
               token.type().equals(TokenType.Divide);
    }
    private boolean isAssignmentOper( ) {
    	return token.type().equals(TokenType.PlusAssign)
    			||token.type().equals(TokenType.MinusAssign)
    			||token.type().equals(TokenType.MultiplyAssign)
    			||token.type().equals(TokenType.DivideAssign)
    			||token.type().equals(TokenType.ModAssign)
    			||token.type().equals(TokenType.Assign)
    			||token.type().equals(TokenType.PlusPlus)
    			||token.type().equals(TokenType.MinusMinus);
    	
    }
    private boolean isUnaryOp( ) {
        return token.type().equals(TokenType.Not) ||
               token.type().equals(TokenType.Minus);
    }
        
    private boolean isEqualityOp( ) {
        return token.type().equals(TokenType.Equals) ||
            token.type().equals(TokenType.NotEqual);
    }
        
    private boolean isRelationalOp( ) {
        return token.type().equals(TokenType.Less) ||
               token.type().equals(TokenType.LessEqual) || 
               token.type().equals(TokenType.Greater) ||
               token.type().equals(TokenType.GreaterEqual);
    }
    private boolean isType( ) {
        return token.type().equals(TokenType.Int)
            || token.type().equals(TokenType.Bool) 
            || token.type().equals(TokenType.Float)
            || token.type().equals(TokenType.Char)
            || token.type().equals(TokenType.Time)
            || token.type().equals(TokenType.Date)
            || token.type().equals(TokenType.Void);
    }
    private boolean isLiteral( ) {
        return token.type().equals(TokenType.IntLiteral) ||
            isBooleanLiteral() ||
            token.type().equals(TokenType.FloatLiteral) ||
            token.type().equals(TokenType.CharLiteral);
    }
    
    private boolean isBooleanLiteral( ) {
        return token.type().equals(TokenType.True) ||
            token.type().equals(TokenType.False);
    }

    private boolean isComma( ) {
	return token.type().equals(TokenType.Comma);
    }
   
    private boolean isSemicolon( ) {
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
 
    private boolean isRightBrace() {
	return token.type().equals(TokenType.RightBrace);
    }

	private boolean isLeftBracket() {
		return token.type().equals(TokenType.LeftBracket);
	}

	private boolean isRightBracket() {
		return token.type().equals(TokenType.RightBracket);
	}

	private boolean isMain() {
		return token.type().equals(TokenType.Main);
	}

	private boolean isStatement() {
	return 	isSemicolon() ||
		isLeftBrace() ||
		token.type().equals(TokenType.If) ||
		token.type().equals(TokenType.While) ||
		token.type().equals(TokenType.Identifier); 
    }

    
}
