package my.project.juja.controller;

import my.project.juja.controller.commands.Command;
import my.project.juja.model.DataBase;
import my.project.juja.model.Storeable;
import my.project.juja.view.Console;
import my.project.juja.view.View;

/**
 * Created by Nikol on 5/13/2016.
 */
public class Controller {
    public void start(){
        View view = new Console();
        Storeable store = new DataBase();
        CommandFactory commandFactory = new MainCommandFactory(store, view);
        view.writeln("Hello");
        String source = "";
        while (!source.equalsIgnoreCase(Command.EXIT)){
            view.writeln("Input your command or 'help'");
            try {
                source = view.read();
                Command command = commandFactory.createCommand(source);
                command.perform();
            } catch (Exception e) {
                view.writeln(e.getMessage());
            }
        }

    }
}
