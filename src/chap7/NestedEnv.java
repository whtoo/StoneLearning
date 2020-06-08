package chap7;

import chap6.Enviroment;

import java.util.HashMap;

public class NestedEnv implements Enviroment {
    protected HashMap<String,Object> values;
    // 父级环境(Parent-level environment)
    protected Enviroment outer;
    public NestedEnv() { this(null);}
    public NestedEnv(Enviroment e) {
        values = new HashMap<>();
        setOuter(e);
    }

    public void setOuter(Enviroment outer) {
        this.outer = outer;
    }

    @Override
    public void put(String name, Object value) {
        // 1. 查询name所在的Env
        Enviroment e = where(name);
        if(e == null)
            e = this;
        ((FunctionEvaluator.EnvEx)e).putNew(name,value);
    }
    public void putNew(String name,Object value) {
        values.put(name,value);
    }

    public Enviroment where(String name) {
        if(values.get(name) != null) {
            return this;
        } else if(outer == null){
            return null;
        } else {
            return ((FunctionEvaluator.EnvEx) outer).where(name);
        }
    }
    @Override
    public Object get(String name) {
        Object v = values.get(name);
        if (v == null && outer != null)
            return outer.get(name);
        else
            return v;
    }


}
