package qc.veko.ranking.utils;

public class Utils {

    public static boolean isInteger(String text) {
        try {
            Integer.parseInt(text);
            return true;
        } catch (NumberFormatException e) {
            return false;
        }
    }
    public static boolean isDouble(String text) {
        try {
            Double.parseDouble(text);
            return true;
        } catch (NumberFormatException e) {
            return false;
        }
    }

    public static int convertStringToInteger(String text) {
        return Integer.parseInt(text);
    }
    public static double convertStringToDouble(String text) {
        return Double.parseDouble(text);
    }

}
