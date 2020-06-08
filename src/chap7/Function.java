package chap7;

import chap6.Enviroment;
import com.sun.tools.doclint.Env;
import stone.ast.BlockStmnt;
import stone.ast.ParameterList;

public class Function {
    protected ParameterList parameterList;
    protected BlockStmnt body;
    protected Enviroment env;
    public Function(ParameterList parameters,BlockStmnt body,Enviroment env) {
        this.parameterList =parameters;
        this.body = body;
        this.env =env;
    }

    public ParameterList parameters() { return  parameterList;}
    public BlockStmnt body() { return body;}
    public Enviroment makeEnv() { return  new NestedEnv(env);}

    @Override
    public String toString() {
        return "<fun:"+hashCode() + ">";
    }
}
