package ast.exp.lit;

import ast.misc.*;
import ast.exp.Expression;

public class LiteralInt extends Expression {
    private final String value;

    public LiteralInt(String value) {
        this.value = value;
    }

    private int getRadix() {
        if (value.startsWith("0x") || value.startsWith("0X"))
            return 16;
        if (value.startsWith("0b") || value.startsWith("0B"))
            return 2;
        return 10;
    }

    private String getDigits() {
        if (value.startsWith("0x") || value.startsWith("0X"))
            return value.substring(2);
        if (value.startsWith("0b") || value.startsWith("0B"))
            return value.substring(2);
        return value;
    }

    private int getValue() {
        return Integer.parseInt(getDigits(), getRadix());
    }

    @Override
    public rst.exp.Expression refine(TypeDef typeCtx, MethodDef methodCtx) {
        return new rst.exp.lit.LiteralInt(getValue());
    }

    @Override
    public String toString() {
        return value;
    }
}
