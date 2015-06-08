package CodeGenerator;

import Lexer.Lexer;
import Parser.Parser;
import Semantic.TypeChecker;
import Syntax.Expression;
import Syntax.Operator;
import Syntax.Program;
import Syntax.Type;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.*;
import java.util.concurrent.TimeUnit;


public class CodeGenerator {
	/**
	 * 전역변수들의 map.
	 * 이름을 key로 넣으면 그 변수에 해당하는 <tt>SymbolTableElement</tt>객체를 가져온다.
	 * <p>
	 * Code Generate시간에 사용됨.
	 */
	protected static final HashMap<String, SymbolTableElement> globalSymbolTable = new HashMap<>();

	/**
	 * 지역변수들의 map.
	 * 현제 U-code로 변환중인 함수의 지역변수 map
	 * <p>
	 * Code Generate시간에 사용됨.
	 */
	protected static final HashMap<String, SymbolTableElement> currentSymbolTable = new HashMap<>();

	/**
	 * <tt>sym</tt>으로 정의가 끝난 후 초기화를 진행해야하는 초기값을 가진 변수들을 초기화할때 사용한다.
	 * <p>
	 * key로 <tt>String</tt>형 변수의 이름과, value로 초기화 값을 <tt>Expression</tt>의 객체로 가진다.
	 */
	private static final LinkedHashMap<String, ArrayList<Expression>> initList = new LinkedHashMap<>();

	/**
	 * TTime 언어 내부에서 사용되는 함수를 Code generating전에 결과코드(U-code)에 link해준다.
	 * <p>
	 * 예를들어 <tt>time</tt>변수끼리의 덧샘은 TTime언어 내부적으로는 <tt>addTime</tt>함수의 호출로 이뤄진다.
	 */
	private static final HashSet<String> needToLinkFunction = new HashSet<>();

	/**
	 * 전역변수 선언부의 U-code가 완성됐는지 여부.
	 * <p>
	 * 전역 변수 선언이 끝나면 초기화가 있는 변수를 초기화할때 쓴다.
	 */
	private static boolean finishGlobalDec = false;

	/**
	 * 현재 선언중인 부분의 마지막 offset.
	 * <p>
	 * sym할때 2번째 매개변수인 변수의 시작주소를 주기 위해 쓴다.
	 */
	private static int variableOffset = 0;

	private static int lastLoopBranchNumber = 0;

	private static int lastIfBranchNumber = 0;

	private static int lastElseIfBranchNumber = 0;


	private TypeChecker typeChecker;
	String outputFile;
	private static FileWriter writer;

	public CodeGenerator(String inputFile, String outPutFile) {
		typeChecker = new TypeChecker(new Parser(new Lexer(inputFile)));
		this.outputFile = outPutFile;

		try {
			writer = new FileWriter(new File(outPutFile));
		} catch (IOException e) {
			System.out.println("error in open or create file");
			e.printStackTrace();
		}
	}

