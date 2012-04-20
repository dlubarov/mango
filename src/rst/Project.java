package rst;

import java.util.*;

import common.RawType;

import rst.misc.TypeDef;

public class Project {
    public static Project singleton;

    private final Map<RawType, TypeDef> typeDefsByDesc;

    public Project(TypeDef[] typeDefs) {
        assert singleton == null;
        singleton = this;

        typeDefsByDesc = new HashMap<RawType, TypeDef>();
        for (TypeDef typeDef : typeDefs)
            typeDefsByDesc.put(typeDef.desc, typeDef);
    }

    public TypeDef resolve(RawType desc) {
        return typeDefsByDesc.get(desc);
    }
}
