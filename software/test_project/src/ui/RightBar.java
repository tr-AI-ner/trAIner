package ui;

<<<<<<< HEAD
import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.geom.Rectangle2D;
import java.awt.geom.RoundRectangle2D;
import java.util.ArrayList;
import java.util.List;
import java.util.*;

import javax.swing.JSlider;
=======
import java.awt.*;
import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.Arrays;
>>>>>>> rightBar

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
	//int plusMinusButtonHeight = 18;

    int minusButtonX = 1100, plusButtonX = 1190;
    int buttonStartY = 100;

    // offset so that the button is drawn beneath the string for each parameter
    int buttonOffsetY = 14;

	// Array to create the rectangles
	int buttonsPositions [][]= new int[8][2];

	// To create the all the buttons
	//private Rectangle[] allButtons = new Rectangle[8];
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

    // +2 due to headers
    private int[] elementsY = new int[staticMapElements.length+dynamicMapElements.length+2];
	
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

		// Loop for creating all the buttons
		for(int i = 0; i < buttonsPositions.length; i++){
			if (i % 2 == 0){
				buttonsPositions[i][0] = minusButtonX;
				buttonsPositions[i][1] = buttonStartY;

			} else {
				buttonsPositions[i][0] = plusButtonX;
				buttonsPositions[i][1] = buttonStartY;
				buttonStartY += 70;
			}
		}

		// creates all GA paramenter rectangles
		for(int btn = 0; btn < allButtons.length; btn++) {
			allButtons[btn] = new Rectangle(buttonsPositions[btn][0], buttonsPositions[btn][1]+buttonOffsetY, plusMinusButtonWidth, plusMinusButtonWidth);
		}
	}

    /**
     * gets which map element was clicked on with the mouse
     * (if a header gets clicked it returns null)
     *
     * @param mouseClickedX
     * @param mouseClickedY
     * @return the clicked map element
     *
     */
    public MapElement getSelectedElement(int mouseClickedX, int mouseClickedY){
        // check for static header 
        if (mouseClickedY >= elementsY[0] && mouseClickedY <= elementsY[0]+listItemHeight){
            System.out.println("Static header clicked");
            return null;
        }
        // check for dynamic header
        else if (mouseClickedY >= elementsY[staticMapElements.length+1] && mouseClickedY <= elementsY[staticMapElements.length+1]+listItemHeight){
            System.out.println("Dynamic header clicked");
            return null;
        } 
        // otherwise check for elements
        else {
            boolean found = false;
            // check for static elements
            for(int stat=0; stat < staticMapElements.length; stat++){
                if(mouseClickedY >= elementsY[stat+1] && mouseClickedY <= elementsY[stat+1]+listItemHeight){
                    System.out.println(staticMapElements[stat].getMapType().name());
                    found = true;
                    return staticMapElements[stat];
                    //break;
                }
            }
            // check for dynamic elements
            if(!found){
                for(int dyn=0; dyn < dynamicMapElements.length; dyn++){
                    if(mouseClickedY >= elementsY[staticMapElements.length+dyn+2] && mouseClickedY <= elementsY[staticMapElements.length+dyn+2]+listItemHeight){
                        System.out.println(dynamicMapElements[dyn].getMapType().name());
                        found = true;
                        return dynamicMapElements[dyn];
                        //break;
                    }
                }      
            }
        }
        return null;
    }

    /**
     * checks if mouse was clicked inside the right bar
     *
     * @param mouseClickedX
     * @param mouseClickedY
     * @return whether the bar was clicked
     *
     */
    public boolean isRightBarClicked(int mouseClickedX, int mouseClickedY){
        Rectangle2D rightBar = new Rectangle2D.Float(getX(), getY(), getWidth(), getHeight());
        return rightBar.contains(mouseClickedX, mouseClickedY);
    }

    /**
     * determine if user changed a parameter
     *
     * @return an int, where following indeces will represent following parameter changes
     *          -1 will represent the exception case when nothing should be changed
     *          0 - population size increase
     *          1 - population size decrease
     *          2 - number of moves increase
     *          3 - number of moves decrease
     *          4 - mutation rate increase
     *          5 - mutation rate decrease
     *          6 - number of generations increase
     *          7 - number of generations decrease
     */
    public int getParameterChanges(){
        // only change parameters in game mode or if mouse is clicked
        if(Main.MODE != 0 || !getInputManager().isMouseClicked()) return -1;

        //population size changes
        if(isSizeMinusButtonClicked(getInputManager().getMouseClickedX(), 
                    getInputManager().getMouseClickedY()) && populationSize > 1){
            populationSize--;
            return 0;
        }
        else if(isSizePlusButtonClicked(getInputManager().getMouseClickedX(), 
                    getInputManager().getMouseClickedY()) 
                && populationSize<=Constants.MAX_POPULATION_SIZE){
            populationSize++;
            return 1;
        }

        //number of moves changes
        else if(isMoveMinusButtonClicked(getInputManager().getMouseClickedX(), 
                    getInputManager().getMouseClickedY()) && noOfMoves > 1){
            noOfMoves--;
            return 2;
        }
        else if(isMovePlusButtonClicked(getInputManager().getMouseClickedX(), 
                    getInputManager().getMouseClickedY()) && noOfMoves <= Constants.MAX_NO_OF_MOVES){
            noOfMoves++;
            return 3;
        }

        // mutation rate changes
        else if(isRateMinusButtonClicked(getInputManager().getMouseClickedX(), 
                    getInputManager().getMouseClickedY()) && mutationRate > 1){
            mutationRate--;
            return 4;
        }
        else if(isRatePlusButtonClicked(getInputManager().getMouseClickedX(), 
                    getInputManager().getMouseClickedY()) && mutationRate <= Constants.MAX_MUTATION_RATE){
            mutationRate++;
            return 5;
        }
        
        //number of generations changes
        else if(isGenerationMinusButtonClicked(getInputManager().getMouseClickedX(), 
                    getInputManager().getMouseClickedY()) &&  noOfGenerations > 1){
            noOfGenerations--;
            return 6;
        }
        else if(isGenerationPlusButtonClicked(getInputManager().getMouseClickedX(), 
                    getInputManager().getMouseClickedY()) &&  noOfGenerations <= Constants.MAX_NO_OF_GENERATIONS){
            noOfGenerations++;
            return 7;
        }

        return -1;
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
		Graphics2D g2d = (Graphics2D)graphics;
		// if null, then it's a header
		if (element == null){
			int textIndent = 15;
			
			//draw background
			g2d.setColor(Constants.COLOR_RIGHT_BAR_HEADER);
			g2d.fillRect(itemX, itemY, Constants.WINDOW_RIGHT_BAR_WIDTH, itemHeight);
			//draw text
			g2d.setColor(Constants.COLOR_AVATAR_WHITE);
			g2d.setFont(font);
			
			// Determine the Y coordinate for the text (note we add the ascent, as in java 2d 0 is top of the screen)
		    int theY = itemY + ((itemHeight - g2d.getFontMetrics(font).getHeight()) / 2) + g2d.getFontMetrics(font).getAscent();
			g2d.drawString(name, itemX+textIndent, theY);
		    
		} else {
			//TODO: these variables should be added to Constants
			int elementIndent = 15;
			int elementWidth = 20;
			int textIndent = 50;
			int x = itemX+elementIndent;
			int y = itemY + (itemHeight/2) - (elementWidth/2);

			//draw background
			g2d.setColor(Constants.COLOR_MAP_LAND);
			g2d.fillRect(itemX, itemY, Constants.WINDOW_RIGHT_BAR_WIDTH, itemHeight);
            //Need to extend to a custom object to add the element type so that we could check which element is clicked
			Rectangle2D tempRect = new Rectangle2D.Float(itemX, itemY, Constants.WINDOW_RIGHT_BAR_WIDTH, itemHeight);
            //Add sidebar elements to a list
            itemList.add(tempRect);

			//draw item
			g2d.setColor(element.getColor());
			g2d.fillRect(x, y, elementWidth, elementWidth);
			//draw border
			Color color = new Color(element.getColor().getRed(),element.getColor().getGreen(),element.getColor().getBlue(),85);
			g2d.setColor(color);
			g2d.setStroke(new BasicStroke(5));
            g2d.drawRect(x, y, elementWidth, elementWidth);
            g2d.setStroke(new BasicStroke(1));
			//draw text
			g2d.setFont(font);
			g2d.setColor(Constants.COLOR_AVATAR_WHITE);
			int theY = itemY + ((itemHeight - graphics.getFontMetrics(font).getHeight()) / 2) + g2d.getFontMetrics(font).getAscent();
			g2d.drawString(name, itemX+textIndent, theY);
		}
		
		// draw separator line at bottom of item
		g2d.setColor(Constants.COLOR_RIGHT_BAR_HEADER);
		// -1 due to stroke width of line
		g2d.drawLine(itemX, itemY+itemHeight-1, itemX+Constants.WINDOW_RIGHT_BAR_WIDTH, itemY+itemHeight-1);
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

		String s = gameParameters[type];
		String plus = "+";
		String minus = "-";
		int minusButtonX = 1100, plusButtonX = 1190;

		int xString = 1090;
		graphics.drawString(s, xString, y );

		int beautyFactor = (plusMinusButtonWidth/2) / 2;
        graphics.drawString(minus, 
                minusButtonX+((plusMinusButtonWidth/2) - (fontSize / 8)), 
                (y+buttonOffsetY) + ((plusMinusButtonWidth/2)+beautyFactor));
        graphics.drawString(plus, 
                plusButtonX+((plusMinusButtonWidth/2) - 4), 
                (y+buttonOffsetY) + ((plusMinusButtonWidth/2)+beautyFactor));
        graphics.drawOval(minusButtonX, y+buttonOffsetY, plusMinusButtonWidth, plusMinusButtonWidth);
        graphics.drawOval(plusButtonX, y+buttonOffsetY, plusMinusButtonWidth, plusMinusButtonWidth);

        //draw the value of each parameter
        int parameterX = (minusButtonX+plusButtonX) / 2 + (fontSize / 2);
        switch (type){
            case 0:
                graphics.drawString(populationSize + "x", parameterX, 128);
                break;
            case 1: 
                graphics.drawString(noOfMoves + "x", parameterX, 198);
                break;
            case 2:
                graphics.drawString(mutationRate + "x", parameterX, 268);
                break;
            default: 
                graphics.drawString(noOfGenerations + "x", parameterX, 338);
                break;
        }
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
