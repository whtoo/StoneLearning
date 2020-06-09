package chap9;

import chap6.Enviroment;
import chap7.FunctionEvaluator;

public class StoneObject {
    protected Enviroment env;

    public StoneObject(Enviroment e) {
        env = e;
    }

    @Override
    public String toString() {
        return "<object: " + hashCode() + ">";
    }

    public Object read(String member) throws AccessException {
        return getEnv(member).get(member);
    }

    public void write(String member, Object value) throws AccessException {
        ((FunctionEvaluator.EnvEx) getEnv(member)).putNew(member, value);
    }

    public Enviroment getEnv(String member) throws AccessException {
        Enviroment e = ((FunctionEvaluator.EnvEx) env).where(member);
        if (e != null && e == env)
            return e;
        else
            throw new AccessException();
    }

    public static class AccessException extends Exception {
    }
}
