package rst.misc;

import java.util.*;

import rst.ty.*;
import rst.ty.con.*;

public class MethodDef {
    public final boolean isStatic, isSealed;
    public final Type returnType;
    public final String name;
    public final GenericParam[] genericParams;
    public final Param[] parameters;

    public MethodDef(boolean isStatic, boolean isSealed,
            Type returnType, String name, GenericParam[] genericParams, Param[] parameters) {
        this.isStatic = isStatic;
        this.isSealed = isSealed;
        this.returnType = returnType;
        this.name = name;
        this.genericParams = genericParams;
        this.parameters = parameters;
    }

    @Override
    public String toString() {
        return "FIXME";
    }

    public static class GenericParam {
        public final int index;
        public final Type[] subOf, supOf;

        public GenericParam(int index, Type[] subOf, Type[] supOf) {
            this.index = index;
            this.subOf = subOf;
            this.supOf = supOf;
        }

        public TypeConstraint toConstraint() {
            MethodGenericParam me = new MethodGenericParam(index);
            List<TypeConstraint> parts = new ArrayList<TypeConstraint>();
            for (Type sup : subOf)
                parts.add(new SubtypeConstraint(me, sup));
            for (Type sub : supOf)
                parts.add(new SubtypeConstraint(sub, me));
            return new ConjunctiveConstraint(parts.toArray(new TypeConstraint[parts.size()]));
        }

        @Override
        public String toString() {
            return String.format("M%d sub %s sup %s", index,
                    Arrays.toString(subOf), Arrays.toString(supOf));
        }
    }

    public static class Param {
        public final Type type;
        public final String name;

        public Param(Type type, String name) {
            this.type = type;
            this.name = name;
        }

        @Override
        public String toString() {
            return type + " " + name;
        }
    }
}
