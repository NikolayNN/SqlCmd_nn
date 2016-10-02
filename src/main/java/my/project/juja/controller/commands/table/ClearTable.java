package my.project.juja.controller.commands.table;

import my.project.juja.controller.commands.Command;
import my.project.juja.model.Storeable;
import my.project.juja.view.View;

/**
 * Created by Nikol on 4/30/2016.
 */
public class ClearTable extends Command {
    private static final int countParameters = 1;
    public static final String name = Command.CLEAR_TABLE;

    public ClearTable(Storeable store, View view) {
        super(store, view);
    }

    @Override
    public void perform() {
        isConnectedDataBase();
        checkCountParameters(commandParametrs, countParameters);
        String tableName = commandParametrs[0];
        checkTableName(tableName);
        confirmCommand("Are you sure clear table '" + tableName + "'? (Y/N)");
        store.clearTable(tableName);
        view.writeln(tableName + " has been cleared");
    }

    @Override
    public String getName() {
        return name;
    }
}
