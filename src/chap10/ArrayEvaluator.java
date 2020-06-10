package chap10;

import chap6.BasicEvaluator;
import chap6.Enviroment;
import chap7.FunctionEvaluator;
import javassist.gluonj.Require;
import javassist.gluonj.Reviser;
import stone.ArrayParser;
import stone.StoneException;
import stone.ast.ASTree;
import stone.ast.ArrayLiteral;
import stone.ast.ArrayRef;
import stone.ast.PrimaryExpr;

import java.util.List;

@Require({FunctionEvaluator.class, ArrayParser.class})
@Reviser
public class ArrayEvaluator {
    @Reviser
    public static class ArrayLitEx extends ArrayLiteral {

        public ArrayLitEx(List<ASTree> list) {
            super(list);
        }

        public Object eval(Enviroment env) {
            int s = numChildren();
            Object[] res = new Object[s];
            int i = 0;
            for (ASTree t : this) {
                res[i++] = ((BasicEvaluator.ASTreeEx) t).eval(env);
            }
            return res;
        }
    }

    @Reviser
    public static class ArrayRefEx extends ArrayRef {

        public ArrayRefEx(List<ASTree> c) {
            super(c);
        }

        public Object eval(Enviroment env, Object value) {
            if (value instanceof Object[]) {
                Object index = ((BasicEvaluator.ASTreeEx) index()).eval(env);
                if (index instanceof Integer) {
                    return ((Object[]) value)[(Integer) index];
                }
            }
            throw new StoneException("bad array access", this);
        }

    }

    @Reviser
    public static class AssignEx extends BasicEvaluator.BinaryEx {

        public AssignEx(List<ASTree> c) {
            super(c);
        }

        @Override
        protected Object computeAssign(Enviroment env, Object rvalue) {
            ASTree le = left();
            if (le instanceof PrimaryExpr) {
                FunctionEvaluator.PrimaryEx p = (FunctionEvaluator.PrimaryEx) le;
                if (p.hasPostfix(0) && p.postfix(0) instanceof ArrayRef) {
                    Object a = ((FunctionEvaluator.PrimaryEx) le).evalSubExpr(env, 1);
                    if (a instanceof Object[]) {
                        ArrayRef aref = (ArrayRef) p.postfix(0);
                        Object index = ((BasicEvaluator.ASTreeEx) aref.index()).eval(env);
                        if (index instanceof Integer) {
                            ((Object[]) a)[(Integer) index] = rvalue;
                            return rvalue;
                        }
                    }
                    throw new StoneException("bad array access ", this);
                }
            }

            return super.computeAssign(env, rvalue);
        }
    }
}
