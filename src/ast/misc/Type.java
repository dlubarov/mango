package ast.misc;

import java.util.*;

import common.*;

public class Type {
    public String rawType;
    public final Type[] genericArgs;

    public Type(String rawType, Type[] genericArgs) {
        this.rawType = rawType;
        this.genericArgs = genericArgs;
    }

    public rst.ty.Type refine(TypeDef typeCtx, MethodDef methodCtx) {
        Set<rst.ty.Type> options = new HashSet<rst.ty.Type>();

        if (genericArgs.length == 0) {
            if (methodCtx != null)
                for (int i = 0; i < methodCtx.genericParams.length; ++i)
                    if (methodCtx.genericParams[i].equals(rawType))
                        options.add(new rst.ty.MethodGenericParam(i));
            for (int i = 0; i < typeCtx.genericParams.length; ++i)
                if (typeCtx.genericParams[i].name.equals(rawType))
                    options.add(new rst.ty.TypeGenericParam(i));
        }

        RawType qualifiedRawType = typeCtx.owner.qualify(rawType);
        if (qualifiedRawType != null) {
            rst.ty.Type[] refinedGenericArgs = Type.refineAll(genericArgs, typeCtx, methodCtx);
            options.add(new rst.ty.ParameterizedType(qualifiedRawType, refinedGenericArgs));
        }

        if (options.isEmpty())
            throw new CompilationException("Could not resolve type '%s'.", rawType);
        if (options.size() > 1)
            throw new CompilationException("Ambiguous type '%s'.", rawType);
        return options.iterator().next();
    }

    public static rst.ty.Type[] refineAll(Type[] types, TypeDef typeCtx, MethodDef methodCtx) {
        rst.ty.Type[] refinedTypes = new rst.ty.Type[types.length];
        for (int i = 0; i < refinedTypes.length; ++i)
            refinedTypes[i] = types[i].refine(typeCtx, methodCtx);
        return refinedTypes;
    }

    @Override
    public String toString() {
        if (genericArgs.length == 0)
            return rawType.toString();
        return rawType + Arrays.toString(genericArgs);
    }
}