	public void codeGenerate() {
		Program AST = typeChecker.getAST();
		AST.display(0);

		for (String string : needToLinkFunction) {
			DefinedFunction.run(string);
		}

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


	/**
	 * TTime언어에서의 time변수의 덧셈 같은 연산은 U-code에 정의 되어있지 않으므로,
	 * 컴파일러에서 제공하는 함수를 이용해서 수행한다.
	 * type checking중에 필요한 연산함수를 기억해 놨다가, code generate하기전에 Link하는데,
	 * type checking중에 연산함수가 필요하다는것을 알게되면 이 메소드를 통해 필요한 연산함수를 등록해놓는다.
	 *
	 * @param operator 컴파일러가 추가로 Link할 함수의 이름
	 * @see Syntax.Unary
	 * @see Syntax.Binary
	 */
	public static void addOperatorLink(String operator) {
		switch (operator) {
			case Operator.TIME_PLUS:
			case Operator.TIME_PLUS_ASSIGN:
				needToLinkFunction.add("addTime");
				needToLinkFunction.add("validTime");
				break;
			case Operator.TIME_MINUS:
			case Operator.TIME_MINUS_ASSIGN:
				needToLinkFunction.add("subTime");
				needToLinkFunction.add("validTime");
				break;
			case Operator.TIME_TIMES:
			case Operator.TIME_TIMES_ASSIGN:
				needToLinkFunction.add("mulTime");
				needToLinkFunction.add("validTime");
				break;
			case Operator.TIME_DIV:
			case Operator.TIME_DIV_ASSIGN:
				needToLinkFunction.add("divTime");
				needToLinkFunction.add("validTime");
				break;
			case Operator.TIME_MOD:
			case Operator.TIME_MOD_ASSIGN:
				needToLinkFunction.add("modTime");
				needToLinkFunction.add("validTime");
				break;
			case Operator.TIME_PP:
			case Operator.TIME_MM:
				needToLinkFunction.add("validTime");
				break;

			case Operator.DATE_PLUS:
			case Operator.DATE_PLUS_ASSIGN:
				needToLinkFunction.add("addDate");
				break;
			case Operator.DATE_MINUS:
			case Operator.DATE_MINUS_ASSIGN:
				needToLinkFunction.add("subDate");
				break;
			case Operator.DATE_PP:
			case Operator.DATE_MM:
				needToLinkFunction.add("addDate");
				break;
		}
	}


	/**
	 * TTime언어 에서는 존재하지만, U-code에서 존재하지 않는 함수들을 사용한다면,
	 * 이 메소드를 code generating전에 호출해서 등록 해야만 U-code로 변환된 결과파일에 그 함수를 Link할 수 있다.
	 *
	 * @param functionName Link할 함수 이름
	 * @see Syntax.Function
	 */
	public static void addFunctionLink(String functionName) {
		needToLinkFunction.add(functionName);
	}


	/******************************************************
	 * 파일에 U-code를 직접 쓰기위해 사용되는 함수들.
	 * <p>
	 * todo 설명추가바람
	 ******************************************************/

	protected static void genFunc(String name, int size, int block, int lexical) {
		StringBuilder result = new StringBuilder(name)
				.append("\tproc\t")
				.append(size).append(' ')
				.append(block).append(' ')
				.append(lexical).append("\r\n");

		try {
			writer.write(result.toString());
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	protected static void genCode(String instr, int... args) {
		StringBuilder result = new StringBuilder("\t").append(instr);

		if (args.length > 0) {
			result.append('\t');

			for (int arg : args) {
				result.append(arg).append(' ');
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

	protected static void genLabel(String label) {
		StringBuilder result = new StringBuilder(label);

		result.append("\tnop\r\n");

		try {
			writer.write(result.toString());
		} catch (IOException e) {
			System.out.println("error in file write");
			e.printStackTrace();
		}
	}


	/******************************************************
	 * for나 while같은 루프의 라벨을 관리하는 메소드
	 * <p>
	 * todo 설명추가바람
	 ******************************************************/

	public static int getLastLoopBranchNumber() {
		return lastLoopBranchNumber;
	}

	public static int makeLoopStartBranch() {
		genLabel(getLoopStartBranch(lastLoopBranchNumber));

		lastLoopBranchNumber++;

		return lastLoopBranchNumber - 1;
	}

	public static void makeForStartBranch(int forNum) {
		genLabel(getForStartBranch(forNum));
	}

	public static void makeLoopEndBranch(int labelStartNum) {
		genLabel(getLoopEndBranch(labelStartNum));
	}

	public static String getForStartBranch(int forNum) {
		return "$FOR" + forNum;
	}

	public static String getLoopStartBranch(int labelStartNum) {
		return "$LOOP" + labelStartNum;
	}

	public static String getLoopEndBranch(int labelStartNum) {
		return "$LOOPE" + labelStartNum;
	}


	/******************************************************
	 * if나 else if같이 조건문의 루프의 라벨을 관리하는 메소드
	 * <p>
	 * todo 설명추가바람
	 ******************************************************/

	public static int getIfNum() {
		lastIfBranchNumber++;

		lastElseIfBranchNumber = 0;

		return lastIfBranchNumber - 1;
	}

	public static void makeElseIfBranch(int ifLabelNum) {
		genLabel(getElseIfBranch(ifLabelNum));

		lastElseIfBranchNumber++;
	}

	public static void makeElseBranch(int ifLabelNum) {
		genLabel(getElseBranch(ifLabelNum));
	}

	public static void makeIfExitBranch(int ifLabelNum) {
		genLabel(getIfExitBranch(ifLabelNum));
	}

	public static String getElseIfBranch(int ifLabelNum) {
		return getElseIfBranch(ifLabelNum, lastElseIfBranchNumber);
	}

	public static String getElseIfBranch(int ifLabelNum, int label) {
		return "$ELIF" + ifLabelNum + "-" + label;
	}

	public static String getElseBranch(int ifLabelNum) {
		return "$ELSE" + ifLabelNum;
	}

	public static String getIfExitBranch(int ifLabelNum) {
		return "$IFE" + ifLabelNum;
	}


	/**
	 * 현재 전역변수 구간을 선언중인지 확인하는 함수.
	 * <p>
	 * <tt>Init</tt>클래스 객체(변수의 선언)에서 전역일 경우
	 * code generate시간에 sym에서 block을 1로 해야하고,
	 * 지역일 경우 2로 해야하는데 이것을 구분하기 위해 사용함.
	 *
	 * @return 현재 전역변수를 선언중인지 여부
	 * @see Syntax.Init
	 */
	public static boolean isInGlobalDeclaration() {
		return !finishGlobalDec;
	}

	/**
	 * code generate때 함수마다 사용 가능한 변수의 테이블인 심볼 테이블이 다른 내용을 갖게되는데,
	 * 이것을 각 함수마다 새로운 테이블을 만들어주기위해서 선언된 함수 부분을 U-code로 변환할때는
	 * 이 메소드를 호출해서 함수를 U-code로 변환 시작을 알리고 시작해야한다.
	 * <p>
	 * todo 설명추가바람
	 * todo 개선 여지
	 *
	 * @see Syntax.FunctionDeclaration
	 */
	public static void startLocalDeclaration() {
		currentSymbolTable.clear();
		currentSymbolTable.putAll(globalSymbolTable);
	}

	/**
	 * TTime언어의 함수에서 지역변수 선언을 U-code로 변환 할 경우 sym으로 변환 가능하다.
	 * 하지만 초기화 하며 선언하는것은 U-code에서 한 명령어로 불가능하다.
	 * 따라서 함수의 지역변수 선언중에 <tt>CodeGenerator</tt>의 <tt>sym</tt>메소드를 통해 변수 선언을하면,
	 * 초기값이 있다면 저장해 놨다가, 지역변수 선은 후에 이 메소드를 호출해서 초기값들을 대입해야한다.
	 *
	 * @see Syntax.Statements
	 */
	public static void finishLocalDeclaration() {
		for (String id : initList.keySet()) {
			int i = 0;
			for (Expression exp : initList.get(id)) {
				exp.genCode();

				str(id, i++);
			}
		}

		initList.clear();

		variableOffset = 0;
	}

	/**
	 * 전역변수선언 끝에 반드시 호출해야함.
	 * <p>
	 * todo 설명추가바람
	 */
	public static void finishGlobalDeclaration() {
		finishGlobalDec = true;

		globalSymbolTable.putAll(currentSymbolTable);

		finishLocalDeclaration();
	}

	/**
	 * 변수 선언중 초기값을 선언(<tt>sym</tt>)중에는 이 메소드를 실행해서 나중에 초기화 할 목록에 추가할 뿐,
	 * 대입하지 않고 넘어간다.
	 *
	 * @param id   초기값을 대입할 변수 이름(TTime언어에서 정의한 언어)
	 * @param init 초기값
	 */
	public static void addInit(String id, Expression init) {
		if (!initList.containsKey(id)) {
			initList.put(id, new ArrayList<>());
		}

		initList.get(id).add(init);
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

	public static void mult() {
		genCode("mult");
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

	public static void str(String id, int index) {
		SymbolTableElement element = currentSymbolTable.get(id);
		genCode("str", element.getBlockNum(), element.getStartAddress() + index);
	}

	public static void ldc(int val) {
		genCode("ldc", val);
	}

	public static void lda(String id) {
		SymbolTableElement element = currentSymbolTable.get(id);
		genCode("lda", element.getBlockNum(), element.getStartAddress());
	}

	public static void ujp(String label) {
		try {
			writer.write("\tujp\t" + label + "\r\n");
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	public static void tjp(String label) {
		try {
			writer.write("\ttjp\t" + label + "\r\n");
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	public static void fjp(String label) {
		try {
			writer.write("\tfjp\t" + label + "\r\n");
		} catch (IOException e) {
			e.printStackTrace();
		}
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

	public static void proc(String id, int size) {
		try {
			writer.write(id);
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
		finishGlobalDec = true;
	}

	public static void sym(String id, int block, int size) {
		genCode("sym", block, variableOffset + 1, size);

		currentSymbolTable.put(id, new SymbolTableElement(block, variableOffset + 1, size));

		variableOffset += size;
	}
}
