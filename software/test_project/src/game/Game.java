package game;

import java.awt.Color;

import custom_objects.Avatar;
import custom_objects.Entity;
import functionality.Clock;
import functionality.GraphicsManager;
import functionality.InputManager;
import functionality.Setup;

public class Game {
	
	private GraphicsManager graphicsManager;
	private InputManager inputManager;
	private Clock clock; // Clock (time manager)
	Setup setup;
	
	Avatar avatar;
	final int avatarWidht=20, avatarHeight=20;
	
	public Game(GraphicsManager gm){
		this.graphicsManager = gm;
		this.clock = new Clock(); // Initialize clock
		this.inputManager = gm.getInputManager();
		this.setup = gm.getSetup();
		
		avatar = new Avatar(
				((setup.getFrameWidth() - (avatarWidht/2)) / 2), 
				((setup.getFrameHeight() - (avatarHeight/2)) / 2),
				avatarWidht, avatarHeight,
				//r, g, b
				new Color(255, 0, 0)
				);
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
	}
	
	private void updateState(){
		
	}
	
	private void redrawAll(){
		graphicsManager.draw(avatar);
	}
	
	
	
	
	
	
}
