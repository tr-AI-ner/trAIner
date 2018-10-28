package game;

import java.awt.Color;
import java.util.ArrayList;

import custom_objects.Avatar;
import custom_objects.Entity;
import functionality.Clock;
import functionality.GraphicsManager;
import functionality.InputManager;
import functionality.Setup;
import map_builder.ElementWall;
import map_builder.MapElement;

public class Game {
	
	private GraphicsManager graphicsManager;
	private InputManager inputManager;
	private Clock clock; // Clock (time manager)
	Setup setup;
	
	Avatar avatar;
	ArrayList<Entity> entities = new ArrayList<>();
	ArrayList<MapElement> mapElements;
	ElementWall theGreatWall;
	final int avatarWidht=20, avatarHeight=20;
	
	public Game(GraphicsManager gm){
		this.graphicsManager = gm;
		this.clock = new Clock(); // Initialize clock
		this.inputManager = gm.getInputManager();
		this.setup = gm.getSetup();
		
		entities = new ArrayList<>();
		
		avatar = new Avatar(
				((setup.getFrameWidth() - (avatarWidht/2)) / 2), 
				((setup.getFrameHeight() - (avatarHeight/2)) / 2),
				avatarWidht, avatarHeight,
				//r, g, b
				new Color(255, 0, 0)
				);
		avatar.setSetup(setup);
		avatar.setGame(this);
		// add avatar to entities
		entities.add(avatar);
		
		mapElements = new ArrayList<>();
		
		theGreatWall = new ElementWall(50, 50, 50, 20, new Color(0, 255, 0));
		mapElements.add(theGreatWall);
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
		
	}
	
	private void redrawAll(){
		//clear full window
		graphicsManager.clear();
		
		// draw all entities
		graphicsManager.draw(entities);
		
		//swap buffers to make changes visible
		graphicsManager.redraw();
	}
	
	
	public ArrayList<MapElement> getMapElements(){
		return mapElements;
	}
	
}
