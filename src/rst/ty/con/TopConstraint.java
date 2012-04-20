package rst.ty.con;

import java.util.Set;

import rst.misc.*;
import rst.ty.*;

public class TopConstraint extends TypeConstraint {
    public static final TopConstraint singleton = new TopConstraint();
    private TopConstraint() {}

    @Override
    public boolean compatible(AnyGenericParam genericParam, Type value,
            TypeDef typeCtx, MethodDef methodCtx) {
        return true;
    }

    @Override
    protected void addSuggestions(AnyGenericParam genericParam, Set<Type> result,
            TypeDef typeCtx, MethodDef methodCtx) {}
}
