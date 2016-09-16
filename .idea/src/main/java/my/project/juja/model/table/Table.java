package my.project.juja.model.table;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Nikol on 9/7/2016.
 */
public class Table {
    private String tableName;
    private List<CellInfo> cellInfos;
    private List<Row> rows;
    private int columnCount;

    public Table(String tableName, List<CellInfo> cellInfos) {
        this.tableName = tableName;
        this.cellInfos = cellInfos;
        this.columnCount = cellInfos.size();
        rows = new ArrayList<>();
    }

    public void addRow(Row row) {
        rows.add(row);
    }

    public int getColumnCount() {
        return columnCount;
    }

    public List<CellInfo> getCellInfos() {
        return cellInfos;
    }

    public CellInfo getCellInfos(int columnIndex) {
        return cellInfos.get(columnIndex);
    }

    public List<Row> getRows() {
        return rows;
    }

    public Row getRow(int rowIndex) {
        return rows.get(rowIndex);
    }

    public String getTableName() {
        return tableName;
    }

    @Override
    public String toString() {
        String result = "";
        String columnNames = "";

        for (CellInfo cellInfo : cellInfos) {
            columnNames += cellInfo.getColumnName() + " | ";
        }
        String values = "";
        for (int i = 0; i < rows.size(); i++) {
            values += rows.get(i) + "\n";
        }
        result += tableName + "\n" +
                "----------------------------------------------" + "\n" +
                columnNames + "\n" +
                "----------------------------------------------" + "\n" +
                values;
        return result;
    }

    private int getMaxLength(int columnIndex) {
        int max = getCellInfos(columnIndex).getColumnName().length();
        for (Row row : rows) {
            int currentLength = row.getCell(columnIndex).value.length();
            if (currentLength > max) {
                max = currentLength;
            }
        }
        return max;
    }
}
