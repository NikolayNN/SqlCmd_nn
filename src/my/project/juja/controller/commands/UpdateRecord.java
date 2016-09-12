package my.project.juja.controller.commands;

import my.project.juja.controller.commands.table.Cell;
import my.project.juja.controller.commands.table.Row;
import my.project.juja.controller.commands.table.Table;
import my.project.juja.model.Storeable;
import my.project.juja.view.View;

/**
 * Created by Nikol on 8/20/2016.
 */
public class UpdateRecord extends Command {

    private static final String NAME = Command.UPDATE_TABLE;
    private static final int COUNT_PARAMETERS = 2;



    public UpdateRecord(Storeable store, View view) {
        super(store, view);
    }

    @Override
    public void perform() {
        checkCountParameters(parametrs, COUNT_PARAMETERS);
        String tableName = parametrs[0];
        String where = parametrs[1];
        Table table = store.getTableData(tableName, where);
        view.writeln(table.toString());
        table.addRow(new Row(table.getCellInfos()));
        for (Cell cell : table.getRow(0).getCells()) {
            view.writeln("input new value or skip");
            view.writeln(cell.getCellInfo().toString());
            cell.setValue(view.read(), false);
        }
        store.updateRecord(where, table);
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
