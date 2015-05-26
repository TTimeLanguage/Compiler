package Semantic;

import Syntax.FunctionDeclaration;
import Syntax.ParamDeclaration;
import Syntax.Type;

import java.util.ArrayList;

/**
 * ÇÔ¼ö ÇÏ³ªÀÇ ¿ÏÀüÇÑ Á¤º¸(ÇÔ¼öÀÇ ¹İÈ¯ Å¸ÀÔ, ÀÌ¸§, ÆÄ¶ó¹ÌÅÍµéÀÇ Å¸ÀÔ)¸¦ ÀúÀåÇÔ.
 * <p>
 * ÇÔ¼ö Á¤ÀÇ Å¸´ç¼º È®ÀÎÀ» À§ÇØ »ç¿ëµÊ.
 *
 * @see Parser.Parser
 * @see FunctionSet
 */
public class FunctionInfo {
	/**
	 * ÇÔ¼öÀÇ ¹İÈ¯ Å¸ÀÔ
	 */
	protected Type type;
	/**
	 * ÇÔ¼öÀÇ ÀÌ¸§
	 */
	protected String name;
	/**
	 * ÆÄ¶ó¹ÌÅÍµéÀÇ Å¸ÀÔÀ» <tt>Type</tt>ÇüÀ¸·Î ÀúÀå
	 */
	protected ArrayList<Type> paramType;

	public FunctionInfo(FunctionDeclaration declaration) {
		type = declaration.getType();
		name = declaration.getName();
		paramType = new ArrayList<>();

		for (ParamDeclaration param : declaration.getParams()) {
			paramType.add(param.getType());
		}
	}


	/**
	 * ì´ ê°ì²´ê°€ ê°€ë¦¬í‚¤ëŠ” í•¨ìˆ˜ì˜ ë°˜í™˜í˜•ì„ <tt>Type</tt>í˜•ìœ¼ë¡œ ë°˜í™˜í•œë‹¤.
	 *
	 * @return ì´ ê°ì²´ê°€ ê°€ë¦¬í‚¤ëŠ” í•¨ìˆ˜ì˜ ë°˜í™˜í˜•
	 */
	public Type getType() {
		return type;
	}

	@Override
	public boolean equals(Object obj) {
		if (!(obj instanceof FunctionInfo)) return false;

		FunctionInfo tmp = (FunctionInfo) obj;

		return name.equals(tmp.name) && paramType.equals(tmp.paramType);
	}

	@Override
	public String toString() {
		StringBuilder result = new StringBuilder("");

		result.append(type).append(" ").append(name).append(" ");
		for (Type type : paramType) {
			result.append(type).append(" ");
		}

		return result.toString();
	}
}
