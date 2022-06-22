/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package DroneSimulator;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Random;
import javafx.scene.paint.Color;
import javax.swing.JFileChooser;

/**
 *
 * @author Dwayne
 */
public class DroneArena {

    private double arenaWidth, arenaHeight;
    private ArrayList<Pieces> droneInfo;//A array list of type drone that stores all the drones added to the arena
    private Random randomPos;//Used to generate random drone x and y position
    int numDronesinarena;//Keeps track of the number of drones in the arena
    double arenaDimension = arenaHeight * arenaWidth;

    /**
     * construct an arena of default size
     */
    DroneArena() {
        this(700, 500);
    }


    /* drone arena constructor
     *Intialises drone arena by assigning it attibutes 
     * @param arenaWidth
     * @param arenaHeight
     */
    public DroneArena(int arenaWidth, int arenaHeight) {
        this.arenaWidth = arenaWidth;
        this.arenaHeight = arenaHeight;
        randomPos = new Random();
        droneInfo = new ArrayList<Pieces>();
    }

    /*Adds drones to the arena unless the numbers of drones is greater then
     *the size of the arena
     *@param c
     */
    public void addDrone(char c) {
        int randX;
        int randY;
        randX = randomPos.nextInt((int) (arenaWidth));
        randY = randomPos.nextInt((int) (arenaHeight));
        if (c == 'd') {
            droneInfo.add(new Drone(randX, randY, 10, 25, 5));
        }
        if (c == 'o') {
            droneInfo.add(new Meteorite(randX, randY, 15));
        }
        if (c == 'h') {
            droneInfo.add(new Hunter(randX, randY, 15, 15, 2));
        }
        if (c == 'b') {
            droneInfo.add(new DroneMove(randX, randY, 10, 10, 2));
        }

    }

    /**
     * method to clear the arena
     */
    public void resetArena() {
        droneInfo.clear();		// clears the arena
    }

    /**
     * method to increase drone speed
     */
    public void increaseSpeed() {
        for (Pieces d : droneInfo) {
            if (d instanceof Drone && ((Drone) d).speed >= 1) {
                ((Drone) d).speed += 1;
            }
        }
    }

    /**
     * method to decrease drone speed
     */
    public void decreaseSpeed() {
        for (Pieces d : droneInfo) {
            if (d instanceof Drone && ((Drone) d).speed >= 1) {
                ((Drone) d).speed -= 1;
            }
        }
    }

    /*this method checks if the more drones can be added to the arena by checking if the 
    number of drones is smaller then the arena dimensions and if its less adds +1 to num of
    drones in arena and if its more returns error statement
     */
    public int Capacity() {
        if (numDronesinarena < arenaDimension) {
            numDronesinarena++;
        }
        return numDronesinarena;
    }

    /*
     *checks all pieces and adjustd the angle if needed
     */
    public void checkDrones() {
        ArrayList<Pieces> allItemsClone = (ArrayList<Pieces>) droneInfo.clone(); 	//This is used to solve an error caused by deleting items as the program runs. borderpane didnt like items being modified this way while the check is being run. this technique makes a clone list and works using that.
        for (Pieces I : allItemsClone) {
            I.checkDrone(this);
        }

    }

    /**
     * check if collision between drones
     *
     * @param drone Pieces
     * @return true if there is a collision
     */
    public boolean checkdronecollision(Pieces d) {
        boolean re = false;
        for (Pieces ai : droneInfo) {
            if (ai instanceof Hunter && ai.collisionDetection(d)) { //run collision detection on all drones
                re = true;
            }
        }
        return re;
    }


    /*arena width and height getter functions */
    public double getarenaWidth() {
        return arenaWidth;
    }

    public double getarenaHeight() {
        return arenaHeight;
    }

    /*arena width and height setter functions */
    public void setarenaWidth(int width) {
        this.arenaWidth = width;
    }

    public void setarenaHeight(int height) {
        this.arenaHeight = height;
    }

    /**
     * Checks for collision between Pieces and wall and adjusts angles
     * accordingly
     *
     * @param dx-x position
     * @param dy-y position
     * @param ag-radius of drone
     * @param angle-current angle
     * @param droneNum-current drone's number
     * @return ans which is the new angle
     */
    public double CheckDroneAngle(double dx, double dy, double ag, double angle, int droneNum) {
        double ans = angle;
        if (dx < ag || dx > arenaWidth - ag) {
            ans = 180 - ans;// collision on left or right side, mirror the direction of travel to opposite way
        }

        if (dy < ag || dy > arenaHeight - ag) {
            ans = -ans;// change drone direction when it hits the top or bottom of the arena
        }

        for (Pieces a : droneInfo) {
            if (a.getdroneNum() != droneNum && a.collisionDetection(dx, dy, ag)) {
                ans = 180 * Math.atan2(dy - a.getDy(), dx - a.getDx()) / Math.PI;
            }
        }
        // runs check on all drones for collision EXCEPT current drone (or else drone would get stuck)
        // if collision = true return angle between collisions

        return ans;		// return the angle
    }

