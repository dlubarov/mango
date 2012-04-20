package rst.ty.con;

import java.util.*;

import common.CompilationException;

import rst.misc.*;
import rst.ty.*;

public abstract class TypeConstraint {
    public abstract boolean compatible(AnyGenericParam genericParam, Type value,
            TypeDef typeCtx, MethodDef methodCtx);

    protected abstract void addSuggestions(AnyGenericParam genericParam, Set<Type> result,
            TypeDef typeCtx, MethodDef methodCtx);

    private final Type[] suggestions(AnyGenericParam genericParam,
            TypeDef typeCtx, MethodDef methodCtx) {
        Set<Type> result = new HashSet<Type>();
        addSuggestions(genericParam, result, typeCtx, methodCtx);
        result.add(Type.coreTop); result.add(Type.coreBottom);
        return result.toArray(new Type[result.size()]);
    }

    public final Type infer(AnyGenericParam genericParam, TypeDef typeCtx, MethodDef methodCtx) {
        Type[] suggestions = suggestions(genericParam, typeCtx, methodCtx);
        Set<Type> options = new HashSet<Type>();
        for (Type sug : suggestions)
            if (compatible(genericParam, sug, typeCtx, methodCtx))
                options.add(sug);

        if (options.isEmpty())
            throw new CompilationException("Can't infer generic argument %s.", genericParam);
        if (options.size() > 1)
            throw new CompilationException("Can't disambiguate options for generic argument %s.",
                    genericParam);
        return options.iterator().next();
    }

    public final Type[] inferMethodGenerics(int n, TypeDef typeCtx, MethodDef methodCtx) {
        Type[] result = new Type[n];
        for (int i = 0; i < n; ++i)
            result[i] = infer(new MethodGenericParam(i), typeCtx, methodCtx);
        return result;
    }

    public final Type[] inferTypeGenerics(int n, TypeDef typeCtx, MethodDef methodCtx) {
        Type[] result = new Type[n];
        for (int i = 0; i < n; ++i)
            result[i] = infer(new TypeGenericParam(i), typeCtx, methodCtx);
        return result;
    }
}
