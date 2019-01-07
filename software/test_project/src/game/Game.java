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
import map_saver.MapSaverLoader;


public class Game {

	private GraphicsManager graphicsManager;
	private InputManager inputManager;
	private Clock clock; // Clock (time manager)
	Setup setup;
	private Map map;
    
    // will hold a selected element when user places new element in map in map-builder-mode
    private MapElement clickedMapElement = null;
	
    //genetic algorithm parameters
    private int populationSize = 1;
    private int speed = 1;
	private int noOfMoves = 20;
	private float mutationRate = (float)0.01;
	private int noOfGenerations = 1;
    private int incMovesAfterGen = 2;
    private int increaseMovesBy = 10;
    private int noOfTries = 1;
    private boolean shouldExtend = true;
    private boolean foundFinish = false;
    private boolean ai_playing;
	
	Avatar avatar;
    ArrayList<Entity> entities;
    ArrayList<MapElement> mapElements;
	ElementWall theGreatWall;
	ElementWall theGreatWall2;
    Population pop;

    // lifetime of a population
    int maxNrOfMoves;
    //size of the population
    //int populationSize;
    //mutation rate of the population
    //float mutationRate;
    //current lifecycle
    int currentLifecycle;
    //least nr of steps to complete the map
    int recordtime;
    //max nr of generations
    int maxGens;
    int currentGen;

	MapSaverLoader mapSaverLoader;

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
    
    /**
     * default constructor for when the AI is playing
     *
     * @param gm Graphics Mangaer
     * @param ai_playing true if in game mode when ai is playing
     */
    public Game(GraphicsManager gm, boolean ai_playing) {
        this.ai_playing = ai_playing;
        if (!ai_playing) {
            new Game(gm);
        } else {
            this.graphicsManager = gm;
            this.clock = new Clock(); // Initialize clock
            this.inputManager = gm.getInputManager();
            this.setup = gm.getSetup();
            gm.getRightBar().setGame(this);
            this.map = gm.getMap();
            this.maxNrOfMoves = 10;

            entities = new ArrayList<>();
            this.populationSize = 500;
            this.mutationRate = (float) 0.01;
            this.maxGens = 500;
            this.incMovesAfterGen = 1;
            this.increaseMovesBy = 10;
            this.currentGen= 1;


            mapElements = new ArrayList<>();
            start = new ElementStart(33,33,Constants.COLOR_MAP_START);
            finish = new ElementFinish(50,12,Constants.COLOR_MAP_FINISH);

            mapElements.add(start);
            mapElements.add(finish);
            // add all map-elements to entities


            for(int i = 0; i < 36; i++) {
                theGreatWall = new ElementWall(40, i, Constants.COLOR_WALL);
                if(i != 18 && i != 17 && i != 13 && i != 14 && i != 15 && i != 16)  mapElements.add(theGreatWall);

            }
//            for(int i = 0; i < 36; i++) {
//                theGreatWall = new ElementWall(43, i, Constants.COLOR_WALL);
//                if(i != 8 && i != 9)  mapElements.add(theGreatWall);
//            }


            entities.addAll(mapElements);
            this.pop = new Population(this.populationSize, this.mutationRate, this.maxNrOfMoves, this);
            this.currentLifecycle = 0;
            for(int i = 0; i < populationSize; i++){
                Individual ind = pop.getIndividual(i);
                ind.setSetup(setup);
                entities.add(ind);
            }

            this.recordtime = this.maxNrOfMoves +1;
            
        }
    }

