package my.project.juja.view;

/**
 * Created by Nikol on 4/23/2016.
 */
public interface View {
    String read();

    void writeln(String message);

    void writeln();

    void write(String message);
}
