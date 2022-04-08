import java.awt.event.KeyEvent;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Scanner;

//handles all details outside the gameloop
public class Invaders {
    //these are functions to display different screens
    public  static void IntroScreen(){
        StdDraw.clear();
        StdDraw.text(0.5, 0.8, "Space Invaders");
        StdDraw.text(0.5, 0.6, "Press space to save the world!");
    }
    public  static void DeathScreen() throws IOException {
        StdDraw.clear();
        StdDraw.text(0.5, 0.9, "You died");
        StdDraw.text(0.5, 0.8, "Press [SPACE] play again");
        StdDraw.text(0.5, 0.7, "Highscores:");
        HighScores();
    }
    public  static void WinScreen(){
        StdDraw.clear();
        StdDraw.text(0.5, 0.8, "You won");
        StdDraw.text(0.5, 0.6, "Press space play again");
    }
    public static void HighScores() throws IOException {
        //reads textfile to get highscores
        ArrayList<Integer> scores = new ArrayList<Integer>();
        File file = new File("highscores.txt");

        FileWriter fileWriter = new FileWriter(file,true);
        fileWriter.append("\n" + InvadersGameState.score);
        fileWriter.close();

        Scanner scFile = new Scanner(file);
        while(scFile.hasNext()){
            Scanner scLine = new Scanner(scFile.nextLine());
            scores.add(scLine.nextInt());
        }
        //sorts scores in descending order
        Collections.sort(scores, Collections.reverseOrder());
        //prints top 5 scores
        if(scores.size() >= 5) {
            for (int i = 0; i < 5; i++) {
                StdDraw.text(0.5, 0.6 - i * 0.05, "#" + (i + 1) + "  " + String.valueOf(scores.get(i)));
            }
        }
    }
    public static void main(String[] args) throws IOException {
        IntroScreen();
        boolean playing = false;
        InvadersGameState gameState = new InvadersGameState();
        gameState.Init();


        while(true){
            //quits if 'q' is pressed
            if(StdDraw.isKeyPressed(KeyEvent.VK_Q)){
                System.exit(0);
            }
            //check when to start running gameloop
            if (StdDraw.isKeyPressed(32)){
                playing = true;
                StdDraw.clear();
            }

            //if we are already playing
            if (playing == true){
                //run the gameloop
                int res = gameState.GameLoop();

                switch (res){
                    //if the player died
                    case 0:{
                        playing=false;
                        DeathScreen();
                        StdDraw.show();
                        gameState = new InvadersGameState();
                        gameState.Init();
                        InvadersGameState.score = 0;
                        InvadersGameState.level = 1;
                        InvadersGameState.disp = 0.0015;
                        break;
                    }
                    //if the player is still alive
                    case 1:{
                        //do nothing
                        break;
                    }
                    //if the player won
                    case 2:{
                        playing = false;
                        //Increases speed of enemies after each level
                        if(InvadersGameState.direction == 1){
                            InvadersGameState.disp += 0.001;
                        }
                        else{
                            InvadersGameState.disp -= 0.001;
                        }
                        WinScreen();
                        StdDraw.show();
                        gameState = new InvadersGameState();
                        gameState.Init();
                        break;
                    }
                }


            }





        }




    }


}
