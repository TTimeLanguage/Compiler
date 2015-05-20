package Syntax;

/**
 * Abstract Syntax :
 * Init = ArrayInit | NoArrayInit
 */
abstract public class Init extends AbstractSyntax {
	protected Type type;
	protected String name;
}