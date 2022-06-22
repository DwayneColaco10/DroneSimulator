/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package DroneSimulator;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Scanner;
import java.util.concurrent.TimeUnit;
import javafx.animation.AnimationTimer;
import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.canvas.Canvas;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.Menu;
import javafx.scene.control.MenuBar;
import javafx.scene.control.MenuItem;
import javafx.scene.effect.DropShadow;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import static javafx.scene.paint.Color.DARKGREEN;
import static javafx.scene.paint.Color.RED;
import javafx.scene.text.Font;
import javafx.scene.text.FontPosture;
import javafx.scene.text.FontWeight;
import javafx.scene.text.Text;
import javafx.stage.Stage;

/**
 *
 * @author Dwayne
 */
public class DroneInterface extends Application {
    
    Scene main, scene;

    private Scanner myScan;								// scanner used for input from user
    private DroneArena myArena;                                                 	// arena in which drones are shown
    private ConsoleCanvas gc;
    private AnimationTimer timer;
    private VBox Vpane;

    @Override
    public void start(Stage primaryStage) throws Exception {
        primaryStage.setTitle("(29009581) Drone Simulator 3000");
        
        // image for main screen
        Image image = new Image(getClass().getResourceAsStream("mainback.jpg"));
        ImageView view = new ImageView(image);
        
          DropShadow ds = new DropShadow();
        ds.setOffsetY(5.0f);
        ds.setColor(Color.color(0.8f, 0.1f, 0.9f));
        //title
        Text gameName = new Text("DRONE SIMULATOR");
        gameName.setEffect(ds);
        gameName.setFill(Color.RED);
        gameName.setCache(true);
        gameName.setFont(Font.font(null, FontWeight.BOLD, 60));
        
       
        //play button on menu screen
        Button playbtn = new Button("Play Drone Simulator");
        playbtn.setOnAction(e -> {

            primaryStage.setScene(scene);
            primaryStage.setTitle("Drone Simulator 3000");
        });
        playbtn.setStyle("-fx-base: #E94B3CFF;");

        //play button on menu screen
        Button exitbtn = new Button("Exit");
        exitbtn.setOnAction(e -> {

            System.exit(0);
            
        });
        exitbtn.setStyle("-fx-base: #E94B3CFF;");

        //display the buttons vertically
        StackPane mainStack = new StackPane();
        VBox mainScreen = new VBox(5);
        mainScreen.setSpacing(30);
        mainScreen.setAlignment(Pos.CENTER);
        mainScreen.getChildren().addAll(gameName, playbtn, exitbtn);
        mainStack.getChildren().addAll(view,mainScreen);
        main = new Scene(mainStack, 1050, 630);

 
        scene();

        primaryStage.setScene(main);
        primaryStage.show();
    }

    public void scene(){
         BorderPane bp = new BorderPane();
        bp.setPadding(new Insets(10, 20, 10, 20));
        bp.setTop(Menu());
        
        Text title = new Text();
        title.setText("The Arena");
        title.setX(270);
        title.setY(-10);
        title.setFont(Font.font("arial",FontWeight.BOLD, FontPosture.ITALIC,27));
        title.setStrokeWidth(1.25);
        title.setFill(RED);
        title.setStroke(DARKGREEN);
        

        Group root = new Group();										// create group with canvas
        Canvas canvas = new Canvas(700, 500);
        root.getChildren().add(canvas);
        bp.setLeft(root);												// sets canvas on left
        root.getChildren().add(title);
        gc = new ConsoleCanvas(canvas.getGraphicsContext2D(), 700, 500);

        setMouseEvents(canvas);											// sets stage mouse events

        myArena = new DroneArena(700, 500);								// defines arena
        updateCanvas(); // updates canvas

        timer = new AnimationTimer() {									// animation time will handle main stage loop
            public void handle(long currentNanoTime) {
                myArena.checkDrones();
                myArena.adjustDrones();								// move all drones
                updateCanvas();										// update
                updatedroneInfo();										// display drone and obstacle info
            }
        };
       
        

        Vpane = new VBox();
        Vpane.setAlignment(Pos.TOP_LEFT);
        Vpane.setPadding(new Insets(5, 75, 75, 5));
        bp.setRight(Vpane);
        bp.setBottom(setButtons());										// set botton panel up with buttons

        scene = new Scene(bp, 1050, 630);			// set up scene size
        bp.prefHeightProperty().bind(scene.heightProperty());
        bp.prefWidthProperty().bind(scene.widthProperty());

    }
    
    /**
     * load everything in
     *
     * @param args
     */
    public static void main(String[] args) {
        Application.launch(args);			// launch the GUI

    }
    
    
     /**
	  * set up the mouse event to allow user to click buttons
	  * @param canvas
	  */
	
	void setMouseEvents (Canvas canvas) {
	       canvas.addEventHandler(MouseEvent.MOUSE_PRESSED, // sets up event - user click
	    	       new EventHandler<MouseEvent>() {
	    	           @Override
	    	           public void handle(MouseEvent e) {
	  		            	updateCanvas();							
	  		            	updatedroneInfo();
	    	           }
	    	       });
	}
    
    
    private void seeAbout(){
            Alert about = new Alert(Alert.AlertType.INFORMATION);	// sets the titles for the sim menu
	    about.setTitle("About");									
	    about.setHeaderText(null); // and about menu
	    about.setContentText("drone simulator created my 29009581");			
	    about.showAndWait();	
    }
    
