package ast.stm;

import ast.exp.Expression;

public class Eval extends Statement {
    public final Expression expression;

    public Eval(Expression condition) {
        this.expression = condition;
    }

    @Override
    public String toString() {
        return expression.toString();
    }
}
