import java.util.Scanner;

public class Main {
    public static void main (String args[]){
        Pacman p = new Pacman(1);
        System.out.println(p.toString());

        while (true){
            Scanner in = new Scanner(System.in);
            String direction = in.nextLine();
            p.move(direction);
            System.out.println(p.toString());
        }

    }
}
