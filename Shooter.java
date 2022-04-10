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
            if (dist < 0.05)
            {
                hit =true;
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
        //System.out.println(x);
        StdDraw.picture(x,y+0.05,"Shooter.png", 0.06, 0.08);
      //  StdDraw.setPenRadius(20);
        StdDraw.setPenRadius(0.01);
        StdDraw.line(x,0.05,x+Math.cos(angle)/20.,0.05+Math.sin(angle)/20.);

        StdDraw.setPenRadius(0.002);
    }



}