package my.project.juja.controller.commands.program;

import my.project.juja.controller.commands.Command;
import my.project.juja.view.Console;
import my.project.juja.view.View;

/**
 * Created by Nikol on 4/13/2016.
 */
public class Unsupported extends Command {
    public Unsupported(View view) {
        super(view);
    }

    @Override
    public void perform() {
        view.writeln("The command doesn't exist");
    }

    @Override
    public String getName() {
        return "";
    }


}
