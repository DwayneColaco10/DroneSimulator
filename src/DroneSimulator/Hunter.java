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
public class Hunter extends Drone {
    
    public Hunter(double dx, double dy, int dsize, int dAngle, int speed) {
        super(dx, dy, dsize, dAngle, speed);
        //col = 'b';
    }
    
    /**
     * check for collision between the hunter and wall
     */
    protected void checkPieces(DroneArena a){
     ag = a.CheckDroneAngle(dx, dy, ag, angle, droneNum);
    }
    /**
     * The displayed name for this item
     */
    @Override
	public String getStrType() {
		return "Drone Hunter ";
	}

    
    
}
