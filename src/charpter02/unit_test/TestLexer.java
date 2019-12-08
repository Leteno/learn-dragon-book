package charpter02.unit_test;

import java.io.*;

import charpter02.lexer.*;
import unit_test.*;

public class TestLexer {
    public static void main(String[] args) throws IOException {
        Report report = new Report();
        report.add(testNumber());
        report.add(testWord());
        System.out.println(report.toString());
    }

    private static Report testNumber() throws IOException {
        Report report = new Report();
        InputStream in = buildStream("1234");
        Lexer lexer = new Lexer(in);
        Token t1 = lexer.scan();
        report.add(UT.assertInstance(t1, Num.class));
        report.add(UT.assertEqual(((Num) t1).value, 1234));
        in.close();
        return report;
    }

    private static Report testWord() throws IOException {
        Report report = new Report();
        InputStream in = buildStream("hello world");
        Lexer lexer = new Lexer(in);
        Token t1 = lexer.scan();
        Token t2 = lexer.scan();
        boolean[] tests = {
            UT.assertInstance(t1, Word.class),
            UT.assertEqual(((Word)t1).lexeme, "hello"),
            UT.assertInstance(t2, Word.class),
            UT.assertEqual(((Word)t2).lexeme, "world"),
        };
        for (boolean test : tests) {
            report.add(test);
        }
        in.close();
        return report;
    }

    private static InputStream buildStream(String input) throws IOException {
        return new ByteArrayInputStream(input.getBytes());
    }
}