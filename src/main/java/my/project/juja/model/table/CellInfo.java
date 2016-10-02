package my.project.juja.model.table;

/**
 * Created by Nikol on 9/10/2016.
 */
public class CellInfo {

    private String columnName;
    private String type;
    private boolean isNullable;
    private boolean hasDefault;
    private boolean canBeNull;
    private int length;
    int index;

    public CellInfo(String columnName, String type, boolean isNullable, boolean hasDefaultValue, int index) {
        this.columnName = columnName;
        this.type = type;
        this.isNullable = isNullable;
        this.hasDefault = hasDefaultValue;
        this.canBeNull = isNullable || hasDefaultValue;
        this.index = index;
    }

    public String getColumnName() {
        return columnName;
    }

    public String getType() {
        return type;
    }

    public boolean isHasDefault() {
        return hasDefault;
    }

    public boolean isCanBeNull() {
        return canBeNull;
    }

    public int getLength() {
        return length;
    }

    public void setLength(int length) {
        this.length = length;
    }

    public String getCellInfoSQL() {
        StringBuilder result = new StringBuilder();
        result.append(columnName);
        result.append(" ");
        result.append(type);
        if (length != 0) {
            result.append("(" + length + ")");
        }
        result.append(" ");
        if (canBeNull == false) {
            result.append("NOT NULL");
        }
        return result.toString();
    }

    @Override
    public String toString() {
        String result;
        String mandatoryCell = "";
        if (!canBeNull) {
            mandatoryCell = "*";
        }
        result = "[" + columnName + mandatoryCell + "(" + type + ")]";
        return result;
    }


}
