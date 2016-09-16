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

    public Row(List<CellInfo> cellsInfo){
        for (CellInfo cellInfo : cellsInfo) {
            cells.add(new Cell(cellInfo));
        }
    }

    public Row (List<CellInfo> cellsInfo, List<String> values){
        if(cellsInfo.size() != values.size()){
            throw new IndexOutOfBoundsException();
        }
        for (int i = 0; i < cellsInfo.size(); i++) {
            cells.add(new Cell(cellsInfo.get(i), values.get(i)));
        }
    }

    public List<Cell> getCells() {
        return cells;
    }

    public List<Cell> getCellsNotNull(){
        List<Cell> result = new ArrayList<>();
        for (Cell cell : cells) {
            if(!cell.getValue().equals("")){
                result.add(cell);
            }
        }
        return result;
    }

    public Cell getCell(int columnIndex){
        return cells.get(columnIndex);
    }

    public List<String> getCellValuesNotNull(){
        List <String> result = new ArrayList<>();
        for (Cell cell : cells) {
            if(!cell.getValue().equals("")){
                result.add(cell.getValue());
            }
        }
        return result;
    }

    public List<String> getColumnNamesNotNull(){
        List <String> result = new ArrayList<>();
        for (Cell cell : cells) {
            if(!cell.getValue().equals("")) {
                result.add(cell.getColumnName());
            }
        }
        return result;
    }

    public void addCell(CellInfo cellInfo, String value){
        cells.add(new Cell(cellInfo, value));
    }

    @Override
    public String toString() {
        String result ="";
        for (Cell cell : cells) {
            result += cell.getValue() + " | ";
        }
        return  result;
    }
}
