package my.project.juja.controller.commands.database;

import my.project.juja.controller.commands.Command;
import my.project.juja.model.Storeable;
import my.project.juja.view.View;

/**
 * Created by Nikol on 9/21/2016.
 */
public class CurrentDataBaseName extends Command {
    public static final String NAME = Command.CURRENT_DATA_BASE_NAME;

    public CurrentDataBaseName(Storeable store, View view) {
        super(store, view);
    }

    @Override
    public void perform() {
        view.writeln("[" + store.getNameCurrentDataBase() + "]");
    }

    @Override
    public String getName() {
        return NAME;
    }
}
