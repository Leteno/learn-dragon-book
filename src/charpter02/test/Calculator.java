package charpter02.test;
import java.io.*;

public class Calculator {
    public static final void main(final String[] args) throws Exception {

        final String[] tests = { "1 + 2", "2 * 3", "4 * 5 + 1", "1 + 4 * 5", "1 + 3 + 3 + 4 + 23 + 5 * 6 + 3 * 4" };

        for (final String test : tests) {
            System.out.println("calc: " + test);
            final Calculator calc = new Calculator(test);
            final int result = calc.calculate();
            System.out.println("result " + result);
        }
    }

    final String mCode;
    int index = 0;

    public Calculator(final String code) {
        mCode = code;
    }

    int calculate() {
        return expr();
    }

    int expr() {
        // + -
        int val1 = term();
        while (checkValid(index)) {
            final char ch = mCode.charAt(index);
            if (ch == '+') {
                moveForward();
                final int val2 = term();
                val1 += val2;
            } else if (ch == '-') {
                moveForward();
                final int val2 = term();
                val1 -= val2;
            } else break;
        }
        return val1;
    }

    int term() {
        int val1 = digit();
        while (checkValid(index)) {
            final char ch = mCode.charAt(index);
            if (ch == '*') {
                moveForward();
                final int val2 = digit();
                val1 *= val2;
            } else if (ch == '/') {
                moveForward();
                final int val2 = digit();
                val1 /= val2;
            } else break;
        }
        return val1;
    }

    int digit() {
        int val1 = number();
        while (checkValid(index)) {
            final char ch = mCode.charAt(index);
            if (Character.isDigit(ch)) {
                final int val2 = number();
                val1 = val1 * 10 + val2;
                moveForward();
            } else break;
        }
        return val1;
    }

    int number() {
        // TODO output error
        if (mCode.charAt(index) == ' ')
            moveForward();
        final int result = Character.digit(mCode.charAt(index), 10);
        moveForward();
        return result;
    }

    boolean checkValid(final int index) {
        return mCode.length() > index;
    }

    void moveForward() {
        // do nothing
        do {
            ++index;
        } while (checkValid(index) && mCode.charAt(index) == ' ');
    }
}