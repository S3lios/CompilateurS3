package src.instruction;

import src.expression.Expression;

public class WhileLoop implements Instruction{
    Expression condition;
    Instruction instruction;

    public WhileLoop(Expression condition, Instruction instruction) {
        this.condition = condition;
        this.instruction = instruction;
    }
}
