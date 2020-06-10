package chap11;

import chap6.BasicEvaluator;
import chap6.Enviroment;
import chap8.Natives;
import stone.*;
import stone.ast.ASTree;
import stone.ast.NullStmnt;

public class EnvOptInterpreter {
    public static void main(String[] args) throws ParseException {
        run(new ClosureParser(), new Natives().enviroment(new ResizableArraryEnv()));
    }

    public static void run(BasicParser bp, Enviroment env) throws ParseException {
        Lexer lexer = new Lexer(new CodeDialog());
        while (lexer.peek(0) != Token.EOF) {
            ASTree t = bp.parse(lexer);
            if (!(t instanceof NullStmnt)) {
                ((EnvOptimizer.ASTreeOptEx) t).lookup(((EnvOptimizer.EnvEx2) env).symbols());
                Object r = ((BasicEvaluator.ASTreeEx) t).eval(env);
                System.out.println("=> " + r);
            }
        }

    }
}
