package charpter02.lexer;

public class FloatToken extends Token {
    public final float value;
    public FloatToken(float value) {
        super(Tag.FLOAT);
        this.value = value;
    }

    @Override
    public String toString() {
        return String.format("<float, %f>", value);
    }
}