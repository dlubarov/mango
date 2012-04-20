package ast.misc;

public class MethodDef extends MemberDef {
    public final String[] qualifiers;
    public final Type returnType;
    public final String name;
    public final Param[] parameters;
    public final String[] genericParams;

    public MethodDef(String[] qualifiers, Type returnType, String name, Param[] parameters, String[] genericParams) {
        this.qualifiers = qualifiers;
        this.returnType = returnType;
        this.name = name;
        this.parameters = parameters;
        this.genericParams = genericParams;
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        for (String qual : qualifiers)
            sb.append(qual).append(' ');
        sb.append(returnType).append(' ').append(name)
                .append('(').append(parameters).append(") {");
        // FIXME finish
        return sb.toString();
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
