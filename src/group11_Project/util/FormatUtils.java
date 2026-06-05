package group11_Project.util;

public final class FormatUtils {
    private FormatUtils() {}

    public static String fmt(double v) {
        return String.format("₱%,.2f", v);
    }
}
