package my.project.juja.controller.commands.table;

import my.project.juja.controller.commands.Command;
import my.project.juja.model.table.Row;
import my.project.juja.model.table.Table;
import my.project.juja.model.Storeable;
import my.project.juja.view.View;

import java.sql.SQLException;

/**
 * Created by Nikol on 8/22/2016.
 */
public class AddRecord extends Command {
    private static final String NAME = Command.ADD_RECORD;
    private static final int EXPECTED_COUNT_PARAMETERS = 1;

    public AddRecord(Storeable store, View view) {
        super(store, view);
    }

    @Override
    public void perform() {
        isConnectedDataBase();
        checkCountParameters(parametrs, EXPECTED_COUNT_PARAMETERS);
        String tableName = parametrs[0];
        Table table = new Table(tableName, store.getColumnInformation(tableName));
        Row row = new Row(table.getCellInfos());
        while (true) {
            int i = 0;
            while (i < table.getColumnCount()) {
                try {
                    view.writeln("input value for the column " + table.getCellInfos(i).toString() + " or just press 'enter' to skip input");
                    row.getCell(i).setValue(view.read(), true);
                    i++;
                } catch (IllegalArgumentException ex) {
                    view.writeln(ex.getMessage());
                }
            }
            try {
                table.addRow(row);
                store.addRecord(table);
                break;
            } catch (SQLException ex) {
                view.writeln(ex.getMessage());
            }
        }
        view.writeln("successful added");
        view.writeln(table.toString());
    }

    @Override
    public String getName() {
        return NAME;
    }
}