    MenuBar Menu() {
		MenuBar menu = new MenuBar();						// create menu
		Menu filestate = new Menu("File");							
		MenuItem savestate = new MenuItem("Save");					
		savestate.setOnAction(new EventHandler<ActionEvent>() {
		    public void handle(ActionEvent m) {
		    	try {
					myArena.saveState();				// calls savefile from area to save
				} catch (IOException a) {
					a.printStackTrace(); // else print stack error
				}											 	
		    }
		});
		MenuItem loadstate = new MenuItem("Load");
		loadstate.setOnAction(new EventHandler<ActionEvent>() {
		    public void handle(ActionEvent m) {
		    	try {
					myArena.loadState();				// same again but to load
				} catch (IOException a) {
					a.printStackTrace();
				}											 	
		    }
		});
		MenuItem exit = new MenuItem("Exit");					
		exit.setOnAction(new EventHandler<ActionEvent>() {
		    public void handle(ActionEvent m) {					
	        	timer.stop(); //stop animation timer									
		        System.exit(0);	//kill program							
		    }
		});
		filestate.getItems().addAll(savestate, loadstate, exit);	// add elements to menu
		Menu help = new Menu("Help");							
		MenuItem about = new MenuItem("About");				
		about.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent actionEvent) {
            	seeAbout();									
            }	
		});
		help.getItems().addAll(about);						
		menu.getMenus().addAll(filestate, help);				
		return menu;											
	}
    
    	public void updateCanvas() { // update the stage
	 	gc.clearCanvas();		
	 	myArena.drawArena(gc);
	}
	
	/**
	 * presents info sent by arena, such as drone loc and direction
	 */
	
	public void updatedroneInfo() {
		Vpane.getChildren().clear();				// right panel
		ArrayList<String> allDrones = myArena.piecesInfo();
		for (String s : allDrones) {
			Label l = new Label(s); 		// send info to label
			Vpane.getChildren().add(l);	// add label	
		}	
	}
    
        /**
	 * creates the panel that will contain and lay the buttons
	 * @return
	 */
	private HBox setButtons() {//horizontal allignment
	    Button startBtn = new Button("Start");	// when pressed, start sim				
	    startBtn.setStyle("-fx-base: #097969;"+"-fx-border-width:3px;");
            startBtn.setOnAction(new EventHandler<ActionEvent>() {	
	        @Override
	        public void handle(ActionEvent event) {
	        	timer.start();									// start animation timer (simulation starts)
	       }
	    });
            
	    Button stopBtn = new Button("Pause"); //pauses sim	
            stopBtn.setStyle("-fx-base: #ee2211;"+"-fx-border-width:3px;");
	    stopBtn.setOnAction(new EventHandler<ActionEvent>() {
	        @Override
	        public void handle(ActionEvent event) {
	           	timer.stop();	// if pressed, pause timer
	       }
	    });
	    
	    Button resetbtn = new Button("Clear sim");		
            resetbtn.setStyle("-fx-base: #FF5733;"+"-fx-border-width:3px;");
	    resetbtn.setOnAction(new EventHandler<ActionEvent>() {
	        @Override
	        public void handle(ActionEvent event) {
	        	timer.start(); 
	        	myArena.resetArena();		// resets all entities in the arena			
	       }
	    });
	    

	    Button addDroneBtn = new Button("Insert Drone");				// spawn a drone entity within the world
	    addDroneBtn.setStyle("-fx-base: #FFC300;"+"-fx-border-width:3px;");
            addDroneBtn.setOnAction(new EventHandler<ActionEvent>() {
	        @Override
	        public void handle(ActionEvent event) {
	           	myArena.addDrone('d');									// call add from arena
	           	updateCanvas();											// update state
	       }
	    });
	    
	    Button addMetBtn = new Button("Insert Meteorite");	
            
	    addMetBtn.setStyle("-fx-base: #808000;"+"-fx-border-width:3px;");

	    addMetBtn.setOnAction(new EventHandler<ActionEvent>() {
	        @Override
	        public void handle(ActionEvent event) {
	           	myArena.addDrone('o');									
	           	updateCanvas();											
	       }
	    });
            
            Button addHunBtn = new Button("Insert Hunter");	
            
	    addHunBtn.setStyle("-fx-base: #808000;"+"-fx-border-width:3px;");

	    addHunBtn.setOnAction(new EventHandler<ActionEvent>() {
	        @Override
	        public void handle(ActionEvent event) {
	           	myArena.addDrone('h');									
	           	updateCanvas();											
	       }
	    });
            
            Button addBotBtn = new Button("Insert Bird");	
            
	    addBotBtn.setStyle("-fx-base: #808000;"+"-fx-border-width:3px;");

	    addBotBtn.setOnAction(new EventHandler<ActionEvent>() {
	        @Override
	        public void handle(ActionEvent event) {
	           	myArena.addDrone('b');									
	           	updateCanvas();											
	       }
	    });

	    
	    Button upSpeedBtn = new Button("Increase Speed");					// calls arena method to increase speed	
	    	    upSpeedBtn.setStyle("-fx-base: #D10DD7;"+"-fx-border-width:3px;");

            upSpeedBtn.setOnAction(new EventHandler<ActionEvent>() {
	        @Override
	        public void handle(ActionEvent event) {
	           	myArena.increaseSpeed();										
	       }
	    });
	    
	    Button decSpeedBtn = new Button("Decrease Speed");						// button to add another obstacle
	    	    decSpeedBtn.setStyle("-fx-base: #D10DD7;"+"-fx-border-width:3px;");

            decSpeedBtn.setOnAction(new EventHandler<ActionEvent>() {
	        @Override
	        public void handle(ActionEvent event) {
	           	myArena.decreaseSpeed();										// redraws the arena to add it in
	       }
	    });
	    
	  
	    return new HBox(startBtn, stopBtn, resetbtn, new Label("  "), addDroneBtn, addMetBtn, addHunBtn,addBotBtn, new Label("  "), decSpeedBtn, upSpeedBtn); //set the buttons
	}

        
}


