package rst.ty.con;

import java.util.Set;

import rst.misc.*;
import rst.ty.*;

public class BottomConstraint extends TypeConstraint {
    public static final BottomConstraint singleton = new BottomConstraint();
    private BottomConstraint() {}

    @Override
    public boolean compatible(AnyGenericParam genericParam, Type value,
            TypeDef typeCtx, MethodDef methodCtx) {
        return false;
    }

    @Override
    protected void addSuggestions(AnyGenericParam genericParam, Set<Type> result,
            TypeDef typeCtx, MethodDef methodCtx) {}
}
