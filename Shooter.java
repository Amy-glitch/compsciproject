import java.util.ArrayList;
class Shooter extends DefaultCritter {
    public static void main(String[] args) {
    }

    public boolean isHit(ArrayList<Missile> m){
        boolean hit=false;
        for (int k =0; k<m.size();k++){
            double mx= m.get(k).getX();
            double my= m.get(k).getY();
            double dist = Math.sqrt(Math.pow(x-mx,2)+Math.pow(y-my,2));
            if (dist < 0.05)
            {
                hit =true;
            }
        }
        return hit;
    }

    public void print(){
        //System.out.println(x);
        StdDraw.filledSquare(x,y,0.05);
    }



}