package ui;

import java.awt.Color;
import java.awt.Font;
import java.awt.GradientPaint;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.RenderingHints;
import java.awt.geom.Rectangle2D;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.geom.RoundRectangle2D;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import javax.imageio.ImageIO;

import functionality.Constants;
import functionality.FontLoader;
import functionality.InputManager;
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

    // icons refer to circular images in top right
    int iconWidth = 28;

    //TODO: these should be moved to Constants
	int BUTTON_SAVE_X = 400, BUTTON_SAVE_Y =35;
	int BUTTON_LOAD_X = 320, BUTTON_LOAD_Y =35;
    int BUTTON_OFFSET_X=15, BUTTON_OFFSET_Y=20;
    int BUTTON_EXIT_X = Constants.WINDOW_MAP_WIDTH + (Constants.WINDOW_MAP_MARGIN * 2) + Constants.WINDOW_RIGHT_BAR_WIDTH - (iconWidth*2);
    int BUTTON_EXIT_Y = ((Constants.WINDOW_HEADER_HEIGHT/2) - (iconWidth/2));

    int BUTTON_BUILD_X = BUTTON_EXIT_X - (iconWidth*3) + 10;
    int BUTTON_BUILD_Y = ((Constants.WINDOW_HEADER_HEIGHT/2) - (iconWidth/2));
	
    int BUTTON_GAMEPLAY_X = BUTTON_BUILD_X - (iconWidth*3) + 10;
    int BUTTON_GAMEPLAY_Y = ((Constants.WINDOW_HEADER_HEIGHT/2) - (iconWidth/2));

    // directory name where images should be loaded from 
    String dirName = "../resources/";
    String pathExitButton = "exit.png";
    String pathGameplayButton = "play_game.png";
    String pathBuilButton = "build_mode.png";
	
    BufferedImage exitImg;
    BufferedImage gameplayImg;
    BufferedImage buildImg;
	
    // arch width and height of rounded corner of a button
	int BUTTON_ARCH_WH = 30;
	int BUTTON_WIDTH = 70, BUTTON_HEIGHT =30;

	int mapNameX=100;

	int fontSize = 16;
    Font font = new Font(Constants.DEFAULT_FONT, Font.PLAIN, fontSize);
	//Font font = FontLoader.getFont("ChakraPetch-SemiBold.ttf"); //TODO: not laoding for everybody
	String mapName;

	private RoundRectangle2D saveButton = new RoundRectangle2D.Float(
                BUTTON_SAVE_X-BUTTON_OFFSET_X, BUTTON_SAVE_Y-BUTTON_OFFSET_Y,
			    BUTTON_WIDTH, BUTTON_HEIGHT, BUTTON_ARCH_WH, BUTTON_ARCH_WH
            );
	private RoundRectangle2D loadButton = new RoundRectangle2D.Float(
                BUTTON_LOAD_X-BUTTON_OFFSET_X,
                BUTTON_LOAD_Y-BUTTON_OFFSET_Y,
                BUTTON_WIDTH,BUTTON_HEIGHT,BUTTON_ARCH_WH,BUTTON_ARCH_WH
            );

	public TopBar(int x, int y, int width, int height, Color backgroundColor, Setup setup, InputManager inputManager, String mapName) {
		super(x, y, width, height, backgroundColor, setup, inputManager);
		this.mapName = mapName;

        try {
            exitImg = ImageIO.read(new File(dirName, pathExitButton));
            gameplayImg = ImageIO.read(new File(dirName, pathGameplayButton));
            buildImg = ImageIO.read(new File(dirName, pathBuilButton));
		} catch (Exception e) {
			e.printStackTrace();
		}

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
        drawButtons(graphics);
		//draw other UI-elements here...
	}

    private void drawButtons(Graphics graphics){
		drawLoadButton(graphics);
		drawSaveButton(graphics);

        drawExitButton(graphics);
        drawGameplayButton(graphics);
        drawBuildButton(graphics);
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
		
		int gameNameX = Constants.WINDOW_MAP_MARGIN / 2;
		// Determine the Y coordinate for the text (note we add the ascent, as in java 2d 0 is top of the screen)
	    int gameNameY = getY() + ((getHeight() - graphics.getFontMetrics(font).getHeight()) / 2)
	    		+ graphics.getFontMetrics(font).getAscent();
		graphics.drawString(Constants.GAME_NAME, gameNameX, gameNameY);
	}

	/**
	 * draw the time right of the seperator
	 *
	 * @param graphics
	 */
	private void drawMapName(Graphics graphics){
		graphics.setColor(Constants.COLOR_AVATAR_WHITE);
		graphics.setFont(font);

		// Determine the Y coordinate for the text (note we add the ascent, as in java 2d 0 is top of the screen)
		int mapNameY = getY() + ((getHeight() - graphics.getFontMetrics(font).getHeight()) / 2)
				+ graphics.getFontMetrics(font).getAscent();
		graphics.drawString("Map name: " + mapName, mapNameX, mapNameY);
	}

	/**
	 * draw the time right of the seperator
	 *
	 * @param graphics
	 */
	private void drawSaveButton(Graphics graphics){
		graphics.setColor(Constants.COLOR_AVATAR_WHITE);
		graphics.setFont(font);

		// Determine the Y coordinate for the text (note we add the ascent, as in java 2d 0 is top of the screen)
		BUTTON_SAVE_Y= getY() + ((getHeight() - graphics.getFontMetrics(font).getHeight()) / 2)
				+ graphics.getFontMetrics(font).getAscent();
		graphics.drawString("SAVE", BUTTON_SAVE_X, BUTTON_SAVE_Y);
		graphics.drawRoundRect(BUTTON_SAVE_X-BUTTON_OFFSET_X,BUTTON_SAVE_Y-BUTTON_OFFSET_Y,BUTTON_WIDTH,BUTTON_HEIGHT,BUTTON_ARCH_WH,BUTTON_ARCH_WH);
	}

	/**
	 * draw the time right of the seperator
	 *
	 * @param graphics
	 */
	private void drawLoadButton(Graphics graphics){
		graphics.setColor(Constants.COLOR_AVATAR_WHITE);
		graphics.setFont(font);
		// Determine the Y coordinate for the text (note we add the ascent, as in java 2d 0 is top of the screen)
		BUTTON_LOAD_Y = getY() + ((getHeight() - graphics.getFontMetrics(font).getHeight()) / 2)
				+ graphics.getFontMetrics(font).getAscent();
		graphics.drawString("LOAD", BUTTON_LOAD_X, BUTTON_LOAD_Y);
		graphics.drawRoundRect(BUTTON_LOAD_X-BUTTON_OFFSET_X,BUTTON_LOAD_Y-BUTTON_OFFSET_Y,BUTTON_WIDTH,BUTTON_HEIGHT,BUTTON_ARCH_WH,BUTTON_ARCH_WH);
	}

    // Draws the settings button
	private void drawExitButton(Graphics graphics){
		graphics.setColor(Constants.COLOR_AVATAR_WHITE);
        //int offset = 5;
        //graphics.drawOval(BUTTON_EXIT_X-offset, BUTTON_EXIT_Y-offset, iconWidth+(offset*2), iconWidth+(offset*2));

        try {
		    graphics.drawImage(exitImg, BUTTON_EXIT_X, BUTTON_EXIT_Y, iconWidth, iconWidth, null);
        } catch (Exception e){
            e.printStackTrace();
        }
	}
    // Draws the game play button
	private void drawGameplayButton(Graphics graphics){
		graphics.setColor(Constants.COLOR_BACKGROUND);
        try {
		    graphics.drawImage(gameplayImg, BUTTON_GAMEPLAY_X, BUTTON_GAMEPLAY_Y, iconWidth, iconWidth, null);
        } catch (Exception e){
            e.printStackTrace();
        }
	}
    
    // Draws the build mode button
	private void drawBuildButton(Graphics graphics){
		graphics.setColor(Constants.COLOR_BACKGROUND);
        try {
		    graphics.drawImage(buildImg, BUTTON_BUILD_X, BUTTON_BUILD_Y, iconWidth, iconWidth, null);
        } catch (Exception e){
            e.printStackTrace();
        }
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

	/**
	 * Takes away .csv extention from a filename for The MapName at the TopBar
	 * */
	public void setMapName(String mapName) {
		this.mapName=mapName;
	}

    public RoundRectangle2D getSaveButton() {
        return saveButton;
    }

    public RoundRectangle2D getLoadButton() {
        return loadButton;
    }
}

