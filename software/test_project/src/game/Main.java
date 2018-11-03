package game;

import functionality.GraphicsManager;
import functionality.InputManager;
import map_builder.Map;

public class Main {

	private Game game = null;

	public static void main(String[] args) {
		new Main();
	}

	public Main() {
		
		InputManager inputManager = new InputManager();
		Map map = new Map();
		GraphicsManager gm = new GraphicsManager(inputManager, map); //create the panel
		game = new Game(gm); // create the game
			
//		game.init(gm, gm.getInputSystem()); // Initialize all variables in game, including the panel
		
		game.run();	// Run game :)
	}
	
}
