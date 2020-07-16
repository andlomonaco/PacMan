import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;

public class Pacman {
    private int [][] grid;
    private int score = 0;
    enum Status  {IN_GAME, LOSE, WIN};
    private Status status = Status.IN_GAME;
    private int levelChoice;
    PlayerPawn playerPawn;
    private String lastMove = "d";

    public Pacman (int levelChoice){
        this.levelChoice = levelChoice;
        this.loadLevel();
        this.initializePawn();
        this.setPlayerPosition();
    }
    public Pacman (){
        this.loadLevel();
    }

    private void initializePawn() {
        if (this.levelChoice == 1){
            this.playerPawn = new PlayerPawn(13, 0);
        }
    }

    private void setPlayerPosition (){
        this.grid [playerPawn.getX()][playerPawn.getY()] = 5;
    }
    /*
    *** VALORI***
    Muro = 1
    Spazio vuoto = 2
    pacman = 5
    cibo = 0
    nemici = 3

     */

    public void move (String direction){
        if (direction.equalsIgnoreCase("d")){
            if (this.grid [playerPawn.getX()] [playerPawn.getY() + 1] == 3){
                this.status = Status.LOSE;
            }
            if (this.grid [playerPawn.getX()] [playerPawn.getY() + 1] != 1){
                if (this.grid [playerPawn.getX()] [playerPawn.getY() + 1] == 0){
                    this.score += 10;
                }
                this.grid [playerPawn.getX()] [playerPawn.getY()] = 2;
                this.playerPawn.setY(this.playerPawn.getY() + 1);
                this.setPlayerPosition();
            }
        }



        if (direction.equalsIgnoreCase("a")){
            if (this.grid [playerPawn.getX()] [playerPawn.getY() - 1] == 3){
                this.status = Status.LOSE;
            }
            if (this.grid [playerPawn.getX()] [playerPawn.getY() - 1] != 1){
                if (this.grid [playerPawn.getX()] [playerPawn.getY() - 1] == 0){
                    this.score += 10;
                }
                this.grid [playerPawn.getX()] [playerPawn.getY()] = 2;
                this.playerPawn.setY(this.playerPawn.getY() - 1);
                this.setPlayerPosition();
            }
        }

        if (direction.equalsIgnoreCase("s")){
            if (this.grid [playerPawn.getX() + 1] [playerPawn.getY()] == 3){
                this.status = Status.LOSE;
            }
            if (this.grid [playerPawn.getX() + 1] [playerPawn.getY()] != 1){
                if (this.grid [playerPawn.getX() + 1] [playerPawn.getY()] == 0){
                    this.score += 10;
                }
                this.grid [playerPawn.getX()] [playerPawn.getY()] = 2;
                this.playerPawn.setX(this.playerPawn.getX() + 1);
                this.setPlayerPosition();
            }
        }

        if (direction.equalsIgnoreCase("w")){
            if (this.grid [playerPawn.getX() - 1] [playerPawn.getY()] == 3){
                this.status = Status.LOSE;
            }
            if (this.grid [playerPawn.getX() - 1] [playerPawn.getY()] != 1){
                if (this.grid [playerPawn.getX() - 1] [playerPawn.getY()] == 0){
                    this.score += 10;
                }
                this.grid [playerPawn.getX()] [playerPawn.getY()] = 2;
                this.playerPawn.setX(this.playerPawn.getX() - 1);
                this.setPlayerPosition();
            }
        }
        this.lastMove = direction;
    }

    public String getLastMove() {
        return lastMove;
    }

    public void loadLevel(){
        if (this.levelChoice == 1){
            this.grid = new int[27][28];
            try {
                FileReader reader = new FileReader("livello1.txt");
                int character;
                do {
                    character = reader.read();
                    for (int i = 0; i < this.grid.length ; i ++){
                        for (int j = 0; j < this.grid[i].length  ; j++) {
                            this.grid [i][j] = character - 48;
                            character = reader.read();
                        }
                    }
                } while (character != -1);

            }  catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    public String toString (){
        String result= "";
        for (int i = 0; i < this.grid.length; i ++){
            result += "|" + i;
            for (int j = 0; j <this.grid[i].length; j ++){
                if (this.grid [i][j] == 1){
                    result += "[" + "\u001B[33m\uD83D\uDEA7\u001B[0m" + "]";
                } else if(this.grid [i][j] == 0) {
                    result += "[" + "\033[0;34m\uD83D\uDC6E\u001B[0m" + "]";
                }else if (this.grid [i][j] == 2){
                    result += "[" + "\033[0;30m〰\u001B[0m" + "]";
                }else if (this.grid [i][j] == 3 ){
                    result += "[" + "\u001B[46m\uD83D\uDE93\u001B[0m" + "]";
                }else if (this.grid [i][j] == 5 ){
                    result += "[" + "\u001B[31m\uD83D\uDEB4\u001B[0m" + "]";
                } else {
                    result += "[" + "\uD83D\uDE94" + "]";
                }
            }
            result += "\n";
        }

        return result;

    }

}
