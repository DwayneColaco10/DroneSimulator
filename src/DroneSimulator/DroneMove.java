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
public class DroneMove extends Drone{
    
    public DroneMove(double dx, double dy, double dsize, double dAngle, double speed) {
        super(dx, dy, dsize, dAngle, speed);
    }
    
	protected void checkDrones(DroneArena da) {

		if  (angle < 10 && dx > da.getarenaWidth() - ag*2) angle = 90; 
		else if (angle > 80 & angle < 100 && dy > da.getarenaHeight()-ag*2) ag = 180;
		else if (angle > 170 && angle < 190 && dx < ag*2) angle = 270;
		else if (angle > 260 && dy < ag*2) angle = 0;
	
		angle = da.CheckDroneAngle(dx, dy, ag, angle, droneNum);
		if (da.checkdronecollision(this)) da.removeDrone(this);
		
		angle = 90*Math.floor(angle/90);
	}
        
    /**
     *
     * @return
     */
    @Override
	public String getStrType() {
		return "Bird ";
	}

    
}
