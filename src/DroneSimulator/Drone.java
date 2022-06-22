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
public class Drone extends Pieces {

    double speed, angle;//speed and angle varaible for drone

    /* Drone constructor for drone, 
     * size=dsize, x=dx, y=dy, angle=dAngle, speed=speed
     * @param dx
     * @param dy
     * @param dsize
     * @param dAngle
     * @param speed
     */
    public Drone(double dx, double dy, double dsize, double dAngle, double speed) {
        super(dx, dy, dsize);
        this.angle = dAngle;
        this.speed = speed;

    }

    /**
     * getStrType return string describing Pieces type which is drone
     */
    public String getStrType() {
        return "Drone";
    }

    /**
     * toString Returns a string of the drone info
     */
    public String toString() {
        return getStrType() + droneNum + " is at " + Math.round(dx) + ", " + Math.round(dy) + " with speed " + Math.round(speed)
                + "\n traveling at angle" + Math.round(angle);
    }

    /**
     * checkDrone changes angle if there is a collision
     *
     * @param d
     */
    protected void checkDrone(DroneArena d) {
        angle = d.CheckDroneAngle(dx, dy, ag, angle, droneNum);
    }

    /**
     * adjustDrone
     * Used to adjust drone, gives new coords modified with angle
     */
    protected void adjustDrone() {
        double newDir = angle * Math.PI / 180;//angle in radians
        //since its sin and cos of the same radian the values differ by 90 degrees
        dx = dx + speed * Math.cos(newDir);
        dy = dy + speed * Math.sin(newDir);
    }

}
