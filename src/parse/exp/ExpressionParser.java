package parse.exp;

import parse.*;
import ast.exp.Expression;
import ast.exp.lit.LiteralInt;

public class ExpressionParser extends Parser<Expression> {
    public static final Parser<Expression> singleton = new ExpressionParser();
    private ExpressionParser() {}

    @Override
    public Result<Expression> parse(String s, int p) {
        if (s.charAt(p) == '0')
            return new Result<Expression>(new LiteralInt("0"), p + 1);
        return null;
    }
}
