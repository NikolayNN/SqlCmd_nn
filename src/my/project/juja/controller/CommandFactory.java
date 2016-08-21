package my.project.juja.controller;

import my.project.juja.controller.commands.Command;

/**
 * Created by Nikol on 5/14/2016.
 */
public interface CommandFactory {
    Command createCommand(String source);
}
