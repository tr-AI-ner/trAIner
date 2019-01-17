package ui;
import java.util.Collections;

import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.geom.Rectangle2D;
import java.util.ArrayList;
import java.util.List;
import java.util.*;
import java.awt.*;

import functionality.Constants;
import functionality.GraphicsManager;
import functionality.InputManager;
import functionality.Setup;
import game.Main;
import game.Game;
import map_builder.ElementBlackHole;
import map_builder.ElementEnemy;
import map_builder.ElementFinish;
import map_builder.ElementLaser;
import map_builder.ElementPlasmaBall;
import map_builder.ElementStart;
import map_builder.ElementWall;
import map_builder.MapElement;
import map_builder.MapType;

import javax.swing.JProgressBar;
/**
 * This class is responsible for the right bar of the game.
 * 
 * Anything that should be on the right bar, should be added here
 * 
 * @author Patrick
 * @author Rahul
 *
 */
public class RightBar extends UIElement {
	
    Game game = null;

	// Height and width of the buttons
	int plusMinusButtonWidth = 20;
	//int plusMinusButtonHeight = 18;

    // start x & y positions of right bar
    final int rightBarStartX = Constants.WINDOW_MAP_MARGIN*2 + Constants.WINDOW_MAP_WIDTH;
    final int rightBarStartY = Constants.WINDOW_HEADER_HEIGHT;

    // start x positions of plus and minus buttons
    int minusButtonX = 15, plusButtonX = Constants.WINDOW_RIGHT_BAR_WIDTH-minusButtonX-plusMinusButtonWidth;
    // start x position of parameter name
	int parameterStringX = 15;
    //int parametersStartY = 100;
    int parametersStartY = Constants.WINDOW_MAP_Y0-90;

    // y start position of first parameter
    int buttonStartY = parametersStartY;

    // offset so that the button is drawn beneath the string for each parameter
    int buttonOffsetY = 22;
	
    int betweenStrings=35;
    int rightBarOffestY=60;
    
    
	// height of each list item & header (in pixels) (for map-building mode)
	final int listItemHeight = 50;
    final int listItemIndentX = 15;
    final int parameterHeight = 80;

	// Array to create the rectangles
	int buttonsPositions [][]= new int[10][2];
	
	// To create the all the buttons
	private Rectangle[] allButtons = new Rectangle[10];

	private String[] gameParameters = new String[]{
			"Population Size","Speed","Number Of Moves","Mutation Rate","Number Of Generations"
	};
    String plusString = "+";
    String minusString = "-";
    String numOfGen = "Gen: ";
    String maxFit = "Max Fitness: ";
    // maximum amount of Characters in a row
    int maxChar = 23;
    // offset for a new Row, so that strings don`t collide
    int lineOffsetY = 35;
    String line="____________________";
    boolean [] finished=new boolean[50];
    //ArrayList finished= new ArrayList();
    

    
    int oldRM=9999999;
    List<Integer> list = new ArrayList<Integer>();
    List<Integer> hlist = new ArrayList<Integer>();
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

    // will be used to check where user clicked on when selecting a map element in building mode
    // +2 due to headers
    private int[] elementsY = new int[staticMapElements.length+dynamicMapElements.length+2];
	
	// string representations of the map elements
	private String[] staticNames = new String[]{"Start", "Finish", "Wall", "Black Hole"};
	private String[] dynamicNames = new String[]{"Enemy", "Laser", "Plasma Ball"};
	private final String[] headers = new String[]{"Static", "Dynamic"};
	
	// font for list items & headers text
	int fontSize = 16;
	Font font = new Font(Constants.DEFAULT_FONT, Font.PLAIN, fontSize);

	public RightBar(int x, int y, int width, int height, Color backgroundColor, Setup setup, InputManager inputManager) {
		super(x, y, width, height, backgroundColor, setup, inputManager);
		// Loop for creating all the buttons
		for(int i = 0; i < buttonsPositions.length; i++){
			if (i % 2 == 0){
				buttonsPositions[i][0] = rightBarStartX + minusButtonX;
				buttonsPositions[i][1] = buttonStartY;

			} else {
				buttonsPositions[i][0] = rightBarStartX + plusButtonX;
				buttonsPositions[i][1] = buttonStartY;
				buttonStartY += parameterHeight;
			}
		}

		// creates all GA paramenter rectangles
		for(int btn = 0; btn < allButtons.length; btn++) {
			allButtons[btn] = new Rectangle(buttonsPositions[btn][0], buttonsPositions[btn][1]+buttonOffsetY, plusMinusButtonWidth, plusMinusButtonWidth);
		}

        // init elementsY
        for (int rect=0; rect < elementsY.length; rect++){
            elementsY[rect] = getY()+(rect*listItemHeight);
        }
	}
	
