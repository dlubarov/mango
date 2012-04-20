package rst;

import rst.misc.*;

public final class CodeContext {
    public final TypeDef type;
    public final MethodDef meth;

    public CodeContext(TypeDef typeCtx, MethodDef methodCtx) {
        this.type = typeCtx;
        this.meth = methodCtx;
    }
}
