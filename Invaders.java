//handles all details outside the gameloop
public class Invaders {
    //these are functions to display different screens
    public  static void IntroScreen(){
        StdDraw.clear();
        StdDraw.text(0.5, 0.8, "Space Invaders");
        StdDraw.text(0.5, 0.6, "Press space to save the world!");
    }
    public  static void DeathScreen(){
        StdDraw.clear();
        StdDraw.text(0.5, 0.8, "You died");
        StdDraw.text(0.5, 0.6, "Press space play again");
    }
    public  static void WinScreen(){
        StdDraw.clear();
        StdDraw.text(0.5, 0.8, "You won");
        StdDraw.text(0.5, 0.6, "Press space play again");
    }

    public static void main(String[] args) {
        IntroScreen();
        boolean playing = false;
        InvadersGameState gameState = new InvadersGameState();
        gameState.Init();


        while(true){
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
