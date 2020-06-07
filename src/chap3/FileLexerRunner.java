package chap3;

import stone.CodeDialog;
import stone.Lexer;
import stone.ParseException;
import stone.Token;

import java.io.FileNotFoundException;

public class FileLexerRunner {
    public static void main(String[] args) throws ParseException {
        try {
            Lexer l = new Lexer(CodeDialog.file());
            for (Token t;(t = l.read()) != Token.EOF;){
                System.out.print("=> "+t.getText());
            }
        }catch (FileNotFoundException e) {
            System.out.println(e.getMessage());
        }
    }
}
