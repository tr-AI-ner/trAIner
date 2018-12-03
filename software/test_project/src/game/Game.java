package game;

import java.util.ArrayList;

import custom_objects.Avatar;
import custom_objects.Entity;
import functionality.Clock;
import functionality.Constants;
import functionality.GraphicsManager;
import functionality.InputManager;
import functionality.Setup;
import map_builder.*;
import map_saver.MapSaverLoader;

public class Game {

	private GraphicsManager graphicsManager;
	private InputManager inputManager;
	private Clock clock; // Clock (time manager)
	Setup setup;
	private Map map;
	
	Avatar avatar;
	ArrayList<Entity> entities = new ArrayList<>();
	ArrayList<MapElement> mapElements;
	ElementWall theGreatWall;
	ElementBall enemy;
	MapSaverLoader mapSaverLoader;


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

		mapSaverLoader = new MapSaverLoader(this);

//		theGreatWall = new ElementWall(100, 100, 50, 20, new Color(0, 255, 0));
		theGreatWall = new ElementWall(15, 15, Constants.COLOR_WALL);
		enemy = new ElementBall(6,6,Constants.COLOR_PLASMA_BALL);
		mapElements.add(enemy);
		mapElements.add(theGreatWall);
		// add all map-elements to entities
		entities.addAll(mapElements);
	//	saveMap("map_03");
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
		if (inputManager.isMouseClicked()  && graphicsManager.getTopBar().getSaveButton().contains(inputManager.getMouseClickedX(), inputManager.getMouseClickedY()) ) {
             mapSaverLoader.saveButtonLogic();
		}
        if (inputManager.isMouseClicked() && graphicsManager.getTopBar().getLoadButton().contains(inputManager.getMouseClickedX(), inputManager.getMouseClickedY()) ) {
				mapSaverLoader.loadButtonLogic();
            }
	}


	private void updateState(){
		
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


	public ArrayList<MapElement> getMapElements(){return mapElements;}
	public void setMapElements(ArrayList<MapElement> mapElements){this.mapElements=mapElements;}
	public Avatar getAvatar(){return avatar;}
	public ArrayList<Entity> getEntities(){return entities;}
    public Map getMap() { return map; }
    public GraphicsManager getGraphicsManager(){return graphicsManager;}


}