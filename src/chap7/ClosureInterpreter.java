package chap7;

import chap6.BasicInterpreter;
import stone.ClosureParser;
import stone.ParseException;

public class ClosureInterpreter extends BasicInterpreter {
    public static void main(String[] args) throws ParseException {
        run(new ClosureParser(),new NestedEnv());
    }
}