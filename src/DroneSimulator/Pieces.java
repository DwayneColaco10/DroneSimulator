/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package DroneSimulator;

/**
 *
 * @author Dwayne
 */
public abstract class Pieces {

    protected double dx, dy, ag;
    protected int droneNum;
    private static int dronecounter = 0;

    Pieces() {
        this(50, 50, 25);
    }
    
    /* Pieces constructor for pieces, 
     * x=dx, y=dy, angle=ag
     * @param dx
     * @param dy
     * @param dr
     */
    public Pieces(double dx, double dy, double dr) {
        this.dx = dx;
        this.dy = dy;
        this.ag = dr;
        droneNum = dronecounter++;
    }

    /**
     * return x position
     *
     * @return
     */
    public double getDx() {
        return dx;
    }

    /**
     * return y position
     *
     * @return
     */
    public double getDy() {
        return dy;
    }

    /*getter method for radius of drone*/
    public double getag() {
        return ag;
    }

    /**
     * setter function for pieces at given pos
     *
     * @param x
     * @param y
     */
    public void setPos(double x, double y) {
        dx = x;
        dy = y;
    }

    /**
     * returns the drone's number in list
     *
     * @return
     */
    public int getdroneNum() {
        return droneNum;
    }

    /**
     * displays the pieces nnto the canvas
     *
     * @param mc
     */
    public void drawDrone(ConsoleCanvas mc) {
        mc.showIt(dx, dy, ag);
    }

    protected String getStrType() {
        return "Pieces";
    }

    /**
     * return string with pieces information
     */
    public String toString() {
        return getStrType() + " at " + Math.round(dx) + ", " + Math.round(dy);
    }

    /**
     * abstract method for checking if a drone is in arena d
     *
     * @param d
     */
    protected abstract void checkDrone(DroneArena d);

    /**
     * abstract method for adjusting a drone
     */
    protected abstract void adjustDrone();

    /**
     * Checks for collisions between pieces and wall
     *
     * @param px
     * @param py
     * @param pr
     * @return true if there is a collision
     */
    public boolean collisionDetection(double px, double py, double pr) {
        if ((px - dx) * (px - dx) + (py - dy) * (py - dy) < (pr + (2 * ag)) * (pr + (2 * ag))) { // collision is detected if distance between drone and ox, oy is less than ag
            return true;//there is a collision so return true
        } else {
            return false;//no collision so return false
        }

    }

    /**
     * checks for collision between two drones
     *
     * @param Drone
     * @return true if there is a collision collision
     */
    public boolean collisionDetection(Pieces Drone) {
        return collisionDetection(Drone.getDx(), Drone.getDy(), Drone.getag());
    }


}
