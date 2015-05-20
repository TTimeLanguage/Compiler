import Syntax.Program;

/**
 * Created by 병훈 on 2015-05-19.
 */
public class TypeChecker {
	public static void main(String[] args) {
		Parser parser = new Parser(new Lexer(args[0]));
		Program program = parser.program();

		program.V();
		program.display(0);
	}
}
