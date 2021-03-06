package game;

import functionality.GraphicsManager;
import functionality.InputManager;
import functionality.Constants;
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
		game.run();	// Run game :)

	}
	
}
