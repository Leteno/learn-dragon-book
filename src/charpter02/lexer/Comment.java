package charpter02.lexer;

public class Comment extends Token {
    public String comment;
    public Comment(String comment) {
        super(Tag.COMMENT);
        this.comment = comment;
    }
    @Override
    public String toString() {
        return String.format("<comment, \"%s\">", comment);
    }
}