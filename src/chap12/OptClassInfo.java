package chap12;

import chap11.Symbols;
import chap6.Enviroment;
import chap9.ClassInfo;
import stone.ast.ClassStmnt;
import stone.ast.DefStmnt;

import java.util.ArrayList;

public class OptClassInfo extends ClassInfo {
    protected Symbols methods, fields;
    protected DefStmnt[] methodDefs;

    public OptClassInfo(ClassStmnt cs, Enviroment env, Symbols methods, Symbols fields) {
        super(cs, env);
        this.methods = methods;
        this.fields = fields;
        this.methodDefs = null;
    }

    public int size() {
        return fields.size();
    }

    @Override
    public OptClassInfo superClass() {
        return (OptClassInfo) superClass;
    }

    public void copyTo(Symbols f, Symbols s, ArrayList<DefStmnt> mlist) {
        f.append(fields);
        s.append(methods);
        for (DefStmnt def : methodDefs) {
            mlist.add(def);
        }
    }

    public Integer fieldIndex(String name) {
        return fields.find(name);
    }

    public Integer methodIndex(String name) {
        return methods.find(name);
    }

    public Object method(OptStoneObject self, int index) {
        DefStmnt def = methodDefs[index];
        return new OptMethod(def.parameters(), def.body(), enviroment(), ((ObjOptimizer.DefStmntEx2) def).locals(), self);
    }

    public void setMethods(ArrayList<DefStmnt> methods) {
        methodDefs = methods.toArray(new DefStmnt[methods.size()]);
    }
}
