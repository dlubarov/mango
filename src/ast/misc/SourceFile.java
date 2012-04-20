package ast.misc;

import java.util.*;

import common.*;

public class SourceFile {
    public final String fileName;
    public final String module;
    public final Import[] imports;
    public final TypeDef[] typeDefs;

    public SourceFile(String fileName, String module, Import[] imports, TypeDef[] typeDefs) {
        this.fileName = fileName;
        this.module = module;
        this.imports = imports;
        this.typeDefs = typeDefs;
        for (TypeDef typeDef : typeDefs)
            typeDef.owner = this;
    }

    public RawType qualify(String typeName) {
        Set<String> moduleOptions = new HashSet<String>();

        // FIXME: search imports

        if (moduleOptions.isEmpty())
            return null;
        if (moduleOptions.size() > 1)
            throw new CompilationException("The type '%s' is ambiguous in %s.",
                    typeName, fileName);
        return new RawType(moduleOptions.iterator().next(), typeName);
    }

    @Override
    public String toString() {
        return "FIXME";
    }
}
