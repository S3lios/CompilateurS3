package src.instruction;

import java.util.List;

public class InstructionList implements Instruction {
	List<Instruction> instructions;

	public InstructionList() {
		this.instructions = new java.util.LinkedList<Instruction>();
	}

	public InstructionList add(Instruction instruction) {
		this.instructions.add(0, instruction);
		return this;
	}
}
