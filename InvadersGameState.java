import java.util.ArrayList;
public class InvadersGameState {
    Shooter player_shooter;
    public static int score = 0;
    public static int level = 1;
    public static int lives = 1;
    ArrayList<Missile> missiles;
    ArrayList<Missile> enemy_missiles;
    ArrayList<Enemy> enemies;
    static ArrayList<Bunker> bunkers;
    double cooldown;
    public static double disp = 0.003;
    public static int direction = 1;
    double absEnemDispX;

    public static void main(String[] args) {}

    //intialises a new game
    public  void Init(){
        absEnemDispX=0;
        player_shooter =  new Shooter();
        enemies = new ArrayList<Enemy>();
        cooldown = 0;
        missiles = new ArrayList<Missile>();
        enemy_missiles = new ArrayList<Missile>();
        disp = 0.003*level;

        for (int i = 0; i<6; i++){
            Enemy e =new Enemy();
            e.setPos(0.1+i/10.0,0.8);
            e.setSkin("Enemy1.png");
            enemies.add(e);
        }
        for (int i = 0; i<6; i++){
            Enemy e =new Enemy();
            e.setPos(0.1+i/10.0,0.7);
            e.setSkin("Enemy2.png");
            enemies.add(e);
        }
    }
    public void initBunkers(){
        bunkers = new ArrayList<Bunker>();

        for (int i = 0; i < 3; i++){
                for (int j = 0; j < 5; j++){
                    Bunker b = new Bunker();
                    b.setPos(0.15+j*0.02, 0.25+i*0.02);
                    bunkers.add(b);
                }
            }
            for (int i = 0; i < 3; i++){
                for (int j = 0; j < 5; j++){
                    Bunker b = new Bunker();
                    b.setPos(0.45+j*0.02, 0.25+i*0.02);
                    bunkers.add(b);
                }
            }
            for (int i = 0; i < 3; i++){
                for (int j = 0; j < 5; j++){
                    Bunker b = new Bunker();
                    b.setPos(0.75+j*0.02, 0.25+i*0.02);
                    bunkers.add(b);
                }
            }
        }

    //this handles the main loop of the game run each iteration
    public int GameLoop() throws IndexOutOfBoundsException{
        int res=1;
        boolean alive = true;
        StdDraw.clear();
        StdDraw.picture(0.5,0.5,"pb.png",1,1);

        //decrement the cooldown
        if (cooldown >0){cooldown-=1;}
        //check for movement
        if(StdDraw.isKeyPressed(65) && player_shooter.getX() > .05) {
            player_shooter.move(-0.01, 0);
        }
        if(StdDraw.isKeyPressed(68) && player_shooter.getX() < .95) {
            player_shooter.move(0.01, 0);
        }
        //check for shooting a missile
        if(StdDraw.isKeyPressed(32) && cooldown==0) {
            StdAudio.play("Shoot.wav");
            Missile m = new Missile();
            m.setPos(player_shooter.x, 0.05);
            double a=player_shooter.getAngle();

            m.setDir(Math.cos(a)/100.,Math.sin(a)/100.);
            missiles.add(m);
            cooldown=35;
        }

        if(StdDraw.isKeyPressed(37)){
            if (player_shooter.getAngle()+0.05 < 3.0) {
            player_shooter.setAngle(player_shooter.getAngle()+0.05);}
        }
        if(StdDraw.isKeyPressed(39)){

            if (player_shooter.getAngle()-0.05 > -0.0) {
                player_shooter.setAngle(player_shooter.getAngle()-0.05);
            }
        }


        //UPDATE THE ENEMIES
        //when should they move other direction?
        double d=0;
        absEnemDispX += disp;
        for(int i = 0; i < enemies.size(); i++){
            if(enemies.get(i).getX() > 1.0 || enemies.get(i).getX() < 0.0){
                disp *=-1;
                direction *= -1;
                d-=0.02;
                break;
            }
        }
        for (int i = 0; i<enemies.size(); i++){
            double r = Math.random();
            if (r<0.002){
                Missile m = new Missile();
                m.setPos(enemies.get(i).getX(),enemies.get(i).getY()-0.1);
                m.setType(true);
                m.setDir(0,-0.005);
                //missiles.add(m);
                enemy_missiles.add(m);
            }
            //we lose if an enemy has made to to the ground
            if(enemies.get(i).getY()<0.1){
                res=0;
            }
            //move the enemy
            enemies.get(i).move(disp,d);
            //show the enemy
            enemies.get(i).print();
            //check if we hit the enemy
            boolean hit = enemies.get(i).isHit(missiles);
            if (hit ==true){
                StdAudio.play("Hit.wav");
                enemies.remove(i);
                score++;
            }
        }
        //UPDATE BUNKERS
        for (int i = 0; i < bunkers.size(); i++){
            bunkers.get(i).print();
            //check if we hit the bunker
            boolean hitShooter = bunkers.get(i).isHit(missiles);
            boolean hitEnemy = bunkers.get(i).isHit(enemy_missiles);
            if (hitShooter == true || hitEnemy == true){
                bunkers.remove(i);
            }
        }

        //UPDATE MISSILES
        for (int k =0; k<missiles.size();k++){
            missiles.get(k).update();
            missiles.get(k).print();
            if (missiles.get(k).getY()>0.9)
                missiles.remove(k); //is this ok?
        }
        for (int k =0; k<enemy_missiles.size();k++){
            enemy_missiles.get(k).update();
            enemy_missiles.get(k).print();
            if (enemy_missiles.get(k).getY()<0)
                enemy_missiles.remove(k); //is this ok?
        }

        //IF WE ARE SHOT
        if (player_shooter.isHit(enemy_missiles) && lives == 0){
            res=0;
        }
        //print the shooter
        player_shooter.print();
        //print the score, level, lives
        StdDraw.text(0.1,0.95,"Score: "+score);
        StdDraw.text(0.9, 0.95, "Level: "+ level);
        for (int i = 0; i < 3; i++){
            StdDraw.picture(0.445+i*0.055, 0.95, "heartEmpty.png");
        }
        for(int i = 0; i < lives; i ++){
            StdDraw.picture(0.445+i*0.055, 0.95, "heart.png");
        }
        //if we win!
        if (enemies.size()==0){res=2;}
        StdDraw.show(10);
        return res;
    }
}
