package Token;

public enum TokenType {
	Void, Bool, Int, Char, Float, Date, Time, Main, True, False, If, Else, While, For, Switch, Case, Default, Break,
	Continue, Return, Eof,
	LeftBrace, RightBrace, LeftBracket, RightBracket, LeftParen, RightParen,
	Semicolon, Colon, Comma, Assign, Equals, Less, LessEqual, Greater, GreaterEqual, Not, NotEqual,
	Plus, PlusAssign, PlusPlus, Minus, MinusAssign, MinusMinus, Multiply, MultiplyAssign,
	Divide, DivideAssign, Mod, ModAssign, And, Or, Ref,
	Identifier, IntLiteral, FloatLiteral, CharLiteral, TimeLiteral, DateLiteral
}