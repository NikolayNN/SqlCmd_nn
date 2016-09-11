package my.project.juja.controller.commands;

import my.project.juja.controller.commands.table.Cell;
import my.project.juja.controller.commands.table.Row;
import my.project.juja.controller.commands.table.Table;
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
        Table table = store.getTableData(tableName, where);
        table.addRow(new Row(table.getCellInfos()));
        view.writeln(table.toString());
        for (Cell cell : table.getRow(0).getCells()) {
            view.writeln("input new value or skip");
            view.writeln(cell.getCellInfo().toString());
            cell.setValue(view.read(), false);
        }
        store.updateRecord(where, table);



//        Set<Integer> selectedColumnIndexes;
//        while(true) {
//            String commands = view.read();
//            commands.trim();
//            try {
//                selectedColumnIndexes = JujaUtils.toSetInteger(commands.split(" "));
//                JujaUtils.validate(selectedColumnIndexes, columnNames.size());
//                break;
//            }catch (NumberFormatException ex){
//                view.writeln("ERROR. check your input! only numbers, and can't be empty!");
//            }catch (IllegalArgumentException ex){
//                view.writeln(ex.getMessage());
//            }
//        }
//        Iterator<Integer> itr = selectedColumnIndexes.iterator();
//        List<String> valueList = new ArrayList<>();
//        String set = "";
//        while(itr.hasNext()){
//            String currentColumnName = columnNames.get(itr.next());
//            String currentColumnType = store.getColumnType(tableName, currentColumnName);
//            view.writeln(currentColumnName + "[" + currentColumnType + "]");
//            String value = "'" + view.read() + "'";
//            set += currentColumnName + "=" + value;
//            valueList.add(set);
//        }
//        store.updateRecord(tableName, where, valueList);
//
//
//
//
//
//
//
//
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
