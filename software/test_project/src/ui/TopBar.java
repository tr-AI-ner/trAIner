package ui;

import java.awt.Color;
import java.awt.Font;
import java.awt.GradientPaint;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.RenderingHints;
import java.awt.geom.Rectangle2D;

import functionality.Constants;
import functionality.FontLoader;
import functionality.Setup;

import ui.UIElement;

/**
 * this will be the header of the game
 * & should draw 2 states:
 * 		- AI/game mode
 * 		- map-building mode
 * 
 * @author Patrick
 *
 */
public class TopBar extends UIElement {
	
	int fontSize = 16;
	Font font = FontLoader.getFont("ChakraPetch-SemiBold.ttf");
	String mapName;

	public TopBar(int x, int y, int width, int height, Color backgroundColor, Setup setup, String mapName) {
		super(x, y, width, height, backgroundColor, setup);
		this.mapName = mapName;
	}
	
	/**
	 * draw top-bar with background & other UI elements
	 * 
	 */
	@Override
	public void draw(Graphics graphics) {
		drawBackground(graphics);
		
		drawGameName(graphics);
		
		drawSeparator(graphics);

		drawMapName(graphics);

		drawLoadButton(graphics);

		drawSaveButton(graphics);
		//draw other UI-elements here...
	}
	
	/**
	 * draw top-bar background with gradients 
	 * 
	 * procedure:
	 * 		draw first gradient to half-screen-size & then inverse gradient 
	 * 		& draw the second gradient on remaining screen
	 * 
	 */
	private void drawBackground(Graphics graphics){
		Graphics2D g2d = (Graphics2D) graphics;
		g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);

		int startX = 0, startY = 0, endX = (getSetup().getFrameWidth())/2, endY = Constants.WINDOW_HEADER_HEIGHT;

		// draw first gradient on first half of window
		GradientPaint theGradient = new GradientPaint(startX, startY, Constants.COLOR_HEADER_1, endX, endY,
				Constants.COLOR_HEADER_2);
		g2d.setPaint(theGradient);
		g2d.fill(new Rectangle2D.Double(startX, startY, endX, endY));

		// draw second gradient on other half of window
		startX = endX; endX = endX * 2;
		theGradient = new GradientPaint(startX, startY, Constants.COLOR_HEADER_2, endX, endY,
				Constants.COLOR_HEADER_1);
		g2d.setPaint(theGradient);
		g2d.fill(new Rectangle2D.Double(startX, startY, endX, endY));
	}
	
	/**
	 * draws the game name on the top bar (top left)
	 * 
	 * @param graphics
	 */
	private void drawGameName(Graphics graphics){
		graphics.setColor(Constants.COLOR_AVATAR_WHITE);
		graphics.setFont(font);
		
		int copyX = Constants.WINDOW_MAP_MARGIN / 2;
		// Determine the Y coordinate for the text (note we add the ascent, as in java 2d 0 is top of the screen)
	    int copyY = getY() + ((getHeight() - graphics.getFontMetrics(font).getHeight()) / 2)
	    		+ graphics.getFontMetrics(font).getAscent();
		graphics.drawString(Constants.GAME_NAME, copyX, copyY);
	}

	/**
	 * draw the time right of the seperator
	 *
	 * @param graphics
	 */
	private void drawMapName(Graphics graphics){
		graphics.setColor(Constants.COLOR_AVATAR_WHITE);
		graphics.setFont(font);

		int copyX = 95;
		// Determine the Y coordinate for the text (note we add the ascent, as in java 2d 0 is top of the screen)
		int copyY = getY() + ((getHeight() - graphics.getFontMetrics(font).getHeight()) / 2)
				+ graphics.getFontMetrics(font).getAscent();
		graphics.drawString("Map name: " + mapName, copyX, copyY);
	}
	/**
	 * draw the time right of the seperator
	 *
	 * @param graphics
	 */
	private void drawLoadButton(Graphics graphics){
		graphics.setColor(Constants.COLOR_AVATAR_WHITE);
		graphics.setFont(font);

		int copyX = 280;
		// Determine the Y coordinate for the text (note we add the ascent, as in java 2d 0 is top of the screen)
		int copyY = getY() + ((getHeight() - graphics.getFontMetrics(font).getHeight()) / 2)
				+ graphics.getFontMetrics(font).getAscent();
		graphics.drawString("SAVE", copyX, copyY);
		graphics.drawRoundRect(copyX-15,copyY-20,70,30,30,30);
	}
	/**
	 * draw the time right of the seperator
	 *
	 * @param graphics
	 */
	private void drawSaveButton(Graphics graphics){
		graphics.setColor(Constants.COLOR_AVATAR_WHITE);
		graphics.setFont(font);

		int copyX = 360;
		// Determine the Y coordinate for the text (note we add the ascent, as in java 2d 0 is top of the screen)
		int copyY = getY() + ((getHeight() - graphics.getFontMetrics(font).getHeight()) / 2)
				+ graphics.getFontMetrics(font).getAscent();
		graphics.drawString("LOAD", copyX, copyY);
		graphics.drawRoundRect(copyX-15,copyY-20,70,30,30,30);
	}

	/**
	 * draw the separator line at the top-bar (to the right of the game name)
	 * 
	 * TODO: currently only some temporary numbers are used, this should be changed 
	 * @param graphics
	 */
	private void drawSeparator(Graphics graphics) {
		graphics.setColor(Constants.COLOR_AVATAR_WHITE);
		int x1 = 80; 
		int y1 = Constants.WINDOW_HEADER_HEIGHT / 3;
		int y2 = Constants.WINDOW_HEADER_HEIGHT - (y1);
		graphics.drawLine(x1, y1, x1, y2);
	}

}
