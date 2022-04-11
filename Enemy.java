import java.io.File;
import java.util.ArrayList;
class Enemy extends DefaultCritter {
    String skin;
    public void print(){
        StdDraw.picture(x,y, skin, 0.06, 0.08);
    }
    public void setSkin(String skin) {
        this.skin = skin;
    }

    boolean isHit(ArrayList<Missile> m){
        boolean hit=false;
        for (int k =0; k<m.size();k++){
            double mx= m.get(k).getX();
            double my= m.get(k).getY();
            double dist = Math.sqrt(Math.pow(x-mx,2)+Math.pow(y-my,2));
            if (dist < 0.05 && m.get(k).getType()==false)
            {
                hit =true;
                System.out.println("Enemy hit!");
                m.remove(k);
            }
        }
        return hit;
    }



}