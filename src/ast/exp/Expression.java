package ast.exp;

import ast.misc.*;

public abstract class Expression {
    public abstract rst.exp.Expression refine(TypeDef typeCtx, MethodDef methodCtx);

    @Override public abstract String toString();
}
