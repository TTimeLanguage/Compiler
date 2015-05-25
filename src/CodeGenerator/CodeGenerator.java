package CodeGenerator;

import Lexer.Lexer;
import Parser.Parser;
import Semantic.TypeChecker;
import Syntax.Expression;
import Syntax.Program;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;


public class CodeGenerator {
	/**
	 * 전역변수들의 map.
	 * 이름을 key로 넣으면 그 변수에 해당하는 <tt>SymbolTableElement</tt>객체를 가져온다.
	 * <p>
	 * Code Generate시간에 사용됨.
	 */
	protected static HashMap<String, SymbolTableElement> globalSymbolTable = null;

	/**
	 * 지역변수들의 map.
	 * 현제 U-code로 변환중인 함수의 지역변수 map
	 * <p>
	 * Code Generate시간에 사용됨.
	 */
	protected static HashMap<String, SymbolTableElement> currentSymbolTable = null;

	private static boolean finishGlobalDecl = false;
	private static int variableOffset = 0;
	private static ArrayList<Expression> initList = new ArrayList<>();
	private TypeChecker typeChecker;
	String outputFile;
	private File file;
	private static FileWriter writer;

	public CodeGenerator(String inputFile, String outPutFile) {
		typeChecker = new TypeChecker(new Parser(new Lexer(inputFile)));
		this.outputFile = outPutFile;

		try {
			file = new File(outPutFile);
			writer = new FileWriter(file);
		} catch (IOException e) {
			System.out.println("error in open or create file");
			e.printStackTrace();
		}
	}

	public void codeGenerate() {
		Program AST = typeChecker.getAST();
		AST.display(0);

		AST.genCode();

		try {
			writer.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	public static void main(String[] args) {
		if (args.length != 2) {
			System.err.println("initialize error. must have two parameter");
			System.exit(1);
		}
		CodeGenerator codeGen = new CodeGenerator(args[0], args[1]);

		codeGen.codeGenerate();
	}


	private static void genCode(String instr, int... args) {
		StringBuilder result = new StringBuilder("\t").append(instr);

		if (args.length > 0) {
			result.append('\t');

			for (int arg : args) {
				result.append(' ').append(arg);
			}
		}
		result.append("\r\n");

		try {
			writer.write(result.toString());
		} catch (IOException e) {
			System.out.println("error in file write");
			e.printStackTrace();
		}
	}

	private void genLabel(String label) {
		StringBuilder result = new StringBuilder(label);

		result.append("\tnop\r\n");

		try {
			writer.write(result.toString());
		} catch (IOException e) {
			System.out.println("error in file write");
			e.printStackTrace();
		}
	}

	public static boolean isInGlobalDeclaration() {
		return !finishGlobalDecl;
	}

	public static void finishLocalDeclaration() {
		for (Expression expression : initList) {
			expression.genCode();
		}

		initList.clear();

		variableOffset = 0;
	}

	public static void finishGlobalDeclaration() {
		finishGlobalDecl = true;

		finishLocalDeclaration();
	}

	public static void addInit(Expression init) {
		initList.add(init);
	}


	public static void notop() {
		genCode("notop");
	}

	public static void neg() {
		genCode("neg");
	}

	public static void inc() {
		genCode("inc");
	}

	public static void dec() {
		genCode("dec");
	}

	public static void dup() {
		genCode("dup");
	}

	public static void add() {
		genCode("add");
	}

	public static void sub() {
		genCode("sub");
	}

	public static void multi() {
		genCode("multi");
	}

	public static void div() {
		genCode("div");
	}

	public static void mod() {
		genCode("mod");
	}

	public static void swp() {
		genCode("swp");
	}

	public static void and() {
		genCode("and");
	}

	public static void or() {
		genCode("or");
	}

	public static void gt() {
		genCode("gt");
	}

	public static void lt() {
		genCode("lt");
	}

	public static void ge() {
		genCode("ge");
	}

	public static void le() {
		genCode("le");
	}

	public static void eq() {
		genCode("eq");
	}

	public static void ne() {
		genCode("ne");
	}

	public static void lod(String id) {
		SymbolTableElement element = currentSymbolTable.get(id);
		genCode("lod", element.getBlockNum(), element.getStartAddress());
	}

	public static void str(String id) {
		SymbolTableElement element = currentSymbolTable.get(id);
		genCode("str", element.getBlockNum(), element.getStartAddress());
	}

	public static void ldc(int val) {
		genCode("ldc", val);
	}

	public static void lda(String id) {
		SymbolTableElement element = currentSymbolTable.get(id);
		genCode("lda", element.getBlockNum(), element.getStartAddress());
	}

	public static void ujp() {
		genCode("ujp");
	}

	public static void tjp() {
		genCode("tjp");
	}

	public static void fjp() {
		genCode("fjp");
	}

	public static void chkh() {
		// todo
		genCode("chkh");
	}

	public static void chkl() {
		// todo
		genCode("chkl");
	}

	public static void ldi() {
		genCode("ldi");
	}

	public static void sti() {
		genCode("sti");
	}

	public static void call(String label) {
		try {
			writer.write("\tcall\t" + label + "\r\n");
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	public static void ret() {
		genCode("ret");
	}

	public static void retv() {
		genCode("retv");
	}

	public static void ldp() {
		genCode("ldp");
	}

	public static void proc(String name, int size) {
		try {
			writer.write(name);
			genCode("proc", size, 2, 2);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	public static void end() {
		genCode("end");
	}

	public static void bgn() {
		genCode("bgn", variableOffset);
		finishGlobalDecl = true;
	}

	public static void sym(int block, int size) {
		genCode("sym", block, variableOffset + 1, size);
		variableOffset += size;
	}
}
