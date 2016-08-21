package my.project.juja.model;

import java.sql.Connection;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;

/**
 * Created by Nikol on 5/2/2016.
 */
public interface Storeable {
    void getConnection(String dbName, String login, String password);

    Connection getConnection();

    void closeConnection();

    void clearTable(String tableName);

    void addRecord(String tableName, String columnNames, String columnValues);

    Set<String> getTableList();

    List<String> getColumnName(String tableName);

    List<String> getTableData(String tableName);

    List<String> getTableData(String tableName, String where);

    void updateRecord(String tableName, String where, List<String> cellValues );

    String getColumnType(String tableName, String columnName);

}
