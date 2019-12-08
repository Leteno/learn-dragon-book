import java.io.*;

class Parser {
    static int lookahead;
    int at;

    public Parser() throws IOException {
        at = 0;
        moveForward();
    }

    void expr() throws IOException {
        term();
        while(true) {
            if (lookahead == '+') {
                match('+'); term(); System.out.write('+');
            }
            else if(lookahead == '-') {
                match('-'); term(); System.out.println('-');
            }
            else return;
        }
    }

    void term() throws IOException {
        digit();
        while (true) {
            if (lookahead == '*') {
                match('*'); term(); System.out.write('*');
            }
            else if (lookahead == '/') {
                match('/'); term(); System.out.write('/');
            }
            else return;
        }
    }

    void digit() throws IOException {
        if (Character.isDigit((char)lookahead)) {
            System.out.write((char)lookahead); match(lookahead);
        }
        else error("syntax error");
    }

    void match(int t) throws IOException {
        if (lookahead == t) moveForward();
        else error("syntax error");
    }

    void moveForward() throws IOException {
        do {
            lookahead = System.in.read();
            at++;
        } while (lookahead == ' ');
    }

    void error(String msg) throws IOException{
        System.out.write('\n');
        System.out.write(("Meet error: " + msg + " at " + at).getBytes());
        System.out.write('\n');
        throw new Error(msg);
    }
}

public class Postfix {
    public static void main(String[] args) throws IOException {
        Parser parse = new Parser();
        parse.expr(); System.out.write('\n');
    }
}