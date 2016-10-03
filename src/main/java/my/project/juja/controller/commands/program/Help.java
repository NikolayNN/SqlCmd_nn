package my.project.juja.controller.commands.program;

import my.project.juja.controller.commands.Command;
import my.project.juja.view.View;

/**
 * Created by Nikol on 4/26/2016.
 */
public class Help extends Command {
    public static final String name = Command.HELP;

    public Help(View view) {
        super(view);
    }

    @Override
    public void perform() {
        String s = Command.SEPARATOR_TO_STRING;
        view.writeln(
                Command.CONNECTION_TO_SERVER + "\n" +
                        "\t - Connect to a server '" + Command.CONNECTION_TO_SERVER + s +
                        "serverUrl" + s +
                        "login" + s +
                        "password'" + "\n" +
                        "\t * Example: '" + Command.CONNECTION_TO_SERVER + s + "localhost:5432" + s + "postgres" + s + "root'\n" +
                        Command.CONNECTION_TO_DB + "\n" +
                        "\t - Connect to database '" + Command.CONNECTION_TO_DB + s +
                        "dbname'" + "\n" +
                        Command.DISCONECT_DATA_BASE + "\n" +
                        "\t - disonnect current database '" + Command.DISCONECT_DATA_BASE + "'" + "\n" +
                        Command.DATA_BASES_NAMES + "\n" +
                        "\t - Show available data bases in the current server '" + Command.DATA_BASES_NAMES + "'" + "\n" +
                        Command.CURRENT_DATA_BASE_NAME + "\n" +
                        "\t - Show current data base name '" + Command.CURRENT_DATA_BASE_NAME + "'" + "\n" +
                        Command.DROP_DATA_BASE + "\n" +
                        "\t - Delete the data base '" + Command.DROP_DATA_BASE + s + "dataBaseName'" + "\n" +
                        Command.CREATE_DATA_BASE + "\n" +
                        "\t - Create new data base '" + Command.CREATE_DATA_BASE + s + "dataBaseName'" + "\n" +
                        Command.CREATE_TABLE + "\n" +
                        "\t - Create new table in the current data base '" + Command.CREATE_TABLE + s + "tableName '" + "\n" +
                        Command.DROP_TABLE + "\n" +
                        "\t - Drop table '" + Command.DROP_TABLE + s + "tableName '" + "\n" +
                        Command.TABLE_LIST + "\n" +
                        "\t - Show exist tables in the current database '" + Command.TABLE_LIST + "'" + "\n" +
                        Command.TABLE_DATA + "\n" +
                        "\t - Show all table rows '" + Command.TABLE_DATA + s + "tableName'" + "\n" +
                        Command.TABLE_DATA_WHERE + "\n" +
                        "\t - Show table rows with the condition WHERE '" + Command.TABLE_DATA_WHERE + s + "tableName'" + "\n" +
                        Command.ADD_RECORD + "\n" +
                        "\t - Add record in the selectd table '" + Command.ADD_RECORD + s + "tableName'" + "\n" +
                        Command.UPDATE_TABLE + "\n" +
                        "\t - Update record in the selected table '" + Command.UPDATE_TABLE + s + "tableName" + s + "where'" + "\n" +
                        Command.CLEAR_TABLE + "\n" +
                        "\t - Clear selected table '" + Command.CLEAR_TABLE + s + "tableName'" + "\n" +
                        Command.EXIT + "\n" +
                        "\t - Close connection to a database and exit"
        );
    }

    @Override
    public String getName() {
        return name;
    }
}
