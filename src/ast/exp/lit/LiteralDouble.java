package ast.exp.lit;

import ast.exp.Expression;
import ast.misc.*;

public class LiteralDouble extends Expression {
    private final String value;

    public LiteralDouble(String value) {
        this.value = value;
    }

    @Override
    public rst.exp.Expression refine(TypeDef typeCtx, MethodDef methodCtx) {
        return new rst.exp.lit.LiteralDecimal(Double.parseDouble(value));
    }

    @Override
    public String toString() {
        return value;
    }
}
