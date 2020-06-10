package chap11;

import chap6.Enviroment;
import chap7.Function;
import stone.ast.BlockStmnt;
import stone.ast.ParameterList;

public class OptFunction extends Function {
    protected int size;

    public OptFunction(ParameterList parameters, BlockStmnt body, Enviroment env, int memorySize) {
        super(parameters, body, env);
        size = memorySize;
    }

    @Override
    public Enviroment makeEnv() {
        return new ArrayEnv(size, env);
    }
}