    /**
     * specific constructor to set all hyperparameters of the genetic algorithm
     *
     * @param gm GraphicsManager
     * @param ai_playing true if ai is playing
     * @param maxNrOfMoves number of moves until the generation ends
     * @param populationSize number of individuals in the population
     * @param mutationRate probability for a genome of an individual to get mutated
     */
    public Game(GraphicsManager gm, boolean ai_playing, int maxNrOfMoves, int populationSize, float mutationRate) {
        this.ai_playing = ai_playing;
        if (!ai_playing) {
            new Game(gm);
        } else {
            this.graphicsManager = gm;
            this.clock = new Clock(); // Initialize clock
            this.inputManager = gm.getInputManager();
            this.setup = gm.getSetup();
            this.map = gm.getMap();
            gm.getRightBar().setGame(this);

            this.maxNrOfMoves = maxNrOfMoves;
            entities = new ArrayList<>();
            this.populationSize = populationSize;
            this.mutationRate = mutationRate;

            this.pop = new Population(this.populationSize, this.mutationRate, this.maxNrOfMoves, this);
            this.currentLifecycle = 0;

            for(int i = 0; i < populationSize; i++){
                Individual ind = pop.getIndividual(i);
                ind.setSetup(setup);
                ind.setGame(this);
                entities.add(ind);
            }
            this.recordtime = this.maxNrOfMoves +1;
            mapElements = new ArrayList<>();
            // help

            theGreatWall = new ElementWall( 50, 20, Constants.COLOR_WALL);
            mapElements.add(theGreatWall);
            // add all map-elements to entities
            entities.addAll(mapElements);
        }
    }
    
   
    /**
     * this will start the game loop
     *
     */
	public void run(){
		while(true)
			this.gameLoop();
	}
    
    /**
     * run the game loop for when the AI is playing
     *
     * @param ai true if ai is playing
     */
    public void run(boolean ai){
        if(ai){
            while(true)
                this.gameLoop(true);
        }
    }
	
	/**
     *main game loop, processes user input, updates states and draws everything
     *
     */
	private void gameLoop(){
		if(this.clock.frameShouldChange()) { // if fps right (ifnot, sleep, or do nothing), and update clock
			this.processUserInput(); // Process user input		
			this.updateState(); // Update state
			this.redrawAll();//graphicsManager); // Redraw everything
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
            if(this.currentGen < this.maxGens){
                if((this.currentGen % this.incMovesAfterGen) == 0  && this.shouldExtend ){
                    System.out.println("slow?");
                    maxNrOfMoves+=this.increaseMovesBy;
                    recordtime = maxNrOfMoves + 1;
                    this.pop.extendGenes(this.increaseMovesBy);
                    this.shouldExtend = false;
                    runGA();             
                }else{
                    System.out.println(recordtime);
                    runGA();         
                }
            }
        }
    }

    private void runGA(){
        if((this.currentLifecycle < this.maxNrOfMoves) && !this.foundFinish){
            this.pop.live(currentLifecycle);
            if(this.pop.reachedGoal() && (this.currentLifecycle < this.recordtime)){
                this.recordtime = this.currentLifecycle;
            }
            this.currentLifecycle++;
        }else{
            if(this.foundFinish) {
                this.foundFinish = false;
                if(this.recordtime>this.currentLifecycle)
                this.recordtime = this.currentLifecycle;
            }
            this.currentLifecycle = 0;
            this.pop.calculateFitness();
            this.pop.selection();
            this.pop.reproduction(this);
            this.pop.resetDaShiat(this);
            this.currentGen++;
            this.shouldExtend = true;
        } 
        this.updateState();
        this.redrawAll();

    }

