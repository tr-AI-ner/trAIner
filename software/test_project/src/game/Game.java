package game;

import java.awt.Color;
import java.util.ArrayList;

import genetic_algorithm.Individual;
import genetic_algorithm.Population;
import custom_objects.Avatar;
import custom_objects.Entity;
import functionality.Clock;
import functionality.Constants;
import functionality.GraphicsManager;
import functionality.InputManager;
import functionality.Setup;
import map_builder.ElementWall;
import map_builder.Map;
import map_builder.MapElement;

public class Game {
	
	private GraphicsManager graphicsManager;
	private InputManager inputManager;
	private Clock clock; // Clock (time manager)
	Setup setup;
	Map map;
	
	Avatar avatar;
	ArrayList<Entity> entities = new ArrayList<>();
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
				);
		avatar.setSetup(setup);
		avatar.setGame(this);
		// add avatar to entities
		entities.add(avatar);
		
		mapElements = new ArrayList<>();
		
		theGreatWall = new ElementWall(15, 15, new Color(0,255,0));
		mapElements.add(theGreatWall);
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
            this.maxNrOfMoves = 40000;

            entities = new ArrayList<>();
            this.populationSize = 10;
            this.mutationRate = (float) 0.2;

            this.pop = new Population(this.populationSize, this.mutationRate, this.maxNrOfMoves);
            this.currentLifecycle = 0;
            for(int i = 0; i < populationSize; i++){
                Individual ind = pop.getIndividual(i);
                ind.setSetup(setup);
                ind.setGame(this);
                entities.add(ind);
            }

            this.recordtime = this.maxNrOfMoves +1;

            mapElements = new ArrayList<>();

            theGreatWall = new ElementWall( 50, 20, new Color(0, 255, 0));
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

            this.pop = new Population(this.populationSize, this.mutationRate, this.maxNrOfMoves);
            this.currentLifecycle = 0;

            for(int i = 0; i < populationSize; i++){
                Individual ind = pop.getIndividual(i);
                ind.setSetup(setup);
                ind.setGame(this);
                entities.add(ind);
            }
            this.recordtime = this.maxNrOfMoves +1;
            mapElements = new ArrayList<>();

            theGreatWall = new ElementWall( 50, 20, new Color(0, 255, 0));
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
    private void gameLoop(boolean ai_playing){
        if(this.clock.frameShouldChange()){
            if(this.currentLifecycle < this.maxNrOfMoves){
                this.pop.live(currentLifecycle);
                if(this.pop.reachedGoal() && (this.currentLifecycle < this.recordtime)){
                this.recordtime = this.currentLifecycle;
                }
                this.currentLifecycle++;
            }else{
                this.currentLifecycle = 0;
                this.pop.calculateFitness();
                this.pop.selection();
                this.pop.reproduction();
            } 
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
		
		//check for mouse dragging
//		if(inputManager.getIsMousePressed() && inputManager.getIsMouseDragged()){
//			System.out.println("mouse pressed at x: "+inputManager.getMousePressedX()+", y: "
//					+inputManager.getMousePressedY());
//		}
		if(inputManager.getIsMousePressed() && inputManager.getIsMouseDragged()){
			//TODO: change this
			int width = mapElements.get(0).getWidth();
			int height = mapElements.get(0).getHeight();
			mapElements.get(0).move(inputManager.getMouseDraggedX() - (width / 2), 
					inputManager.getMouseDraggedY() - (height / 2));
		}
	}
	
	private void updateState(){
		//TODO
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
	
	
	public ArrayList<MapElement> getMapElements(){
		return mapElements;
	}
	
}

