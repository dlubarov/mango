package ast.stm;

import ast.exp.Expression;

public class Return extends Statement {
    public final Expression value;

    public Return(Expression value) {
        this.value = value;
    }

    @Override
    public String toString() {
        if (value == null)
            return "return";
        return "return " + value;
    }
}
