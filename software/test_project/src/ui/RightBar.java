package ui;

import java.awt.*;
import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.Arrays;

import javax.swing.JSlider;

import functionality.Constants;
import functionality.GraphicsManager;
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
	
//	private GraphicsManager graphicsManager;


	/**
	 * Genetic Algorithm parameters
	 * @param populationSize
	 * @param mutationRate
	 * @param noOfMoves
	 * @param increaseGeneration
	 */
	int populationSize = 1;
	int mutationRate = 1;
	int noOfMoves = 1;
	int increaseGeneration = 1;


	// Height and width of the buttons
	int width = 22;
	int height = 18;

	// Array to create to rectangles
	int positionArray [][]= new int[8][2] ;

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

	
	public RightBar(int x, int y, int width, int height, Color backgroundColor, Setup setup) {
		super(x, y, width, height, backgroundColor, setup);

		int minusButtonX = 1100, plusButtonX = 1190;
		//(para * 70) + 100)
		int buttonY = 100;

		// Loop for creating all the buttons
		for(int i = 0; i < positionArray.length; i++){
			if (i % 2 == 0){
				positionArray[i][0] = minusButtonX;
				positionArray[i][1] = buttonY;

			}
			else
			{
				positionArray[i][0] = plusButtonX;
				positionArray[i][1] = buttonY;
				buttonY += 70;
			}

		}
		System.out.println(Arrays.deepToString(positionArray));

		// creates all GA paramenter rectangles
		for(int btn = 0; btn < allButtons.length; btn++) {
			allButtons[btn] = new Rectangle(positionArray[btn][0], positionArray[btn][1], width, height);
		}

		System.out.println(Arrays.deepToString(allButtons));

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
		drawGAvariable(graphics);
		drawNumberOfTriesString(graphics);
		// decide whether to draw list with map-elements or configurations for AI game-play
		switch (Main.MODE){
			case 0:
				for (int para = 0; para < gameParameters.length; para++) {
					drawParameter(graphics, para, (para * 70) + 100);
				}
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
		graphics.drawString(mutationRate + "x", 1145,  198);
		graphics.drawString(noOfMoves + "x", 1145,  268);
		graphics.drawString(increaseGeneration + "x", 1145,  338);

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
		graphics.drawString(minus, minusButtonX + 4,yValue + 14);
		graphics.drawString(plus, plusButtonX + 5, yValue + 14);
		graphics.drawRect(minusButtonX, yValue , width, height);
		graphics.drawRect(plusButtonX, yValue , width, height);

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

	public int getIncreaseGeneration() {
		return increaseGeneration;
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

	public void setIncreaseGeneration(int increaseGeneration) {
		this.increaseGeneration = increaseGeneration;
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
