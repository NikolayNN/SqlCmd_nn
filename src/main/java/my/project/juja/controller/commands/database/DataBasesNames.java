package my.project.juja.controller.commands.database;

import my.project.juja.controller.commands.Command;
import my.project.juja.model.Storeable;
import my.project.juja.view.View;

/**
 * Created by Nikol on 9/20/2016.
 */
public class DataBasesNames extends Command {
    public static final String name = Command.DATA_BASES_NAMES;

    public DataBasesNames(Storeable store, View view) {
        super(store, view);
    }

    @Override
    public void perform() {
        view.writeln(store.getDataBasesNames().toString());
    }

    @Override
    public String getName() {
        return name;
    }
}
