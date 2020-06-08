package chap8;

import chap6.BasicEvaluator;
import chap6.Enviroment;
import chap7.FunctionEvaluator;
import javassist.gluonj.Require;
import javassist.gluonj.Reviser;
import stone.StoneException;
import stone.ast.ASTree;

import java.util.List;

@Require(FunctionEvaluator.class)
@Reviser public class NativeEvaluator {
    @Reviser public static class NativeArgEx extends FunctionEvaluator.ArgumentsEx {

        public NativeArgEx(List<ASTree> c) {
            super(c);
        }

        @Override
        public Object eval(Enviroment callerEnv, Object value) {
            if(!(value instanceof NativeFunction)) {
                return super.eval(callerEnv,value);
            }
            NativeFunction func = (NativeFunction)value;
            int nparams = func.numParams;
            if(size() != nparams){
                throw new StoneException("bad number of arguments",this);
            }
            Object[] args = new Object[nparams];
            int num = 0;
            for(ASTree a:this){
                BasicEvaluator.ASTreeEx ae = (BasicEvaluator.ASTreeEx)a;
                args[num++] = ae.eval(callerEnv);
            }
            return func.invoke(args,this);
        }
    }
}
