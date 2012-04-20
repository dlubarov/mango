package rst.ty;

import rst.misc.*;

public class TypeGenericParam extends AnyGenericParam {
    public final int index;

    public TypeGenericParam(int index) {
        this.index = index;
    }

    @Override
    protected Type[] getSupertypes(TypeDef typeCtx, MethodDef methodCtx) {
        return typeCtx.genericParams[index].subOf;
    }

    @Override
    protected Type[] getSubtypes(TypeDef typeCtx, MethodDef methodCtx) {
        return typeCtx.genericParams[index].supOf;
    }

    @Override
    public Type withGenericArgs(Type[] typeGenericArgs, Type[] methodGenericArgs) {
        return typeGenericArgs[index];
    }

    @Override
    public boolean equals(Object o) {
        return (o instanceof TypeGenericParam) && index == ((TypeGenericParam) o).index;
    }

    @Override
    public int hashCode() {
        return index;
    }

    @Override
    public String toString() {
        return "T" + index;
    }
}
