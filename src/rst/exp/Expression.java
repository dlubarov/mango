package rst.exp;

import rst.CodeContext;
import rst.ty.Type;

public abstract class Expression {
    public boolean canConform(Type type, CodeContext ctx) {
        return inferType(ctx).isSubtype(type, ctx.type, ctx.meth);
    }

    public Type inferType(CodeContext ctx) {
        return null;
    }

    @Override public abstract String toString();
}
