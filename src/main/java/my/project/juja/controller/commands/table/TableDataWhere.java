package my.project.juja.controller.commands.table;

import my.project.juja.controller.commands.Command;
import my.project.juja.model.Storeable;
import my.project.juja.model.table.Table;
import my.project.juja.view.View;

/**
 * Created by Nikol on 9/23/2016.
 */
public class TableDataWhere extends Command {
    public static final String NAME = Command.TABLE_DATA_WHERE;
    private static final int EXPECTED_COUNT_PARAMETERS = 1;

    public TableDataWhere(Storeable store, View view) {
        super(store, view);
    }

    @Override
    public void perform() {
        isConnectedDataBase();
        checkCountParameters(parametrs, EXPECTED_COUNT_PARAMETERS);
        String tableName = parametrs[0];
        checkTableName(tableName);
        while (true) {
            try {
                String where = createWhere(view, store.getColumnInformation(tableName));
                Table table = store.getTableData(tableName, where);
                view.writeln(table.toString());
                break;
            } catch (RuntimeException ex) {
                view.writeln(ex.getMessage());
            }
        }
    }

    @Override
    public String getName() {
        return NAME;
    }
}
