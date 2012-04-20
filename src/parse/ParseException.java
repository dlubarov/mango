package parse;

public class ParseException extends RuntimeException {
    private static final long serialVersionUID = -338474752028914377L;

    public final int lineNum, colNum;
    public final String line;

    public ParseException(String s, int p, String format, Object... args) {
        super(String.format(format, args));

        int pStart = s.lastIndexOf('\n', p);
        if (pStart < 0)
            pStart = 0;
        else
            ++pStart;
        int pEnd = s.indexOf('\n', p);
        if (pEnd < 0)
            pEnd = s.length();
        line = s.substring(pStart, pEnd);
        colNum = p - pStart;

        int newlines = 0;
        for (int i = 0; i < p; ++i)
            if (s.charAt(i) == '\n')
                ++newlines;
        lineNum = newlines + 1;
    }
}
