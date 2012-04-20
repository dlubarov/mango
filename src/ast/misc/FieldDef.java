package ast.misc;

import ast.exp.Expression;

public class FieldDef extends MemberDef {
    public final Type type;
    public final Component[] components;

    public FieldDef(Type type, Component[] components) {
        this.type = type;
        this.components = components;
    }

    public static class Component {
        public final String fieldName;
        public final Expression initVal;

        public Component(String fieldName, Expression initVal) {
            this.fieldName = fieldName;
            this.initVal = initVal;
        }

        @Override
        public String toString() {
            if (initVal == null)
                return fieldName;
            return fieldName + " = " + initVal;
        }
    }
}
