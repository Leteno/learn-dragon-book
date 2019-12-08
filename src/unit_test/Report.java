package unit_test;

public class Report {
    public boolean totalPass = true;
    public int passCount = 0;
    public int totalCount = 0; // NOTE   divide-zero

    public void add(boolean pass) {
        totalPass &= pass;
        if (pass) ++passCount;
        ++totalCount;
    }

    public boolean pass() {
        return totalPass;
    }

    public void add(Report report) {
        totalPass &= report.totalPass;
        passCount += report.passCount;
        totalCount += report.totalCount;
    }

    @Override
    public String toString() {
        return "pass " + passCount + "/" + totalCount;
    }
}