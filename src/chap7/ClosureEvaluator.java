package chap7;

import chap6.Enviroment;
import javassist.gluonj.Require;
import javassist.gluonj.Reviser;
import stone.ast.ASTree;
import stone.ast.Fun;

import java.util.List;

@Require(FunctionEvaluator.class)
@Reviser public class ClosureEvaluator {
    @Reviser public static class FuncEx extends Fun {

        public FuncEx(List<ASTree> c) {
            super(c);
        }

        public Object eval(Enviroment env) {
            return new Function(parameters(),body(),env);
        }
    }
}
