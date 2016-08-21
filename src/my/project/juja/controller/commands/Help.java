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
                        "\t - Connect to database 'connect dbname login password'" + "\n" +
                Command.TABLE_LIST + "\n" +
                        "\t - Show exist tables in the current database 'tablelist'" + "\n" +
                Command.TABLE_DATA + "\n" +
                        "\t - Show table rows 'tabledata tableName'" + "\n" +
                Command.ADD_RECORD + "\n" +
                        "\t - Add record in the selectd table 'addrecord tableName'" + "\n" +
                Command.CLEAR_TABLE + "\n" +
                        "\t - Clear selected table 'cleartable tableName'" + "\n" +
                Command.EXIT + "\n" +
                        "\t - Close connection to a database and exit"
        );
    }

    @Override
    public String getName() {
        return name;
    }
}
