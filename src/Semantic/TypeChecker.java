package Semantic;

import Lexer.Lexer;
import Parser.Parser;
import Syntax.Program;


public class TypeChecker {
	Parser parser;

	public TypeChecker(Parser parser) {
		this.parser = parser;
	}

	public Program getAST() {
		Program unCheckedAST = parser.getAST();

		unCheckedAST.validation();

		return unCheckedAST;
	}

	public static void main(String[] args) {
		TypeChecker typeChecker = new TypeChecker(new Parser(new Lexer(args[0])));
		Program program = typeChecker.getAST();

		program.display(0);
	}
}