    /*
     * adjustDrone
     * Used to adjust drone, gives new coords modified with angle
     */
    public void adjustDrones() {
        for (Pieces a : droneInfo) {
            a.adjustDrone();
        }
    }
    
    
        /*
	 * outputs the info of all the pieces like x and y coord and angle
	 * @return print
	 */
    public ArrayList<String> piecesInfo() {
        ArrayList<String> print = new ArrayList<String>(); //creates a array of type string to hold all the pieces info
        for (Pieces m : droneInfo) {
            print.add(m.toString());			// build array using info of all drones and obstacles
        }
        return print;												// return the built string

    }

    /*
     * draws the pieces onto the canvas
     * @param gc
     */
    public void drawArena(ConsoleCanvas m) {
        for (Pieces d : droneInfo) {
            if (d instanceof Meteorite) {
                m.setFillColour(Color.OLIVE);
                d.drawDrone(m);
            }
            if (d instanceof Drone) {
                m.setFillColour(Color.DARKORANGE);
                d.drawDrone(m);
            }
            if (d instanceof Hunter) {
                m.setFillColour(Color.BROWN);
                d.drawDrone(m);
            }
            if (d instanceof DroneMove) {
                m.setFillColour(Color.GREEN);
                d.drawDrone(m);
            }
        }

    }

    public void removeDrone(Pieces drone) {
        droneInfo.remove(drone);
    }

    /*
     *resets the arraylist
     */
    public void reset() {
        droneInfo.clear();
    }


    /* this savestate method is used to save the current arena droneInfo and position to a file that can later
    be loaded
     */
    void saveState() throws IOException { //*Selection*
        JFileChooser jFC = new JFileChooser("C:\\Users\\Derin\\eclipse-workspace");	//Set file directory
        int uSelect = jFC.showOpenDialog(null); //To receive user input
        if (uSelect == JFileChooser.APPROVE_OPTION) { //Approved when user has opened a file
            File userFile = jFC.getSelectedFile(); //jFC function to open the select file
            writeToFile(userFile); //Now write to file
        }
    }

    void writeToFile(File fToWrite) throws IOException { //*Saving*
        FileWriter fileWriter = new FileWriter(fToWrite); //File buffer object to write to save file
        BufferedWriter buffer = new BufferedWriter(fileWriter); //Need to use buffer buffer for multiple writing statements for efficiency
        buffer.write(Integer.toString((int) arenaWidth)); //Firstly write arena width and height to file
        buffer.write(","); //Separate by comma for distinction when reading
        buffer.write(Integer.toString((int) arenaHeight));
        buffer.newLine();

        for (Pieces d : droneInfo) {
            if (d instanceof Drone) {
                buffer.write(Integer.toString((int) d.getDx()));
                buffer.write(",");								//Reads X,Y,RAR
                buffer.write(Integer.toString((int) d.getDy()));
                buffer.write(",");
                buffer.write(Integer.toString((int) d.getag()));
                buffer.newLine();
            }
            if (d instanceof Meteorite) {
                buffer.write(Integer.toString((int) d.getDx()));
                buffer.write(",");								//Reads X,Y,RAR
                buffer.write(Integer.toString((int) d.getDy()));
                buffer.write("");
                buffer.write(Integer.toString((int) 0));
                buffer.newLine();
            }
        }
        buffer.close();
    }

    void loadState() throws IOException {
        JFileChooser jFC = new JFileChooser("C:\\Users\\Derin\\eclipse-workspace");
        int uSelect = jFC.showOpenDialog(null);
        if (uSelect == JFileChooser.APPROVE_OPTION) {
            File userFile = jFC.getSelectedFile();
            if (jFC.getSelectedFile().isFile()) {
                readFile(userFile);
            }
        }
    }

    void readFile(File fileToRead) throws IOException {
        String data = " ";
        FileReader fileReader = new FileReader(fileToRead);
        BufferedReader reader = new BufferedReader(fileReader);
        data = reader.readLine(); //Reads one character at time, stores in data
        String[] splitArgs = data.split(","); //Text is split into parameters by commas
        if (!droneInfo.isEmpty()) {
            droneInfo.clear(); //Clear existing drones, if there are any
        }

        while (data != null) {
            data = reader.readLine();
            String[] numbers = data.split(","); //Split X,Y,DIRECTION 
            int x = Integer.parseInt(numbers[0]); //Like arena, index 0 is x
            int y = Integer.parseInt(numbers[1]); //Index 1 is y
            int rad = Integer.parseInt(numbers[2]);
            droneInfo.add(new Drone(x, y, rad, 25, 5)); //With gathered data on drone, add it to list
        }
        reader.close();
    }

}
