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
                "\t * Example: '"+Command.CONNECTION_TO_SERVER + s + "localhost:5432" + s + "postgres" + s + "root'\n"+
                Command.CONNECTION + "\n" +
                "\t - Connect to database '" + Command.CONNECTION + s +
                "dbname" + s +
                "login" + s +
                "password'" + "\n" +
                Command.DATA_BASES_NAMES + "\n" +
                "\t - Show available data bases in the current server '" + Command.DATA_BASES_NAMES + "'" + "\n" +
                Command.TABLE_LIST + "\n" +
                "\t - Show exist tables in the current database '" + Command.TABLE_LIST + "'" + "\n" +
                Command.TABLE_DATA + "\n" +
                "\t - Show table rows '" + Command.TABLE_DATA + s + "tableName'" + "\n" +
                Command.ADD_RECORD + "\n" +
                "\t - Add record in the selectd table '" + Command.ADD_RECORD + s + "tableName'" + "\n" +
                Command.UPDATE_TABLE + "\n" +
                "\t - Update record in the selectd table '" + Command.UPDATE_TABLE + s + "tableName" + s + "where'" + "\n" +
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
