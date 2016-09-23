package my.project.juja.controller.commands.table;

import my.project.juja.controller.commands.Command;
import my.project.juja.model.Storeable;
import my.project.juja.utils.WhereConstructor;
import my.project.juja.view.View;

/**
 * Created by Nikol on 9/23/2016.
 */
public class DeleteRecord extends Command {
    private static final String NAME = Command.DELETE_RECORD;
    private static final int EXPECTED_COUNT_PARAMETERS = 1;

    public DeleteRecord(Storeable store, View view) {
        super(store, view);
    }

    @Override
    public void perform() {
        isConnectedDataBase();
        checkCountParameters(parametrs, EXPECTED_COUNT_PARAMETERS);
        String tableName = parametrs[0];
        while (true){
            WhereConstructor whereConstructor = new WhereConstructor(view, store.getColumnInformation(tableName));
            whereConstructor.create();
            store.deleteRecord(tableName, whereConstructor.getWhere());
            view.writeln("record deleted");
            break;
        }
    }

    @Override
    public String getName() {
        return NAME;
    }
}