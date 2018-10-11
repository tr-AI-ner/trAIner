package game;

import functionality.GraphicsManager;

public class Main {

	private Game game = null;

	public static void main(String[] args) {
		new Main();
	}

	public Main() {
		
		GraphicsManager gm = new GraphicsManager(); //create the panel
		game = new Game(gm); // create the game
			
//		game.init(gm, gm.getInputSystem()); // Initialize all variables in game, including the panel
		
		game.run();	// Run game :)
	}
	
}
