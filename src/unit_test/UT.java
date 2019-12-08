package unit_test;

public class UT {

    public static boolean assertInstance(Object obj, Class clazz) {
        boolean result = obj.getClass().equals(clazz);
        if (!result) {
            println("assertInstance fail: " + obj + " class " + clazz);
            printCurrentStackTrace();
        }
        return result;
    }

    public static boolean assertEqual(int i, int j) {
        boolean result = (i == j);
        if (!result) {
            println("assertEqual fail: " + i + " " + j);
            printCurrentStackTrace();
        }
        return result;
    }

    public static boolean assertEqual(String a, String b) {
        boolean result = a == b || (a != null && a.equals(b));
        if (!result) {
            println("assertEqual fail: \"" + a + "\", \"" + b + "\"");
            printCurrentStackTrace();
        }
        return result;
    }

    private static void printCurrentStackTrace() {
        StackTraceElement[] WhereWeAre = Thread.currentThread().getStackTrace();
        for (int i = 1; i < WhereWeAre.length; i++){
            StackTraceElement ste = WhereWeAre[i];
            println("\t" + " " + ste.getMethodName()
                        + "("+ ste.getFileName() + ":" + ste.getLineNumber() + ")");
        }
    }

    private static void println(String msg) {
        System.out.println(msg);
    }
}