package my.project.juja.utils;

import my.project.juja.model.table.CellInfo;
import my.project.juja.view.View;
import org.apache.commons.lang3.math.NumberUtils;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * Created by Nikol on 9/22/2016.
 */
public class WhereConstructor {
    View view;
    List<CellInfo> cellInfos;
    List<String> supportedSym;


    String where;

    {
        String[] array = {"=", "<", ">", "!=", "<=", ">="};
        supportedSym = new ArrayList<>(Arrays.asList(array));
    }

    public WhereConstructor(View view, List<CellInfo> cellInfos) {
        this.view = view;
        this.cellInfos = cellInfos;
    }

    @Override
    public String toString() {
        return where;
    }

    public void create() {
        StringBuilder result = new StringBuilder();
        while (true) {
            result.append(createCondition());
            view.write("do you like add another condition? (Y/N)");
            String command = view.read().trim();
            if (JujaUtils.confirm(command, view)) {
                result.append(" ");
                result.append(createConnection());
                result.append(" ");
                continue;
            }else {
                break;
            }
        }
        where = result.toString();
    }


    private StringBuilder createCondition() {
        StringBuilder result = new StringBuilder();
        result.append(createTableName());
        result.append(createSym());
        result.append(createValue());
        return result;
    }

    private String createTableName() {
        view.writeln(cellInfos.toString());
        String columnName;
        while (true) {
            view.writeln("input column name for add to condition");
            columnName = view.read().trim();
            if (!columnName.contains(columnName)) {
                view.writeln("column name '" + columnName + "'" + " doesn't exist");
                continue;
            }
            break;
        }
        return columnName;
    }

    private String createSym() {
        String result;
        while (true) {
            view.writeln("choose " + supportedSym.toString());
            result = view.read().trim();
            if (!supportedSym.contains(result)) {
                view.writeln("symbol '" + result + " is not supported");
                continue;
            }
            break;
        }
        return result;
    }

    private String createValue() {
        String result;
        view.writeln("input value");
        result = view.read();
        return "'" + result + "'";
    }

    private String createConnection() {
        String result;
        while (true) {
            view.writeln("choose and input condition 'or', 'and'");
            result = view.read().trim();
            if (result.equalsIgnoreCase("or") || result.equalsIgnoreCase("and")) {
                break;
            } else {
                view.writeln("you have to input 'or', 'and'. But you input " + result);
                continue;
            }
        }
        return result;
    }
}
