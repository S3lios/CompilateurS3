package src.instruction;

import src.expression.Expression;

import src.type.Type;

public class VariableDeclaration implements Instruction {

	String name;
	Type type;
	Expression value;
	
	public VariableDeclaration(Type type, String name, Expression value) {
		this.name = name;
		this.type = type;
		this.value = value;
	}


}
