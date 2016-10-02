package my.project.juja.controller.commands.table;

import my.project.juja.controller.commands.Command;
import my.project.juja.model.Storeable;
import my.project.juja.model.table.CellInfo;
import my.project.juja.view.View;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Set;

/**
 * Created by Nikol on 9/21/2016.
 */
public class CreateTable extends Command {
    private static final String NAME = Command.CREATE_TABLE;
    private static final int EXPECTED_COUNT_PARAMETERS = 1;
    private List<String> supportedTypes = Arrays.asList(new String[]{"text", "character", "integer", "real"});

    public CreateTable(Storeable store, View view) {
        super(store, view);
    }

    @Override
    public void perform() {
        isConnectedDataBase();
        checkCountParameters(parametrs, EXPECTED_COUNT_PARAMETERS);
        String tableName = parametrs[0];
        checkName(tableName);
        List<CellInfo> cellInfos = createCellInfos(view);
        store.createTable(tableName, cellInfos);
        view.writeln("table '" + tableName + "' added");
        view.writeln(cellInfos.toString());
    }

    private void checkName(String tableName) {
        if (isTableExist(tableName)) {
            throw new RuntimeException("ERROR. table '" + tableName + "'" + " is already exist.");
        }
    }

    private List<CellInfo> createCellInfos(View view) {
        List<CellInfo> cellInfos = new ArrayList<>();
        int columnIndex = 0;
        String command;
        main:
        while (true) {
            CellInfo cellInfo = createColumnInfo(view, cellInfos, columnIndex++);
            cellInfos.add(cellInfo);
            second:
            while (true) {
                view.writeln("input 'save' to save the table, input 'add' to add one more column, input 'cancel' to cancel");
                command = view.read();
                if (command.equalsIgnoreCase("save")) {
                    break main;
                }
                if (command.equalsIgnoreCase("cancel")) {
                    throw new RuntimeException("create table cancelled");
                }
                if (command.equalsIgnoreCase("add")) {
                    continue main;
                } else {
                    view.writeln("You input wrong command '" + command + "'. Available comands: 'save', 'add', 'cancel' ");
                    continue second;
                }
            }
        }
        return cellInfos;
    }


    private CellInfo createColumnInfo(View view, List<CellInfo> cellInfos, int columnIndex) {
        String columnName = inputColumnName(view);
        String type = inputColumnType(view);
        int length = inputColumnLength(view, type);
        boolean canBeNull = inputCanBeNull(view);
        CellInfo cellInfo = new CellInfo(columnName, type, canBeNull, false, columnIndex);
        cellInfo.setLength(length);
        return cellInfo;
    }


    private String inputColumnName(View view) {
        String columnName;
        while (true) {
            try {
                view.writeln("input column name:");
                columnName = view.read().trim();
                checkHasSpace(columnName);
                break;
            } catch (RuntimeException ex) {
                view.writeln(ex.getMessage());
            }

        }
        return columnName;
    }

    private String inputColumnType(View view) {
        String type;
        while (true) {
            try {
                view.writeln("choose column type:");
                view.writeln(supportedTypes.toString());
                type = view.read().trim();
                if (!supportedTypes.contains(type.toLowerCase())) {
                    throw new RuntimeException("you input wrong type.");
                }
                break;
            } catch (RuntimeException ex) {
                view.writeln(ex.getMessage());
            }
        }
        return type;
    }

    private int inputColumnLength(View view, String type) {
        if (!type.equalsIgnoreCase("character")) {
            return 0;
        }
        int length;
        while (true) {
            try {
                view.writeln("input length or press 'enter' to set length = 256");
                String s = view.read().trim();
                if (s.equalsIgnoreCase("")) {
                    return 256;
                }
                length = Integer.parseInt(s);
                break;
            } catch (NumberFormatException ex) {
                view.writeln("you inputed wrong number");
            }
        }
        return length;
    }

    private boolean inputCanBeNull(View view) {
        while (true) {
            try {
                view.writeln("Column can be null?(Y/N)");
                String s = view.read().trim();
                if (s.equalsIgnoreCase("y")) {
                    return true;
                }
                if (s.equalsIgnoreCase("n")) {
                    return false;
                } else {
                    throw new RuntimeException("you have to input 'Y' or 'N'");
                }
            } catch (RuntimeException ex) {
                view.writeln(ex.getMessage());
            }
        }
    }

    private void checkHasSpace(String s) {
        if (s.contains(" ")) {
            throw new RuntimeException("ERROR. you can't use spaces here");
        }
    }


    @Override
    public String getName() {
        return NAME;
    }
}
