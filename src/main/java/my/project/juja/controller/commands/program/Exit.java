package my.project.juja.controller.commands.program;

import my.project.juja.controller.commands.Command;
import my.project.juja.model.Storeable;
import my.project.juja.view.View;

/**
 * Created by Nikol on 4/13/2016.
 */
public class Exit extends Command {
    public static final String name = Command.EXIT;

    public Exit(Storeable store, View view) {
        super(store, view);
    }

    @Override
    public void perform() {
        if (store.getConnectToDataBase() == null) {
            view.writeln("Goodbye");
            return;
        }
        store.closeAllConnections();
        view.writeln("Connection to data base was closed");
        view.writeln("Goodbye");
    }

    @Override
    public String getName() {
        return name;
    }
}
