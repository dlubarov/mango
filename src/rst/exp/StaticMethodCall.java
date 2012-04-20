package rst.exp;

import java.util.*;

import rst.*;
import rst.misc.*;
import rst.ty.*;
import rst.ty.con.*;
import common.*;

public class StaticMethodCall extends Expression {
    public final RawType owner;
    public final String methodName;
    public final Type[] genericArgs;
    public final Expression[] arguments;

    public StaticMethodCall(RawType owner, String methodName,
            Type[] genericArgs, Expression[] arguments) {
        this.owner = owner;
        this.methodName = methodName;
        this.genericArgs = genericArgs;
        this.arguments = arguments;
    }

    private Set<MethodDef> getNormalMatches(Type requiredType, CodeContext ctx) {
        TypeDef ownerDef = Project.singleton.resolve(owner);
        Set<MethodDef> matches = new HashSet<MethodDef>();

        search:
        for (MethodDef m : ownerDef.staticMethodDefs) {
            // Check name and number of params.
            if (!m.name.equals(methodName))
                continue;
            if (m.parameters.length != arguments.length)
                continue;

            // Infer generic arguments, if necessary.
            Type[] effectiveGenericArgs = genericArgs;
            if (genericArgs.length == 0 && m.genericParams.length > 0) {
                // Attempt to infer generic arguments for this call.
                List<TypeConstraint> constraints = new ArrayList<TypeConstraint>();
                // Generic bounds.
                for (MethodDef.GenericParam param : m.genericParams)
                    constraints.add(param.toConstraint());
                // Parameter types.
                for (int i = 0; i < m.parameters.length; ++i) {
                    Type argType = arguments[i].inferType(ctx);
                    if (argType != null) {
                        Type paramType = m.parameters[i].type;
                        TypeConstraint paramTypeCon = new SubtypeConstraint(argType, paramType);
                        constraints.add(paramTypeCon);
                    }
                }
                // Return type.
                constraints.add(new SubtypeConstraint(m.returnType, requiredType));

                TypeConstraint allCons = new ConjunctiveConstraint(constraints);
                effectiveGenericArgs = allCons.inferMethodGenerics(
                        m.genericParams.length, ctx.type, ctx.meth);
            } else if (genericArgs.length != m.genericParams.length)
                throw new CompilationException("Wrong number of generic arguments for %s.", m.name);

            // Check argument types.
            for (int i = 0; i < arguments.length; ++i) {
                Type expected = m.parameters[i].type.withGenericArgs(null, effectiveGenericArgs);
                if (!arguments[i].canConform(expected, ctx))
                    continue search;
            }

            matches.add(m);
        }

        return matches;
    }

    private Set<MethodDef> getInstanceMethodMatches(Type requiredType, CodeContext ctx) {
        Set<MethodDef> matches = new HashSet<MethodDef>();
        if (arguments.length == 0)
            return matches;

        TypeDef ownerDef = Project.singleton.resolve(owner);

        search:
        for (MethodDef m : ownerDef.instanceMethodDefs) {
            if (!m.equals(methodName))
                continue;
            if (m.parameters.length + 1 != arguments.length)
                continue;

            // TODO: avoid inferring type for first param.
            Type[] typeGenerics = arguments[0].inferType(ctx).asSuper(owner, ctx.type, ctx.meth).genericArgs;

            // FIXME: first param
            for (int i = 0; i < m.parameters.length; ++i) {
                //Type expected = m.parameters[i].type.withGenericArgs(typeGenericArgs, genericArgs);
            }
            // TODO: check generic constraints
            matches.add(m);
        }

        return matches;
    }

    private MethodDef[] getMatches(Type requiredType, CodeContext ctx) {
        Set<MethodDef> matches = new HashSet<MethodDef>();
        matches.addAll(getNormalMatches(requiredType, ctx));
        matches.addAll(getInstanceMethodMatches(requiredType, ctx));
        return matches.toArray(new MethodDef[matches.size()]);
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder().append(owner).append('.').append(methodName);
        if (genericArgs.length > 0)
            sb.append(Arrays.toString(genericArgs));
        return sb.append('(').append("FIXME").append(')').toString();
    }
}
