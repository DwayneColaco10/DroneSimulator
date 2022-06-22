/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package DroneSimulator;

import javafx.geometry.VPos;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.paint.Color;
import javafx.scene.shape.ArcType;
import javafx.scene.text.TextAlignment;

/**
 *
 * @author Dwayne
 */
public class ConsoleCanvas {

    private int cx;
    private int cy;
    GraphicsContext gc;

    /*  Console canvas constructor
     *Intialises a canvas for the arena by assigning it attibutes  
     *The user inputs the arena size in drone interface, the borders are set to '#'
      and the empty space are made by being set to ' ' so the drones can move
     * @param g
     * @param x
     * @param y
     */
    public ConsoleCanvas(GraphicsContext g, int x, int y) {
        cx = x;//width of canvas
        cy = y;//height of canvas
        gc = g;

    }

    /**
     * return canvas width in x direction
     *
     * @return
     */
    public int getcanvasWidth() {
        return cx;
    }

    /**
     * return canvas height in y direction
     *
     * @return
     */
    public int getcanvasHeight() {
        return cy;
    }

    /*
     * clear the canvas
     */
    public void clearCanvas() {
        gc.setFill(Color.CORNFLOWERBLUE); //set colour to Bcornflowerblue

        gc.fillRect(0, 0, cx, cy);        // clear canvas
    }

    /*Draws the drone as a circle at the x and y position
    * @param x
    * @param y
    * @param ag
     */
    public void showIt(double dx, double dy, double ag) {
        gc.fillArc(dx - ag, dy - ag, ag * 2, ag * 2, 0, 360, ArcType.ROUND);

    }

    /*This method prints the text for the coordinate
     * @param x
     * @param y
     * @param m    
     */
    public void printLocation(double x, double y, String m) {
        gc.setTextAlign(TextAlignment.CENTER);
        gc.setTextBaseline(VPos.CENTER);
        gc.setFill(Color.AQUA);
    }

    /**
     * converts the integer as a string to print the loaction
     *
     * @param x
     * @param y
     * @param i
     */
    public void showCoord(double x, double y, int i) {
        printLocation(x, y, Integer.toString(i));
    }

    public void setFillColour(Color c) {
        gc.setFill(c);
    }

}
