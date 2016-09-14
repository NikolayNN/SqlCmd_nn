package my.project.juja.model.table;

/**
 * Created by Nikol on 8/22/2016.
 */
public class Cell {
    CellInfo cellInfo;
    String value;

    public CellInfo getCellInfo() {
        return cellInfo;
    }

    public String getValue() {
        return value;
    }

    public String getColumnName(){
        return cellInfo.getColumnName();
    }

    public Cell(CellInfo cellInfo) {
        this.cellInfo = cellInfo;
    }

    public Cell(CellInfo cellInfo, String value) {
        this.cellInfo = cellInfo;
        this.value = value;
    }

    public void setValue(String value, boolean check) {
        if(!check){
            this.value = value;
            return;
        }
        if(value.equals("") && !cellInfo.isCanBeNull()){
            throw new IllegalArgumentException("column \"" + cellInfo.getColumnName() + "\" can't be null");
        }
         this.value = value;

    }




}
