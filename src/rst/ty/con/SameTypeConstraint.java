package rst.ty.con;

import java.util.Set;

import rst.misc.*;
import rst.ty.*;

public class SameTypeConstraint extends TypeConstraint {
    public final Type a, b;

    public SameTypeConstraint(Type a, Type b) {
        this.a = a;
        this.b = b;
    }

    private TypeConstraint simplify(TypeDef typeCtx, MethodDef methodCtx) {
        if (!(a instanceof ParameterizedType && b instanceof ParameterizedType))
            return null;

        ParameterizedType aa = (ParameterizedType) a, bb = (ParameterizedType) b;
        Type[] genA = aa.genericArgs, genB = bb.genericArgs;

        if (!aa.rawType.equals(bb.rawType))
            return BottomConstraint.singleton;

        assert genA.length == genB.length;
        TypeConstraint[] parts = new TypeConstraint[genA.length];
        for (int i = 0; i < genA.length; ++i) {
            if (!genA[i].equals(genB[i]))
                return this;
            parts[i] = new SameTypeConstraint(genA[i], genB[i]);
        }
        return new ConjunctiveConstraint(parts);
    }

    @Override
    public boolean compatible(AnyGenericParam genericParam, Type value, TypeDef typeCtx, MethodDef methodCtx) {
        TypeConstraint s = simplify(typeCtx, methodCtx);
        if (s != null)
            return s.compatible(genericParam, value, typeCtx, methodCtx);

        if (a.equals(genericParam))
            return b.equals(value);
        if (b.equals(genericParam))
            return a.equals(value);
        return true;
    }

    @Override
    protected void addSuggestions(AnyGenericParam genericParam, Set<Type> result, TypeDef typeCtx, MethodDef methodCtx) {
        TypeConstraint s = simplify(typeCtx, methodCtx);
        if (s != null) {
            s.addSuggestions(genericParam, result, typeCtx, methodCtx);
            return;
        }

        if (a.equals(genericParam))
            result.add(b);
        if (b.equals(genericParam))
            result.add(a);
    }

    @Override
    public String toString() {
        return a + " = " + b;
    }
}
