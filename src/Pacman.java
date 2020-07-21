import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.util.ArrayList;

/*
    *** VALORI***
    Muro = 1
    Spazio vuoto = 2
    pacman = 5
    cibo = 0
    nemici = 3

     */


public class Pacman extends Thread {
    private int [][] grid;
    private int xSize;
    private int ySize;
    private String currentMove = "d";
    private int score = 0;
    enum Status  {IN_GAME, LOSE, WIN};
    private Status status = Status.IN_GAME;
    private int levelChoice;
    private Pawn playerPawn;
    private Pawn enemyPawn1;
    private Pawn enemyPawn2;
    private Pawn enemyPawn3;
    private Pawn enemyPawn4;
    private ArrayList<Pawn> enemyList = new ArrayList<>();



    public Pacman (int levelChoice){
        this.levelChoice = levelChoice;
        this.loadLevel();
        this.initializePawn();
        this.setPlayerPosition();
        this.initializeEnemy();
        this.setEnemyPosition();
    }


    private void initializePawn() {
        if (this.levelChoice == 1){
            this.playerPawn = new Pawn(13, 0);
        }
    }

    private void setPlayerPosition (){
        this.grid [playerPawn.getX()][playerPawn.getY()] = 5;
    }


    private void initializeEnemy (){
        if (this.levelChoice == 1){
            this.enemyPawn1 = new Pawn(13,11);
            this.enemyPawn2 = new Pawn(13,16);
            this.enemyPawn3 = new Pawn(21,6);
            this.enemyPawn4 = new Pawn(21,21);
        }
        enemyList.add(this.enemyPawn1);
        enemyList.add(this.enemyPawn2);
        enemyList.add(this.enemyPawn3);
        enemyList.add(this.enemyPawn4);
    }

    private void setEnemyPosition (){
        this.grid[enemyPawn1.getX()][enemyPawn1.getY()] =  3;
        this.grid[enemyPawn2.getX()][enemyPawn2.getY()] =  3;
        this.grid[enemyPawn3.getX()][enemyPawn3.getY()] =  3;
        this.grid[enemyPawn4.getX()][enemyPawn4.getY()] =  3;
    }

    public void setCurrentMove (String move){
        this.currentMove = move;
    }

    private void enemyMove (){

        for (Pawn p: enemyList) {
            double value = Math.random();
            if (value < 0.25){
                //"d"
                if (this.grid[p.getX()][p.getY()] != 1 && this.grid[p.getX()][p.getY()] != 3){
                    int temporary = this.grid [p.getX()][p.getY() + 1];
                    this.grid [p.getX()][p.getY() + 1] = 3;
                    this.grid [p.getX()] [p.getY()] = temporary;
                    p.setY(p.getY() + 1);
                }
            }
        }




        /*

        for (Pawn p: this.enemyList) {
            double value = Math.random();
            if (value < 0.25){
                //destra
                if (this.grid [p.getX()][p.getY() + 1] == 3){
                    return;
                }
                if (this.grid [p.getX()][p.getY() + 1] == 5){
                    this.status = Status.LOSE;
                }
                if (this.grid [p.getX()][p.getY() + 1] != 1){
                    int temporary = this.grid [p.getX()][p.getY() + 1];
                    this.grid[p.getX()][p.getY() + 1] = 3;
                    this.grid[p.getX()][p.getY()] = temporary;
                    p.setY(p.getY() + 1);
                }


            } else
                if (value < 0.50){
                   // sopra
                    if (this.grid[p.getX() - 1][p.getY()] == 3){
                        return;
                    }
                    if (this.grid[p.getX() - 1][p.getY()] == 5){
                        this.status = Status.LOSE;
                    }
                    if (this.grid[p.getX() - 1][p.getY()] != 1){
                        int temporary = this.grid[p.getX() - 1][p.getY()];
                        this.grid[p.getX() - 1][p.getY()] = 3;
                        this.grid[p.getX()][p.getY()] = temporary;
                        p.setX(p.getX() - 1);
                    }

                } else
                    if (value < 0.75){
                        // giù
                        if (this.grid [p.getX() + 1][p.getY()] == 3){
                            return;
                        }
                        if (this.grid [p.getX() + 1][p.getY()] == 5){
                            this.status = Status.LOSE;
                        }
                        if (this.grid [p.getX() + 1][p.getY()] != 1){
                            int temporary = this.grid [p.getX() + 1][p.getY()];
                            this.grid [p.getX() + 1][p.getY()] = 3;
                            this.grid[p.getX()][p.getY()] = temporary;
                            p.setX(p.getX() + 1);
                        }
                        //sinistra
                    } else
                        if (this.grid[p.getX()] [p.getY() - 1] == 3){
                            return;
                        }
                        if (this.grid[p.getX()] [p.getY() - 1] == 5){
                            this.status = Status.LOSE;
                        }
                        if (this.grid[p.getX()] [p.getY() - 1] != 1){
                            int temporary = this.grid[p.getX()] [p.getY() - 1];
                            this.grid[p.getX()] [p.getY() - 1] = 3;
                            this.grid[p.getX()][p.getY()] = temporary;
                            p.setY(p.getY() - 1);
                    }
            this.setEnemyPosition();

        }

         */
    }

    @Override
    public void run(){
        while ( status == Status.IN_GAME){
            try {
                Thread.sleep(1000);
                move();
                this.enemyMove();
                System.out.println(this.toString());
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }

    public void move (){



        if (this.currentMove.equalsIgnoreCase("d")){
            if (this.playerPawn.getY() + 1 >= this.ySize){
                this.grid [playerPawn.getX()] [playerPawn.getY()] = 2;
                this.playerPawn.setY(0);
            }
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



        if (this.currentMove.equalsIgnoreCase("a")){
            if (this.playerPawn.getY() - 1 < 0){
                this.grid [playerPawn.getX()] [playerPawn.getY()] = 2;
                this.playerPawn.setY(this.ySize-1);
            }
            if (this.playerPawn.getY() - 1 < 0){
                this.grid [playerPawn.getX()] [playerPawn.getY()] = 2;
                this.playerPawn.setY(ySize - 1);
            }
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

        if (this.currentMove.equalsIgnoreCase("s")){
            if (this.playerPawn.getX() + 1 >= this.xSize){
                this.grid [playerPawn.getX()] [playerPawn.getY()] = 2;
                this.playerPawn.setX(0);
            }
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

        if (this.currentMove.equalsIgnoreCase("w")){
            if (this.playerPawn.getX() - 1 < 0){
                this.grid [playerPawn.getX()] [playerPawn.getY()] = 2;
                this.playerPawn.setX(this.xSize - 1);
            }
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

    }

    public Status getStatus() {
        return status;
    }



    public void loadLevel(){
        if (this.levelChoice == 1){
            this.grid = new int[27][28];
            this.xSize = 27;
            this.ySize = 28;
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
            result += "|";
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
