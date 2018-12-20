package game;

import functionality.GraphicsManager;
import functionality.InputManager;
import map_builder.Map;


public class Main {

	public static int MODE = 0; //0 = Player_Game , 1 = MapBuilder, 2 = Preview mode, 3 = AI_Game, 4 = Challenge mode
	private Game game = null;

	public static void main(String[] args) {
		new Main();
	}

	public Main() {
		
		InputManager inputManager = new InputManager();
		Map map = new Map();
		GraphicsManager gm = new GraphicsManager(inputManager, map); //create the panel

		game = new Game(gm); // create the game 
		game.run();	// run game 
	}
	
}
