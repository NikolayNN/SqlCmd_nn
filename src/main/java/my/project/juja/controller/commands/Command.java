package my.project.juja.controller.commands;

import my.project.juja.model.Storeable;
import my.project.juja.model.table.CellInfo;
import my.project.juja.testutils.WhereConstructor;
import my.project.juja.view.View;

import java.util.List;

/**
 * Created by Nikol on 4/12/2016.
 */
public abstract class Command {
    public static final String HELP = "help";
    public static final String EXIT = "exit";
    public static final String CONNECTION_TO_DB = "connect-db";
    public static final String CONNECTION_TO_SERVER = "connect-server";
    public static final String TABLE_LIST = "table-list";
    public static final String TABLE_DATA = "table-data";
    public static final String ADD_RECORD = "add-record";
    public static final String CLEAR_TABLE = "clear-table";
    public static final String UPDATE_TABLE = "update-table";
    public static final String SEPARATOR = "\\|";
    public static final String SEPARATOR_TO_STRING = "|";
    public static final String DATA_BASES_NAMES = "list-db";
    public static final String DISCONECT_DATA_BASE = "disconnect-db";
    public static final String CURRENT_DATA_BASE_NAME = "current-db";
    public static final String DROP_DATA_BASE = "drop-db";
    public static final String CREATE_DATA_BASE = "create-db";
    public static final String CREATE_TABLE = "create-table";
    public static final String DROP_TABLE = "drop-table";
    public static final String DELETE_RECORD = "delete-record";
    public static final String TABLE_DATA_WHERE = "table-data-where";
    protected String command;
    protected int countParametrs;
    protected String[] parametrs;
    protected View view;
    protected Storeable store;

    public Command(Storeable store, View view) {
        this.store = store;
        this.view = view;
    }

    public Command(View view) {
        this.view = view;
    }

    public void setup(String source) {
        String[] splitedSource = source.split(SEPARATOR);
        this.command = splitedSource[0];
        this.countParametrs = splitedSource.length - 1;
        this.parametrs = new String[countParametrs];
        if (splitedSource.length > 1) {
            System.arraycopy(splitedSource, 1, parametrs, 0, parametrs.length);
        }
    }

    public void checkCountParameters(String parameters[], int expectedCount) {
        if (parameters.length != expectedCount) {
            throw new RuntimeException("ERROR. Wrong count parameters expected " +
                    expectedCount + ", but found " + parameters.length);
        }
    }

    protected boolean confirmCommand (String message) {
        while (true) {
            view.writeln("Are you sure " + message + "? (Y/N)");
            String confirm = view.read().trim();
            if (confirm.equalsIgnoreCase("n")) {
                throw new RuntimeException("Canceled");
            }
            if (confirm.equalsIgnoreCase("y")) {
                return true;
            } else {
                view.writeln("wrong input");
                continue;
            }
        }
    }

    protected boolean isConnectedDataBase(){
        if(store.getConnectToDataBase() != null){
            return true;
        }else{
            throw new RuntimeException("ERROR. at first connect to database");
        }
    }

    public String createWhere(View view, List<CellInfo> cellInfos){
        WhereConstructor whereConstructor = new WhereConstructor(view, cellInfos);
        return whereConstructor.toString();
    }

    public abstract void perform();

    public abstract String getName();
}

