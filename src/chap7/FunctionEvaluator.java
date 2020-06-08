package chap7;

import chap6.BasicEvaluator;
import chap6.Enviroment;
import javassist.gluonj.Require;
import javassist.gluonj.Reviser;
import stone.StoneException;
import stone.ast.*;

import java.util.List;

@Require(BasicEvaluator.class)
// 如果不加@Reviser，则会导致类转换异常(stone.ast.DefStmnt cannot be cast to chap6.BasicEvaluator$ASTreeEx)
@Reviser public class FunctionEvaluator  {
    @Reviser public static interface EnvEx extends Enviroment {
        void putNew(String name,Object value);
        Enviroment where(String name);
        void setOuter(Enviroment e);
    }

    @Reviser public static class DefStmtEx extends DefStmnt {

        public DefStmtEx(List<ASTree> c) {
            super(c);
        }

        public  Object eval(Enviroment env) {
            ((EnvEx)env).putNew(name(),new Function(parameters(),body(),env));
            return name();
        }
    }

    @Reviser public static class PrimaryEx extends PrimaryExpr {

        public PrimaryEx(List<ASTree> c) {
            super(c);
        }

        public ASTree operand() { return child(0);}
        public Postfix postfix(int nest) {
            return (Postfix)child(numChildren() - nest - 1);
        }
        public boolean hasPostfix(int nest){
            return numChildren() - nest > 1;
        }

        public Object eval(Enviroment env) {
            return evalSubExpr(env,0);
        }

        public Object evalSubExpr(Enviroment env,int nest) {
            if(hasPostfix(nest)) {
                Object target = evalSubExpr(env,nest + 1);
                return ((PostfixEx)postfix(nest)).eval(env,target);
            }
            else {
                return ((BasicEvaluator.ASTreeEx)operand()).eval(env);
            }
        }
    }

    @Reviser public static abstract class PostfixEx extends Postfix {

        public PostfixEx(List<ASTree> c) {
            super(c);
        }

        public abstract Object eval(Enviroment env,Object value);
    }

    @Reviser public static class ArgumentsEx extends Arguments {

        public ArgumentsEx(List<ASTree> c) {
            super(c);
        }

        public Object eval(Enviroment callerEnv,Object value) {
            if(!(value instanceof Function)) {
                throw new StoneException("bad function",this);
            }
            Function func = (Function)value;
            ParameterList params = func.parameters();
            if(size() != params.size())
                throw new StoneException("bad number of arguments",this);
            Enviroment newEnv = func.makeEnv();
            int num = 0;
            for(ASTree a: this)
                ((ParamsEx)params).eval(newEnv,num++,((BasicEvaluator.ASTreeEx)a).eval(callerEnv));
            return ((BasicEvaluator.BinaryEx.BlockEx)func.body()).eval(newEnv);
        }

    }
    @Reviser public static class ParamsEx extends ParameterList {

        public ParamsEx(List<ASTree> c) {
            super(c);
        }
        public void eval(Enviroment env,int index,Object value) {
            ((EnvEx)env).putNew(name(index),value);
        }
    }
}
