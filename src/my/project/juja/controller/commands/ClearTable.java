package my.project.juja.controller.commands;

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
        checkCountParameters(parametrs, countParameters);
        String tableName = parametrs[0];
        store.clearTable(tableName);
        view.writeln(tableName + " has been cleared" );
    }

    @Override
    public String getName() {
        return name;
    }
}
