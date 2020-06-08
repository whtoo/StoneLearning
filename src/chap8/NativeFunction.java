package chap8;

import stone.StoneException;
import stone.ast.ASTree;

import java.lang.reflect.Method;

public class NativeFunction {
    protected Method method;
    protected String name;
    protected int numParams;
    public NativeFunction(String n,Method m){
        this.method = m;
        this.name = n;
        this.numParams = m.getParameterTypes().length;
    }

    @Override
    public String toString() {
        return "<native: "+hashCode()+">";
    }

    public int numOfParameters() { return numParams;}
    public Object invoke(Object[] args, ASTree tree) {
        try {
            return method.invoke(null,args);
        } catch (Exception e){
            throw new StoneException("bad native function call:"+ name,tree);
        }
    }
}
