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
    private Arrow.Direction pointOfAreaOfInfluence;
    public void setPointOfAreaOfInfluence(Arrow.Direction direction) {
        this.pointOfAreaOfInfluence = direction;
        this.deflectionType++;


    }

    public Arrow.Direction getPointOfAreaOfInfluence() {
        return pointOfAreaOfInfluence;
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
