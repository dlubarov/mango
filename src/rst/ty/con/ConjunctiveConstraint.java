package rst.ty.con;

import java.util.*;

import rst.misc.*;
import rst.ty.*;

public class ConjunctiveConstraint extends TypeConstraint {
    public final TypeConstraint[] parts;

    public ConjunctiveConstraint(TypeConstraint[] parts) {
        this.parts = parts;
    }

    public ConjunctiveConstraint(Collection<TypeConstraint> parts) {
        this(parts.toArray(new TypeConstraint[parts.size()]));
    }

    @Override
    public boolean compatible(AnyGenericParam genericParam, Type value,
            TypeDef typeCtx, MethodDef methodCtx) {
        for (TypeConstraint part : parts)
            if (!part.compatible(genericParam, value, typeCtx, methodCtx))
                return false;
        return true;
    }

    @Override
    protected void addSuggestions(AnyGenericParam genericParam, Set<Type> result,
            TypeDef typeCtx, MethodDef methodCtx) {
        for (TypeConstraint part : parts)
            part.addSuggestions(genericParam, result, typeCtx, methodCtx);
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        for (TypeConstraint part : parts) {
            if (sb.length() > 0)
                sb.append(" & ");
            sb.append('(').append(part).append(')');
        }
        return sb.toString();
    }
}
