package io.github.klsmith.ifpfc;

import java.math.BigDecimal;

public class PostfixTestHelper {

    public static boolean assumeFiniteDoubles(Double... doubles) {
        for (Double doubleVal : doubles) {
            if (!Double.isFinite(doubleVal)) {
                return false;
            }
        }
        return true;
    }

    public static String withSpaces(Object... objects) {
        String[] strings = new String[objects.length];
        for (int i = 0; i < objects.length; i++) {
            Object obj = objects[i];
            if (obj instanceof Integer) {
                obj = BigDecimal.valueOf((Integer) obj);
            }
            if (obj instanceof Double) {
                obj = BigDecimal.valueOf((Double) obj);
            }
            strings[i] = obj.toString();
        }
        return String.join(" ", strings);
    }

}
