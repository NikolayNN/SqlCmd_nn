package my.project.juja.controller.commands.table;

import my.project.juja.controller.commands.Command;
import my.project.juja.model.Storeable;
import my.project.juja.model.table.Table;
import my.project.juja.utils.WhereConstructor;
import my.project.juja.view.View;

import java.sql.SQLException;

/**
 * Created by Nikol on 9/23/2016.
 */
public class DeleteRecord extends Command {
    private static final String NAME = Command.DELETE_RECORD;
    private static final int EXPECTED_COUNT_PARAMETERS = 1;

    public DeleteRecord(Storeable store, View view) {
        super(store, view);
    }

    @Override
    public void perform() throws SQLException {
        isConnectedDataBase();
        checkCountParameters(parametrs, EXPECTED_COUNT_PARAMETERS);
        String tableName = parametrs[0];
        while (true){
            WhereConstructor whereConstructor = new WhereConstructor(view, store.getColumnInformation(tableName));
            whereConstructor.create();
            Table table = store.getTableData(tableName, whereConstructor.toString());
            view.writeln(table.toString());
            confirmCommand("delete this records");
            store.deleteRecord(tableName, whereConstructor.toString());
            view.writeln("record deleted");
            break;
        }
    }

    @Override
    public String getName() {
        return NAME;
    }
}
