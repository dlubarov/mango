package ast.misc;

public class Import {
    public final String module, type;

    public Import(String module, String type) {
        this.module = module;
        this.type = type;
    }

    @Override
    public String toString() {
        return String.format("import %s.%s;", module, type);
    }
}
