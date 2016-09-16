package my.project.juja.view;

import java.util.Scanner;

/**
 * Created by Nikol on 4/12/2016.
 */
public class Console implements View {
    @Override
    public String read() {
        Scanner sc = new Scanner(System.in);
        return sc.nextLine();
    }
    @Override
    public void writeln(String message){
        System.out.println(message);
    }
    @Override
    public void writeln(){
        System.out.println();
    }
    @Override
    public void write(String message){
        System.out.print(message);
    }

}
