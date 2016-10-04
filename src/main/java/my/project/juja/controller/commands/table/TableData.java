package my.project.juja.controller.commands.table;

import my.project.juja.controller.commands.Command;
import my.project.juja.model.table.Table;
import my.project.juja.model.Storeable;
import my.project.juja.view.View;

/**
 * Created by Nikol on 4/13/2016.
 */
public class TableData extends Command {
    public static final String name = Command.TABLE_DATA;
    private static final int COUNT_PARAMETERS = 1;

    public TableData(Storeable store, View view) {
        super(store, view);
    }

    @Override
    public void perform() {
        checkConnection();
        checkCountParameters(parametrs, COUNT_PARAMETERS);
        String tableName = parametrs[0];
        Table table = store.getTableData(tableName);
        view.writeln(table.toString());
    }

    @Override
    public String getName() {
        return name;
    }
}
