package rst.ty;

import rst.misc.*;

public class MethodGenericParam extends AnyGenericParam {
    public final int index;

    public MethodGenericParam(int index) {
        this.index = index;
    }

    @Override
    public Type[] getSupertypes(TypeDef typeCtx, MethodDef methodCtx) {
        return methodCtx.genericParams[index].subOf;
    }

    @Override
    public Type[] getSubtypes(TypeDef typeCtx, MethodDef methodCtx) {
        return methodCtx.genericParams[index].supOf;
    }

    @Override
    public Type withGenericArgs(Type[] typeGenericArgs, Type[] methodGenericArgs) {
        return methodGenericArgs[index];
    }

    @Override
    public boolean equals(Object o) {
        return (o instanceof MethodGenericParam) && index == ((MethodGenericParam) o).index;
    }

    @Override
    public int hashCode() {
        return index;
    }

    @Override
    public String toString() {
        return "M" + index;
    }
}
