package rst.exp.lit;

import rst.CodeContext;
import rst.exp.*;
import rst.ty.Type;

public class LiteralDecimal extends Expression {
    private final double value;

    public LiteralDecimal(double value) {
        this.value = value;
    }

    @Override
    public boolean canConform(Type type, CodeContext ctx) {
        return type.isSupertype(Type.coreDouble, ctx.type, ctx.meth);
    }

    @Override
    public String toString() {
        return Double.toString(value);
    }
}
