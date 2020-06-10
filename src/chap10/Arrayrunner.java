package chap10;

import chap7.ClosureEvaluator;
import chap8.NativeEvaluator;
import chap9.ClassEvaluator;
import chap9.ClassInterpreter;
import javassist.gluonj.util.Loader;

public class Arrayrunner {
    public static void main(String[] args) throws Throwable {
        Loader.run(ClassInterpreter.class, args, ClassEvaluator.class,
                ArrayEvaluator.class, NativeEvaluator.class,
                ClosureEvaluator.class);
    }
}
