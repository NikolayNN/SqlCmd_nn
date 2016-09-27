package my.project.juja.model.table;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Nikol on 8/22/2016.
 */
public class Row {
    private List<Cell> cells;

    {
        cells = new ArrayList<>();
    }

    public Row(List<CellInfo> cellsInfo) {
        for (CellInfo cellInfo : cellsInfo) {
            cells.add(new Cell(cellInfo));
        }
    }

    public List<Cell> getCells() {
        return cells;
    }

    public List<Cell> getCellsNotNull() {
        List<Cell> result = new ArrayList<>();
        for (Cell cell : cells) {
            if (!cell.getValue().equals("")) {
                result.add(cell);
            }
        }
        return result;
    }

    public Cell getCell(int columnIndex) {
        return cells.get(columnIndex);
    }

    public List<String> getCellValuesNotNull() {
        List<String> result = new ArrayList<>();
        for (Cell cell : cells) {
            if (!cell.getValue().equals("")) {
                result.add(cell.getValue());
            }
        }
        return result;
    }

    public List<String> getColumnNamesNotNull() {
        List<String> result = new ArrayList<>();
        for (Cell cell : cells) {
            if (!cell.getValue().equals("")) {
                result.add(cell.getColumnName());
            }
        }
        return result;
    }

    @Override
    public String toString() {
        String result = "";
        for (Cell cell : cells) {
            result += cell.getValue() + " | ";
        }
        return result;
    }
}
