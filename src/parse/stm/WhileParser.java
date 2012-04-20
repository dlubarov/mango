package parse.stm;

import ast.exp.Expression;
import ast.stm.*;
import parse.*;
import parse.exp.ExpressionParser;
import parse.misc.IdentifierParser;

public class WhileParser extends Parser<Statement> {
    public static final Parser<Statement> singleton = new WhileParser();
    private WhileParser() {}

    @Override
    public Result<Statement> parse(String s, int p) {
        // Parse the 'while'.
        Result<String> resWhile = IdentifierParser.singleton.parse(s, p);
        if (resWhile == null || !resWhile.value.equals("while"))
            return null;
        p = ws(s, resWhile.rem, false);

        // Parse the condition.
        Result<Expression> resCond = ExpressionParser.singleton.parse(s, p);
        if (resCond == null)
            throw new ParseException(s, p, "Expecting expression after 'while'.");
        p = ws(s, resCond.rem, false);

        // Parse the ':'.
        if (s.charAt(p) != ':')
            throw new ParseException(s, p, "Expecting ':' after expression in while statement.");
        ++p;

        // Parse the body.
        Result<Statement> resBody = StatementParser.singleton.parse(s, p);
        p = ws(s, resBody.rem, true);

        Statement result = new While(resCond.value, resBody.value);
        return new Result<Statement>(result, p);
    }
}
