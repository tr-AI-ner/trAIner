package game;

import functionality.GraphicsManager;
import functionality.InputManager;
import functionality.Constants;
import map_builder.Map;


public class Main {

    /**
     * Modes:
     *     -1: None
     *      0: Player Game
     *      1: Map Builder
     *      2: Preview Mode
     *      3: AI Game
     *      4: Challenge Mode
     *      5: Menu
     *      6: Help Screen
     *      7: Exit Screen
     */
	public static int MODE = Constants.MODE_MENU; 
    public static int PREVIOUS_MODE = Constants.MODE_NONE;
    public static int NEXT_MODE = Constants.MODE_NONE;

    private Game game = null;

	public static void main(String[] args) {
		new Main();
	}

	public Main() {
		
		InputManager inputManager = new InputManager();
		Map map = new Map();
		GraphicsManager gm = new GraphicsManager(inputManager, map); //create the panel
		game = new Game(gm); // create the game
		//game = new Game(gm,true); // create the game
			
//		game.init(gm, gm.getInputSystem()); // Initialize all variables in game, including the panel
		
		game.run(true);	// Run game :)

	}
	
}
