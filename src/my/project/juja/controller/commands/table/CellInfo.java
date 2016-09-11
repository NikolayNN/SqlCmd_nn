package my.project.juja.controller.commands.table;

/**
 * Created by Nikol on 8/22/2016.
 */
public class CellInfo {
    private String columnName;
    private String type;
    private boolean canBeNull;

    public CellInfo(String columnName, String type, boolean isNullable, boolean hasDefaultValue) {
        this.columnName = columnName;
        this.type = type;
        this.canBeNull = isNullable || hasDefaultValue;
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
        String result = "";
        String mandatoryCell = "";
        if(!canBeNull){
            mandatoryCell ="*";
        }
        result = "[" + columnName + mandatoryCell + "(" + type + ")]";
        return result;
    }

}
