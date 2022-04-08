class DefaultCritter implements Critter {

    double x=0.2;
    double y=0.0;

    public void move(double px, double py){
        System.out.println("has moved");
        x +=px;
        y+=py;
    }

    public void setPos(double px, double py){
        x=px;
        y=py;
    }

    public double getX(){
        return x;
    }

    public double getY(){
        return y;
    }

}