package rst.misc;

import java.util.*;

import rst.ty.*;
import rst.ty.con.*;
import common.*;

public class TypeDef {
    public final boolean isAbstract, isSealed;
    public final RawType desc;
    public final GenericParam[] genericParams;
    public final ParameterizedType[] parents;
    public final MethodDef[] staticMethodDefs, instanceMethodDefs;

    public TypeDef(boolean isAbstract, boolean isSealed,
            RawType desc, GenericParam[] genericParams, ParameterizedType[] parents,
            MethodDef[] staticMethodDefs, MethodDef[] instanceMethodDefs) {
        this.isAbstract = isAbstract;
        this.isSealed = isSealed;
        this.desc = desc;
        this.genericParams = genericParams;
        this.parents = parents;
        this.staticMethodDefs = staticMethodDefs;
        this.instanceMethodDefs = instanceMethodDefs;
    }

    @Override
    public String toString() {
        return "FIXME";
    }

    public static class GenericParam {
        public final Variance var;
        public final int index;
        public final Type[] subOf, supOf;

        public GenericParam(Variance var, int index, Type[] subOf, Type[] supOf) {
            this.var = var;
            this.index = index;
            this.subOf = subOf;
            this.supOf = supOf;
        }

        public TypeConstraint toConstraint() {
            TypeGenericParam me = new TypeGenericParam(index);
            List<TypeConstraint> parts = new ArrayList<TypeConstraint>();
            for (Type sup : subOf)
                parts.add(new SubtypeConstraint(me, sup));
            for (Type sub : supOf)
                parts.add(new SubtypeConstraint(sub, me));
            return new ConjunctiveConstraint(parts.toArray(new TypeConstraint[parts.size()]));
        }

        @Override
        public String toString() {
            return String.format("%sT%d sub %s sup %s", var, index,
                    Arrays.toString(subOf), Arrays.toString(supOf));
        }
    }
}
