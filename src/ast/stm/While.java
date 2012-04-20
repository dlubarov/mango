package ast.stm;

import util.StringUtils;
import ast.exp.Expression;

public class While extends Statement {
    public final Expression condition;
    public final Statement body;

    public While(Expression condition, Statement body) {
        this.condition = condition;
        this.body = body;
    }

    @Override
    public String toString() {
        return String.format("while %s:\n%s", condition, StringUtils.indent(body));
    }
}
