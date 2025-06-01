package cn.valuetodays.module.codegenerator.util;

import java.util.Objects;

public class StringExUtil {
    private StringExUtil() {
    }

    /**
     * 大写一个字符串的第一个字符
     *
     * @param s s
     */
    public static String capitaliseFirst(final String s) {
        if (s == null) {
            return null;
        }
        if (s.length() == 0) {
            return s;
        }
        StringBuilder cap = new StringBuilder(s.substring(0, 1).toUpperCase());
        if (s.length() > 1) {
            cap.append(s.substring(1));
        }
        return cap.toString();
    }

    /**
     * 小写一个字符串的第一个字符
     *
     * @param s s
     */
    public static String uncapitaliseFirst(final String s) {
        if (s == null) {
            return null;
        }
        if (s.length() == 0) {
            return s;
        }
        StringBuilder cap = new StringBuilder(s.substring(0, 1).toLowerCase());
        if (s.length() > 1) {
            cap.append(s.substring(1));
        }
        return cap.toString();
    }

    public static String trimToEmpty(String str) {
        return str == null ? "" : str.trim();
    }

    public static boolean isNotBlank(String str) {
        return !isBlank(str);
    }

    public static boolean isBlank(String str) {
        int strLen;
        if (str == null || (strLen = str.length()) == 0) {
            return true;
        }
        for (int i = 0; i < strLen; i++) {
            if ((!Character.isWhitespace(str.charAt(i)))) {
                return false;
            }
        }
        return true;
    }

    public static boolean isNumeric(String str) {
        if (str == null) {
            return false;
        }
        int sz = str.length();
        for (int i = 0; i < sz; i++) {
            if (!Character.isDigit(str.charAt(i))) {
                return false;
            }
        }
        return true;
    }

    public static boolean equals(String str1, String str2) {
        return Objects.equals(str1, str2);
    }
}
