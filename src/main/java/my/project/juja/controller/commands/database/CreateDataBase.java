package my.project.juja.controller.commands.database;

import my.project.juja.controller.commands.Command;
import my.project.juja.model.Storeable;
import my.project.juja.view.View;

/**
 * Created by Nikol on 9/21/2016.
 */
public class CreateDataBase extends Command {
    private static final int EXPECTED_COUNT_PARAMETERS = 1;
    public static final String NAME = Command.CREATE_DATA_BASE;

    public CreateDataBase(Storeable store, View view) {
        super(store, view);
    }

    @Override
    public void perform() {
        checkCountParameters(commandParametrs, EXPECTED_COUNT_PARAMETERS);
        String dataBaseName = commandParametrs[0];
        store.createDataBase(dataBaseName);
        view.writeln("data base '" + dataBaseName + "' created.");
    }

    @Override
    public String getName() {
        return NAME;
    }
}
