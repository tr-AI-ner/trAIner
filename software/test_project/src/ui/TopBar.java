package ui;

import java.awt.Color;
import java.awt.Font;
import java.awt.GradientPaint;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.RenderingHints;
import java.awt.geom.Rectangle2D;

import functionality.Constants;
import functionality.Setup;

import ui.UIElement;

public class TopBar extends UIElement {
	
	int fontSize = 17;

	public TopBar(int x, int y, int width, int height, Color backgroundColor, Setup setup) {
		super(x, y, width, height, backgroundColor, setup);
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
	
	private void drawGameName(Graphics graphics){
		graphics.setColor(Constants.COLOR_AVATAR_WHITE);
		graphics.setFont(new Font(Constants.DEFAULT_FONT, Font.PLAIN, fontSize));
		
		int copyX = Constants.WINDOW_MAP_MARGIN / 2;
		int copyY = (getHeight()/2) + (fontSize/2);
		graphics.drawString(Constants.GAME_NAME, copyX, copyY);
	}
	
	private void drawSeparator(Graphics graphics) {
		graphics.setColor(Constants.COLOR_AVATAR_WHITE);
		int x1 = 80; 
		int y1 = Constants.WINDOW_HEADER_HEIGHT / 3;
		int y2 = Constants.WINDOW_HEADER_HEIGHT - (y1);
		graphics.drawLine(x1, y1, x1, y2);
	}

}
