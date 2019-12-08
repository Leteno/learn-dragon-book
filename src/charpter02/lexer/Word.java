package charpter02.lexer;
public class Word extends Token {
    public final String lexeme;
    public Word(int t, String s) {
        super(t);
        lexeme = new String(s);
    }
    @Override
    public String toString() {
        return String.format("<word, %d, %s>", super.tag, lexeme);
    }
}