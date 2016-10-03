package my.project.juja.model;

import my.project.juja.model.table.Cell;
import my.project.juja.model.table.CellInfo;
import my.project.juja.model.table.Row;
import my.project.juja.model.table.Table;
import my.project.juja.utils.JujaUtils;

import java.sql.*;
import java.util.*;

/**
 * Created by Nikol on 4/12/2016.
 */
public class DataBase implements Storeable {
    private static final String ERROR_WRONG_TABLENAME = "ERROR. check table name";
    private static final String ERROR_WRONG_COMMAND = "ERROR. check inputed commands";
    private static final String ERROR_JDBCDRIVER_NOT_FOUND = "ERROR. add jdbc driver to project";
    private static final String ERROR_CONNECT_UNSUCCESSFUL = "ERROR. connect to database unsuccessful, check your command.";
    private static final String ERROR_CONNECTION_NOT_EXIST = "ERROR. connect to database";
    private static final String ERROR_CONNECTION_TO_SERVER_NOT_EXIST = "ERROR. At first connect to a server.";
    private Connection connectionDataBase;
    private Connection connectionServer;
    private String serverUrl;
    private String login;
    private String password;
    private String dataBaseName;

    @Override
    public void connectToServer(String serverUrl, String login, String password) {
        try {
            try {
                Class.forName("org.postgresql.Driver");
            } catch (ClassNotFoundException e) {
                throw new RuntimeException(ERROR_JDBCDRIVER_NOT_FOUND);
            }
            connectionServer = DriverManager.getConnection("jdbc:postgresql://" + serverUrl + "/", login, password);
        } catch (SQLException ex) {
            throw new RuntimeException(ERROR_CONNECT_UNSUCCESSFUL + " " + ex.getMessage());
        }
        this.serverUrl = serverUrl;
        this.login = login;
        this.password = password;
    }

    @Override
    public void connectToDataBase(String dbName) {
        try {
            Class.forName("org.postgresql.Driver");
        } catch (ClassNotFoundException e) {
            throw new RuntimeException(ERROR_JDBCDRIVER_NOT_FOUND);
        }
        try {
            connectionDataBase = DriverManager.getConnection(
                    "jdbc:postgresql://" + serverUrl + "/" + dbName, login,
                    password);
        } catch (SQLException ex) {
            throw new RuntimeException(ERROR_CONNECT_UNSUCCESSFUL + " " + ex.getMessage());
        }
        this.dataBaseName = dbName;
    }

    @Override
    public Connection getConnectToDataBase() {
        return connectionDataBase;
    }

    @Override
    public String disconectDataBase() {
        checkConnectionToServer();
        try {
            checkConnectionToDataBase();
        } catch (RuntimeException ex) {
            return "";
        }
        String dbName = dataBaseName;
        try {
            connectionDataBase.close();
            connectionDataBase = null;
            dataBaseName = null;

        } catch (SQLException e) {
            e.printStackTrace();
        }
        return dbName;
    }

