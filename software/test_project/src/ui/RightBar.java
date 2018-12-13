package ui;

import java.awt.*;
import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.Arrays;

import javax.swing.JSlider;

import functionality.Constants;
import functionality.GraphicsManager;
import functionality.InputManager;
import functionality.Setup;
import game.Main;
import map_builder.ElementBlackHole;
import map_builder.ElementEnemy;
import map_builder.ElementFinish;
import map_builder.ElementLaser;
import map_builder.ElementPlasmaBall;
import map_builder.ElementStart;
import map_builder.ElementWall;
import map_builder.MapElement;
import map_builder.MapType;

/**
 * This class is responsible for the right bar of the game.
 * 
 * Anything that should be on the right bar, should be added here
 * 
 * @author Patrick
 *
 */
public class RightBar extends UIElement {
	
	/*
	 * build mode:
	 * 		static mode:
	 * 			- wall
	 * 			- start
	 * 			- finish
	 * 			- black hole
	 * 
	 * 		dynamic mode:
	 * 			- laser
	 * 			- enemy
	 * 			- plasma ball
	 * 		
	 */
	
	/**
	 * Genetic Algorithm parameters
	 * @param populationSize
	 * @param mutationRate
	 * @param noOfMoves
	 * @param noOfGenerations
	 */
	int populationSize = 1;
	int noOfMoves = 1;
	int mutationRate = 1;
	int noOfGenerations = 1;


	// Height and width of the buttons
	int plusMinusButtonWidth = 22;
	int plusMinusButtonHeight = 18;

    // offset so that the button is drawn beneath the string for each parameter
    int buttonOffsetY = 14;

	// Array to create the rectangles
	int buttonsPositions [][]= new int[8][2];

	// To create the all the buttons
	private Rectangle[] allButtons = new Rectangle[8];

	private String[] gameParameters = new String[]{
			"Population Size: ","Number Of Moves: ","Mutation Rate: ","Number Of Generations: "
	};
	
	// list of the static map elements that should be shown in map-building-mode
	private MapElement[] staticMapElements = new MapElement[]{
			new ElementStart(0, 0, Constants.COLOR_MAP_START),
			new ElementFinish(0, 0, Constants.COLOR_MAP_FINISH),
			new ElementWall(0,0, Constants.COLOR_WALL),
			new ElementBlackHole(0, 0, Constants.COLOR_BLACK_HOLE)
	}; 
	// list of the dynamic map elements that should be shown in map-building-mode
	private MapElement[] dynamicMapElements = new MapElement[]{
			new ElementEnemy(0, 0, Constants.COLOR_ENEMY),
			new ElementLaser(0, 0, Constants.COLOR_LASER),
			new ElementPlasmaBall(0,0, Constants.COLOR_PLASMA_BALL)
	}; 
	
	// string representations of the map elements
	private String[] staticNames = new String[]{"Start", "Finish", "Wall", "Black Hole"};
	private String[] dynamicNames = new String[]{"Enemy", "Laser", "Plasma Ball"};
	
	final String[] headers = new String[]{"Static", "Dynamic"};
	
	// height of each list item & header (in pixels)
	final int listItemHeight = 50;
	
	// font for list items & headers text
	int fontSize = 16;
	Font font = new Font(Constants.DEFAULT_FONT, Font.PLAIN, fontSize);

	
	public RightBar(int x, int y, int width, int height, Color backgroundColor, Setup setup, InputManager inputManager) {
		super(x, y, width, height, backgroundColor, setup, inputManager);

		int minusButtonX = 1100, plusButtonX = 1190;
		//(para * 70) + 100)
		int buttonY = 100;

		// Loop for creating all the buttons
		for(int i = 0; i < buttonsPositions.length; i++){
			if (i % 2 == 0){
				buttonsPositions[i][0] = minusButtonX;
				buttonsPositions[i][1] = buttonY;

			}
			else
			{
				buttonsPositions[i][0] = plusButtonX;
				buttonsPositions[i][1] = buttonY;
				buttonY += 70;
			}

		}
		//System.out.println(Arrays.deepToString(buttonsPositions));

		// creates all GA paramenter rectangles
		for(int btn = 0; btn < allButtons.length; btn++) {
			allButtons[btn] = new Rectangle(buttonsPositions[btn][0], buttonsPositions[btn][1]+buttonOffsetY, plusMinusButtonWidth, plusMinusButtonHeight);
		}

		//System.out.println(Arrays.deepToString(allButtons));

	}

