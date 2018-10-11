package game;

import functionality.Clock;
import functionality.GraphicsManager;

public class Game {
	
	private GraphicsManager graphicsManager;
	private Clock clock; // Clock (time manager)
	
	public Game(GraphicsManager gm){
		this.graphicsManager = gm;
		this.clock = new Clock(); // Initialize clock
	}
	
	public void run(){
		while(true)
			this.gameLoop();
	}
	
	// Main game loop
	private void gameLoop(){
		if(this.clock.frameShouldChange()) { // if fps right (ifnot, sleep, or do nothing), and update clock
			this.processUserInput();//inputSystem); // Process user input		
			this.updateState(); // Update state
			this.redrawAll();//graphicsManager); // Redraw everything
		}
	}

	// process User Input
	private void processUserInput(/*InputSystem is*/) {

	}
	
	private void updateState(){
		
	}
	
	private void redrawAll(){
		
	}
	
	
	
	
	
	
}
