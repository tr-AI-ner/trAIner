package game;

import java.awt.geom.Rectangle2D;
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
	ArrayList<Entity> entities;
	ArrayList<MapElement> mapElements;
	ElementWall theGreatWall;

	MapSaverLoader mapSaverLoader;

	ElementPlasmaBall ball;
    ElementEnemy theEnemy;
	ElementBlackHole blackHole;
    ElementBlackHole blackHole2;
	ElementStart start;
	ElementFinish finish;

    private MapElement clickedMapElement = null;

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

		mapSaverLoader = new MapSaverLoader(this);

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
		if (inputManager.getKeyResult()[0]) {
			avatar.move(0, (-setup.getNewEntitySpeed()));
		}
		if (inputManager.getKeyResult()[1]) {
			avatar.move(0, (+setup.getNewEntitySpeed()));
		}
		if (inputManager.getKeyResult()[2]) {
			avatar.move((-setup.getNewEntitySpeed()), 0);
		}
		if (inputManager.getKeyResult()[3]) {
			avatar.move((+setup.getNewEntitySpeed()), 0);
		}
		//Exits when escape is pressed
		if(inputManager.getKeyResult()[4]) {System.exit(0);}
		//Switches between the game and the build mode
		if(inputManager.getKeyResult()[5]) { Main.MODE = 0; }
		if(inputManager.getKeyResult()[6]) { Main.MODE = 1; }

        // check if user clicked on save button
        if (inputManager.isMouseClicked()  && mapSaverLoader.saveButtonClicked()){
            mapSaverLoader.saveButtonLogic();
        }
        //check if user clicked on load button
        if (inputManager.isMouseClicked()  && mapSaverLoader.loadButtonClicked()){
            mapSaverLoader.loadButtonLogic();
        }

        //handle mouse clicks during building mode
        if(Main.MODE==1 && inputManager.isMouseClicked()){
            //Check for clicks on blocks on right bar
            if (graphicsManager.getRightBar().isRightBarClicked(inputManager.getMouseClickedX(), inputManager.getMouseClickedY())) {
                MapElement clickedElement = graphicsManager.getRightBar().getSelectedElement(
                        inputManager.getMouseClickedX(), inputManager.getMouseClickedY());
                inputManager.setMouseClicked(false);
                if (clickedElement != null){
                    this.clickedMapElement = clickedElement;
                }
            }

            // check for right mouse button click
            if(inputManager.getMouseButton()==3){
                if(this.clickedMapElement==null){removeMapElement();}
                else
                    this.clickedMapElement = null; 
                inputManager.setMouseClicked(false);
            }
            // check for placing a map element on map
            if(clickedMapElement != null && inputManager.getMouseButton()==1){
                addMapElement();
            }
        }

        // check for parameter changes by user and process them
        processParameterChanges(graphicsManager.getRightBar().getParameterChanges());

		/**
		 * All buttons on the Bottom and Right bar
		 *
		 */
		// Minus Button to decrease the speed between 1 to 10
		if (inputManager.isMouseClicked()
				&& graphicsManager.getBottomBar().isSpeedPlusButtonClicked(inputManager.getMouseClickedX(), inputManager.getMouseClickedY())) {

			System.out.println("Minus Button Clicked");
			int getDown = graphicsManager.getBottomBar().getSpeedUp();

			if (getDown > 1) {
				graphicsManager.getBottomBar().setSpeedUp(getDown - 1);
			}

			inputManager.setMouseClicked(false);
		}

		// Plus Button to increase the speed between 1 to 10
		else if (inputManager.isMouseClicked()
				&& graphicsManager.getBottomBar().isSpeedMinusButtonClicked(inputManager.getMouseClickedX(), inputManager.getMouseClickedY())) {

			System.out.println("Plus Button Clicked");
			int getUp = graphicsManager.getBottomBar().getSpeedUp();

			if (getUp >= 1 && getUp < 10) {
				graphicsManager.getBottomBar().setSpeedUp(getUp + 1);
			} else
				graphicsManager.getBottomBar().setSpeedUp(getUp);

			inputManager.setMouseClicked(false);

		}

		// 	Play Button to play the game
		else if (inputManager.isMouseClicked()
				&& graphicsManager.getBottomBar().isPlayButtonClicked(inputManager.getMouseClickedX(), inputManager.getMouseClickedY())) {
			System.out.println("Play Button Clicked");
			inputManager.setMouseClicked(false);
		}

		//  Pause Button to pause the game
		else if (inputManager.isMouseClicked()
				&& graphicsManager.getBottomBar().isPauseButtonClicked(inputManager.getMouseClickedX(), inputManager.getMouseClickedY())) {
			System.out.println("Pause Button Clicked");
			inputManager.setMouseClicked(false);
			System.exit(0);

		}

        inputManager.setMouseClicked(false);

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

    /**
     * Updates the state of the game according to the current mode
     * e.g.:
     *      0 - Game
     *      1 - Map Building
     *      2 - Preview Mode
     *
     */
	private void updateState(){
	    switch(Main.MODE){
            case 0:
                moveElements();
                break;
            case 1:
                break;
            case 2:
                moveElements();
                break;
        }
        cleanUp();
    }

    /**
     * redraws the full window
     *
     */
	private void redrawAll(){
		//clear full window
		graphicsManager.clear();
		
		// draw all entities
		graphicsManager.drawMap(entities);
		
        // draw selected map element when in building mode
        graphicsManager.drawBuilderElement(clickedMapElement);

		//swap buffers to make changes visible
		graphicsManager.redraw();
	}

    /**
     * Moves all dynamic map elements
     *
     */
    private void moveElements(){
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

    /**
     * process a parameter change when user clicked on a minus or plus button
     * on the right bar
     *
     * TODO: implementation
     */
    private void processParameterChanges(int type){
        //exit in case of exception case
        if(type == -1) return;

        switch (type){
            case 0:
                break;
            case 1:
                break;
            case 2:
                break;
            case 3:
                break;
            case 4:
                break;
            case 5:
                break;
            case 6:
                break;
            default:
                break;
        }
    }

    /**
     * removes a map element in case the user made a right mouse click on it
     * in building mode
     */
    private void removeMapElement(){
        //exit if game is not in building mode or right mouse button was not clicked
        if(Main.MODE != 1 /*|| inputManager.getMouseButton() != 3*/) return;

        int removeX = inputManager.getMouseClickedX();
        int removeY = inputManager.getMouseClickedY();
        //System.out.println("should remove element at x: "+removeX+", y: "+removeY);

        // iterate through all map elements and check if mouse was clicked on one of them
        // if so, remove it
        for(int elem=0; elem < mapElements.size(); elem++){
            int elemStartX = mapElements.get(elem).getX()+Constants.WINDOW_MAP_MARGIN;
            int elemEndX = mapElements.get(elem).getX()+mapElements.get(elem).getWidth()+Constants.WINDOW_MAP_MARGIN;
            int elemStartY = mapElements.get(elem).getY()+Constants.WINDOW_MAP_MARGIN+Constants.WINDOW_HEADER_HEIGHT;
            int elemEndY = mapElements.get(elem).getY()+mapElements.get(elem).getHeight()+Constants.WINDOW_MAP_MARGIN+Constants.WINDOW_HEADER_HEIGHT;

            if(elemStartX<=removeX && elemEndX>=removeX
                && elemStartY<=removeY && elemEndY>=removeY
                ){
                //find the element also in entities and remove it
                for(int i=0; i<entities.size(); i++){
                    if(mapElements.get(elem).equals(entities.get(i))){
                        entities.remove(i);
                        break;
                    }
                }
                mapElements.remove(elem);
                break;
            }
        }
    }

    private void addMapElement(){
        int mouseX = inputManager.getMouseClickedX()-Constants.WINDOW_MAP_MARGIN;
        int mouseY = inputManager.getMouseClickedY()-Constants.WINDOW_MAP_MARGIN-Constants.WINDOW_HEADER_HEIGHT;
        System.out.println("should place "+clickedMapElement.getMapType().name()+" at x: "
                +mouseX+", y: "+mouseY);

        // check for range, element cannot be placed outside of map
        if(mouseX < Constants.WINDOW_MAP_MARGIN
                || mouseX > (Constants.WINDOW_MAP_MARGIN + Constants.WINDOW_MAP_WIDTH)
                || mouseY < (Constants.WINDOW_MAP_MARGIN+Constants.WINDOW_HEADER_HEIGHT)
                || mouseY > (Constants.WINDOW_MAP_MARGIN+Constants.WINDOW_HEADER_HEIGHT+Constants.WINDOW_MAP_HEIGHT)){
            System.out.println("Aborting placing element, out of range...");
            return;
        }

        int gridX = -1, gridY = -1;
        ArrayList<Integer> possibleXPos = new ArrayList<>();
        ArrayList<Integer> possibleYPos = new ArrayList<>();
        //find closest grid position to place the element
        for(int i=0; i<Constants.MAP_ELEMENT_SIZE; i++){
            if((mouseX+i) % Constants.MAP_ELEMENT_SIZE == 0){
                gridX = (mouseX + i) / Constants.MAP_ELEMENT_SIZE;
                possibleXPos.add(gridX);
            }
            if((mouseY+i) % Constants.MAP_ELEMENT_SIZE == 0){
                gridY = (mouseY + i) / Constants.MAP_ELEMENT_SIZE;
                possibleYPos.add(gridY);
            }
            if((mouseX-i) % Constants.MAP_ELEMENT_SIZE == 0 && i != 0){
                gridX = (mouseX - i) / Constants.MAP_ELEMENT_SIZE;
                possibleXPos.add(gridX);
            }
            if((mouseY-i) % Constants.MAP_ELEMENT_SIZE == 0 && i != 0){
                gridY = (mouseY - i) / Constants.MAP_ELEMENT_SIZE;
                possibleYPos.add(gridY);
            }
        }

        if(gridX != -1 && gridY != -1){
            System.out.println("found gridX: "+gridX+", gridY: "+gridY);
            System.out.println("possibleXs: "+possibleXPos.toString()+", possibleYs: "+possibleYPos.toString());
            //MapElement newElement = new MapElement(clickedMapElement);
            clickedMapElement.setGridX(gridX);
            clickedMapElement.setGridY(gridY);

            mapElements.add(clickedMapElement);
            entities.add(clickedMapElement);
        } else {
            System.out.println("Houston we have a problem... gridX: "+gridX+", gridY: "+gridY);
        }
        
        this.clickedMapElement = null;
    }

    /**
     * anything that should be avoided in different game modes
     * should be handled here
     *
     */
    private void cleanUp(){
        //if player is not in building mode and an element is still underneath the mouse
        //will be removed
        if(Main.MODE != 1 && clickedMapElement != null) clickedMapElement=null;
    }

	public Avatar getAvatar(){return avatar;}
	public ArrayList<Entity> getEntities(){return entities;}

    public void resetEntities(){this.entities = new ArrayList<>();}

    public Map getMap() { return map; }
    public GraphicsManager getGraphicsManager(){return graphicsManager;}

	public void setMapElements(ArrayList<MapElement> mapElements){this.mapElements=mapElements;}
	public void resetMapElements(){this.mapElements = new ArrayList<>();}
	public ArrayList<MapElement> getMapElements(){return mapElements;}

	public ElementStart getStart() {return start;}
}