    public void processParameterChanges(){
        // only change parameters in game mode or if mouse is clicked
        if(Main.MODE != 0 || !getInputManager().isMouseClicked()) return;

        //population size changes
        if(isSizeMinusButtonClicked(getInputManager().getMouseClickedX(), 
                    getInputManager().getMouseClickedY()) /*&& populationSize > 1*/){
            //System.out.println("Minus Button Clicked, pop size: "+populationSize);
            System.out.println("minus pop");
            if(populationSize > 1){
                populationSize--;
            }
        } 
        else if(isSizePlusButtonClicked(getInputManager().getMouseClickedX(), 
                    getInputManager().getMouseClickedY()) /*&& populationSize <= Constants.MAX_POPULATION_SIZE*/){
            //System.out.println("Plus Button Clicked, pop size: "+populationSize);
            System.out.println("plus pop");
            if(populationSize <= Constants.MAX_POPULATION_SIZE){
                populationSize++;
            }
        } 

        //number of moves changes
        else if(isMoveMinusButtonClicked(getInputManager().getMouseClickedX(), 
                    getInputManager().getMouseClickedY()) && noOfMoves > 1)
            noOfMoves--;
        else if(isMovePlusButtonClicked(getInputManager().getMouseClickedX(), 
                    getInputManager().getMouseClickedY()) && noOfMoves <= Constants.MAX_NO_OF_MOVES)
            noOfMoves++;

        // mutation rate changes
        else if(isRateMinusButtonClicked(getInputManager().getMouseClickedX(), 
                    getInputManager().getMouseClickedY()) && mutationRate > 1)
            mutationRate--;
        else if(isRatePlusButtonClicked(getInputManager().getMouseClickedX(), 
                    getInputManager().getMouseClickedY()) && mutationRate <= Constants.MAX_MUTATION_RATE)
            mutationRate++;
        
        //number of generations changes
        else if(isGenerationMinusButtonClicked(getInputManager().getMouseClickedX(), 
                    getInputManager().getMouseClickedY()) &&  noOfGenerations > 1)
            noOfGenerations--;
        else if(isGenerationPlusButtonClicked(getInputManager().getMouseClickedX(), 
                    getInputManager().getMouseClickedY()) &&  noOfGenerations <= Constants.MAX_NO_OF_GENERATIONS)
            noOfGenerations++;
    }
	
	/**
	 * overriding draw method for custom draw behavior:
	 * 
	 * either display the map-elements list or
	 * the game-play options
	 * 
	 */
	@Override
	public void draw(Graphics graphics) {
		drawBackground(graphics);
		drawNumberOfTriesString(graphics);
		// decide whether to draw list with map-elements or configurations for AI game-play
		switch (Main.MODE){
			case 0:
				for (int para = 0; para < gameParameters.length; para++) {
					drawParameter(graphics, para, (para * 70) + 100);
				}
                drawGAvariable(graphics);
				break;
			case 1:
				drawMapBuilderList(graphics);
				break;
			default:
				break;
		}

	}
	
	/**
	 * draws the basic background of the right bar
	 * @param graphics
	 */
	private void drawBackground(Graphics graphics){
		graphics.setColor(getBackgroundColor());
		graphics.fillRect(getX(), getY(), getWidth(), getHeight());
	}

	
	/**
	 * draws the map-builder-list, according to design specifications
	 * 
	 * @param graphics
	 */
	private void drawMapBuilderList(Graphics graphics){
		int counter = 0;
		// draw header 'static'
		drawMapElementListItem(graphics, getX(), getY(), listItemHeight, null, headers[0]);
		counter++;
		// draw static list
		for (int i=0; i<staticNames.length; i++){
			drawMapElementListItem(graphics, getX(), getY()+(counter*listItemHeight), 
					listItemHeight, staticMapElements[i], staticNames[i]);
			counter++;
		}
		// draw header 'dynamic'
		drawMapElementListItem(graphics, getX(), getY()+(counter*listItemHeight), 
				listItemHeight, null, headers[1]);
		counter++;
		// draw dynamic list
		for (int i=0; i<dynamicNames.length; i++){
			drawMapElementListItem(graphics, getX(), getY()+(counter*listItemHeight), 
					listItemHeight, dynamicMapElements[i], dynamicNames[i]);
			counter++;
		}
	}
	
	/**
	 * 
	 * draws a single list item for the map-builder-mode
	 * 
	 * @param graphics
	 * @param itemX			-	start x-coordinate of list item 
	 * @param itemY			-	start y-coordinate of list item
	 * @param itemHeight	-	height of list item
	 * @param element		-	the element that should be shown by the list item (if null, it's a header)
	 * @param name			-	the name that should be shown for the list item
	 */
	private void drawMapElementListItem(Graphics graphics, int itemX, int itemY, int itemHeight, 
			MapElement element, String name){
		
		// if null, then it's a header
		if (element == null){
			int textIndent = 15;
			
			//draw background
			graphics.setColor(Constants.COLOR_RIGHT_BAR_HEADER);
			graphics.fillRect(itemX, itemY, Constants.WINDOW_RIGHT_BAR_WIDTH, itemHeight);
			//draw text
			graphics.setColor(Constants.COLOR_AVATAR_WHITE);
			graphics.setFont(font);
			
			// Determine the Y coordinate for the text (note we add the ascent, as in java 2d 0 is top of the screen)
		    int theY = itemY + ((itemHeight - graphics.getFontMetrics(font).getHeight()) / 2) + graphics.getFontMetrics(font).getAscent();
		    graphics.drawString(name, itemX+textIndent, theY);
		    
		} else {
			//TODO: these variables should be added to Constants
			int elementIndent = 15;
			int elementWidth = 20;
			int textIndent = 50;
			
			//draw background
			graphics.setColor(Constants.COLOR_MAP_LAND);
			graphics.fillRect(itemX, itemY, Constants.WINDOW_RIGHT_BAR_WIDTH, itemHeight);
			//draw item
			graphics.setColor(element.getColor());
			graphics.fillRect(itemX+elementIndent, itemY + (itemHeight/2) - (elementWidth/2), elementWidth, elementWidth);
			//draw text
			graphics.setFont(font);
			graphics.setColor(Constants.COLOR_AVATAR_WHITE);
			int theY = itemY + ((itemHeight - graphics.getFontMetrics(font).getHeight()) / 2) + graphics.getFontMetrics(font).getAscent();
			graphics.drawString(name, itemX+textIndent, theY);
		}
		
		// draw separator line at bottom of item
		graphics.setColor(Constants.COLOR_RIGHT_BAR_HEADER);
		// -1 due to stroke width of line
		graphics.drawLine(itemX, itemY+itemHeight-1, itemX+Constants.WINDOW_RIGHT_BAR_WIDTH, itemY+itemHeight-1);
	}

