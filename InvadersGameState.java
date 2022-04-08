import java.util.ArrayList;
public class InvadersGameState {
    Shooter player_shooter;
    int score;
    ArrayList<Missile> missiles;
    ArrayList<Missile> enemy_missiles;
    ArrayList<Enemy> enemies;
    double cooldown;
    double disp;
    double absEnemDispX;

    public static void main(String[] args) {}

    //intialises a new game
    public  void Init(){
        absEnemDispX=0;
        player_shooter =  new Shooter();
        enemies = new ArrayList<Enemy>();
        cooldown = 0;
        disp=0.0025;
        score=0;
        missiles = new ArrayList<Missile>();
        enemy_missiles = new ArrayList<Missile>();
        for (int i = 0; i<6; i++){
            Enemy e =new Enemy();
            e.setPos(0.1+i/10.0,0.8);
            enemies.add(e);
        }
        for (int i = 0; i<6; i++){
            Enemy e =new Enemy();
            e.setPos(0.1+i/10.0,0.7);
            enemies.add(e);
        }
    }

    //this handles the main loop of the game run each iteration
    public int GameLoop(){
        int res=1;
        boolean alive = true;
        StdDraw.clear();
        //decrement the cooldown
        if (cooldown >0){cooldown-=1;}
        //check for movement
        if(StdDraw.isKeyPressed(65)) {
            player_shooter.move(-0.01, 0);
        }
        if(StdDraw.isKeyPressed(68)) {
            player_shooter.move(0.01, 0);
        }
        //check for shooting a missile
        if(StdDraw.isKeyPressed(32) && cooldown==0) {
            Missile m = new Missile();
            m.setPos(player_shooter.x, 0.1);
            missiles.add(m);
            cooldown=35;
        }

        //UPDATE THE ENEMIES
        //when should they move other direction?
        double d=0;
        absEnemDispX += disp;
        if(absEnemDispX > 0.3 || absEnemDispX < 0.0){
            disp *=-1;
            d=-0.01; //for downward movement
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
                enemies.remove(i);
                score++;
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
        if (player_shooter.isHit(enemy_missiles)){
            res=0;
        }
        //print the shooter
        player_shooter.print();
        //print the score
        StdDraw.text(0.1,0.95,"Score: "+score);
        //if we win!
        if (enemies.size()==0){res=2;}
        StdDraw.show(10);
        return res;
    }
}
