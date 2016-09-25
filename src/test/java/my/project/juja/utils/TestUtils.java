package my.project.juja.utils;

/**
 * Created by Nikol on 9/25/2016.
 */
public class TestUtils {
    public static String replaceLineSeparator(String s){
        return s.replaceAll("\\n", System.lineSeparator());
    }
}
