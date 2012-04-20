package ast.misc;

import common.Variance;

public class TypeDef {
    public SourceFile owner;
    public final String[] qualifiers;
    public final String name;
    public final GenericParam[] genericParams;
    public final MemberDef[] memberDefs;

    public TypeDef(String[] qualifiers, String name, GenericParam[] genericParams, MemberDef[] memberDefs) {
        this.qualifiers = qualifiers;
        this.name = name;
        this.genericParams = genericParams;
        this.memberDefs = memberDefs;
        for (MemberDef memberDef : memberDefs)
            memberDef.owner = this;
    }

    @Override
    public String toString() {
        return "FIXME";
    }

    public static class GenericParam {
        public final Variance variance;
        public final String name;

        public GenericParam(Variance variance, String name) {
            this.variance = variance;
            this.name = name;
        }

        @Override
        public String toString() {
            return variance + name;
        }
    }
}
