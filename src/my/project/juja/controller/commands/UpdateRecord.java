package my.project.juja.controller.commands;

import my.project.juja.model.Storeable;
import my.project.juja.utils.JujaUtils;
import my.project.juja.view.View;

import java.sql.Array;
import java.util.*;

/**
 * Created by Nikol on 8/20/2016.
 */
public class UpdateRecord extends Command {

    private static final String NAME = Command.UPDATE_TABLE;
    private static final int COUNT_PARAMETERS = 2;
    private static final String SUB_COMMAND_SAVE = "save";
    private static final String SUB_COMMAND_CANCEL = "cancel";


    public UpdateRecord(Storeable store, View view) {
        super(store, view);
    }

    @Override
    public void perform() {
        String tableName = parametrs[0];
        String where = addQuotes(parametrs[1]);

        List<String> columnNames = store.getColumnName(tableName);
        view.writeln(JujaUtils.numberList(columnNames));
        view.writeln(store.getTableData(tableName, where).toString());

        view.writeln("choose column for update. input column index separated by space. Example \"0 1 2 3\" ");

        Set<Integer> selectedColumnIndexes;
        while(true) {
            String command = view.read();
            command.trim();
            try {
                selectedColumnIndexes = JujaUtils.toSetInteger(command.split(" "));
                JujaUtils.validate(selectedColumnIndexes, columnNames.size());
                break;
            }catch (NumberFormatException ex){
                view.writeln("ERROR. check your input! only numbers, and can't be empty!");
            }catch (IllegalArgumentException ex){
                view.writeln(ex.getMessage());
            }
        }
        Iterator<Integer> itr = selectedColumnIndexes.iterator();
        List<String> valueList = new ArrayList<>();
        String set = "";
        while(itr.hasNext()){
            String currentColumnName = columnNames.get(itr.next());
            String currentColumnType = store.getColumnType(tableName, currentColumnName);
            view.writeln(currentColumnName + "[" + currentColumnType + "]");
            String value = "'" + view.read() + "'";
            set += currentColumnName + "=" + value;
            valueList.add(set);
        }
        store.updateRecord(tableName, where, valueList);








    }
    //example input: name=Alex return: name='Alex'
    private String addQuotes(String where) {
        String result;
        String[] StringArray = where.split("=");
        result = StringArray[0] + "=" + "'" + StringArray[1] + "'";
        return result;
    }

    @Override
    public String getName() {
        return NAME;
    }
}
