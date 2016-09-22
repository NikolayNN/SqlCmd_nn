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
        checkCountParameters(parametrs, EXPECTED_COUNT_PARAMETERS);
        String dataBaseName = parametrs[0];
        while (true) {
            try {
                if (confirmCommand("delete " + dataBaseName)){
                    store.dropDataBase(dataBaseName);
                    break;
                }
            }catch (IllegalArgumentException ex){
                view.writeln();
            }
        }
        view.writeln("data base '" + dataBaseName + "'" + " deleted");
    }

    @Override
    public String getName() {
        return NAME;
    }
}
