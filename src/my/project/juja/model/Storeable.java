package my.project.juja.model;

import my.project.juja.controller.commands.table.Table;

import java.sql.Connection;
import java.sql.SQLException;
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

    void addRecord(Table table) throws SQLException;

    Set<String> getTableList();

    Table getTableData(String tableName);

    Table getTableData(String tableName, String where);

    void updateRecord(String where, Table table);

    List<CellInfo> getColumnInformation(String tableName);
}
