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
        int result = 0;
        if (mCode.charAt(index) == ' ') moveForward();
        while (checkValid(index) && '0' <= mCode.charAt(index) && mCode.charAt(index) <= '9') {
            result = result * 10 + (mCode.charAt(index) - '0');
            moveForward();
        }
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