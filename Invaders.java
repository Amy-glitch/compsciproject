
import javax.swing.*;
import java.awt.*;
import java.awt.event.KeyEvent;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.*;
//handles all details outside the gameloop
public class Invaders {
    //these are functions to display different screens
    static String player_name;
    public  static void IntroScreen(){
        StdDraw.clear();
        StdDraw.picture(.5,.5,"introBackground.png");
        //StdAudio.loop("Music.wav");
    }
    public  static void DeathScreen() throws IOException {
        StdDraw.clear();
        StdDraw.setPenColor(StdDraw.RED);
        Font fontLevel = new Font("Arial", Font.BOLD, 50);
        StdDraw.setFont(fontLevel);
        StdDraw.text(0.65, 0.785, String.valueOf(InvadersGameState.level));
        StdDraw.picture(0.5,0.5,"deathBackground.png");
        HighScores();

        //back to normal font
        StdDraw.setPenColor(StdDraw.WHITE);
        Font standard = new Font("Arial", Font.PLAIN, 16);
        StdDraw.setFont(standard);
    }
    public  static void WinScreen(){
        StdDraw.clear();
        StdDraw.picture(0.5,0.5,"winBackground.png");
        StdDraw.setPenColor(StdDraw.RED);
        Font fontLevel = new Font("Arial", Font.BOLD, 50);
        StdDraw.setFont(fontLevel);
        StdDraw.text(0.65, 0.785, String.valueOf(InvadersGameState.level));
        StdDraw.setPenColor(StdDraw.WHITE);
        Font standard = new Font("Arial", Font.PLAIN, 16);
        StdDraw.setFont(standard);
    }
    public static void HighScores() throws IOException {
        //reads textfile to get highscores
        StdDraw.setPenColor(StdDraw.WHITE);
        Font standard = new Font("Arial", Font.PLAIN, 16);
        StdDraw.setFont(standard);
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
            StdDraw.setPenColor(StdDraw.BLACK);
            Font highscoreFont = new Font("Arial", Font.BOLD, 25);
            StdDraw.setFont(highscoreFont);
            for (int i = 0; i < 5; i++) {
               StdDraw.text(0.5, 0.5 - i * 0.05, "#" + (i + 1) + "  " +names.get(i) +' '+ String.valueOf(scores.get(i)));

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
                        InvadersGameState.lives = 1;
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
                        WinScreen();
                        InvadersGameState.level++;
                        if(InvadersGameState.lives < 3){
                            InvadersGameState.lives++;
                        }
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
