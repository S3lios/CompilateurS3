package src.instruction;

import src.expression.Expression;

public class VariableAffectation implements Instruction {
	
	String name;
	Expression value;
	
	public VariableAffectation(String name, Expression value) {
		this.name = name;
		this.value = value;
	}
}
