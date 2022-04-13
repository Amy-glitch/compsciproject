import java.util.ArrayList;
class Shooter extends DefaultCritter {
    public double angle;
    public static void main(String[] args) {
    }

    public Shooter(){
        angle = 1.0;
    }

    public boolean isHit(ArrayList<Missile> m){
        boolean hit=false;
        for (int k =0; k<m.size();k++){
            double mx= m.get(k).getX();
            double my= m.get(k).getY();
            double dist = Math.sqrt(Math.pow(x-mx,2)+Math.pow(y-my,2));
            if (dist < 0.06)
            {
                m.remove(k);
                hit =true;
                InvadersGameState.lives--;
            }
        }
        return hit;
    }

    public void setAngle(double a){
        angle =a;
    }

    public double getAngle(){
        return  angle;
    }


    public void print(){
        StdDraw.picture(x,y+0.05,"Shooter.png", 0.06, 0.08, angle*57.296-90);
    }



}