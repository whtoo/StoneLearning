package chap9;

import chap7.NestedEnv;
import chap8.Natives;
import stone.ClassParser;
import stone.ParseException;

import static chap6.BasicInterpreter.run;

public class ClassInterpreter {
    public static void main(String[] args) throws ParseException {
        run(new ClassParser(), new Natives().enviroment(new NestedEnv()));
    }
}
