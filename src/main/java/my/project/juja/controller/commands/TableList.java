package my.project.juja.controller.commands;

import my.project.juja.model.Storeable;
import my.project.juja.view.View;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

/**
 * Created by Nikol on 4/13/2016.
 */
public class TableList extends Command {
    public static final String name = Command.TABLE_LIST;

    public TableList(Storeable store, View view) {
        super(store, view);
    }

    @Override
    public void perform() {
        Set<String> tableList = store.getTableList();
        view.writeln(tableList.toString());
    }

    @Override
    public String getName() {
        return name;
    }

}
