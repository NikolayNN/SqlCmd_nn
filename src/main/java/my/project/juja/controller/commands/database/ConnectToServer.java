package my.project.juja.controller.commands.database;

import my.project.juja.controller.commands.Command;
import my.project.juja.model.Storeable;
import my.project.juja.view.View;

/**
 * Created by Nikol on 9/20/2016.
 */
public class ConnectToServer extends Command {
    private static final int EXPECTED_COUNT_PARAMETERS = 3;
    public static final String name = Command.CONNECTION_TO_SERVER;

    public ConnectToServer(Storeable store, View view) {
        super(store, view);
    }

    @Override
    public void perform() {
        checkCountParameters(parametrs,EXPECTED_COUNT_PARAMETERS);
        String serverUrl = parametrs[0];
        String login = parametrs[1];
        String password = parametrs[2];
        store.connectToServer(serverUrl, login,password);
        view.writeln("Connect to the server successful!");
    }

    @Override
    public String getName() {
        return name;
    }
}
