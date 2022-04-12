import java.util.ArrayList;

public class Bunker extends DefaultCritter {
    public void print(){
        StdDraw.picture(x,y,"bunker.png", 0.021, 0.021);
    }
    boolean isHit(ArrayList<Missile> m){
        boolean hit=false;
        for (int k =0; k<m.size();k++){
            double mx= m.get(k).getX();
            double my= m.get(k).getY();
            double dist = Math.sqrt(Math.pow(x-mx,2)+Math.pow(y-my,2));
            if (dist < 0.0125)
            {
                hit =true;
                System.out.println("Bunker hit!");
                m.remove(k);
            }
        }
        return hit;
    }
}