	// Genetic algorithm variable Strings

	public void drawGAvariable(Graphics graphics){
		graphics.setColor(Constants.COLOR_AVATAR_WHITE);
		graphics.setFont(font);

		graphics.drawString(populationSize + "x", 1145,   128);
		graphics.drawString(noOfMoves + "x", 1145,  198);
		graphics.drawString(mutationRate + "x", 1145,  268);
		graphics.drawString(noOfGenerations + "x", 1145,  338);

	}

	public void drawNumberOfTriesString(Graphics graphics){
		graphics.setColor(Constants.COLOR_AVATAR_WHITE);
		graphics.setFont(font);
		String numberOfTries = "Number of Tries:";
		graphics.drawString(numberOfTries, 1090, 404);

	}

	public void drawParameter(Graphics graphics, int type, int y ){
//		Type: 0 = Population Size , 1 = NUmber of Moves, 2 = Mutation Rate, 3 = Number of generations

		graphics.setColor(Constants.COLOR_AVATAR_WHITE);
		graphics.setFont(font);

		int yValue = y + 14;
		String s = gameParameters[type];
		String plus = "+";
		String minus = " -";
		int minusButtonX = 1100, plusButtonX = 1190;

		int xString = 1090;
		graphics.drawString(s, xString, y );
		graphics.drawString(minus, minusButtonX + 4,yValue + buttonOffsetY);
		graphics.drawString(plus, plusButtonX + 5, yValue + buttonOffsetY);
		graphics.drawRect(minusButtonX, y+buttonOffsetY, plusMinusButtonWidth, plusMinusButtonHeight);
		graphics.drawRect(plusButtonX, y+buttonOffsetY, plusMinusButtonWidth, plusMinusButtonHeight);

	}

	// Getters setters for all GA parameters
	public int getPopulationSize() {
		return populationSize;
	}

	public int getMutationRate() {
		return mutationRate;
	}

	public int getNoOfMoves() {
		return noOfMoves;
	}

	public int getNoOfGenerations() {
		return noOfGenerations;
	}

	public void setPopulationSize(int populationSize) {
		this.populationSize = populationSize;
	}

	public void setMutationRate(int mutationRate) {
		this.mutationRate = mutationRate;
	}

	public void setNoOfMoves(int noOfMoves) {
		this.noOfMoves = noOfMoves;
	}

	public void setNoOfGenerations(int noOfGenerations) {
		this.noOfGenerations = noOfGenerations;
	}


	// to return if the buttons clicked

	public boolean isSizeMinusButtonClicked(int mouseClickedX, int mouseClickedY) {
		return allButtons[0].contains(mouseClickedX, mouseClickedY);
	}

	public boolean isSizePlusButtonClicked(int mouseClickedX, int mouseClickedY) {
		return allButtons[1].contains(mouseClickedX, mouseClickedY);
	}

	public boolean isMoveMinusButtonClicked(int mouseClickedX, int mouseClickedY) {
		return allButtons[2].contains(mouseClickedX, mouseClickedY);
	}

	public boolean isMovePlusButtonClicked(int mouseClickedX, int mouseClickedY) {
		return allButtons[3].contains(mouseClickedX, mouseClickedY);
	}

	public boolean isRateMinusButtonClicked(int mouseClickedX, int mouseClickedY) {
		return allButtons[4].contains(mouseClickedX, mouseClickedY);
	}

	public boolean isRatePlusButtonClicked(int mouseClickedX, int mouseClickedY) {
		return allButtons[5].contains(mouseClickedX, mouseClickedY);
	}

	public boolean isGenerationMinusButtonClicked(int mouseClickedX, int mouseClickedY) {
		return allButtons[6].contains(mouseClickedX, mouseClickedY);
	}

	public boolean isGenerationPlusButtonClicked(int mouseClickedX, int mouseClickedY) {
		return allButtons[7].contains(mouseClickedX, mouseClickedY);
	}


}
