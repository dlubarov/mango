package parse.stm;

import ast.exp.Expression;
import ast.stm.*;
import parse.*;
import parse.exp.ExpressionParser;

public class EvalParser extends Parser<Statement> {
    public static final Parser<Statement> singleton = new EvalParser();
    private EvalParser() {}

    @Override
    public Result<Statement> parse(String s, int p) {
        Result<Expression> resExp = ExpressionParser.singleton.parse(s, p);
        if (resExp == null)
            return null;
        Statement result = new Eval(resExp.value);
        return new Result<Statement>(result, p);
    }
}
