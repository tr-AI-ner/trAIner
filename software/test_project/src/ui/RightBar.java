package ui;

import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.geom.Rectangle2D;
import java.awt.geom.RoundRectangle2D;
import java.util.ArrayList;
import java.util.List;

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
	
	private String[] gameParameters = new String[]{
			"Population Size","Number Of Moves","Mutation Rate","Number Of Generations"
	};
	private CustomSlider[] sliders;
	
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

	// rectangles of map items
    List<Rectangle2D> itemList = new ArrayList<>();


	public RightBar(int x, int y, int width, int height, Color backgroundColor, Setup setup) {
		super(x, y, width, height, backgroundColor, setup);
		
		int sliderWidth = 170, sliderHeight = 5;
		
		sliders = new CustomSlider[]{
				new CustomSlider(getX()+(getWidth()/2)-(sliderWidth/2), 
						getY()+150, 
						sliderWidth, sliderHeight, getBackgroundColor(), getSetup())
		};
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
		if (Main.MODE == 1){
			drawMapBuilderList(graphics);
		} else {
			drawGamePlayList(graphics);
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
	 * TODO: draw everything that is needed for the game-play mode
	 * @param graphics
	 */
	private void drawGamePlayList(Graphics graphics){
		//parameters: no_of_moves, population_size, mutation_rate, no_of_generations
		
//		int sliderWidth = 170, sliderHeight = 5;
//		JSlider slider = new JSlider(JSlider.HORIZONTAL, 0, 50, 25);
//		graphicsManager.setLayout(null);
//		graphicsManager.add(slider);
//		
//		slider.setBounds(getX()+(getWidth()/2)-(sliderWidth/2), 
//				getY()+150, 
//				sliderWidth, 10);
		
		for (int i=0; i<sliders.length; i++){
			sliders[i].draw(graphics);
		}
		
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

    public List<Rectangle2D> getItemList() {
        return itemList;
    }
}
