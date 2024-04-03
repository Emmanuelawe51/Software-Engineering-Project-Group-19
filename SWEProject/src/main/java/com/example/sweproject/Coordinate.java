package com.example.sweproject;


public class Coordinate {
    private double x;
    private double y;
    private int noOfAreaOfInfluence = 0;
    //this variable hold the value of where the hexagon is relative to an atom
    //holds the direction of the point relative to the atom
    private Arrow.Direction pointOfAreaOfInfluence;
    public void setPointOfAreaOfInfluence(Arrow.Direction direction) {
        this.pointOfAreaOfInfluence = direction;
        this.noOfAreaOfInfluence++;


    }

    public Arrow.Direction getPointOfAreaOfInfluence() {
        return pointOfAreaOfInfluence;
    }
    public int getNoOfAreaOfInfluence(){
        return noOfAreaOfInfluence;
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
