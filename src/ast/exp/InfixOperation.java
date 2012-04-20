package ast.exp;

import ast.misc.*;

public class InfixOperation extends Expression {
    public final String operator;
    public final Expression a, b;

    public InfixOperation(String operator, Expression a, Expression b) {
        this.operator = operator;
        this.a = a; this.b = b;
    }

    @Override
    public rst.exp.Expression refine(TypeDef typeCtx, MethodDef methodCtx) {
        return null; // FIXME
    }

    @Override
    public String toString() {
        return String.format("%s %s %s", a, operator, b);
    }
}
