package chap12;

import chap11.EnvOptInterpreter;
import chap11.ResizableArraryEnv;
import chap8.Natives;
import stone.ClassParser;
import stone.ParseException;

public class ObjOptInterpreter extends EnvOptInterpreter {
    public static void main(String[] args) throws ParseException {
        run(new ClassParser(), new Natives().enviroment(new ResizableArraryEnv()));
    }
}
