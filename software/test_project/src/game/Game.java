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

public class Game {
	
	private GraphicsManager graphicsManager;
	private InputManager inputManager;
	private Clock clock; // Clock (time manager)
	Setup setup;
	Map map;
	
	Avatar avatar;
	ArrayList<Entity> entities;
	ArrayList<MapElement> mapElements;
	ElementWall theGreatWall;

	ElementPlasmaBall ball;
    ElementEnemy theEnemy;
	ElementBlackHole blackHole;
    ElementBlackHole blackHole2;
	ElementStart start;
	ElementFinish finish;

	public Game(GraphicsManager gm){
		this.graphicsManager = gm;
		this.clock = new Clock(); // Initialize clock
		this.inputManager = gm.getInputManager();
		this.setup = gm.getSetup();
		this.map = gm.getMap();
		
		entities = new ArrayList<>();

		//TODO set the avatar to appear on the start block
		avatar = new Avatar(33 * Constants.MAP_ELEMENT_SIZE,33 * Constants.MAP_ELEMENT_SIZE,
				Constants.AVATAR_WIDTH, Constants.AVATAR_HEIGHT,
				Constants.COLOR_AVATAR_BLUE
				);
		avatar.setSetup(setup);
		avatar.setGame(this);
		// add avatar to entities
		entities.add(avatar);
		
		mapElements = new ArrayList<>();

		start = new ElementStart(33,33,Constants.COLOR_MAP_START);
        finish = new ElementFinish(50,12,Constants.COLOR_MAP_FINISH);

		for(int i = 0; i < 36; i++) {
            theGreatWall = new ElementWall(15, i, Constants.COLOR_WALL);
            if(i != 30 && i != 31)  mapElements.add(theGreatWall);
        }
        for(int i = 0; i < 36; i++) {
            theGreatWall = new ElementWall(43, i, Constants.COLOR_WALL);
            mapElements.add(theGreatWall);
        }

		ball = new ElementPlasmaBall(13,7,Constants.COLOR_PLASMA_BALL);

        theEnemy = new ElementEnemy(30, 30, Constants.COLOR_ENEMY);

        blackHole = new ElementBlackHole(52,2,Constants.COLOR_BLACK_HOLE);
        blackHole2 = new ElementBlackHole(4,
                25,
                       Constants.COLOR_BLACK_HOLE);


        blackHole.setAttachedBlackHole(blackHole2);
        blackHole2.setAttachedBlackHole(blackHole);


        mapElements.add(start);
        mapElements.add(finish);
		mapElements.add(ball);
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
		//Switches between the game and the build mode
		if(inputManager.getKeyResult()[5]) { Main.MODE = 0; }
		if(inputManager.getKeyResult()[6]) { Main.MODE = 1; }

	}

    /**
     * Restart the game by resetting the enemies to their original positions. This is needed so that the game is
     * consistent every time
     */
	public void restart() {
        for (MapElement element: this.getMapElements()){
            element.reset();
        }
        avatar.reset();
    }

	private void updateState(){
		if(Main.MODE == 0 || Main.MODE == 2) {
            for (MapElement element: this.getMapElements()){
                if(element.getMapType() == MapType.PLASMA_BALL || element.getMapType() == MapType.ENEMY) {
                    element.update();
                    // Because of the grid element system we have to check if the elements don't collide on the grid level
                    if(Math.abs(element.getX()  - avatar.getX()) < Constants.MAP_ELEMENT_SIZE
                            && Math.abs(element.getY()  - avatar.getY()) < Constants.MAP_ELEMENT_SIZE) {
                        this.restart();
                    }
                }
            }
        }
        map.updateEntitiesInMap(entities);
	}
	
	private void redrawAll(){
		//clear full window
		graphicsManager.clear();
		
		// draw all entities
		graphicsManager.drawMap(entities);
		
		//swap buffers to make changes visible
		graphicsManager.redraw();
	}
	
	
	public ArrayList<MapElement> getMapElements(){
		return mapElements;
	}

	public ElementStart getStart() {
		return start;
	}
}
