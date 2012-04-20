package rst.ty;

import java.util.Arrays;

import rst.Project;
import rst.misc.*;

import common.RawType;

public class ParameterizedType extends Type {
    public final RawType rawType;
    public final Type[] genericArgs;

    public ParameterizedType(RawType rawType, Type[] genericArgs) {
        this.rawType = rawType;
        this.genericArgs = genericArgs;
    }

    public ParameterizedType(RawType rawType) {
        this(rawType, Type.NONE);
    }

    @Override
    public ParameterizedType withGenericArgs(Type[] typeGenericArgs, Type[] methodGenericArgs) {
        Type[] newGenericArgs = new Type[genericArgs.length];
        for (int i = 0; i < newGenericArgs.length; ++i)
            newGenericArgs[i] = genericArgs[i].withGenericArgs(typeGenericArgs, methodGenericArgs);
        return new ParameterizedType(rawType, newGenericArgs);
    }

    @Override
    public Type[] getSupertypes(TypeDef typeCtx, MethodDef methodCtx) {
        TypeDef myTypeDef = Project.singleton.resolve(rawType);
        ParameterizedType[] parentsWithGenerics = new ParameterizedType[myTypeDef.parents.length];
        for (int i = 0; i < parentsWithGenerics.length; ++i)
            parentsWithGenerics[i] = myTypeDef.parents[i].withGenericArgs(genericArgs, null);
        return parentsWithGenerics;
    }

    @Override
    public Type[] getSubtypes(TypeDef typeCtx, MethodDef methodCtx) {
        // We don't actually need to find our child types, because they will find us
        // when their getSupertypes is called. So we just return Bottom.
        if (rawType.equals(RawType.coreBottom))
            return Type.NONE;
        return new Type[] {Type.coreBottom};
    }

    @Override
    public boolean equals(Object o) {
        try {
            ParameterizedType that = (ParameterizedType) o;
            return rawType.equals(that.rawType) && Arrays.equals(genericArgs, that.genericArgs);
        } catch (ClassCastException e) {
            return false;
        }
    }

    @Override
    public int hashCode() {
        return rawType.hashCode() + Arrays.hashCode(genericArgs);
    }

    @Override
    public String toString() {
        if (genericArgs.length == 0)
            return rawType.toString();
        return rawType + Arrays.toString(genericArgs);
    }
}
