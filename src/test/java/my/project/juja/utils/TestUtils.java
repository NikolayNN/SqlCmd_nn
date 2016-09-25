package my.project.juja.utils;

import my.project.juja.model.table.CellInfo;
import my.project.juja.model.table.Row;
import my.project.juja.model.table.Table;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Nikol on 9/25/2016.
 */
public class TestUtils {
    public static String replaceLineSeparator(String s){
        return s.replaceAll("\\n", System.lineSeparator());
    }



}
