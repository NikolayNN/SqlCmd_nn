package my.project.juja.controller.commands.database;

import my.project.juja.controller.commands.Command;
import my.project.juja.model.Storeable;
import my.project.juja.view.View;

/**
 * Created by Nikol on 9/21/2016.
 */
public class DropDataBase extends Command {
    private static final int EXPECTED_COUNT_PARAMETERS = 1;
    public static final String NAME = Command.DROP_DATA_BASE;

    public DropDataBase(Storeable store, View view) {
        super(store, view);
    }

    @Override
    public void perform() {
        checkCountParameters(commandParametrs, EXPECTED_COUNT_PARAMETERS);
        String dataBaseName = commandParametrs[0];
        while (true) {
            if (confirmCommand("Are you sure delete " + dataBaseName + "? (Y/N)")) {
                store.dropDataBase(dataBaseName);
                break;
            }
        }
        view.writeln("data base '" + dataBaseName + "'" + " deleted");
    }

    @Override
    public String getName() {
        return NAME;
    }
}
