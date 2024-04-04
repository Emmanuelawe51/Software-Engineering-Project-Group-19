package com.example.sweproject;


public class Coordinate {
    private double x;
    private double y;
    //0 is for no deflection
    //1 is for 120 degree deflections
    //2 is for 60 degree deflections
    //3 is for 180 degree deflections
    private int deflectionType = 0;
    //this variable hold the value of where the hexagon is relative to an atom
    //holds the direction of the point relative to the atom
    private Arrow.Direction pointOfAreaOfInfluence1;
    private Arrow.Direction pointOfAreaOfInfluence2;
    public void setPointOfAreaOfInfluence(Arrow.Direction direction) {
        if(deflectionType == 0)
        {
            pointOfAreaOfInfluence1 = direction;
            deflectionType++;
        } else if (deflectionType == 1) {
            pointOfAreaOfInfluence2 = direction;
            deflectionType++;
            if(!(direction.iterate(1) == pointOfAreaOfInfluence1 || direction.iterate(5) == pointOfAreaOfInfluence1))
                deflectionType = 3;
        } else {
            deflectionType = 3;
        }


    }

    public Arrow.Direction getPointOfAreaOfInfluence1() {
        return pointOfAreaOfInfluence1;
    }
    public Arrow.Direction getPointOfAreaOfInfluence2() {
        return pointOfAreaOfInfluence2;
    }
    public int getDeflectionType(){
        return deflectionType;
    }

    public Coordinate(double x, double y) {
        this.x = x;
        this.y = y - 45;
    }


    public double getX() {
        return x;
    }

    public void setX(double x) {
        this.x = x;
    }

    public double getY() {
        return y;
    }

    public void setY(double y) {
        this.y = y;
    }
    @Override
    public String toString() {
        return "(" + x + ", " + y + ")";
    }
}