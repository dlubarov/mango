package parse;

public abstract class Parser<T> {
    public abstract Result<T> parse(String s, int p);

    public static int ws(String s, int p, boolean sameLineOnly) {
        while (p < s.length())
            switch (s.charAt(p)) {
                case '\n':
                case '\r':
                    if (sameLineOnly)
                        break;
                case ' ':
                case '\t':
                    ++p;
                    break;
                case '#':
                    while (p < s.length() && s.charAt(p) != '\n')
                        ++p;
                    break;
            }
        return p;
    }

    public static int indentation(String s, int p) {
        int p2 = p;
        while (p2 > 0 && s.charAt(p2 - 1) == ' ')
            --p2;
        if (s.charAt(p2) != ' ')
            ;
        return p - p2;
    }
}
