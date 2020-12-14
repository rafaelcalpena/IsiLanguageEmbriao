package br.com.professorisidro.isilanguage.ast;

public class CommandComentario extends AbstractCommand {

	private String expr;
	
	public CommandComentario (String expr) {
		this.expr = expr;
	}
	
	@Override
	public String generateJavaCode() {
		// TODO Auto-generated method stub
		return ("// " + expr + "\n"  );
	}
	@Override
	public String toString() {
		return "CommandComentario [ " + expr + " ]";
	}
}
