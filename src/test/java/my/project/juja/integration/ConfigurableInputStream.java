package my.project.juja.integration;

import java.io.IOException;
import java.io.InputStream;

/**
 * Created by Nikol on 4/25/2016.
 */
public class ConfigurableInputStream extends InputStream {
    private String line;
    private boolean endLine = false;

    @Override
    public int read() throws IOException {
        if (line.length() == 0) {
            endLine = false;
            return -1;
        }
        if (endLine) {
            endLine = false;
            return -1;
        }

        char ch = line.charAt(0);
        line = line.substring(1);

        if (ch == '\n') {
            endLine = true;
        }

         return (int)ch;
    }

    @Override
    public synchronized void reset() throws IOException {
        line = null;
        endLine = false;
    }

    public void add (String line){
        if(this.line == null) {
            this.line = line + "\n";
        }else {
            this.line += line + "\n";
        }
    }
}
