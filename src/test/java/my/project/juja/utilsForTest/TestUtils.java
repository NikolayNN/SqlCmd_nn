package my.project.juja.utilsForTest;

import org.mockito.ArgumentCaptor;

/**
 * Created by Nikol on 9/25/2016.
 */
public class TestUtils {
    public static String replaceLineSeparator(String s) {
        return s.replaceAll("\\n", System.lineSeparator());
    }

    public static String getString(ArgumentCaptor<String> captor) {
        StringBuilder result = new StringBuilder();
        for (String s : captor.getAllValues()) {
            result.append(s);
            result.append("\n");
        }
        return result.toString();
    }

}
