package my.project.juja.controller.commands.database;

import my.project.juja.controller.commands.Command;
import my.project.juja.model.Storeable;
import my.project.juja.view.View;

/**
 * Created by Nikol on 9/20/2016.
 */
public class DisconnectDataBase extends Command {
    public static final String NAME = Command.DISCONECT_DATA_BASE;
    private static final int EXPECTED_COUNT_PARAMETERS = 0;

    public DisconnectDataBase(Storeable store, View view) {
        super(store, view);
    }

    @Override
    public void perform() {
        checkCountParameters(commandParametrs, EXPECTED_COUNT_PARAMETERS);
        String dbName = store.disconectDataBase();
        view.writeln("Data base '" + dbName + "' disconnected.");
    }

    @Override
    public String getName() {
        return NAME;
    }
}
