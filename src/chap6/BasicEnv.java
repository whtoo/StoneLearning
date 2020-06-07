package chap6;

import java.util.HashMap;

public class BasicEnv implements Enviroment{
    protected HashMap<String,Object> values;
    public BasicEnv() { values = new HashMap<>(); }
    @Override
    public void put(String name, Object value) {
        values.put(name,value);
    }

    @Override
    public Object get(String name) {
        return values.get(name);
    }
}
