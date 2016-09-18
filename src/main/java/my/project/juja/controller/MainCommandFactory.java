package my.project.juja.controller;

import my.project.juja.controller.commands.*;
import my.project.juja.model.Storeable;
import my.project.juja.view.View;

/**
 * Created by Nikol on 4/13/2016.
 */
public class MainCommandFactory implements CommandFactory {
    Storeable store;
    View view;
    public Command[] supportedCommands;

    public MainCommandFactory(Storeable store, View view) {
        this.store = store;
        this.view = view;
        supportedCommands = new Command[]{
                new Connection(store, view),
                new Exit(store, view),
                new TableList(store, view),
                new TableData(store, view),
                new ClearTable(store, view),
                new UpdateRecord(store, view),
                new Help(view),
                new AddRecord(store,view)
        };

    }

    @Override
    public Command createCommand(String source) {
        String command = source.split(Command.SEPARATOR)[0];
        for (int i = 0; i < supportedCommands.length; i++) {
            if (command.equalsIgnoreCase(supportedCommands[i].getName())) {
                supportedCommands[i].setup(source);
                return supportedCommands[i];
            }
        }
        return new Wrong(view);
    }
}
