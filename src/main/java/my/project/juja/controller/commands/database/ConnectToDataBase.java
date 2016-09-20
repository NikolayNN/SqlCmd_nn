package my.project.juja.controller.commands.database;

import my.project.juja.controller.commands.Command;
import my.project.juja.model.Storeable;
import my.project.juja.view.View;

/**
 * Created by Nikol on 4/13/2016.
 */
public class ConnectToDataBase extends Command {
    private static final int EXPECTED_COUNT_PARAMETERS = 1;
    public static final String name = Command.CONNECTION;

    public ConnectToDataBase(Storeable store, View view) {
        super(store, view);
    }

    @Override
    public void perform() {
        checkCountParameters(parametrs, EXPECTED_COUNT_PARAMETERS);
        String dbName = parametrs[0];
        store.connectToDataBase(dbName);
        view.writeln("Connect to the data base '" + dbName + "' successful!");
    }

    @Override
    public String getName() {
        return name;
    }
}
