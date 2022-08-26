package me.coley.recaf.parse.bytecode.ast;

public class ItfAST extends AST {
	/**
	 * @param line  Line number this node is written on.
	 * @param start Offset from line start this node starts at.
	 */
	public ItfAST(int line, int start) {
		super(line, start);
	}

	@Override
	public String print() {
		return "itf";
	}
}
