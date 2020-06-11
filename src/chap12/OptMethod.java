package chap12;

import chap11.ArrayEnv;
import chap11.OptFunction;
import chap6.Enviroment;
import stone.ast.BlockStmnt;
import stone.ast.ParameterList;

public class OptMethod extends OptFunction {
    OptStoneObject self;

    public OptMethod(ParameterList parameters, BlockStmnt body, Enviroment env, int memorySize, OptStoneObject self) {
        super(parameters, body, env, memorySize);
        this.self = self;
    }

    @Override
    public Enviroment makeEnv() {
        ArrayEnv e = new ArrayEnv(size, env);
        e.put(0, 0, self);
        return e;
    }
}
