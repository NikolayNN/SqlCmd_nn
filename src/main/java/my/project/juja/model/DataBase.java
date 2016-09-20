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
    private static final String ERROR_CONNECTION_NOT_EXIST = "ERROR. at first connect to database";
    private Connection connection;
    private Connection connectionToServer;
    private String dbUrl;
    private String login;
    private String password;

    @Override
    public void connectToServer(String dbUrl, String login, String password){
    try{
        try {
            Class.forName("org.postgresql.Driver");
        } catch (ClassNotFoundException e) {
            throw new RuntimeException(ERROR_JDBCDRIVER_NOT_FOUND);
        }
        connectionToServer = DriverManager.getConnection("jdbc:postgresql://" + dbUrl + "/" , login, password);
    } catch (SQLException ex) {
        throw new RuntimeException(ERROR_CONNECT_UNSUCCESSFUL + " " + ex.getMessage());
    }
        this.dbUrl = dbUrl;
        this.login = login;
        this.password = password;
    }

    @Override
    public void getConnection(String dbName, String login, String password) {
        try {
            Class.forName("org.postgresql.Driver");
        } catch (ClassNotFoundException e) {
            throw new RuntimeException(ERROR_JDBCDRIVER_NOT_FOUND);
        }
        try {
            connection = DriverManager.getConnection(
                    "jdbc:postgresql://localhost:5432/", login,
                    password);
        } catch (SQLException ex) {
            throw new RuntimeException(ERROR_CONNECT_UNSUCCESSFUL + " " + ex.getMessage());
        }
    }

    @Override
    public Connection getConnection() {
        return connection;
    }

    @Override
    public void closeConnection() {
        try {
            connection.close();
            connection = null;
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    private void checkConnection() throws RuntimeException {
        if (connection == null) {
            throw new RuntimeException(ERROR_CONNECTION_NOT_EXIST);
        }
    }

    @Override
    public void clearTable(String tableName) {
        checkConnection();
        try (Statement stmt = connection.createStatement()) {
            String sql = "DELETE FROM " + tableName;
            stmt.executeUpdate(sql);
        } catch (SQLException ex) {
            throw new RuntimeException(ERROR_WRONG_TABLENAME);
        }
    }

    @Override
    public void addRecord(Table table) throws SQLException {
        checkConnection();
        Row row = table.getRow(0);
        String columnNames = format(row.getColumnNamesNotNull(), "\"");
        String columnValues = format(row.getCellValuesNotNull(), "'");
        Statement stmt = connection.createStatement();
        String sql = "INSERT INTO " + table.getTableName() + "(" + columnNames + ")" +
                " VALUES (" + columnValues + ")";
        stmt.executeUpdate(sql);
        stmt.close();
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
        checkConnection();
        Set<String> result = new LinkedHashSet<>();
        String query = "SELECT table_name" +
                " FROM information_schema.tables" +
                " WHERE table_schema='public'" +
                " AND table_type='BASE TABLE';";
        try (Statement stmt = connection.createStatement(); ResultSet rs = stmt.executeQuery(query)) {
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
        checkConnection();
        Table table = new Table(tableName, getColumnInformation(tableName));
        String query = "SELECT * FROM " + tableName;
        try (Statement stmt = connection.createStatement();
             ResultSet rs = stmt.executeQuery(query)) {
            ResultSetMetaData rsmd = rs.getMetaData();
            while (rs.next()) {
                Row row = new Row(table.getCellInfos());
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
    public Table getTableData(String tableName, String where) throws SQLException {
        checkConnection();
        Table table = new Table(tableName, getColumnInformation(tableName));
        String query = "SELECT * FROM " + tableName + " WHERE " + where;
        Statement stmt = connection.createStatement();
        ResultSet rs = stmt.executeQuery(query);
        ResultSetMetaData rsmd = rs.getMetaData();
        while (rs.next()) {
            Row row = new Row(table.getCellInfos());
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
        return table;
    }

    @Override
    public void updateRecord(String where, Table table) {
        checkConnection();
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
        try (Statement stmt = connection.createStatement()) {
            String sql = "UPDATE " + tableName + " SET " + set + " WHERE " + where;
            stmt.executeUpdate(sql);
            stmt.close();
        } catch (SQLException ex) {
            throw new RuntimeException(ERROR_WRONG_COMMAND + " " + ex.getMessage());
        }
    }

    @Override
    public List<CellInfo> getColumnInformation(String tableName) {
        List<CellInfo> cellInfos = new ArrayList<>();
        checkConnection();
        String query = "SELECT column_name, data_type, is_nullable, column_default from information_schema.columns where table_name = '" + tableName + "'";
        try (Statement stmt = connection.createStatement();
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
}
