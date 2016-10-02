package my.project.juja.controller.commands.database;

import my.project.juja.controller.commands.Command;
import my.project.juja.model.Storeable;
import my.project.juja.view.View;

/**
 * Created by Nikol on 9/20/2016.
 */
public class DataBasesNames extends Command {
    public static final String name = Command.DATA_BASES_NAMES;
    private static final int EXPECTED_COUNT_PARAMETERS = 0;

    public DataBasesNames(Storeable store, View view) {
        super(store, view);
    }

    @Override
    public void perform() {
        checkCountParameters(parametrs, EXPECTED_COUNT_PARAMETERS);
        view.writeln(store.getDataBasesNames().toString());
    }

    @Override
    public String getName() {
        return name;
    }
}
