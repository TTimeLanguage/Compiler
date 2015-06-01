package Lexer;

import Token.Token;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;

public class Lexer {
	private boolean isEof = false;
	private char ch = ' ';
	private BufferedReader input;
	private String line = "";
	private int lineno = 0;
	private int col = 1;
	private final String letters = "abcdefghijklmnopqrstuvwxyz" + "ABCDEFGHIJKLMNOPQRSTUVWXYZ";
	private final String digits = "0123456789";
	private final char eolnCh = '\n';
	private final char eofCh = '\004';


	public Lexer(String fileName) {
		try {
			input = new BufferedReader(new FileReader(fileName));
		} catch (FileNotFoundException e) {
			System.out.println("File not found: " + fileName);
			System.exit(1);
		}
	}

	private char nextChar() {
		if (ch == eofCh) {
			error("Attempt to read past end of file");
		}

		col++;

		if (col >= line.length()) {
			try {
				line = input.readLine();
			} catch (IOException e) {
				e.printStackTrace();
				System.exit(1);
			}

			if (line == null) {
				line = "" + eofCh;
			} else {
				// System.out.println(lineno + ":\t" + line);
				lineno++;
				line += eolnCh;
			}

			col = 0;
		}

		return line.charAt(col);
	}


	public Token next() {
		do {
			if (isLetter(ch)) { // ident or keyword
				String spelling = concat(letters + digits);
				return Token.keyword(spelling);

			} else if (isDigit(ch)) { // int or float literal
				String number = concat(digits);

				if (ch == '.') {
					number += concat(digits);

					if (ch == '.') {
						number += concat(digits);
						return Token.mkDateLiteral(number);
					}
					return Token.mkFloatLiteral(number);

				} else if (ch == ':') {

					if (!isDigit(nextChar())) {
						return Token.mkIntLiteral(number);
					} else {
						col--;
					}

					number += concat(digits);

					if (ch == ':') {
						number += concat(digits);
						return Token.mkTimeLiteral(number);

					} else {
						error("illegal time literal");
					}
				}
				return Token.mkIntLiteral(number);
			} else switch (ch) {
				case ' ':
				case '\t':
				case '\r':
				case eolnCh:
					ch = nextChar();
					break;

				case '/':
					ch = nextChar();
					if (ch != '/') {
						if (ch != '=') {
							return Token.divideTok;
						} else {
							ch = nextChar();
							return Token.divideAssignTok;
						}
					}

					// comment
					do {
						ch = nextChar();
					} while (ch != eolnCh);
					ch = nextChar();
					break;

				case '\'':  // char literal
					char ch1 = nextChar();
					nextChar(); // get '
					ch = nextChar();
					return Token.mkCharLiteral("" + ch1);

				case eofCh:
					return Token.eofTok;

				case '+':
					return chkOpt3('+', '=', Token.plusTok, Token.plusAssignTok, Token.plusPlusTok);
				case '-':
					return chkOpt3('-', '=', Token.minusTok, Token.minusAssignTok, Token.minusMinusTok);
				case '*':
					return chkOpt('=', Token.multiplyTok, Token.multiplyAsssignTok);
				case '%':
					return chkOpt('=', Token.modTok, Token.modAssignTok);
				case '(':
					ch = nextChar();
					return Token.leftParenTok;
				case ')':
					ch = nextChar();
					return Token.rightParenTok;
				case '{':
					ch = nextChar();
					return Token.leftBraceTok;
				case '}':
					ch = nextChar();
					return Token.rightBraceTok;
				case '[':
					ch = nextChar();
					return Token.leftBracketTok;
				case ']':
					ch = nextChar();
					return Token.rightBracketTok;
				case ';':
					ch = nextChar();
					return Token.semicolonTok;
				case '&':
					return chkOpt('&', Token.refTok, Token.andTok);
				case '|':
					check('|');
					return Token.orTok;
				case '=':
					return chkOpt('=', Token.assignTok, Token.eqeqTok);
				case '<':
					return chkOpt('=', Token.ltTok, Token.lteqTok);
				case '>':
					return chkOpt('=', Token.gtTok, Token.gteqTok);
				case '!':
					return chkOpt('=', Token.notTok, Token.noteqTok);
				case ',':
					ch = nextChar();
					return Token.commaTok;
				case ':':
					ch = nextChar();
					return Token.colonTok;

				default:
					error("Illegal character " + ch);
			}
		} while (true);
	}


	private boolean isLetter(char c) {
		return (c >= 'a' && c <= 'z' || c >= 'A' && c <= 'Z');
	}

	private boolean isDigit(char c) {
		return (c >= '0' && c <= '9');
	}

	private void check(char c) {
		ch = nextChar();
		if (ch != c)
			error("Illegal character, expecting " + c);
		ch = nextChar();
	}

	private Token chkOpt(char c, Token one, Token two) {
		ch = nextChar();
		if (c == ch) {
			ch = nextChar();
			return two;

		} else {
			return one;
		}
	}

	private Token chkOpt3(char c1, char c2, Token one, Token two, Token three) {
		ch = nextChar();
		if (c1 == ch) {
			ch = nextChar();
			return three;

		} else if (c2 == ch) {
			ch = nextChar();
			return two;

		} else {
			return one;
		}
	}

	private String concat(String set) {
		String r = "";

		do {
			r += ch;
			ch = nextChar();
		} while (set.indexOf(ch) >= 0);

		return r;
	}

	public void error(String msg) {
		System.err.print(line);
		System.err.println("Error: column " + col + " " + msg);
		System.exit(1);
	}

	static public void main(String[] argv) {
		Lexer lexer = new Lexer(argv[0]);
		Token tok = lexer.next();

		while (tok != Token.eofTok) {
			System.out.println(tok.toString());
			tok = lexer.next();
		}
	}
}

