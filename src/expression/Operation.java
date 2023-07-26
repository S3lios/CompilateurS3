package src.expression;

public class Operation implements Expression {
    public static enum Operator {
        PLUS,
        MINUS,
        TIMES,
        DIVIDE,
        MODULO,
        EQUAL,
        NOT_EQUAL,
        GREATER,
        GREATER_EQUAL,
        LESS,
        LESS_EQUAL,
        AND,
        OR,
        NOT
    }

    Operator operator;
    Expression left;
    Expression right;

    public Operation(Operator operator, Expression left, Expression right) {
        this.operator = operator;
        this.left = left;
        this.right = right;
    }
}
