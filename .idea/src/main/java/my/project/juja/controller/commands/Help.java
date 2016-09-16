package my.project.juja.controller.commands;

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
        view.writeln(
                Command.CONNECTION + "\n" +
                        "\t - Connect to database '" + Command.CONNECTION + Command.SEPARATOR_TO_STRING +
                        "dbname" + Command.SEPARATOR_TO_STRING +
                        "login" + Command.SEPARATOR_TO_STRING +
                        "password'" + "\n" +
                        Command.TABLE_LIST + "\n" +
                        "\t - Show exist tables in the current database '" + Command.TABLE_LIST + "'" + "\n" +
                        Command.TABLE_DATA + "\n" +
                        "\t - Show table rows '" + Command.TABLE_DATA + Command.SEPARATOR_TO_STRING + "tableName'" + "\n" +
                        Command.ADD_RECORD + "\n" +
                        "\t - Add record in the selectd table '" + Command.ADD_RECORD + Command.SEPARATOR_TO_STRING + "tableName'" + "\n" +
                        Command.UPDATE_TABLE + "\n" +
                        "\t - Update record in the selectd table '" + Command.UPDATE_TABLE + Command.SEPARATOR_TO_STRING + "tableName" + Command.SEPARATOR_TO_STRING + "where'" + "\n" +
                        Command.CLEAR_TABLE + "\n" +
                        "\t - Clear selected table '" + Command.CLEAR_TABLE + Command.SEPARATOR_TO_STRING + "tableName'" + "\n" +
                        Command.EXIT + "\n" +
                        "\t - Close connection to a database and exit"
        );
    }

    @Override
    public String getName() {
        return name;
    }
}
