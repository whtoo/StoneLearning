package chap11;

import chap6.Enviroment;
import stone.StoneException;

public class ArrayEnv implements Enviroment {
    protected Object[] values;
    protected Enviroment outer;

    public ArrayEnv(int size, Enviroment out) {
        values = new Object[size];
        outer = out;
    }

    public Symbols symbols() {
        throw new StoneException("no symbols");
    }

    public Object get(int nest, int index) {
        if (nest == 0) {
            return values[index];
        } else if (outer == null) {
            return null;
        } else {
            return ((EnvOptimizer.EnvEx2) outer).get(nest - 1, index);
        }
    }

    public void put(int nest, int index, Object value) {
        if (nest == 0) {
            values[index] = value;
        } else if (outer == null) {
            throw new StoneException("no outer enviroment");
        } else {
            ((EnvOptimizer.EnvEx2) outer).put(nest - 1, index, value);
        }
    }

    @Override
    public void put(String name, Object value) {
        error(name);
    }

    @Override
    public Object get(String name) {
        error(name);
        return null;
    }

    public void putNew(String name, Object value) {
        error(name);
    }

    public Enviroment where(String name) {
        error(name);
        return null;
    }

    public void setOuter(Enviroment env) {
        outer = env;
    }

    private void error(String name) {
        throw new StoneException("cannot access by name " + name);
    }
}
