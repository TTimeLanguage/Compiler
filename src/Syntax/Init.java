package Syntax;

/**
 * Abstract Syntax :
 * Syntax.Init = Syntax.ArrayInit | Syntax.NoArrayInit
 */
abstract public class Init extends AbstractSyntax {
	protected Type type;
	protected String name;
}