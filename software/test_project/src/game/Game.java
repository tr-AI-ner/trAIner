package game;

import java.awt.Color;
import java.util.ArrayList;

import custom_objects.Avatar;
import custom_objects.Entity;
import functionality.Clock;
import functionality.Constants;
import functionality.GraphicsManager;
import functionality.InputManager;
import functionality.Setup;
import map_builder.*;

public class Game {
	
	private GraphicsManager graphicsManager;
	private InputManager inputManager;
	private Clock clock; // Clock (time manager)
	Setup setup;
	Map map;
	
	Avatar avatar;
	ArrayList<Entity> entities = new ArrayList<>();
	ArrayList<MapElement> mapElements;
	ElementWall theGreatWall;

	ElementPlasmaBall ball;
    ElementPlasmaBall ball2;
    ElementPlasmaBall ball3;
    ElementPlasmaBall ball4;
    ElementPlasmaBall ball5;
    ElementEnemy theEnemy;
	ElementBlackHole blackHole;
    ElementBlackHole blackHole2;

	public Game(GraphicsManager gm){
		this.graphicsManager = gm;
		this.clock = new Clock(); // Initialize clock
		this.inputManager = gm.getInputManager();
		this.setup = gm.getSetup();
		this.map = gm.getMap();
		
		entities = new ArrayList<>();
		
		avatar = new Avatar(Constants.AVATAR_START_X,Constants.AVATAR_START_Y,
				Constants.AVATAR_WIDTH, Constants.AVATAR_HEIGHT,
				Constants.COLOR_AVATAR_RED
				//r, g, b
//				new Color(255, 0, 0)
				);
		avatar.setSetup(setup);
		avatar.setGame(this);
		// add avatar to entities
		entities.add(avatar);
		
		mapElements = new ArrayList<>();
		
//		theGreatWall = new ElementWall(100, 100, 50, 20, new Color(0, 255, 0));
		theGreatWall = new ElementWall(15, 15, new Color(0,255,0));
		ball = new ElementPlasmaBall(10,10,Constants.COLOR_PLASMA_BALL);
        ball2 = new ElementPlasmaBall(4,14,Constants.COLOR_PLASMA_BALL);
        ball3 = new ElementPlasmaBall(36,22,Constants.COLOR_PLASMA_BALL);
        ball4 = new ElementPlasmaBall(17,12,Constants.COLOR_PLASMA_BALL);
        ball5 = new ElementPlasmaBall(50,32,Constants.COLOR_PLASMA_BALL);
        theEnemy = new ElementEnemy(30, 30, Constants.COLOR_ENEMY);
        blackHole = new ElementBlackHole(0,0,Constants.COLOR_BLACK_HOLE);
        blackHole2 = new ElementBlackHole(Constants.WINDOW_MAP_WIDTH /Constants.MAP_ELEMENT_SIZE-2,Constants.WINDOW_MAP_HEIGHT /Constants.MAP_ELEMENT_SIZE-2,Constants.COLOR_BLACK_HOLE);
        blackHole.setAttachedBlackHole(blackHole2);
        blackHole2.setAttachedBlackHole(blackHole);
		mapElements.add(theGreatWall);
		mapElements.add(ball);
        mapElements.add(ball2);
        mapElements.add(ball3);
        mapElements.add(ball4);
        mapElements.add(ball5);
        mapElements.add(theEnemy);
        mapElements.add(blackHole);
        mapElements.add(blackHole2);
		// add all map-elements to entities
		entities.addAll(mapElements);
	}
	
	public void run(){
		while(true)
			this.gameLoop();
	}
	
	// Main game loop
	private void gameLoop(){
		if(this.clock.frameShouldChange()) { // if fps right (ifnot, sleep, or do nothing), and update clock
			this.processUserInput(); // Process user input		
			this.updateState(); // Update state
			this.redrawAll();//graphicsManager); // Redraw everything
		}
	}

	// process User Input
	private void processUserInput() {
		//moves in the desired direction
		if(inputManager.getKeyResult()[0]) {avatar.move(0, ( -setup.getNewEntitySpeed() ));}
		if(inputManager.getKeyResult()[1]) {avatar.move(0, ( +setup.getNewEntitySpeed() ));}
		if(inputManager.getKeyResult()[2]) {avatar.move(( -setup.getNewEntitySpeed() ), 0);}
		if(inputManager.getKeyResult()[3]) {avatar.move(( +setup.getNewEntitySpeed() ), 0);}
		//Exits when escape is pressed
		if(inputManager.getKeyResult()[4]) {System.exit(0);}
		
		//check for mouse dragging
//		if(inputManager.getIsMousePressed() && inputManager.getIsMouseDragged()){
//			System.out.println("mouse pressed at x: "+inputManager.getMousePressedX()+", y: "
//					+inputManager.getMousePressedY());
//		}
		if(inputManager.getIsMousePressed() && inputManager.getIsMouseDragged()){
			//TODO: change this
			int width = mapElements.get(0).getWidth();
			int height = mapElements.get(0).getHeight();
			mapElements.get(0).move(inputManager.getMouseDraggedX() - (width / 2), 
					inputManager.getMouseDraggedY() - (height / 2));
			
			//look if mouse clicked on a map-element & on which
//			int index = -1;
//			
//			int counter = 0;
//			for (MapElement e: mapElements){
//				if (e.mousePressedInRange(inputManager.getMousePressedX(), inputManager.getMousePressedY())){
//					index = counter;
//					break;
//				}
//				counter++;
//			}
//			
//			// if index was found, move the element 
//			if (index >= 0){
//				System.out.println("mouse dragged at x: "+inputManager.getMouseDraggedX()+", y: "
//					+inputManager.getMouseDraggedY());
//			mapElements.get(index).move(inputManager.getMouseDraggedX(), inputManager.getMouseDraggedY());
//			}
		}
	}
	
	private void updateState(){
		if(Main.MODE == 0 || Main.MODE == 2) {
            ball.update();
            ball2.update();
            ball3.update();
            ball4.update();
            ball5.update();
            theEnemy.updateEnemy();
        }
        map.updateEntitiesInMap(entities);
	}
	
	private void redrawAll(){
		//clear full window
		graphicsManager.clear();
		
		// draw all entities
//		graphicsManager.draw(entities);
		graphicsManager.drawMap(entities);
		
		//swap buffers to make changes visible
		graphicsManager.redraw();
	}
	
	
	public ArrayList<MapElement> getMapElements(){
		return mapElements;
	}
	
}
