package charpter02.lexer;

public class Operator extends Token {
    public final static int
        LESS = 0, LESS_EQUAL = 1, EQUAL_EQUAL = 2, NOT_EQUAL = 3, GREATER_EQUAL = 4, GREATER = 5,
        EQUAL = 6, NOT = 7;
    
    public final int operator;
    public Operator(int op) {
        super(Tag.OPERATOR);
        this.operator = op;
    }

    @Override
    public String toString() {
        String opStr;
        switch (operator) {
            case LESS:
                opStr = "LESS";
                break;
            case LESS_EQUAL:
                opStr = "LESS_EQUAL";
                break;
            case EQUAL:
                opStr = "EQUAL";
                break;
            case EQUAL_EQUAL:
                opStr = "EQUAL_EQUAL";
                break;
            case NOT:
                opStr = "NOT";
                break;
            case NOT_EQUAL:
                opStr = "NOT_EQUAL";
                break;
            case GREATER_EQUAL:
                opStr = "GREATER_EQUAL";
                break;
            case GREATER:
                opStr = "GREATER";
                break;
            default:
                opStr = "UNKNOWN";
        }
        return String.format("<operator, %s>", opStr);
    }
}