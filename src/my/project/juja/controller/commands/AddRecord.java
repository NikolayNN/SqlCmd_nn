package my.project.juja.controller.commands;

import my.project.juja.model.Storeable;
import my.project.juja.model.Table;
import my.project.juja.view.View;

import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Created by Nikol on 4/16/2016.
 */
public class AddRecord extends Command {
    public static final String name = Command.ADD_RECORD;
    private static final int EXPECTED_COUNT_PARAMETERS = 1;
    private static final String SUB_COMMAND_SAVE = "save";
    private static final String SUB_COMMAND_CANCEL = "cancel";
    private int countColumnsToEdit;

    public AddRecord(Storeable dataBase, View view) {
        super(dataBase, view);
    }

    @Override
    public void perform() {
        checkCountParameters(parametrs, EXPECTED_COUNT_PARAMETERS);
        String tableName = parametrs[0];
        Table table = new Table(store, tableName);
        view.writeln(getColumnNameWithIndexes(table));
        view.writeln("Choose index column to fill, through space. Example \"0 1 2 3\"");
        //ввод номеров колонок для редактирования
        while(true) {
            try {
                String str = view.read();
                table.setColumnsToEditIdx(receiveColumnToEdit(str, table));
                break;
            } catch (RuntimeException e) {
                view.writeln(e.getMessage());
            }
        }
        view.writeln("Input values for columns, available \"cancel\" and \"save\" commands");
        String command;
        //ввод данных таблицы
        while(true) {
            command = view.read();
            if(command.equalsIgnoreCase(SUB_COMMAND_SAVE)){
                table.saveTable();
                view.writeln("Records saved!");
                break;
            }
            if(command.equalsIgnoreCase(SUB_COMMAND_CANCEL)){
                table.clearTable();
                view.writeln("Canceled!");
                break;
            }
            String tableLine = command;
            try {
                checkCountParameters(command.split(" "), countColumnsToEdit);
            } catch (RuntimeException e) {
                view.writeln("Check count values. Expected " + countColumnsToEdit + ", but found " + command.split(" ").length);
                tableLine = null;
            }
            table.addTableLine(tableLine);
        }
    }

    private String receiveColumnToEdit(String source, Table table) {
        if(!isOnlySpaceAndNumbs(source)){
            throw new RuntimeException("ERROR. This command may consist only numbs " +
                    "throught spaces. Example \"0 1 2 3\"");
        }
        if(!isValidIndexes(source, table)){
            int maxColumnIndex = table.getColumnQuantity() - 1;
            throw new RuntimeException( "ERROR. You input incorrect column index. " +
                                        "valid index >= 0, and <= " + maxColumnIndex );
        }
        return source;
    }

    private boolean isValidIndexes(String source, Table table){
        int[] columnsIndexes = StringToIntArray(source);
        int maxColumnIndex = table.getColumnQuantity() - 1;
        for (int i = 0; i < columnsIndexes.length; i++) {
            if((columnsIndexes[i] < 0) || (columnsIndexes[i] > maxColumnIndex) ){
                return false;
            }
        }
        countColumnsToEdit = columnsIndexes.length;
        return true;
    }

    private int[] StringToIntArray(String source){
        String[] strArray = source.split(" ");
        int[] result = new int[strArray.length];
        for (int i = 0; i < strArray.length; i++) {
            result[i] = Integer.parseInt(strArray[i]);
        }
        return result;
    }

    private boolean isOnlySpaceAndNumbs(String line) {
        Pattern p = Pattern.compile("^[0-9\\s-]{3,60}$");
        Matcher m = p.matcher(line);
        return m.matches();
    }

    private String getColumnNameWithIndexes(Table table){
        List<String> columnNames = table.getColumnNames();
        String result = "";
        for (int i = 0; i < columnNames.size(); i++) {
            result += columnNames.get(i) + "(" + i + ") ";
        }
        return result;
    }

    @Override
    public String getName() {
        return name;
    }
}
