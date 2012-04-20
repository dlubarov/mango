package parse.stm;

import ast.stm.Statement;
import parse.*;

public class StatementParser extends Parser<Statement> {
    public static final Parser<Statement> singleton = new StatementParser();
    private StatementParser() {}

    @SuppressWarnings("unchecked")
    private static final Parser<Statement>[] proxies = new Parser[] {
        WhileParser.singleton,
        EvalParser.singleton
    };

    @Override
    public Result<Statement> parse(String s, int p) {
        for (Parser<Statement> proxy : proxies) {
            Result<Statement> result = proxy.parse(s, p);
            if (result != null)
                return result;
        }
        return null;
    }
}
