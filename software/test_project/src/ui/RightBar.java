package ui;

import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.util.ArrayList;

import functionality.Constants;
import functionality.Setup;
import map_builder.ElementBlackHole;
import map_builder.ElementFinish;
import map_builder.ElementLaser;
import map_builder.ElementPlasmaBall;
import map_builder.ElementStart;
import map_builder.ElementWall;
import map_builder.MapElement;
import map_builder.MapType;

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
	
	private MapElement[] staticMapElements = new MapElement[]{
			new ElementStart(0, 0, MapType.START, Constants.COLOR_ACCENT),
			new ElementFinish(0, 0, MapType.FINISH, Constants.COLOR_ACCENT),
			new ElementWall(0,0, Constants.COLOR_ACCENT),
			new ElementBlackHole(0, 0, MapType.BLACK_HOLE, Constants.COLOR_ACCENT)
	}; 
	private MapElement[] dynamicMapElements = new MapElement[]{
//			new ElementEnemy(0, 0, MapType.ENEMY, Constants.COLOR_MAP_START),
			new ElementLaser(0, 0, MapType.LASER, Constants.COLOR_ACCENT_2),
			new ElementPlasmaBall(0,0, MapType.PLASMA_BALL, Constants.COLOR_ACCENT_2)
	}; 
	
	private String[] staticNames = new String[]{"Start", "Finish", "Wall", "Black Hole"};
	private String[] dynamicNames = new String[]{/*"Enemy", */"Laser", "Plasma Ball"};
	
	final String[] headers = new String[]{"Static", "Dynamic"};
	
	final int listHeaderHeight = 50;
	final int listItemHeight = 50;
	
	int fontSize = 16;
	Font font = new Font(Constants.DEFAULT_FONT, Font.PLAIN, fontSize);

	public RightBar(int x, int y, int width, int height, Color backgroundColor, Setup setup) {
		super(x, y, width, height, backgroundColor, setup);
		
		
	}
	
	@Override
	public void draw(Graphics graphics) {
		drawBackground(graphics);
		
		boolean mapBuildingMode = true;
		
		if (mapBuildingMode){
			drawMapBuilderList(graphics);
		}
	}
	
	private void drawBackground(Graphics graphics){
		graphics.setColor(getBackgroundColor());
		graphics.fillRect(getX(), getY(), getWidth(), getHeight());
	}
	
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
		    
//			graphics.drawString(name, itemX+textIndent, itemY+(itemHeight/2)-(fontSize/2));
//			System.out.println("header y-start:"+itemY+", header-height: "+itemHeight+", fontSize: "+fontSize+
//					", middle: "+(itemY+(itemHeight/2)-(fontSize/2)));
		} else {
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
			int theY = itemY + ((itemHeight - graphics.getFontMetrics(font).getHeight()) / 2) + graphics.getFontMetrics(font).getAscent();
			graphics.drawString(name, itemX+textIndent, theY);
		}
		
		// draw separator line at bottom of item
		graphics.setColor(Constants.COLOR_RIGHT_BAR_HEADER);
		// -1 due to stroke width of line
		graphics.drawLine(itemX, itemY+itemHeight-1, itemX+Constants.WINDOW_RIGHT_BAR_WIDTH, itemY+itemHeight-1);
	}

}
