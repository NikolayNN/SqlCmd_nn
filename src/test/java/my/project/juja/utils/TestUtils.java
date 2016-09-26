package my.project.juja.utils;

import my.project.juja.model.table.CellInfo;
import my.project.juja.model.table.Row;
import my.project.juja.model.table.Table;
import org.mockito.ArgumentCaptor;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Nikol on 9/25/2016.
 */
public class TestUtils {
    public static String replaceLineSeparator(String s){
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
