package my.project.juja.controller.commands;

import my.project.juja.model.Storeable;
import my.project.juja.view.View;

/**
 * Created by Nikol on 8/22/2016.
 */
public class addRecordNew extends Command {

    public addRecordNew(Storeable store, View view) {
        super(store, view);
    }

    @Override
    public void perform() {

    }

    @Override
    public String getName() {
        return null;
    }
}
