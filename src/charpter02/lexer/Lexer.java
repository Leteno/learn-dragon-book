package charpter02.lexer;
import java.io.*;
import java.util.*;

public class Lexer {
    public int line = 1;
    private char peek = ' ';
    private Hashtable words = new Hashtable();
    private final InputStream in;
    void reserve(Word t) {
        words.put(t.lexeme, t);
    }
    public Lexer(InputStream in) {
        this.in = in;
        reserve(new Word(Tag.TRUE, "true"));
        reserve(new Word(Tag.FALSE, "false"));
    }
    public Token scan() throws IOException {
        for (;; peek = (char) read()) {
            if (peek == ' ' || peek == '\t') continue;
            else if (peek == '\n') line = line + 1;
            else break;
        }
        if (peek == '/') {
            char nextPeek = (char) read();
            if (nextPeek == '/') {
                peek = ' '; // clear peek
                return commentOneLine();
            } else if (nextPeek == '*') {
                peek = ' ';
                return commentMultiLine();
            } else {
                Token t = new Token(peek);
                peek = nextPeek; // store it.
                return t;
            }
        }
        if (peek == '<') {
            char nextPeek = (char) read();
            if (nextPeek == '=') {
                return new Operator(Operator.LESS_EQUAL);
            } else {
                peek = nextPeek; // don't waste it.
                return new Operator(Operator.LESS);
            }
        }
        if (peek == '>') {
            char nextPeek = (char) read();
            if (nextPeek == '=') {
                return new Operator(Operator.GREATER_EQUAL);
            } else {
                peek = nextPeek; // don't waste it.
                return new Operator(Operator.GREATER);
            }
        }
        if (peek == '=') {
            char nextPeek = (char) read();
            if (nextPeek == '=') {
                return new Operator(Operator.EQUAL_EQUAL);
            } else {
                peek = nextPeek; // don't waste it.
                return new Operator(Operator.EQUAL);
            }
        }
        if (peek == '!') {
            char nextPeek = (char) read();
            if (nextPeek == '=') {
                return new Operator(Operator.NOT_EQUAL);
            } else {
                peek = nextPeek; // don't waste it.
                return new Operator(Operator.NOT);
            }
        }
        if (Character.isDigit(peek) || peek == '.') {
            return numberOrFloat();
        }
        if (Character.isLetter(peek)) {
            StringBuffer b = new StringBuffer();
            do {
                b.append(peek);
                peek = (char) read();
            } while (Character.isLetter(peek));
            String s = b.toString();
            Word w = (Word) words.get(s);
            if (w != null) return w;
            w = new Word(Tag.ID, s);
            words.put(s, w);
            return w;
        }
        Token t = new Token(peek);
        peek = ' ';
        return t;
    }

    int read() throws IOException {
        return in.read();
    }

    private Token numberOrFloat() throws IOException {
        int v = 0;
        while (Character.isDigit(peek)) {
            v = 10*v + Character.digit(peek, 10);
            peek = (char) read();
        };

        if (peek == '.') {
            peek = (char) read();
            int fvalue = 0;
            int exp = 0;
            while (Character.isDigit(peek)) {
                fvalue = fvalue * 10 + Character.digit(peek, 10);
                ++exp;
                peek = (char) read();
            }
            if (peek == 'f') { // eat the last 'f'
                peek = (char) read();
            }
            return new FloatToken(v + fvalue * floatBase(exp));
        }

        if (peek == 'f') {
            peek = (char) read();
            return new FloatToken(v);
        }

        return new Num(v);
    }

    private float floatBase(int exp) {
        float[] store = {
            1f,
            1e-1f,
            1e-2f,
            1e-3f,
            1e-4f,
            1e-5f,
            1e-6f,
            1e-7f,
            1e-8f,
            1e-9f,
            1e-10f,
        };
        if (exp < store.length) {
            return store[exp];
        }
        throw new RuntimeException("float exp should be lower than " + (store.length - 1));
    }

    private Token commentOneLine() throws IOException {
        char ch = (char) read();
        StringBuffer comment = new StringBuffer(ch);
        for (; ch != '\n'; ch = (char) read()) {
            comment.append(ch);
        }
        return new Comment(comment.toString());
    }

    private Token commentMultiLine() throws IOException {
        char ch = (char) read();
        StringBuffer comment = new StringBuffer(ch);
        for (;; ch = (char) read()) {
            while (ch == '*') {
                char another = (char) read();
                if (another == '/') {
                    return new Comment(comment.toString());
                }
                comment.append(ch);
                ch = another;
                continue;
            }
            comment.append(ch);
        }
    }

    public static final void main(String[] args) throws IOException {
        Lexer lexer = new Lexer(System.in);
        while (true) {
            Token token = lexer.scan();
            System.out.println(token.toString());
        }
    }
}