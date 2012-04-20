package ast.stm;

import util.StringUtils;
import ast.exp.Expression;

public class If extends Statement {
    public final Expression condition;
    public final Statement body;

    public If(Expression condition, Statement body) {
        this.condition = condition;
        this.body = body;
    }

    @Override
    public String toString() {
        return String.format("if %s:\n%s", condition, StringUtils.indent(body));
    }
}
