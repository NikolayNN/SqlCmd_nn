package my.project.juja.model;

import my.project.juja.model.table.CellInfo;
import my.project.juja.model.table.Table;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.List;
import java.util.Set;

/**
 * Created by Nikol on 5/2/2016.
 */
public interface Storeable {


    void connectToServer(String dbUrl, String login, String password);

    Connection getConnectToServer();

    void connectToDataBase(String dbName);

    Connection getConnectToDataBase();

    String disconectDataBase();

    void closeAllConnections();

    void clearTable(String tableName);

    void addRecord(Table table);

    Set<String> getTableList();

    Table getTableData(String tableName);

    Table getTableData(String tableName, String where);

    void updateRecord(String where, Table table);

    List<CellInfo> getColumnInformation(String tableName);

    Set<String> getDataBasesNames();

    String getNameCurrentDataBase();

    void dropDataBase(String dataBaseName);

    void createDataBase(String dataBaseName);

    void createTable(String tableName, List<CellInfo> cellInfos);

    void dropTable(String tableName);

    void deleteRecord(String tableName, String where);
}
