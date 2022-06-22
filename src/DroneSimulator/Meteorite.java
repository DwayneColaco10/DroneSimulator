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
public class Meteorite extends Pieces{


	public Meteorite(double ix, double iy, double ir) {
		super(ix, iy, ir);
	}


	@Override
	protected void checkDrone(DroneArena da) { // override to provide new implementation of existing method

	}


	@Override
	protected void adjustDrone() {	
	}
	@Override
	protected String getStrType() {
		return "Meteorite";
	}

}
