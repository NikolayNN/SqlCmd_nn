package my.project.juja.controller.commands.table;

import my.project.juja.controller.commands.Command;
import my.project.juja.model.table.Cell;
import my.project.juja.model.table.Row;
import my.project.juja.model.table.Table;
import my.project.juja.model.Storeable;
import my.project.juja.view.View;

/**
 * Created by Nikol on 8/20/2016.
 */
public class UpdateRecord extends Command {
    private static final String NAME = Command.UPDATE_TABLE;
    private static final int COUNT_PARAMETERS = 1;

    public UpdateRecord(Storeable store, View view) {
        super(store, view);
    }

    @Override
    public void perform() {
        isConnectedDataBase();
        checkCountParameters(parametrs, COUNT_PARAMETERS);
        String tableName = parametrs[0];
        checkTableName(tableName);
        String where = createWhere(view, store.getColumnInformation(tableName));
        Table table = getTableToUpdate(tableName, where);
        view.writeln(table.toString());
        table.addRow(new Row(table.getTableHeader()));
        while (true) {
            try {
                inputValuesToUpdate(table);
                store.updateRecord(where, table);
                view.writeln("table updated");
                break;
            } catch (RuntimeException ex) {
                System.out.println(ex.getMessage());
            }
        }
    }

    private void inputValuesToUpdate(Table table) {
        for (Cell cell : table.getRow(0).getCells()) {
            view.writeln("input new value or just press 'enter' to skip");
            view.writeln(cell.getCellInfo().toString());
            cell.setValue(view.read(), false);
        }
    }

    private Table getTableToUpdate(String tableName, String where) {
        Table table;
        try {
            table = store.getTableData(tableName, where);
            if (table.getRows().size() == 0) {
                throw new RuntimeException("ERROR. there is not record for input condition " + where);
            }
        } catch (RuntimeException ex) {
            throw new RuntimeException("ERROR. you input not exist table or wrong where. " + ex.getMessage());
        }
        return table;
    }

    @Override
    public String getName() {
        return NAME;
    }
}
