package charpter02.unit_test;

import java.io.*;

import charpter02.lexer.*;
import unit_test.*;

public class TestLexer {
    public static void main(String[] args) throws IOException {
        Report report = new Report();
        report.add(testNumber());
        report.add(testWord());
        report.add(testComment());
        report.add(testOperator());
        report.add(testFloat());
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

    private static Report testFloat() throws IOException {
        Report report = new Report();
        InputStream in = buildStream(".1 .1f 0.1234 0.5678f 1234.5678 1234.5678f");
        Lexer lexer = new Lexer(in);
        Token f1 = lexer.scan();
        report.add(UT.assertInstance(f1, FloatToken.class));
        report.add(UT.assertEqual(((FloatToken) f1).value, 0.1f));

        report.add(UT.assertEqual(((FloatToken) lexer.scan()).value, 0.1f));
        report.add(UT.assertEqual(((FloatToken) lexer.scan()).value, 0.1234f));
        report.add(UT.assertEqual(((FloatToken) lexer.scan()).value, 0.5678f));
        report.add(UT.assertEqual(((FloatToken) lexer.scan()).value, 1234.5678f));
        report.add(UT.assertEqual(((FloatToken) lexer.scan()).value, 1234.5678f));

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

    private static Report testComment() throws IOException {
        Report report = new Report();
        InputStream in = buildStream("a // hello world\n" +
                                    "anotherLine");
        Lexer lexer = new Lexer(in);
        lexer.scan();
        Token comment = lexer.scan();
        Token another = lexer.scan();
        boolean [] tests = {
            UT.assertInstance(comment, Comment.class),
            UT.assertEqual(((Comment)comment).comment, " hello world"),
            UT.assertEqual(((Word) another).lexeme, "anotherLine"),
        };
        for (boolean test : tests) report.add(test);
        in.close();
        return report;
    }

    private static Report testOperator() throws IOException {
        Report report = new Report();
        InputStream in = buildStream("1 < 2\n" +
                                    "3 <= 4\n");
        Lexer lexer = new Lexer(in);
        lexer.scan();
        Token less = lexer.scan();
        lexer.scan(); lexer.scan();
        Token lessEqual = lexer.scan();
        boolean[] tests = {
            UT.assertInstance(less, Operator.class),
            UT.assertInstance(lessEqual, Operator.class),
            UT.assertEqual(((Operator)less).operator, Operator.LESS),
            UT.assertEqual(((Operator)lessEqual).operator, Operator.LESS_EQUAL),
        };
        for (boolean test : tests) report.add(test);
        in.close();
        return report;
    }

    private static InputStream buildStream(String input) throws IOException {
        return new ByteArrayInputStream(input.getBytes());
    }
}