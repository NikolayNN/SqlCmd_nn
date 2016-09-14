package my.project.juja.model.table;

/**
 * Created by Nikol on 9/10/2016.
 */
public class CellInfo {

    private String columnName;
    private String type;
    private boolean canBeNull;
    int index;

    public CellInfo(String columnName, String type, boolean isNullable, boolean hasDefaultValue, int index) {
        this.columnName = columnName;
        this.type = type;
        this.canBeNull = isNullable || hasDefaultValue;
        this.index = index;
    }

    public String getColumnName() {
        return columnName;
    }

    public String getType() {
        return type;
    }

    public boolean isCanBeNull() {
        return canBeNull;
    }

    @Override
    public String toString() {
        String result;
        String mandatoryCell = "";
        if(!canBeNull){
            mandatoryCell ="*";
        }
        result = "[" + columnName + mandatoryCell + "(" + type + ")]";
        return result;
    }


}
