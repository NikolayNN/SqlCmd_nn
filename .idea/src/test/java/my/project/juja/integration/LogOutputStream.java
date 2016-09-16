package my.project.juja.integration;

import java.io.IOException;
import java.io.OutputStream;
import java.io.PrintStream;

/**
 * Created by Nikol on 4/25/2016.
 */
public class LogOutputStream extends OutputStream{
    private String log = "";
    @Override
    public void write(int b) throws IOException {
        log += String.valueOf((char)b);
    }

    public String getData() {
        return log;
    }
}
