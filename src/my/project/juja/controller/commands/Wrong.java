package my.project.juja.controller.commands;

import my.project.juja.view.Console;
import my.project.juja.view.View;

/**
 * Created by Nikol on 4/13/2016.
 */
public class Wrong extends Command {
    public Wrong(View view) {
        super(view);
    }

    @Override
    public void perform() {
        view.writeln("This commands doesn't exist, please check your commands");
    }

    @Override
    public String getName() {
        return "";
    }

}
