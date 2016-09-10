package my.project.juja.controller.commands;

import my.project.juja.controller.commands.table.Cell;
import my.project.juja.controller.commands.table.Row;
import my.project.juja.controller.commands.table.Table;
import my.project.juja.model.Storeable;
import my.project.juja.view.View;

import java.sql.SQLException;

/**
 * Created by Nikol on 8/22/2016.
 */
public class AddRecordNew extends Command {
    private static final String NAME = Command.ADD_RECORD_NEW;
    private static final int EXPECTED_COUNT_PARAMETERS = 1;

    public AddRecordNew(Storeable store, View view) {
        super(store, view);
    }

    @Override
    public void perform() {
        checkCountParameters(parametrs, EXPECTED_COUNT_PARAMETERS);
        String tableName = parametrs[0];
        Table table = new Table(tableName, store.getColumnInformation(tableName));
        Row row = new Row(store.getColumnInformation(tableName));

        while(true) {
            int i = 0;
            while (i < table.getColumnCount()) {
                try {
                    view.writeln("input value for " + table.getCellInfos(i).toString());
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
