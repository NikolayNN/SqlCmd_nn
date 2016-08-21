package my.project.juja.model;
import my.project.juja.controller.commands.Command;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * Created by Nikol on 4/21/2016.
 */
public class Table {
    private Storeable store;
    private String tableName;
    private List<String> columnNames;
    private int columnQuantity;
    private List<String> records;
    private int[] columnsToEdit; //todo move this field to another class

    {
        records = new ArrayList<>();
    }

    public List<String> getColumnNames() {
        return columnNames;
    }
    public int getColumnQuantity() {
        return columnQuantity;
    }

    public Table(Storeable store, String tableName) {
        this.store = store;
        this.tableName = tableName;
        this.columnNames = store.getColumnName(tableName);
        this.columnQuantity = columnNames.size();
    }

    private int[] stringToArrayInt(String s){
        String[] temp = s.split(Command.SEPARATOR);
        int[] result = new int[temp.length];
        for (int i = 0; i < result.length; i++) {
            result[i] = Integer.parseInt(temp[i]);
        }
        return result;
    }

    public int setColumnsToEditIdx (String s){
        int[] idx = stringToArrayInt(s);
        if(idx.length == 0){
            return -1;
        }
        for (int i = 0; i < idx.length; i++) {
            if((idx[i] > columnQuantity) || (idx[i]<0)){
                return -1;
            }
        }

        Arrays.sort(idx);
        for (int i = 0; i < idx.length - 1; i++) {
            if(idx[i] == idx[i+1]){
                return -1;
            }
        }
        columnsToEdit = idx;
        return 1;
    }

    public void addTableLine(String line){
        records.add(line);
    }

    public void saveTable(){
        String columnsToEdit = "";
        for (int i = 0; i < this.columnsToEdit.length; i++) {
            columnsToEdit += columnNames.get(this.columnsToEdit[i]) + Command.SEPARATOR;
        }

        for (int i = 0; i < records.size(); i++) {
            store.addRecord(tableName, columnsToEdit, records.get(i));
        }

    }

    public void clearTable(){
        columnNames = null;
        columnsToEdit = null;
        columnQuantity = 0;
        records = null;
    }


}