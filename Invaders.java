import javax.sound.sampled.AudioFormat;
import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.swing.*;
import java.awt.*;
import java.awt.event.KeyEvent;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.*;
import java.awt.Font;
//handles all details outside the gameloop
public class Invaders {
    //these are functions to display different screens
    static String player_name;
    public  static void IntroScreen(){
        StdDraw.clear();
        StdDraw.picture(.5,.5,"introBackground.png");

        //Font title_font = new Font("Courier", Font.BOLD, 55);
        //StdDraw.setFont(title_font);
        //StdDraw.setPenColor(StdDraw.BLUE);
        //StdDraw.text(0.5, 0.8, "Space Invaders");
        //StdDraw.setPenColor(StdDraw.YELLOW);
        //Font other_font = new Font("Courier", Font.BOLD, 18);
        //StdDraw.setFont(other_font);
        //StdDraw.text(0.5, 0.6, "Press space to save the world!");
        //StdDraw.text(0.5, 0.4, "Shoot: [SPACE]");
        //StdDraw.text(0.5, 0.3, "Move: WASD");
        //StdDraw.text(0.5, 0.2, "Rotate: Left and right arrows");
        //StdDraw.setPenColor(StdDraw.BLACK);
        StdAudio.loopInBackground("MainMenuTheme.wav");

    }
    public  static void DeathScreen() throws IOException {
        StdDraw.clear();
        StdDraw.picture(0,0,"background.png",2,2);
        StdDraw.setPenColor(StdDraw.RED);
        StdDraw.text(0.5, 0.9, "GAME OVER!");
        StdDraw.setPenColor(StdDraw.WHITE);
        StdDraw.text(0.5, 0.8, "Press [SPACE] play again");
        StdDraw.text(0.5, 0.7, "Highscores:");
       HighScores();
        StdDraw.setPenColor(StdDraw.BLACK);
    }
    public  static void WinScreen(){
        StdDraw.clear();
        StdDraw.picture(0.5,0.5,"background.png");
        StdDraw.setPenColor(StdDraw.YELLOW);
        StdDraw.text(0.5, 0.8, "You beat level "+(InvadersGameState.level-1)+"!");
        StdDraw.text(0.5, 0.6, "Press [SPACE] for the "+InvadersGameState.level);
        StdDraw.setPenColor(StdDraw.BLACK);
    }
    public static void HighScores() throws IOException {
        //get name of payer

        char c;



        //reads textfile to get highscores
        ArrayList<Integer> scores = new ArrayList<Integer>();
        ArrayList<String> names = new ArrayList<String>();
        File file = new File("highscores.txt");
        FileWriter fileWriter = new FileWriter(file,true);
        fileWriter.append("\n" + InvadersGameState.score);
        fileWriter.append("\n" + player_name);
        fileWriter.close();

        Scanner scFile = new Scanner(file);
        while(scFile.hasNext()){
            Scanner scLine = new Scanner(scFile.nextLine());
            scores.add(scLine.nextInt());
            if (scFile.hasNext()){
            names.add(scFile.nextLine());}
        }
        //bubble sort
        //Collections.sort(scores, Collections.reverseOrder());
        for (int i =0; i< scores.size();i++){
            for (int j =0; j< scores.size()-1;j++){
                if (scores.get(j)<scores.get(j+1)){
                    int temp = scores.get(j);
                    scores.set(j,scores.get(j+1));
                    scores.set(j+1,temp);
                    String stemp = names.get(j);
                    names.set(j,names.get(j+1));
                    names.set(j+1,stemp);
                }
            }

        }

        //prints top 5 scores
        if(scores.size() >= 5) {
            for (int i = 0; i < 5; i++) {
               StdDraw.text(0.5, 0.4 - i * 0.05, "#" + (i + 1) + "  " +names.get(i) +' '+ String.valueOf(scores.get(i)));

            }
        }
    }
    public static void main(String[] args) throws IOException {
        IntroScreen();
        player_name= JOptionPane.showInputDialog("Type you name:");
        boolean playing = false;
        InvadersGameState gameState = new InvadersGameState();
        gameState.Init();
        gameState.initBunkers();

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
                        InvadersGameState.score = 0;
                        InvadersGameState.level = 1;
                        InvadersGameState.disp = 0.0015;
                        gameState = new InvadersGameState();
                        gameState.Init();
                        gameState.initBunkers();
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
                        InvadersGameState.level++;
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
