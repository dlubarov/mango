package rst.exp.lit;

import rst.CodeContext;
import rst.exp.*;
import rst.ty.*;

public class LiteralSequence extends Expression {
    public final Expression[] contents;

    public LiteralSequence(Expression[] contents) {
        this.contents = contents;
    }

    @Override
    public boolean canConform(Type type, CodeContext ctx) {
        return Type.coreSequence.isSubtype(type, ctx.type, ctx.meth);
    }

    @Override
    public String toString() {
        return "{" + "FIXME" + "}";
    }
}
