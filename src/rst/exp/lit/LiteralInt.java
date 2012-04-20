package rst.exp.lit;

import rst.CodeContext;
import rst.exp.*;
import rst.ty.*;

public class LiteralInt extends Expression {
    private final int value;

    public LiteralInt(int value) {
        this.value = value;
    }

    @Override
    public boolean canConform(Type type, CodeContext ctx) {
        return Type.coreInt.isSubtype(type, ctx.type, ctx.meth);
    }

    @Override
    public String toString() {
        return Integer.toBinaryString(value);
    }
}
