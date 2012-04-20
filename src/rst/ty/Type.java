package rst.ty;

import rst.misc.*;
import common.RawType;

public abstract class Type {
    public static final Type[] NONE = new Type[0];

    public static final Type
            coreTop = new ParameterizedType(RawType.coreTop),
            coreBottom = new ParameterizedType(RawType.coreBottom),
            coreUnit = new ParameterizedType(RawType.coreUnit),
            coreInt = new ParameterizedType(RawType.coreInt),
            coreByte = new ParameterizedType(RawType.coreByte),
            coreLong = new ParameterizedType(RawType.coreLong),
            coreDouble = new ParameterizedType(RawType.coreDouble),
            coreSequence = new ParameterizedType(RawType.coreSequence);

    public abstract Type[] getSupertypes(TypeDef typeCtx, MethodDef methodCtx);

    public abstract Type[] getSubtypes(TypeDef typeCtx, MethodDef methodCtx);

    public boolean isSubtype(Type that, TypeDef typeCtx, MethodDef methodCtx) {
        // TODO inefficient search
        if (equals(that))
            return true;
        for (Type sup : getSupertypes(typeCtx, methodCtx))
            if (sup.isSubtype(that, typeCtx, methodCtx))
                return true;
        for (Type thatSub : that.getSubtypes(typeCtx, methodCtx))
            if (isSubtype(thatSub, typeCtx, methodCtx))
                return true;
        return false;
    }

    public boolean isSupertype(Type that, TypeDef typeCtx, MethodDef methodCtx) {
        return that.isSubtype(this, typeCtx, methodCtx);
    }

    // Returns null if this doesn't have the specified supertype.
    public ParameterizedType asSuper(RawType rawSupertype, TypeDef typeCtx, MethodDef methodCtx) {
        throw new RuntimeException("FIXME");
    }

    public ParameterizedType[] concreteSupertypes(TypeDef typeCtx, MethodDef methodCtx) {
        throw new RuntimeException("FIXME");
    }

    public final RawType[] rawConcreteSupertypes(TypeDef typeCtx, MethodDef methodCtx) {
        ParameterizedType[] paramSupers = concreteSupertypes(typeCtx, methodCtx);
        RawType[] result = new RawType[paramSupers.length];
        for (int i = 0; i < result.length; ++i)
            result[i] = paramSupers[i].rawType;
        return result;
    }

    public abstract Type withGenericArgs(Type[] typeGenericArgs, Type[] methodGenericArgs);

    // Make equals, hashCode, and toString abstract to force subclasses to override them.
    @Override public abstract boolean equals(Object o);
    @Override public abstract int hashCode();
    @Override public abstract String toString();
}
