package my.project.juja.controller.commands;

import my.project.juja.model.table.Cell;
import my.project.juja.model.table.Row;
import my.project.juja.model.table.Table;
import my.project.juja.model.Storeable;
import my.project.juja.view.View;

import java.sql.SQLException;

/**
 * Created by Nikol on 8/20/2016.
 */
public class UpdateRecord extends Command {

    private static final String NAME = Command.UPDATE_TABLE;
    private static final int COUNT_PARAMETERS = 2;



    public UpdateRecord(Storeable store, View view) {
        super(store, view);
    }

    @Override
    public void perform() {
        checkCountParameters(parametrs, COUNT_PARAMETERS);
        String tableName = parametrs[0];
        String where = parametrs[1];
        Table table;
        try{
            table = store.getTableData(tableName, where);
            if(table.getRows().size() == 0){
                throw new RuntimeException("ERROR. there is not record for input condition " + where);
            }
        }catch (SQLException ex){
            throw new RuntimeException("ERROR. you input not exist table or wrong where. " + ex.getMessage());
        }

        view.writeln(table.toString());
        table.addRow(new Row(table.getCellInfos()));
        while (true) {
            try {
                for (Cell cell : table.getRow(0).getCells()) {
                    view.writeln("input new value or skip");
                    view.writeln(cell.getCellInfo().toString());
                    cell.setValue(view.read(), false);
                }
                store.updateRecord(where, table);
                view.writeln("table updated");
                break;
            }catch (RuntimeException ex){
                System.out.println(ex.getMessage());
            }
        }
    }


    @Override
    public String getName() {
        return NAME;
    }
}
