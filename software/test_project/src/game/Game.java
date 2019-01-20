package game;
import java.awt.geom.Rectangle2D;
import java.util.ArrayList;
import java.util.concurrent.TimeUnit;
import genetic_algorithm.Individual;
import genetic_algorithm.Population;
import custom_objects.Avatar;
import custom_objects.Entity;
import custom_objects.EntityType;
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
	private Map map;
    private GameMode gameMode;
    
    // will hold a selected element when user places new element in map in map-builder-mode
    private MapElement clickedMapElement = null;
	
    //genetic algorithm parameters
    private int populationSize = 1;
    public static int speed = 10;
	private int maxNrOfMoves = 20;
	private float mutationRate = (float)0.01;
	private int noOfGenerations = 100;
    
    private int incMovesAfterGen = 2;
    private int increaseMovesBy = 10;
    private int noOfTries = 1;

    private boolean shouldExtend = true;
    private boolean foundFinish = false;
    //true if in game mode AI
    private boolean ai_playing;
    private boolean foundPrevFinish = false;	
    // true if genetic algorithm is running
    private boolean aiRunning = false;
	Avatar avatar;
    ArrayList<Entity> entities;
    ArrayList<MapElement> mapElements;
	ElementWall theGreatWall;
	ElementWall theGreatWall2;
    Population pop;


    //size of the population
    //int populationSize;
    //mutation rate of the population
    //float mutationRate;
    //current lifecycle
    int currentMove;
    //least nr of steps to complete the map
    int recordtime;

	ElementPlasmaBall ball;
    ElementEnemy theEnemy;
	ElementBlackHole blackHole;
    ElementBlackHole blackHole2;
	ElementStart start;
	ElementFinish finish;

	public Game(GraphicsManager gm){
		this.graphicsManager = gm;
        gm.setGame(this);
        gm.getRightBar().setGame(this);
		this.clock = new Clock(); // Initialize clock
		this.inputManager = gm.getInputManager();
		this.setup = gm.getSetup();
		this.map = gm.getMap();
        this.gameMode = gm.getGameMode();

		entities = new ArrayList<>();
        this.maxNrOfMoves = 10;

        this.populationSize = 500;
        this.mutationRate = (float) 0.01;
        this.noOfGenerations = 500;
        this.increaseMovesBy = 10;


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

        mapElements.add(start);
        mapElements.add(finish);

		// add all map-elements to entities
		entities.addAll(mapElements);
        this.pop = new Population(this.populationSize, this.mutationRate, this);
        this.currentMove = 0;
        for(int i = 0; i < populationSize; i++){
            Individual ind = pop.getIndividual(i);
            ind.setSetup(setup);
            entities.add(ind);
        }

        this.recordtime = this.maxNrOfMoves +(this.noOfGenerations * this.increaseMovesBy)+ 1;
            

	}
    
   
    /**
     * this will start the game loop
     *
     */
	public void run(){
		while(true){
            if(gameMode.getMode() == Constants.MODE_AI_GAME){
                this.gameLoop(true);
            }else{
                this.gameLoop(false);
            }
        }
	}
    
     /**
     * main game loop for when the AI is playing, let the population live, 
     * do selection and reproduction
     *
     * @param ai_playing true if ai is playing
     */
    private void gameLoop(boolean ai_playing) {
        if(this.clock.frameShouldChange()){
            if(ai_playing){
                if(aiRunning){
                    if(this.pop.getCurrentGeneration() < this.noOfGenerations){
                        if((this.pop.getCurrentGeneration() % this.incMovesAfterGen) == 0  && this.shouldExtend ){
                            this.maxNrOfMoves+=this.increaseMovesBy;
                            this.pop.extendGenes(this.increaseMovesBy);
                            this.shouldExtend = false;
                            runGA();
                        }else{
                            runGA();
                        }
                    }else{
                        gameMode.changeMode(8,false);
                   }
                }else{
                    this.restart();
                    this.processUserInput(); // Process user input
                    this.updateState(); // Update state
                    this.redrawAll();//graphicsManager); // Redraw everything

                }
            }else{
                this.processUserInput(); // Process user input
                this.updateState(); // Update state
                this.redrawAll();//graphicsManager); // Redraw everything

            }
        }
    }
    /**
     * runs one step of the genetic algorithm, checks if individuals reached
     * the goal, handles selection and reproduction, contains the evolution strategy
     *
     */
    private void runGA(){
        // finish found, evolve
        if(this.foundFinish){
            this.maxNrOfMoves = this.recordtime;
            this.currentMove = 0;
            this.pop.calculateFitness();
            this.pop.selection();
            this.pop.reproduction(this);
            this.pop.resetDaShiat(this);
            this.foundFinish = false;
            this.shouldExtend = false;

        // normal behavior
        }else if(this.currentMove < this.maxNrOfMoves){
            this.pop.live(currentMove);
            this.currentMove++;
            if(this.foundFinish){
                this.foundPrevFinish = true;
                this.recordtime = this.currentMove-1;

            }
        // generation has ended, evolve
        }else{
            this.currentMove = 0;
            this.pop.calculateFitness();
            this.pop.selection();
            this.pop.reproduction(this);
            this.pop.resetDaShiat(this);
            if(this.foundPrevFinish){
                this.shouldExtend = false;
            }else{
                this.shouldExtend = true;
            }
            
        }
        this.processUserInput();
        this.updateState();
        this.redrawAll();
    }
    /**
     * Processing user input, e.g. mouse clicks and keyboard presses.
     *
     * @author Kasparas
     * @author Rahul
     * @author Sasha
     * @author Patrick
     *
     */
	private void processUserInput() {
		//Enters menu when escape is pressed
		if(inputManager.getKeyResult()[Constants.KEY_ESCAPE]) {
            gameMode.changeMode(Constants.MODE_MENU, false);
        }

        //Handle user input if menu is open
        if(gameMode.getMode() == Constants.MODE_MENU){
            graphicsManager.getMenu().processUserInput();
        }
        //Handle user input if help screen is open
        else if(gameMode.getMode() == Constants.MODE_HELP){
            graphicsManager.getHelpScreen().processUserInput();
        }
        //Handle user input if finish screen is open
        else if(gameMode.getMode() == Constants.MODE_FINISH){
            graphicsManager.getFinishScreen().processUserInput();
        }
        // Hanlde user input if exit screen is open
        else if (gameMode.getMode() == Constants.MODE_EXIT){
            graphicsManager.getExitScreen().processUserInput();
        } else {
            //Switches between the game and the build mode
            if(inputManager.getKeyResult()[Constants.KEY_G]) { 
                gameMode.changeMode(Constants.MODE_PLAYER_GAME, false);
            }
            if(inputManager.getKeyResult()[Constants.KEY_B]) { 
                gameMode.changeMode(Constants.MODE_MAP_BUILDER, false);
            }
            if(inputManager.getKeyResult()[Constants.KEY_A]) { 
                gameMode.changeMode(Constants.MODE_AI_GAME, false);
            }
            // loads an empty map
            //if(inputManager.getKeyResult()[Constants.KEY_E]) { mapSaverLoader.initEmptyMap(); }
            // switch to AI mode

            if (gameMode.getMode()==Constants.MODE_PLAYER_GAME){
                //moves in the desired direction
                if (inputManager.getKeyResult()[Constants.KEY_UP]) {
                    avatar.move(0, (-setup.getNewEntitySpeed()));
                }
                if (inputManager.getKeyResult()[Constants.KEY_DOWN]) {
                    avatar.move(0, (+setup.getNewEntitySpeed()));
                }
                if (inputManager.getKeyResult()[Constants.KEY_LEFT]) {
                    avatar.move((-setup.getNewEntitySpeed()), 0);
                }
                if (inputManager.getKeyResult()[Constants.KEY_RIGHT]) {
                    avatar.move((+setup.getNewEntitySpeed()), 0);
                }
            }

            //handle mouse clicks during building mode
            if(gameMode.getMode()==Constants.MODE_MAP_BUILDER && inputManager.isMouseClicked()){
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
            
            graphicsManager.getTopBar().processUserInput();
            graphicsManager.getBottomBar().processUserInput(this);

        }

        inputManager.setMouseClicked(false);
	}

    /**
     * Restart the game by resetting the enemies to their original positions. This is needed so that the game is
     * consistent every time
     *
     * @author Kasparas
     */
	public void restart() {
        for (MapElement element: this.getMapElements()) {
            element.reset();
        }
        if(gameMode.getMode() == Constants.MODE_AI_GAME) {
            if(this.aiRunning) this.foundFinish = true;
        } else {
            avatar.reset();
            gameMode.changeMode(Constants.MODE_FINISH, false);
        }
    }

    /**
     * Updates the state of the game according to the current mode
     * e.g.:
     *      0 - Player Game
     *      1 - Map Building
     *      2 - Preview Mode
     *      3 - AI Game
     *      4 - Challenge Mode
     *      5 - Menu Mode
     *      6 - Help Screen
     * 
     * @author Patrick
     */
	private void updateState(){
	    switch(gameMode.getMode()){
            case Constants.MODE_PLAYER_GAME:
                moveElements();
                break;
            case Constants.MODE_MAP_BUILDER:
                break;
            case Constants.MODE_PREVIEW:
                moveElements();
                break;
            case Constants.MODE_AI_GAME:
                moveElements();
                break;
            case Constants.MODE_CHALLENGE:
                moveElements();
                break;
            case Constants.MODE_MENU:
                break;
            case Constants.MODE_HELP:
                break;
        }
        cleanUp();
    }

    /**
     * Redraws the full window according to current mode.
     *
     */
	private void redrawAll(){
		//clear full window
		graphicsManager.clear();
		
        if (gameMode.getMode() == Constants.MODE_MENU){
            graphicsManager.drawMenu();
        } 
        else if (gameMode.getMode() == Constants.MODE_EXIT){
            graphicsManager.drawExitScreen();
        } 
        else if (gameMode.getMode() == Constants.MODE_FINISH){
            graphicsManager.drawFinishScreen();
        } 
        else if (gameMode.getMode() == Constants.MODE_HELP){
            graphicsManager.drawHelpScreen();
        } else {
            // draw all bars
            graphicsManager.drawWindowSetup();

            // draw all entities
            graphicsManager.drawMap(entities);
            
            // draw selected map element when in building mode
            graphicsManager.drawBuilderElement(clickedMapElement);
        }

        //swap buffers to make changes visible
        graphicsManager.redraw();
	}

    /**
     * Moves all dynamic map elements
     *
     * @author Kasparas
     */
    private void moveElements(){
		if(gameMode.getMode() == Constants.MODE_PLAYER_GAME || gameMode.getMode() == Constants.MODE_PREVIEW) {
            for (MapElement element: this.getMapElements()){
                if(element.getMapType() == MapType.PLASMA_BALL || element.getMapType() == MapType.ENEMY) {
                    if(element.getMapType() == MapType.ENEMY) {
                        ((ElementEnemy)element).update(mapElements);
                    } else {
                        element.update();
                    }
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
     *
     * @author Patrick
     */
    private void processParameterChanges(int type){
        //exit in case of exception case
        if(type == -1) return;

        switch (type){
            case 0: populationSize++; break;
            case 1: populationSize--; break;
            case 2: speed++; break;
            case 3: speed--; break;
            case 4: maxNrOfMoves++; break;
            case 5: maxNrOfMoves--; break;
            case 6: mutationRate+= (float)0.005; break;
            case 7: mutationRate-= (float)0.005; break;
            case 8: noOfGenerations++; break;
            case 9: noOfGenerations--; break;
            default: break;
        }
    }

    /**
     * removes a map element in case the user made a right mouse click on it
     * in building mode
     *
     * @author Patrick
     */
    private void removeMapElement(){
        //exit if game is not in building mode or right mouse button was not clicked
        if(gameMode.getMode() != Constants.MODE_MAP_BUILDER /*|| inputManager.getMouseButton() != 3*/) return;

        int removeX = inputManager.getMouseClickedX()-Constants.WINDOW_MAP_X0;
        int removeY = inputManager.getMouseClickedY()-Constants.WINDOW_MAP_Y0;

        // iterate through all map elements and check if mouse was clicked on one of them
        // if so, remove it
        for(int elem=0; elem < mapElements.size(); elem++){
            int elemStartX = mapElements.get(elem).getX();
            int elemEndX = mapElements.get(elem).getX()+mapElements.get(elem).getWidth();
            int elemStartY = mapElements.get(elem).getY();
            int elemEndY = mapElements.get(elem).getY()+mapElements.get(elem).getHeight();

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
                map.setMapArr(mapElements.get(elem).getGridX(),mapElements.get(elem).getGridY(),MapType.LAND.representation());
                mapElements.remove(elem);
                break;
            }
        }
    }

    /**
     * adds an element to the map in map-builder-mode
     *
     * @author Patrick
     */
    private void addMapElement(){
        int mouseX = inputManager.getMouseClickedX()-Constants.WINDOW_MAP_X0;
        int mouseY = inputManager.getMouseClickedY()-Constants.WINDOW_MAP_Y0;

        // check for range, element cannot be placed outside of map
        if(mouseX < 0 || mouseX >= Constants.WINDOW_MAP_WIDTH || mouseY < 0 || mouseY >= Constants.WINDOW_MAP_HEIGHT){
            return;
        }

        int gridX = -1, gridY = -1;
        ArrayList<Integer> possibleXPos = new ArrayList<>();
        ArrayList<Integer> possibleYPos = new ArrayList<>();
        //find closest grid positions to place the element
        for(int i=0; i< Constants.MAP_ELEMENT_SIZE+1; i++){
            if((mouseX+i) % Constants.MAP_ELEMENT_SIZE == 0){
                int possibleGridX = (mouseX + i) / Constants.MAP_ELEMENT_SIZE;
                if(possibleGridX >= 0 && possibleGridX < Constants.GRID_COLUMNS){
                    gridX = (mouseX + i) / Constants.MAP_ELEMENT_SIZE;
                    possibleXPos.add(gridX);
                }
            }
            if((mouseY+i) % Constants.MAP_ELEMENT_SIZE == 0){
                int possibleGridY = (mouseY + i) / Constants.MAP_ELEMENT_SIZE;
                if(possibleGridY >= 0 && possibleGridY < Constants.GRID_COLUMNS){
                    gridY = (mouseY + i) / Constants.MAP_ELEMENT_SIZE;
                    possibleYPos.add(gridY);
                }
            }
            if((mouseX-i) % Constants.MAP_ELEMENT_SIZE == 0 && i != 0){
                int possibleGridX = (mouseX - i) / Constants.MAP_ELEMENT_SIZE;
                if(possibleGridX >= 0 && possibleGridX < Constants.GRID_COLUMNS){
                    gridX = (mouseX - i) / Constants.MAP_ELEMENT_SIZE;
                    possibleXPos.add(gridX);
                }
            }
            if((mouseY-i) % Constants.MAP_ELEMENT_SIZE == 0 && i != 0){
                int possibleGridY = (mouseY - i) / Constants.MAP_ELEMENT_SIZE;
                if(possibleGridY >= 0 && possibleGridY < Constants.GRID_COLUMNS){
                    gridY = (mouseY - i) / Constants.MAP_ELEMENT_SIZE;
                    possibleYPos.add(gridY);
                }
            }
        }

        // if at least one positon was found, filter for best position and add it
        if(possibleXPos.size() > 0 && possibleYPos.size() > 0){
            //filter found positions to find best suitable position
            for(int k=0; k<possibleXPos.size(); k++){
                for(int l=0; l<possibleYPos.size(); l++){
                    int multi = Constants.MAP_ELEMENT_SIZE;
                    gridX = ((possibleXPos.get(k)*multi-mouseX) < (gridX*multi-mouseX) ? possibleXPos.get(k) : gridX);
                    gridY = ((possibleYPos.get(l)*multi-mouseY) < (gridY*multi-mouseY) ? possibleYPos.get(l) : gridY);
                }
            }

            // create a new element at best suitable position and add it to the game
            MapElement newElement = clickedMapElement;
            newElement.setGridX(gridX);
            newElement.setGridY(gridY);
            switch (clickedMapElement.getMapType()){
                case START: newElement = new ElementStart((ElementStart)clickedMapElement); break;
                case FINISH: newElement = new ElementFinish((ElementFinish)clickedMapElement); break;
                case WALL: newElement = new ElementWall((ElementWall)clickedMapElement); break;
                case BLACK_HOLE: newElement = new ElementBlackHole((ElementBlackHole)clickedMapElement); break;
                case ENEMY: newElement = new ElementEnemy((ElementEnemy)clickedMapElement); break;
                case LASER: newElement = new ElementLaser((ElementLaser)clickedMapElement); break;
                case PLASMA_BALL: newElement = new ElementPlasmaBall((ElementPlasmaBall)clickedMapElement); break;
                default: break;
            }
            mapElements.add(newElement);
            entities.add(newElement);
            map.setMapArr(newElement.getGridX(), newElement.getGridY(), newElement.getMapType().representation());
        }         
        // reset clicked element -> uncomment if only 1 element should be added
        //this.clickedMapElement = null;
    }

    /**
     * Reloads the original coordinates of the elements when switchin preview mode off.
     *
     */
    public void reloadBuildState(){
        for(int i=0; i<entities.size(); i++){
            if (entities.get(i).getType()==EntityType.MapElement){
                if(((MapElement)entities.get(i)).getMapType()==MapType.ENEMY){
                    ((ElementEnemy)entities.get(i)).reset();
                }
                else if (((MapElement)entities.get(i)).getMapType()==MapType.PLASMA_BALL){
                    ((ElementPlasmaBall)entities.get(i)).reset();
                } 
            } 
        }
    }

    /**
     * anything that should be avoided in different game modes
     * should be handled here
     *
     */
    private void cleanUp(){
        //if player is not in building mode and an element is still underneath the mouse
        //will be removed
        if(gameMode.getMode() != Constants.MODE_MAP_BUILDER && clickedMapElement != null) clickedMapElement=null;
    }

    /**
     * getters and setters
     *
     */
    public int[] getStartXY(){
        for(int i = 0; i < entities.size(); i++){
            if (entities.get(i).getType()==EntityType.MapElement){
                if(((MapElement)entities.get(i)).getMapType()==MapType.START){
                    return new int[] {((ElementStart)entities.get(i)).getX(), ((ElementStart)entities.get(i)).getY()};
                }
            }
        }
        return new int[] {110,500};
    }


    public int[] getFinishXY(){
        for(int i = 0; i < entities.size(); i++){
            if (entities.get(i).getType()==EntityType.MapElement){
                if(((MapElement)entities.get(i)).getMapType()==MapType.FINISH){
                    return new int[] {((ElementFinish)entities.get(i)).getX(), ((ElementFinish)entities.get(i)).getY()};
                }
            }
        }
        return new int[] {222,220};

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

    public int getPopulationSize(){return populationSize;}
    public int getSpeed(){return speed;}
    public float getMutationRate(){return mutationRate;}
    public int getNoOfGenerations(){return noOfGenerations;}

    public int getMaxNrOfMoves() {
        return maxNrOfMoves;
    }

    public void setMaxNrOfMoves(int maxNrOfMoves) {
        this.maxNrOfMoves = maxNrOfMoves;
    }

    public int getNoOfTries(){return noOfTries;}
    public Population getPopulation() {return pop;}
    public int getRecordTime() {return recordtime;}
    public boolean hasFinished() {return foundFinish;}
    public int getCurentMoove() {return currentMove;}

    public ElementFinish getFinish(){ return finish;}
    public boolean getAiRunning(){return this.aiRunning;}
    public void setAiRunning(boolean running){this.aiRunning = running;}
}

