package my.project.juja.controller.commands.table;

import my.project.juja.controller.commands.Command;
import my.project.juja.model.Storeable;
import my.project.juja.model.table.CellInfo;
import my.project.juja.model.table.Table;
import my.project.juja.utils.WhereConstructor;
import my.project.juja.view.View;

import java.sql.SQLException;
import java.util.List;

/**
 * Created by Nikol on 9/23/2016.
 */
public class TableDataWhere extends Command {
    public static final String name = Command.TABLE_DATA_WHERE;
    private static final int EXPECTED_COUNT_PARAMETERS = 1;

    public TableDataWhere(Storeable store, View view) {
        super(store, view);
    }

    @Override
    public void perform() {
        isConnectedDataBase();
        checkCountParameters(parametrs, EXPECTED_COUNT_PARAMETERS);
        String tableName = parametrs[0];
        List<CellInfo> cellInfos = store.getColumnInformation(tableName);
        while (true) {
            try {
                WhereConstructor whereConstructor = new WhereConstructor(view, cellInfos);
                Table table = store.getTableData(tableName, whereConstructor.toString());
                view.writeln(table.toString());
                break;
            } catch (RuntimeException ex) {
                view.writeln(ex.getMessage());
            }
        }
    }

    @Override
    public String getName() {
        return null;
    }
}
