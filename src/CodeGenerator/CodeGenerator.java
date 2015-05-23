package CodeGenerator;

import Lexer.Lexer;
import Parser.Parser;
import Semantic.TypeChecker;


public class CodeGenerator {
	TypeChecker typeChecker;
	String outputFile;

	public CodeGenerator(String inputFile, String outPutFile) {
		typeChecker = new TypeChecker(new Parser(new Lexer(inputFile)));
		this.outputFile = outPutFile;
	}
	
	public void codeGenerate() {

	}

	public static void main(String[] args) {
		if (args.length != 2) {
			System.err.println("initialize error. must have two parameter");
			System.exit(1);
		}
		CodeGenerator codeGen = new CodeGenerator(args[0], args[1]);

		codeGen.codeGenerate();
	}
}
