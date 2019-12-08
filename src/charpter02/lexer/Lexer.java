package charpter02.lexer;
import java.io.*;
import java.util.*;

public class Lexer {
    public int line = 1;
    private char peek = ' ';
    private Hashtable words = new Hashtable();
    void reserve(Word t) {
        words.put(t.lexeme, t);
    }
    public Lexer() {
        reserve(new Word(Tag.TRUE, "true"));
        reserve(new Word(Tag.FALSE, "false"));
    }
    public Token scan() throws IOException {
        for (;; peek = (char) System.in.read()) {
            if (peek == ' ' || peek == '\t') continue;
            else if (peek == '\n') line = line + 1;
            else break;
        }
        if (peek == '/') {
            char nextPeek = (char) System.in.read();
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
        if (Character.isDigit(peek)) {
            int v = 0;
            do {
                v = 10*v + Character.digit(peek, 10);
                peek = (char) System.in.read();
            } while (Character.isDigit(peek));
            return new Num(v);
        }
        if (Character.isLetter(peek)) {
            StringBuffer b = new StringBuffer();
            do {
                b.append(peek);
                peek = (char) System.in.read();
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

    private Token commentOneLine() throws IOException {
        char ch = (char) System.in.read();
        StringBuffer comment = new StringBuffer(ch);
        for (; ch != '\n'; ch = (char) System.in.read()) {
            comment.append(ch);
        }
        return new Comment(comment.toString());
    }

    private Token commentMultiLine() throws IOException {
        char ch = (char) System.in.read();
        StringBuffer comment = new StringBuffer(ch);
        for (;; ch = (char) System.in.read()) {
            if (ch == '*') {
                char another = (char) System.in.read();
                if (another == '/') {
                    return new Comment(comment.toString());
                }
                comment.append(ch);
                comment.append(another);
                continue;
            }
            comment.append(ch);
        }
    }

    public static final void main(String[] args) throws IOException {
        Lexer lexer = new Lexer();
        while (true) {
            Token token = lexer.scan();
            System.out.println(token.toString());
        }
    }
}