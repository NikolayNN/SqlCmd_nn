package my.project.juja.controller.commands;

import my.project.juja.model.Storeable;
import my.project.juja.view.Console;
import my.project.juja.view.View;

/**
 * Created by Nikol on 4/13/2016.
 */
public class Connection extends Command {
    private static final int EXPECTED_COUNT_PARAMETERS = 3;
    public static final String name = Command.CONNECTION;
    public Connection(Storeable store, View view) {
        super(store, view);
    }

    @Override
    public void perform() {
        checkCountParameters(parametrs, EXPECTED_COUNT_PARAMETERS);
        String dbName = parametrs[0];
        String login = parametrs[1];
        String password = parametrs[2];
        store.getConnection(dbName, login, password);
        view.writeln("Connect to " + dbName + " successful!");
    }

    @Override
    public String getName() {
        return name;
    }
}
