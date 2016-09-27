package my.project.juja.testutils;

import my.project.juja.view.View;

import java.util.List;
import java.util.Set;
import java.util.TreeSet;

/**
 * Created by Nikol on 8/20/2016.
 */
public class JujaUtils {

    public static Boolean setBoolean(String str, String isTrue) {
        if (isTrue == null && str == null) {
            return true;
        }
        return str.equalsIgnoreCase(isTrue);
    }

    public static String numberList(List<String> list) {
        String result = "";
        for (int i = 0; i < list.size(); i++) {
            result += list.get(i) + "(" + i + ") ";
        }
        return result;
    }

    public static Set<Integer> toSetInteger(String[] array) throws NumberFormatException {
        Set<Integer> result = new TreeSet<>();
        for (String s : array) {
            result.add(Integer.parseInt(s));
        }
        return result;
    }

    public static void main(String[] args) {
        String[] array = {"0", "1", "2", "4"};
        Set<Integer> set = toSetInteger(array);

        validate(set, 4);
    }

    public static void validate(Set<Integer> indexes, int size) throws IllegalArgumentException {

        int maxIndex = size - 1;

        if (indexes.size() == 0) {
            throw new IllegalArgumentException(" expect at least one number, but input 0");
        }
        if (indexes.size() > size) {
            throw new IllegalArgumentException(" expect count" + size + " , but input " + indexes.size() + " elements ");
        }

        for (Integer index : indexes) {
            if (index >= size) {
                throw new IllegalArgumentException(" the numbers can't be more than " + maxIndex);
            }
            if (index < 0) {
                throw new IllegalArgumentException(" the numbers can't be less than 0");
            }
        }
    }

    public static boolean confirm (String command, View view) {
        while (true) {
            if (command.equalsIgnoreCase("n")) {
                return false;
            }
            if (command.equalsIgnoreCase("y")) {
                return true;
            } else {
                view.writeln("wrong input");
                continue;
            }
        }
    }
}