    @Override
    public void closeAllConnections() {
        try {
            connectionDataBase.close();
            connectionDataBase = null;
            connectionServer.close();
            connectionServer = null;
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    private void checkConnectionToDataBase() throws RuntimeException {
        if (connectionDataBase == null) {
            throw new RuntimeException(ERROR_CONNECTION_NOT_EXIST);
        }
    }

    public void checkConnectionToServer() throws RuntimeException {
        if (connectionServer == null) {
            throw new RuntimeException(ERROR_CONNECTION_TO_SERVER_NOT_EXIST);
        }
    }


    @Override
    public void clearTable(String tableName) {
        checkConnectionToServer();
        checkConnectionToDataBase();
        try (Statement stmt = connectionDataBase.createStatement()) {
            String query = "DELETE FROM " + tableName;
            stmt.executeUpdate(query);
        } catch (SQLException ex) {
            throw new RuntimeException(ERROR_WRONG_TABLENAME);
        }
    }

    @Override
    public void addRecord(Table table) throws SQLException {
        checkConnectionToServer();
        checkConnectionToDataBase();
        for (Row row : table.getRows()) {
            String columnNames = format(row.getColumnNamesNotNull(), "");
            String columnValues = format(row.getCellValuesNotNull(), "'");
            Statement stmt = connectionDataBase.createStatement();
            String query = "INSERT INTO " + table.getTableName() + "(" + columnNames + ")" +
                    " VALUES (" + columnValues + ")";
            stmt.executeUpdate(query);
            stmt.close();
        }

    }


    private String format(List<String> strings, String quoteType) {
        String result = "";
        for (int i = 0; i < strings.size(); i++) {
            result += quoteType + strings.get(i) + quoteType;
            if (!(i == strings.size() - 1)) {
                result += ",";
            } else {
                break;
            }
        }
        return result;
    }

    @Override
    public Set<String> getTableList() {
        checkConnectionToServer();
        checkConnectionToDataBase();
        Set<String> result = new LinkedHashSet<>();
        String query = "SELECT table_name" +
                " FROM information_schema.tables" +
                " WHERE table_schema='public'" +
                " AND table_type='BASE TABLE';";
        try (Statement stmt = connectionDataBase.createStatement(); ResultSet rs = stmt.executeQuery(query)) {
            Set<String> tables = new HashSet<>();
            while (rs.next()) {
                result.add(rs.getString("table_name"));
            }
            rs.close();
            stmt.close();
        } catch (SQLException ex) {
            throw new RuntimeException(ERROR_WRONG_COMMAND);
        }
        return result;
    }

    @Override
    public Table getTableData(String tableName) {
        checkConnectionToServer();
        checkConnectionToDataBase();
        Table table = new Table(tableName, getColumnInformation(tableName));
        String query = "SELECT * FROM " + tableName;
        try (Statement stmt = connectionDataBase.createStatement();
             ResultSet rs = stmt.executeQuery(query)) {
            ResultSetMetaData rsmd = rs.getMetaData();
            while (rs.next()) {
                Row row = new Row(table.getTableHeader());
                for (int i = 1; i <= rsmd.getColumnCount(); i++) {
                    if (rs.getString(i) != null) {
                        row.getCell(i - 1).setValue(rs.getString(i).trim(), false);
                    } else {
                        row.getCell(i - 1).setValue("", false);
                    }
                }
                table.addRow(row);
            }
        } catch (SQLException ex) {
            throw new RuntimeException(ERROR_WRONG_TABLENAME);
        }
        return table;
    }

    @Override
    public Table getTableData(String tableName, String where) {
        checkConnectionToServer();
        checkConnectionToDataBase();
        Table table = new Table(tableName, getColumnInformation(tableName));
        try {
            String query = "SELECT * FROM " + tableName + " WHERE " + where;
            Statement stmt = connectionDataBase.createStatement();
            ResultSet rs = stmt.executeQuery(query);
            ResultSetMetaData rsmd = rs.getMetaData();
            while (rs.next()) {
                Row row = new Row(table.getTableHeader());
                for (int i = 1; i <= rsmd.getColumnCount(); i++) {
                    if (rs.getString(i) != null) {
                        row.getCell(i - 1).setValue(rs.getString(i).trim(), false);
                    } else {
                        row.getCell(i - 1).setValue("", false);
                    }
                }
                table.addRow(row);
            }
            stmt.close();
            rs.close();
        } catch (SQLException ex) {
            throw new RuntimeException(ex.getMessage());
        }
        return table;
    }

    @Override
    public void updateRecord(String where, Table table) {
        checkConnectionToServer();
        checkConnectionToDataBase();
        String set = "";
        for (int i = 0; i < table.getRow(0).getCellsNotNull().size(); i++) {
            Cell cell = table.getRow(0).getCellsNotNull().get(i);
            if (i == table.getRow(0).getCellsNotNull().size() - 1) {
                set += cell.getColumnName() + "=" + "'" + cell.getValue() + "' ";
                continue;
            }
            set += cell.getColumnName() + "=" + "'" + cell.getValue() + "', ";
        }

        String tableName = table.getTableName();
        try (Statement stmt = connectionDataBase.createStatement()) {
            String query = "UPDATE " + tableName + " SET " + set + " WHERE " + where;
            stmt.executeUpdate(query);
            stmt.close();
        } catch (SQLException ex) {
            throw new RuntimeException(ERROR_WRONG_COMMAND + " " + ex.getMessage());
        }
    }

    @Override
    public List<CellInfo> getColumnInformation(String tableName) {
        checkConnectionToServer();
        checkConnectionToDataBase();
        List<CellInfo> cellInfos = new ArrayList<>();
        String query = "SELECT column_name, data_type, is_nullable, column_default from information_schema.columns where table_name = '" + tableName + "'";
        try (Statement stmt = connectionDataBase.createStatement();
             ResultSet rs = stmt.executeQuery(query)) {
            int index = 0;
            while (rs.next()) {
                String columnName = rs.getString(1);
                String dataType = rs.getString(2);
                Boolean isNullable = JujaUtils.setBoolean(rs.getString(3), "YES");
                Boolean columnDefault = !JujaUtils.setBoolean(rs.getString(4), null);
                CellInfo cellInfo = new CellInfo(columnName, dataType, isNullable, columnDefault, index);
                cellInfos.add(cellInfo);
                index++;
            }
        } catch (SQLException ex) {
            throw new RuntimeException(ERROR_WRONG_TABLENAME);
        }
        return cellInfos;
    }

    @Override
    public Set<String> getDataBasesNames() {
        checkConnectionToServer();
        Set<String> result = new LinkedHashSet<>();
        String query = "SELECT datname FROM pg_database WHERE datistemplate = false;";
        try (Statement stmt = connectionServer.createStatement();
             ResultSet rs = stmt.executeQuery(query)) {
            while (rs.next()) {
                result.add(rs.getString(1));
            }
        } catch (SQLException ex) {
            throw new RuntimeException(ex.getMessage());
        }
        return result;
    }

    @Override
    public String getNameCurrentDataBase() {
        checkConnectionToServer();
        checkConnectionToDataBase();
        return dataBaseName;
    }

    @Override
    public void dropDataBase(String dataBaseName) {
        checkConnectionToServer();
        if (this.dataBaseName != null && this.dataBaseName.equalsIgnoreCase(dataBaseName)) {
            throw new RuntimeException("At first you need to disconnect " + dataBaseName);
        }
        String query = "DROP DATABASE " + dataBaseName + ";";
        try (Statement stmt = connectionServer.createStatement()) {
            stmt.executeUpdate(query);
        } catch (SQLException ex) {
            throw new RuntimeException(ex.getMessage());
        }
    }

    @Override
    public void createDataBase(String dataBaseName) {
        checkConnectionToServer();
        String query = "CREATE DATABASE " + dataBaseName + ";";
        try (Statement stmt = connectionServer.createStatement()) {
            stmt.executeUpdate(query);
        } catch (SQLException ex) {
            throw new RuntimeException(ex.getMessage());
        }
    }

    @Override
    public void createTable(String tableName, List<CellInfo> cellInfos) {
        checkConnectionToServer();
        checkConnectionToDataBase();
        try (Statement stmt = connectionDataBase.createStatement()) {

            String query = "CREATE TABLE " + tableName +
                    "(" + cellInfosToSQL(cellInfos) + ")";
            stmt.executeUpdate(query);

        } catch (SQLException ex) {
            throw new RuntimeException(ex.getMessage());
        }
    }

    private String cellInfosToSQL(List<CellInfo> cellInfos) {
        StringBuilder result = new StringBuilder();
        for (int i = 0; i < cellInfos.size(); i++) {
            result.append(cellInfos.get(i).getCellInfoSQL());
            if (i != cellInfos.size() - 1) {
                result.append(", ");
            }
        }
        return result.toString();
    }

    @Override
    public void dropTable(String tableName) {
        try (Statement stmt = connectionDataBase.createStatement()) {
            String query = "DROP TABLE " + tableName;
            stmt.executeUpdate(query);
        } catch (SQLException ex) {
            throw new RuntimeException(ex.getMessage());
        }
    }

    @Override
    public void deleteRecord(String tableName, String where) {
        checkConnectionToServer();
        checkConnectionToDataBase();
        try (Statement stmt = connectionDataBase.createStatement()) {
            String query = "DELETE FROM " + tableName + " WHERE " + where;
            stmt.executeUpdate(query);
        } catch (SQLException ex) {
            throw new RuntimeException(ex.getMessage());
        }
    }
}
