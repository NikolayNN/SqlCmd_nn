package my.project.juja.controller.commands.table;

import my.project.juja.controller.commands.Command;
import my.project.juja.model.Storeable;
import my.project.juja.view.View;

/**
 * Created by Nikol on 9/22/2016.
 */
public class DropTable extends Command {
    private static final int EXPECTED_COUNT_PARAMETERS = 1;
    public static final String NAME = Command.DROP_TABLE;

    public DropTable(Storeable store, View view) {
        super(store, view);
    }

    @Override
    public void perform() {
        checkCountParameters(parametrs, EXPECTED_COUNT_PARAMETERS);
        String tableName = parametrs[0];
        if (confirmCommand("delete table '" + tableName + "'")) {
            store.dropTable(tableName);
        }
        view.writeln("table '" + tableName + "'" + " deleted");
    }

    @Override
    public String getName() {
        return NAME;
    }
}
