package rst;

import rst.misc.*;
import rst.ty.Type;

public final class TypeUtil {
    private TypeUtil() {}

    private static Type binaryUnion(Type a, Type b, TypeDef typeCtx, MethodDef methodCtx) {
        if (a.isSubtype(b, typeCtx, methodCtx))
            return b;
        if (b.isSubtype(a, typeCtx, methodCtx))
            return a;
        // FIXME use upperBounds
        return Type.coreTop;
    }

    public static Type union(Type[] types, TypeDef typeCtx, MethodDef methodCtx) {
        Type bound = Type.coreBottom;
        for (Type type : types)
            bound = binaryUnion(type, bound, typeCtx, methodCtx);
        return bound;
    }
}
