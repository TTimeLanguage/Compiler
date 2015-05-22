package Semantic;

import Lexer.Lexer;
import Paser.Parser;
import Syntax.Program;

/**
 * Created by 병훈 on 2015-05-19.
 */
public class TypeChecker {
	Parser parser;

	public TypeChecker(Parser parser) {
		this.parser = parser;
	}

	public Program getAST() {
		Program unCheckedAST = parser.getAST();

		unCheckedAST.V();

		return unCheckedAST;
	}

	public static void main(String[] args) {
		TypeChecker typeChecker = new TypeChecker(new Parser(new Lexer(args[0])));
		Program program = typeChecker.getAST();

		program.display(0);
	}
}