    /**
     * process user input
     *
     * @author Kasparas
     * @author Rahul
     * @author Sasha
     * @author Patrick
     *
     */
	private void processUserInput() {
		//Exits when escape is pressed
		if(inputManager.getKeyResult()[4]) {System.exit(0);}
		//Switches between the game and the build mode
		if(inputManager.getKeyResult()[5]) { Main.MODE = 0; }
		if(inputManager.getKeyResult()[6]) { 
            Main.MODE = 1; 
            reloadBuildState();
        }
		// loads an empty map
		if(inputManager.getKeyResult()[7]) { mapSaverLoader.initEmptyMap(); }
        // check if user clicked on save button
        if (inputManager.isMouseClicked()  && mapSaverLoader.saveButtonClicked()){
            mapSaverLoader.saveButtonLogic();
        }
        //check if user clicked on load button
        if (inputManager.isMouseClicked()  && mapSaverLoader.loadButtonClicked()){
            mapSaverLoader.loadButtonLogic();
        }

        if (Main.MODE==0){
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

        // check if preview button was clicked
        if((Main.MODE==1 || Main.MODE==2) && inputManager.isMouseClicked() 
                && graphicsManager.getBottomBar().isPreviewButtonClicked(inputManager.getMouseClickedX(), inputManager.getMouseClickedY())){
            if(Main.MODE==1){ 
                Main.MODE = 2;
            }
            else if(Main.MODE == 2){ 
                Main.MODE = 1;
                reloadBuildState();
            }
        }

        // check for parameter changes by user and process them
        processParameterChanges(graphicsManager.getRightBar().getParameterChanges());
		
        // 	Exit Button to exit the game
		if (inputManager.isMouseClicked()
				&& graphicsManager.getTopBar().isExitButtonClicked(inputManager.getMouseClickedX(), inputManager.getMouseClickedY())) {
			System.exit(0);
		}
        // 	building mode Button to exit the game
		if (inputManager.isMouseClicked()
				&& graphicsManager.getTopBar().isBuildModeButtonClicked(inputManager.getMouseClickedX(), inputManager.getMouseClickedY())) {
			Main.MODE = 1;
		}
        // 	game-play mode Button to exit the game
		if (inputManager.isMouseClicked()
				&& graphicsManager.getTopBar().isGamePlayModeButtonClicked(inputManager.getMouseClickedX(), inputManager.getMouseClickedY())) {
			Main.MODE = 0;
		}

        if (Main.MODE != 1 && Main.MODE != 2 && inputManager.isMouseClicked()){
            // 	Play Button to play the game
            if (graphicsManager.getBottomBar().isPlayButtonClicked(inputManager.getMouseClickedX(), inputManager.getMouseClickedY())) {
                System.out.println("Play Button Clicked");
            }
            //  Pause Button to pause the game
            if (graphicsManager.getBottomBar().isPauseButtonClicked(inputManager.getMouseClickedX(), inputManager.getMouseClickedY())) {
                System.out.println("Pause Button Clicked");
            }
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
        if(this.ai_playing) {
            this.foundFinish = true;
            System.out.println("AI PLAYING");
        } else {
            avatar.reset();
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
     * 
     * @author Patrick
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
            case 3:
                moveElements();
                break;
            case 4:
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
     * @author Kasparas
     */
    private void moveElements(){
		if(Main.MODE == 0 || Main.MODE == 2) {
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
            case 4: noOfMoves++; break;
            case 5: noOfMoves--; break;
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
        if(Main.MODE != 1 /*|| inputManager.getMouseButton() != 3*/) return;

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
            System.out.println("Aborting placing element, out of range...");
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
        } else {
            System.out.println("No position found for placing element... gridX: "+gridX+", gridY: "+gridY);
        }
        
        // reset clicked element -> uncomment if only 1 element should be added
        //this.clickedMapElement = null;
    }

    /**
     * Reloads the original coordinates of the elements when switchin preview mode off.
     *
     */
    private void reloadBuildState(){
        for(int i=0; i<entities.size(); i++){
            if (entities.get(i).getType()==EntityType.MapElement){
                if(((MapElement)entities.get(i)).getMapType()==MapType.ENEMY){
                    ((ElementEnemy)entities.get(i)).reset();
                }
                else if (((MapElement)entities.get(i)).getMapType()==MapType.PLASMA_BALL){
                    ((ElementPlasmaBall)entities.get(i)).reset();
                } 
            } else {
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
        if(Main.MODE != 1 && clickedMapElement != null) clickedMapElement=null;
    }

    //public ElementStart getStart(){
    //    for(int i = 0; i < entities.size(); i++){
    //        if(entities.get(i).getMapType() == MapType.START)
    //            return (ElementStart)entities.get(i);
    //    }
    //    return new ElementStart(0,0,Constants.COLOR_MAP_START);
    //}

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
    public int getNoOfMoves(){return noOfMoves;}
    public float getMutationRate(){return mutationRate;}
    public int getNoOfGenerations(){return noOfGenerations;}

    public int getMaxNrOfMoves() {
        return maxNrOfMoves;
    }

    public void setMaxNrOfMoves(int maxNrOfMoves) {
        this.maxNrOfMoves = maxNrOfMoves;
    }

    public int getNoOfTries(){return noOfTries;}

}

