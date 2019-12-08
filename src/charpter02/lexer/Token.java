package charpter02.lexer;

public class Token {
    public final int tag;
    public Token(int t) { tag = t; }
    @Override
    public String toString() {
        return String.format("<%d>", tag);
    }
}