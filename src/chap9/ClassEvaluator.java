package chap9;

import chap6.BasicEvaluator;
import chap6.Enviroment;
import chap7.FunctionEvaluator;
import chap7.NestedEnv;
import javassist.gluonj.Require;
import javassist.gluonj.Reviser;
import stone.StoneException;
import stone.ast.ASTree;
import stone.ast.ClassBody;
import stone.ast.ClassStmnt;
import stone.ast.Dot;

import java.util.List;

@Require(FunctionEvaluator.class)
@Reviser
public class ClassEvaluator {
    @Reviser
    public static class ClassStmtEx extends ClassStmnt {

        public ClassStmtEx(List<ASTree> c) {
            super(c);
        }

        public Object eval(Enviroment env) {
            ClassInfo ci = new ClassInfo(this, env);
            ((FunctionEvaluator.EnvEx) env).putNew(name(), ci);
            return name();
        }
    }

    @Reviser
    public static class ClassBodyEx extends ClassBody {

        public ClassBodyEx(List<ASTree> c) {
            super(c);
        }

        public Object eval(Enviroment env) {
            for (ASTree t : this) {
                ((BasicEvaluator.ASTreeEx) t).eval(env);
            }
            return null;
        }
    }

    @Reviser
    public static class DotEx extends Dot {

        public DotEx(List<ASTree> c) {
            super(c);
        }

        public Object eval(Enviroment env, Object value) {
            String member = name();
            if (value instanceof ClassInfo) {
                if ("new".equals(member)) {
                    ClassInfo ci = (ClassInfo) value;
                    NestedEnv e = new NestedEnv(ci.enviroment());
                    StoneObject so = new StoneObject(e);
                    e.putNew("this", so);
                    initObject(ci, e);
                    return so;
                }
            } else if (value instanceof StoneObject) {
                try {
                    return ((StoneObject) value).read(member);
                } catch (StoneObject.AccessException e) {
                }
            }
            throw new StoneException("bad member access: " + member, this);
        }

        protected void initObject(ClassInfo ci, Enviroment env) {
            if (ci.superClass() != null) {
                initObject(ci.superClass(), env);
            }
            ((ClassBodyEx) ci.body()).eval(env);
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
            if (le instanceof FunctionEvaluator.PrimaryEx) {
                FunctionEvaluator.PrimaryEx p = (FunctionEvaluator.PrimaryEx) le;
                if (p.hasPostfix(0) && p.postfix(0) instanceof Dot) {
                    Object t = ((FunctionEvaluator.PrimaryEx) le).evalSubExpr(env, 1);
                    if (t instanceof StoneObject) {
                        return setField((StoneObject) t, (Dot) p.postfix(0), rvalue);
                    }
                }
            }
            return super.computeAssign(env, rvalue);
        }

        protected Object setField(StoneObject obj, Dot expr, Object rvalue) {
            String name = expr.name();
            try {
                obj.write(name, rvalue);
                return rvalue;
            } catch (StoneObject.AccessException e) {
                throw new StoneException("bad member access " + location() + ": " + name);
            }
        }
    }


}