	//clear array and other values
	public boolean contains(boolean[] arr) {
		for(int i=0;i<arr.length;i++) {
			if(arr[i]==true) {return true;}
		}
		return false;
	}	
	public int firstOccurance(boolean[] arr) {
		for(int i=0;i<arr.length;i++) {
			if(arr[i]==true) {return i;}
		}
		return 0;
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
                }
            }
            // check for dynamic elements
            if(!found){
                for(int dyn=0; dyn < dynamicMapElements.length; dyn++){
                    if(mouseClickedY >= elementsY[staticMapElements.length+dyn+2] && mouseClickedY <= elementsY[staticMapElements.length+dyn+2]+listItemHeight){
                        System.out.println(dynamicMapElements[dyn].getMapType().name());
                        found = true;
                        return dynamicMapElements[dyn];
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
     *          2 - speed increase
     *          3 - speed decrease
     *          4 - number of moves increase
     *          5 - number of moves decrease
     *          6 - mutation rate increase
     *          7 - mutation rate decrease
     *          8 - number of generations increase
     *          9 - number of generations decrease
     */
    public int getParameterChanges(){
        // only change parameters in game mode or if mouse is clicked
        if(Main.MODE != 0 || !getInputManager().isMouseClicked()) return -1;

        //population size changes
        if(isSizePlusButtonClicked(getInputManager().getMouseClickedX(), 
                    getInputManager().getMouseClickedY()) 
                && game.getPopulationSize()<=Constants.MAX_POPULATION_SIZE){
            return 0;
        }
        else if(isSizeMinusButtonClicked(getInputManager().getMouseClickedX(), 
                    getInputManager().getMouseClickedY()) && game.getPopulationSize() > 1){
            return 1;
        }
        
        //speed changes
        if(isSpeedPlusButtonClicked(getInputManager().getMouseClickedX(), 
                    getInputManager().getMouseClickedY()) && game.getSpeed()<=Constants.MAX_SPEED){
            return 2;
        }
        else if(isSpeedMinusButtonClicked(getInputManager().getMouseClickedX(), 
                    getInputManager().getMouseClickedY()) && game.getSpeed() > 1){
            return 3;
        }

        //number of moves changes
        else if(isMovePlusButtonClicked(getInputManager().getMouseClickedX(), 
                    getInputManager().getMouseClickedY()) && game.getNoOfMoves() <= Constants.MAX_NO_OF_MOVES){
            return 4;
        }
        else if(isMoveMinusButtonClicked(getInputManager().getMouseClickedX(), 
                    getInputManager().getMouseClickedY()) && game.getNoOfMoves() > 1){
            return 5;
        }

        // mutation rate changes
        else if(isRatePlusButtonClicked(getInputManager().getMouseClickedX(), 
                    getInputManager().getMouseClickedY()) && game.getMutationRate() <= Constants.MAX_MUTATION_RATE){
            return 6;
        }
        else if(isRateMinusButtonClicked(getInputManager().getMouseClickedX(), 
                    getInputManager().getMouseClickedY()) && game.getMutationRate() > 1){
            return 7;
        }
        
        //number of generations changes
        else if(isGenerationPlusButtonClicked(getInputManager().getMouseClickedX(), 
                    getInputManager().getMouseClickedY()) &&  game.getNoOfGenerations() <= Constants.MAX_NO_OF_GENERATIONS){
            return 8;
        }
        else if(isGenerationMinusButtonClicked(getInputManager().getMouseClickedX(), 
                    getInputManager().getMouseClickedY()) &&  game.getNoOfGenerations() > 1){
            return 9;
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
		// decide whether to draw list with map-elements or configurations for AI game-play
		switch (Main.MODE){
			case 2:
				drawParametersList(graphics);
                break;
			case 1:
				drawMapBuilderList(graphics);
				break;
			case 0:
				drawAIGameModeList(graphics);
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
			//int textIndent = 15;
			
			//draw background
			g2d.setColor(Constants.COLOR_RIGHT_BAR_HEADER);
			g2d.fillRect(itemX, itemY, Constants.WINDOW_RIGHT_BAR_WIDTH, itemHeight);
			//draw text
			g2d.setColor(Constants.COLOR_AVATAR_WHITE);
			g2d.setFont(font);
			
			// Determine the Y coordinate for the text (note we add the ascent, as in java 2d 0 is top of the screen)
		    int theY = itemY + ((itemHeight - g2d.getFontMetrics(font).getHeight()) / 2) + g2d.getFontMetrics(font).getAscent();
			g2d.drawString(name, itemX+listItemIndentX, theY);
		    
		} else {
			//TODO: these variables should be added to Constants
			int elementWidth = 20;
			int textIndent = listItemIndentX+35;
			int x = itemX+listItemIndentX; 
			int y = itemY + (itemHeight/2) - (elementWidth/2);

			//draw background
			g2d.setColor(Constants.COLOR_MAP_LAND);
			g2d.fillRect(itemX, itemY, Constants.WINDOW_RIGHT_BAR_WIDTH, itemHeight);

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

    /**
     * draws the parameter list along with other options shown in right bar during game mode
     *
     * @author Rahul
     * @author Patrick
     *
     * @param graphics
     *
     */
    private void drawParametersList(Graphics graphics){
        for (int para = 0; para < gameParameters.length; para++) {
            drawParameter(graphics, para, (para * parameterHeight) + parametersStartY);
        }
        drawNumberOfGenerationString(graphics);
        drawFittestString(graphics);
        drawRecordTime(graphics);
        drawCheckboxes(graphics);
    }
    
    private void drawAIGameModeList(Graphics graphics){
		graphics.setColor(Constants.COLOR_AVATAR_WHITE);
		graphics.setFont(font);
    	graphics.drawString("Population Size: "+String.format("%1$9s",game.getPopulationSize())+"x", minusButtonX+rightBarStartX, parametersStartY+rightBarOffestY+betweenStrings*4);
    	graphics.drawString("Speed: "+String.format("%1$27s",game.getSpeed())+"x", minusButtonX+rightBarStartX, parametersStartY+rightBarOffestY+betweenStrings*1);
    	graphics.drawString("Maximum Moves: "+String.format("%1$9s",game.getNoOfMoves()+"x"), minusButtonX+rightBarStartX,parametersStartY+rightBarOffestY+betweenStrings*3);
    	graphics.drawString("Mutation Rate: "+String.format("%1$13s",game.getMutationRate()+"x"), minusButtonX+rightBarStartX,  parametersStartY+rightBarOffestY+betweenStrings*2);
    	graphics.drawString("Max Generations: "+String.format("%1$9s",game.getNoOfGenerations()+"x"), minusButtonX+rightBarStartX,  parametersStartY+rightBarOffestY+betweenStrings*5);
    	graphics.drawString("Current Move: "+game.getCurentMoove(), minusButtonX+rightBarStartX,  parametersStartY+betweenStrings*10);
    	drawBarMoves(graphics);
    	drawBar(graphics);
    	drawNumberOfGenerationString(graphics);
        drawFittestString(graphics);
        drawRecordTime(graphics);
        drawCheckboxes(graphics);
		drawChart(graphics);
    }
    
    public void drawBar(Graphics graphics) {
    	int current =game.getPopulation().getCurrentGeneration();
    	int total = game.getNoOfGenerations();

    	for (int i=0; i<game.getNoOfGenerations();i++) {
    		if (i==current) {finished[i]=game.hasFinished();}
    		if(i<current) {
    			if(finished[i]==true) {graphics.setColor(Color.GREEN);}
    			if(finished[i]==false) {graphics.setColor(Color.RED);}
    		}
    		else {
    			graphics.setColor(Color.BLACK);
    		}
    	graphics.fillRect(rightBarStartX+9+200*i/total, parametersStartY+betweenStrings*7+15, 200/total, 15);
    	}
    	
    }
    
    public void drawBarMoves(Graphics graphics) {
    		graphics.setColor(Color.GREEN);
    		graphics.fillRect(rightBarStartX+9, parametersStartY+betweenStrings*9-8, 200*game.getCurentMoove()/game.getMaxNrOfMoves(), 15);
			graphics.setColor(Color.BLACK);
    		graphics.fillRect(rightBarStartX+9+200*game.getCurentMoove()/game.getMaxNrOfMoves(), parametersStartY+betweenStrings*9-8, 200-200*game.getCurentMoove()/game.getMaxNrOfMoves(), 15);
    }

    /**
     * draws number of tries string in right bar during game mode
     *
     * @author Rahul
     * @author Patrick
     *
     */
	private void drawNumberOfGenerationString(Graphics graphics){
		graphics.setColor(Constants.COLOR_AVATAR_WHITE);
		graphics.setFont(font);
		String numberOfGeneration = numOfGen+ String.format("%05d", game.getPopulation().getCurrentGeneration())+" out of  "+String.format("%05d",game.getNoOfGenerations());
	//	graphics.drawString(correctStringLength(line), minusButtonX+rightBarStartX, parametersStartY+rightBarOffestY+betweenStrings*7);
		graphics.drawString(numberOfGeneration, minusButtonX+rightBarStartX,  parametersStartY+betweenStrings*8+15);
	}

	private void drawFittestString(Graphics graphics){
		graphics.setColor(Constants.COLOR_AVATAR_WHITE);
		graphics.setFont(font);
		String maxFitness = maxFit+Math.round(game.getPopulation().getMaxFitness());
	//	graphics.drawString(correctStringLength(line), minusButtonX+rightBarStartX, (gameParameters.length * parameterHeight) + parametersStartY+10);
		graphics.drawString(correctStringLength(maxFitness), minusButtonX+rightBarStartX,  parametersStartY+rightBarOffestY+betweenStrings*9+5);

	}
	
	private void drawRecordTime(Graphics graphics){
		graphics.setColor(Constants.COLOR_AVATAR_WHITE);
		graphics.setFont(font);
		String recordTime = "Best Time: "+ String.format("%1$7s", game.getRecordTime())+" moves";
	//	graphics.drawString(correctStringLength(line), minusButtonX+rightBarStartX, (gameParameters.length * parameterHeight) + parametersStartY+47);
		graphics.drawString(recordTime, minusButtonX+rightBarStartX,  parametersStartY+rightBarOffestY+betweenStrings*10);

	}
	
	

	public String correctStringLength(String str) {
		if (str.length()>maxChar) {str=str.substring(0, maxChar);}
		return str;
	}

    /**
     * TODO: draw checkboxes below parameters
     *
     * @author Patrick
     */
    private void drawCheckboxes(Graphics graphics){
        //draw checkbox for showing only fittest genome
    }

    /**
     * draws a parameter item
     *
     * @author Rahul
     * @author Patrick
     *
     * @param graphics
     * @param type 0=population_size, 1=speed, 2=no_of_moves, 3=mutation_rate, 4=no_of_generations
     * @param y start y position of parameter
     *
     */
	public void drawParameter(Graphics graphics, int type, int y){
		Graphics2D g2d = (Graphics2D)graphics;
        //draw header
        g2d.setColor(Constants.COLOR_RIGHT_BAR_HEADER);
        g2d.fillRect(rightBarStartX, y-(parameterHeight/3), Constants.WINDOW_RIGHT_BAR_WIDTH, (int)((float)parameterHeight/1.75)-5);

        graphics.setColor(Constants.COLOR_AVATAR_WHITE);
		graphics.setFont(font);
		graphics.drawString(gameParameters[type], rightBarStartX+parameterStringX, y);

        // draw parameter
		int beautyFactor = (plusMinusButtonWidth/2) / 2;
        graphics.drawString(minusString, 
                rightBarStartX+minusButtonX+((plusMinusButtonWidth/2) - (fontSize / 8)), 
                (y+buttonOffsetY) + ((plusMinusButtonWidth/2)+beautyFactor));
        graphics.drawString(plusString, 
                rightBarStartX+plusButtonX+((plusMinusButtonWidth/2) - 4), 
                (y+buttonOffsetY) + ((plusMinusButtonWidth/2)+beautyFactor));
        graphics.drawOval(rightBarStartX+minusButtonX, y+buttonOffsetY, plusMinusButtonWidth, plusMinusButtonWidth);
        graphics.drawOval(rightBarStartX+plusButtonX, y+buttonOffsetY, plusMinusButtonWidth, plusMinusButtonWidth);

        //draw the value of each parameter
        int parameterX = rightBarStartX+((minusButtonX+plusButtonX) / 2 + (fontSize / 2));
        switch (type){
        case 0:
            graphics.drawString(game.getPopulationSize()+"x", parameterX, (y+buttonOffsetY)+((plusMinusButtonWidth/2)+beautyFactor));
            break;
        case 1: 
            graphics.drawString(game.getSpeed()+"x", parameterX, (y+buttonOffsetY)+((plusMinusButtonWidth/2)+beautyFactor));
            break;
        case 2: 
            graphics.drawString(game.getNoOfMoves()+"x", parameterX, (y+buttonOffsetY)+((plusMinusButtonWidth/2)+beautyFactor));
            break;
        case 3:
            graphics.drawString(game.getMutationRate()+"x", parameterX, (y+buttonOffsetY)+((plusMinusButtonWidth/2)+beautyFactor));
            break;
        default: 
            graphics.drawString(game.getNoOfGenerations()+"x", parameterX, (y+buttonOffsetY)+((plusMinusButtonWidth/2)+beautyFactor));
            break;
    }
        }
          
	


    public void setGame(Game game){this.game = game;}

	// to return if the buttons clicked
	public boolean isSizeMinusButtonClicked(int mouseClickedX, int mouseClickedY) {
		return allButtons[0].contains(mouseClickedX, mouseClickedY);
	}
	public boolean isSizePlusButtonClicked(int mouseClickedX, int mouseClickedY) {
		return allButtons[1].contains(mouseClickedX, mouseClickedY);
	}
	
    public boolean isSpeedMinusButtonClicked(int mouseClickedX, int mouseClickedY) {
		return allButtons[2].contains(mouseClickedX, mouseClickedY);
	}
	public boolean isSpeedPlusButtonClicked(int mouseClickedX, int mouseClickedY) {
		return allButtons[3].contains(mouseClickedX, mouseClickedY);
	}

	public boolean isMoveMinusButtonClicked(int mouseClickedX, int mouseClickedY) {
		return allButtons[4].contains(mouseClickedX, mouseClickedY);
	}
	public boolean isMovePlusButtonClicked(int mouseClickedX, int mouseClickedY) {
		return allButtons[5].contains(mouseClickedX, mouseClickedY);
	}

	public boolean isRateMinusButtonClicked(int mouseClickedX, int mouseClickedY) {
		return allButtons[6].contains(mouseClickedX, mouseClickedY);
	}
	public boolean isRatePlusButtonClicked(int mouseClickedX, int mouseClickedY) {
		return allButtons[7].contains(mouseClickedX, mouseClickedY);
	}

	public boolean isGenerationMinusButtonClicked(int mouseClickedX, int mouseClickedY) {
		return allButtons[8].contains(mouseClickedX, mouseClickedY);
	}
	public boolean isGenerationPlusButtonClicked(int mouseClickedX, int mouseClickedY) {
		return allButtons[9].contains(mouseClickedX, mouseClickedY);
	}
	public void drawChart(Graphics graphics) {
		int current =game.getPopulation().getCurrentGeneration();
		if(current>200) {
			current=current%200;
		}
		int newRM=game.getRecordTime();
		if (newRM>1000) {
			newRM=1000;
		}
		list.add(newRM);
		int width=200/game.getNoOfGenerations();
		if (width<1) {
			width=1;
		}
		oldRM=Collections.max(list);
		if (contains(finished)) {
			int i =firstOccurance(finished);
			oldRM=hlist.get(i);
		}
		int height=newRM*200/oldRM;	
		if (height >200) {
			height=200;
		}
		int loops=game.getNoOfGenerations();
		if (loops>200) {
			loops=200;
		}
		if (list.size()<loops) {
			for (int i=0;i<loops;i++) {
				list.add(i, height);
			}
		}
		for (int i=0; i<loops;i++) {
    		if (i==current) {
    			hlist.add(i,height);}
    		if(i<=current) {
    			if (finished[i]==false) {
    				graphics.setColor(Constants.COLOR_RIGHT_BAR_HEADER);
    			}
    			else {
    				graphics.setColor(Color.GREEN);
    			}
				int a=200-hlist.get(i);
				graphics.fillRect(rightBarStartX+10+width*i, parametersStartY+430+a, width, hlist.get(i));
			}
    	}
		graphics.setColor(Color.WHITE);
		graphics.drawString("Visualisation of Genes", rightBarStartX+15, 630);
		graphics.drawString("      (smaller => faster)", rightBarStartX+20, 655);
	}
}
