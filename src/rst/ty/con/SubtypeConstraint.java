package rst.ty.con;

import java.util.Set;

import rst.Project;
import rst.misc.*;
import rst.ty.*;

public class SubtypeConstraint extends TypeConstraint {
    public final Type a, b;

    public SubtypeConstraint(Type a, Type b) {
        this.a = a;
        this.b = b;
    }

    private TypeConstraint simplify(TypeDef typeCtx, MethodDef methodCtx) {
        if (!(a instanceof ParameterizedType && b instanceof ParameterizedType))
            return null;

        ParameterizedType aa = (ParameterizedType) a, bb = (ParameterizedType) b;
        TypeDef aDef = Project.singleton.resolve(aa.rawType);
        Type[] genA = aa.genericArgs, genB = bb.genericArgs;

        if (aa.rawType.equals(bb.rawType)) {
            TypeConstraint[] parts = new TypeConstraint[aa.genericArgs.length];
            for (int i = 0; i < aa.genericArgs.length; ++i)
                switch (aDef.genericParams[i].var) {
                    case COVARIANT:
                        parts[i] = new SubtypeConstraint(genA[i], genB[i]);
                        break;
                    case NONVARIANT:
                        parts[i] = new SameTypeConstraint(genA[i], genB[i]);
                        break;
                    case CONTRAVARIANT:
                        parts[i] = new SubtypeConstraint(genB[i], genA[i]);
                        break;
                }
            return new ConjunctiveConstraint(parts);
        }

        ParameterizedType aAsB = aa.asSuper(bb.rawType, typeCtx, methodCtx);
        if (aAsB == null)
            return BottomConstraint.singleton;
        return new SubtypeConstraint(aAsB, b);
    }

    @Override
    public boolean compatible(AnyGenericParam genericParam, Type value, TypeDef typeCtx, MethodDef methodCtx) {
        TypeConstraint s = simplify(typeCtx, methodCtx);
        if (s != null)
            return s.compatible(genericParam, value, typeCtx, methodCtx);

        if (a.equals(genericParam))
            return value.isSubtype(b, typeCtx, methodCtx);
        if (b.equals(genericParam))
            return a.isSubtype(value, typeCtx, methodCtx);
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
        return a + " < " + b;
    }
}
