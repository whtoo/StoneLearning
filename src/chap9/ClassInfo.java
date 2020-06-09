package chap9;

import chap6.Enviroment;
import stone.StoneException;
import stone.ast.ClassBody;
import stone.ast.ClassStmnt;

public class ClassInfo {
    protected ClassStmnt definition;
    protected Enviroment enviroment;
    protected ClassInfo superClass;

    public ClassInfo(ClassStmnt cs, Enviroment env) {
        definition = cs;
        enviroment = env;
        Object obj = env.get(cs.superClass());
        if (obj == null)
            superClass = null;
        else if (obj instanceof ClassInfo)
            superClass = (ClassInfo) obj;
        else
            throw new StoneException("unknown super class: " + cs.superClass(), cs);
    }

    public String name() {
        return definition.name();
    }

    public ClassInfo superClass() {
        return superClass;
    }

    public ClassBody body() {
        return definition.body();
    }

    public Enviroment enviroment() {
        return enviroment;
    }

    @Override
    public String toString() {
        return "<class " + name() + ">";
    }
}
