package chap8;

import chap6.BasicInterpreter;
import chap7.NestedEnv;
import stone.ClosureParser;
import stone.ParseException;

public class NativeInterpreter extends BasicInterpreter {
    public static void main(String[] args) throws ParseException {
        run(new ClosureParser(),new Natives().enviroment(new NestedEnv()));
    }
}
