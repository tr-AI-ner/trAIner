package game;
import java.lang.InterruptedException;
import java.util.ArrayList;
import java.util.concurrent.TimeUnit;
import genetic_algorithm.Individual;
import genetic_algorithm.Population;
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
    public ArrayList<Entity> entities;
	ArrayList<MapElement> mapElements;
	ElementWall theGreatWall;
    Population pop;

    // lifetime of a population
    int maxNrOfMoves;
    //size of the population
    int populationSize;
    //mutation rate of the population
    float mutationRate;
    //current lifecycle
    int currentLifecycle;
    //least nr of steps to complete the map
    int recordtime;

	MapSaverLoader mapSaverLoader;

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
        if (!ai_playing) {
            new Game(gm);
        } else {
            this.graphicsManager = gm;
            this.clock = new Clock(); // Initialize clock
            this.inputManager = gm.getInputManager();
            this.setup = gm.getSetup();
            this.map = gm.getMap();
            this.maxNrOfMoves = 200;

            entities = new ArrayList<>();
            this.populationSize = 2;
            this.mutationRate = (float) 0.2;

            this.pop = new Population(this.populationSize, this.mutationRate, this.maxNrOfMoves, this);
            this.currentLifecycle = 0;
            for(int i = 0; i < populationSize; i++){
                Individual ind = pop.getIndividual(i);
                ind.setSetup(setup);
                entities.add(ind);
            }

            this.recordtime = this.maxNrOfMoves +1;

            mapElements = new ArrayList<>();

            theGreatWall = new ElementWall( 50, 20, Constants.COLOR_WALL);
            mapElements.add(theGreatWall);
            // add all map-elements to entities
            entities.addAll(mapElements);
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
        if (!ai_playing) {
            new Game(gm);
        } else {
            this.graphicsManager = gm;
            this.clock = new Clock(); // Initialize clock
            this.inputManager = gm.getInputManager();
            this.setup = gm.getSetup();
            this.map = gm.getMap();

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

            theGreatWall = new ElementWall( 50, 20, Constants.COLOR_WALL);
            mapElements.add(theGreatWall);
            // add all map-elements to entities
            entities.addAll(mapElements);
        }
    }
    
    /**
     * run the game loop
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
            if(this.currentLifecycle < this.maxNrOfMoves){
                this.pop.live(currentLifecycle);
                if(this.pop.reachedGoal() && (this.currentLifecycle < this.recordtime)){
                    this.recordtime = this.currentLifecycle;
                }
                this.currentLifecycle++;
            }else{
                this.pop.resetDaShiat(this);
                this.currentLifecycle = 0;
                this.pop.calculateFitness();
                this.pop.selection();
                this.pop.reproduction(this);

            } 
            this.updateState();
            this.redrawAll();
        }
    }

    /**
     * processes user input 
     *
     */
	private void processUserInput() {
		//moves in the desired direction
		if(inputManager.getKeyResult()[0]) {avatar.move(0, ( -setup.getNewEntitySpeed() ));}
		if(inputManager.getKeyResult()[1]) {avatar.move(0, ( +setup.getNewEntitySpeed() ));}
		if(inputManager.getKeyResult()[2]) {avatar.move(( -setup.getNewEntitySpeed() ), 0);}
		if(inputManager.getKeyResult()[3]) {avatar.move(( +setup.getNewEntitySpeed() ), 0);}
		//Exits when escape is pressed
		if(inputManager.getKeyResult()[4]) {System.exit(0);}

        // check if user clicked on save button
        if (inputManager.isMouseClicked()  && graphicsManager.getTopBar().isSaveButtonClicked(inputManager.getMouseClickedX(), inputManager.getMouseClickedY())){
            mapSaverLoader.saveButtonLogic();
        }
        //check if user clicked on load button
        if (inputManager.isMouseClicked()  && graphicsManager.getTopBar().isLoadButtonClicked(inputManager.getMouseClickedX(), inputManager.getMouseClickedY())){
            mapSaverLoader.loadButtonLogic();
        }

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

    /**
     * Moves all dynamic map elements
     *
     */
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

    /**
     * redraws the full window
     *
     */
	private void redrawAll(){
		//clear full window
		graphicsManager.clear();
		
		// draw all entities
		graphicsManager.drawMap(entities);
		
		//swap buffers to make changes visible
		graphicsManager.redraw();
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

