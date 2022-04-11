class Missile extends DefaultCritter {

    double dirx=0;
    double diry=0.01;
    boolean enemy_missile=false;
    public static void main(String[] args) {
    }

    public void print(){
        StdDraw.picture(x,y,"enemymissile.png");
    }

    public void setDir(double dx,double dy){
        dirx=dx;
        diry=dy;
    }

    public void update(){
        y+=diry;
        x+=dirx;
    }

    public double getY(){
        return y;
    }

    public double getX(){
        return x;
    }
    public void setType(boolean b){
        enemy_missile=b;
    }
    public boolean getType(){
        return enemy_missile;
    }





}