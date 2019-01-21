package ui;

import java.awt.*;
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
import game.Game;
import game.GameMode;
import ui.UIElement;
import map_saver.MapSaverLoader;

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
	int mapNameX=270;
	int BUTTON_SAVE_X = 200, BUTTON_SAVE_Y =35;
	int BUTTON_LOAD_X = 120, BUTTON_LOAD_Y =35;
    int BUTTON_OFFSET_X=15, BUTTON_OFFSET_Y=20;

    int BUTTONS_Y = ((Constants.WINDOW_HEADER_HEIGHT/2) - (iconWidth/2)) - 3;
    int BUTTON_BUILD_X = Constants.WINDOW_MAP_WIDTH + (Constants.WINDOW_MAP_MARGIN * 2) + Constants.WINDOW_RIGHT_BAR_WIDTH - (iconWidth*2);
    int BUTTON_GAMEPLAY_X = BUTTON_BUILD_X - (iconWidth*3) + 10;
    int BUTTON_BRAIN_X = BUTTON_GAMEPLAY_X - (iconWidth*3) + 10;

    // directory name where images should be loaded from 
    String dirName = "../resources/";
    String pathGameplayInactiveButton = "play_game_inactive.png", pathGameplayActiveButton = "play_game_active.png";
    String pathBuildInactiveButton = "build_mode_inactive.png", pathBuildActiveButton = "build_mode_active.png";
    String pathBrainInactiveButton = "brain_inactive.png", pathBrainActiveButton = "brain_active.png";
	
    BufferedImage gameplayInactiveImg, gameplayActiveImg, buildInactiveImg, buildActiveImg, brainInactiveImg, brainActiveImg;

    Rectangle gamePlayButton = new Rectangle(BUTTON_GAMEPLAY_X, BUTTONS_Y, iconWidth, iconWidth);
    Rectangle buildButton = new Rectangle(BUTTON_BUILD_X, BUTTONS_Y, iconWidth, iconWidth);
    Rectangle brainButton = new Rectangle(BUTTON_BRAIN_X, BUTTONS_Y, iconWidth, iconWidth);
	
	int fontSize = 16;
    Font font = new Font(Constants.DEFAULT_FONT, Font.PLAIN, fontSize);
	//Font font = FontLoader.getFont("ChakraPetch-SemiBold.ttf"); //TODO: not loading for everybody
	String mapName;

    // whether load-map-window should be shown
    boolean shouldLoadMap = false;

	private RoundRectangle2D saveButton = new RoundRectangle2D.Float(
                BUTTON_SAVE_X-BUTTON_OFFSET_X, BUTTON_SAVE_Y-BUTTON_OFFSET_Y,
			    Constants.BUTTON_WIDTH, Constants.BUTTON_HEIGHT, Constants.BUTTON_ARCH_WH, Constants.BUTTON_ARCH_WH
            );
	private RoundRectangle2D loadButton = new RoundRectangle2D.Float(
                BUTTON_LOAD_X-BUTTON_OFFSET_X,
                BUTTON_LOAD_Y-BUTTON_OFFSET_Y,
                Constants.BUTTON_WIDTH,Constants.BUTTON_HEIGHT,Constants.BUTTON_ARCH_WH,Constants.BUTTON_ARCH_WH
            );

    GameMode gameMode;
    Game game;
	MapSaverLoader mapSaverLoader;

	public TopBar(int x, int y, int width, int height, Color backgroundColor, Setup setup, InputManager inputManager, String mapName, GameMode gameMode) {
		super(x, y, width, height, backgroundColor, setup, inputManager);
		this.mapName = mapName;
        this.gameMode = gameMode;

		mapSaverLoader = new MapSaverLoader(gameMode.getGame());

        try {
            gameplayInactiveImg = ImageIO.read(new File(dirName, pathGameplayInactiveButton));
            gameplayActiveImg = ImageIO.read(new File(dirName, pathGameplayActiveButton));
            buildInactiveImg = ImageIO.read(new File(dirName, pathBuildInactiveButton));
            buildActiveImg = ImageIO.read(new File(dirName, pathBuildActiveButton));
            brainInactiveImg = ImageIO.read(new File(dirName, pathBrainInactiveButton));
            brainActiveImg = ImageIO.read(new File(dirName, pathBrainActiveButton));
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
	}

    private void drawButtons(Graphics graphics){
		drawLoadButton(graphics);
		drawSaveButton(graphics);

        drawGameplayButton(graphics);
        drawBuildButton(graphics);
        drawBrainButton(graphics);
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
	 * Draw the map name. 
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
	 * Draw the save button.
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
		graphics.drawRoundRect(BUTTON_SAVE_X-BUTTON_OFFSET_X,BUTTON_SAVE_Y-BUTTON_OFFSET_Y,Constants.BUTTON_WIDTH,Constants.BUTTON_HEIGHT,Constants.BUTTON_ARCH_WH,Constants.BUTTON_ARCH_WH);
	}

	/**
	 * Draw the load button.
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
		graphics.drawRoundRect(BUTTON_LOAD_X-BUTTON_OFFSET_X,BUTTON_LOAD_Y-BUTTON_OFFSET_Y,Constants.BUTTON_WIDTH,Constants.BUTTON_HEIGHT,Constants.BUTTON_ARCH_WH,Constants.BUTTON_ARCH_WH);
	}

    // Draws the game play button
	private void drawGameplayButton(Graphics graphics){
        if (gameMode.getMode() == Constants.MODE_PLAYER_GAME){
		    graphics.setColor(Constants.COLOR_AVATAR_RED);
        } else {
		    graphics.setColor(Constants.COLOR_AVATAR_WHITE);
        }
        int offset = 6;
        graphics.drawOval(BUTTON_GAMEPLAY_X-((offset+2)/2), BUTTONS_Y-((offset+2)/2), iconWidth+offset, iconWidth+offset);
        try {
            if (gameMode.getMode() == Constants.MODE_PLAYER_GAME)
		        graphics.drawImage(gameplayActiveImg, BUTTON_GAMEPLAY_X+(offset/2), BUTTONS_Y+(offset/2), iconWidth-offset, iconWidth-offset, null);
            else
		        graphics.drawImage(gameplayInactiveImg, BUTTON_GAMEPLAY_X+(offset/2), BUTTONS_Y+(offset/2), iconWidth-offset, iconWidth-offset, null);
        } catch (Exception e){
            e.printStackTrace();
        }
	}
    
    // Draws the build mode button
	private void drawBuildButton(Graphics graphics){
        if (gameMode.getMode() == Constants.MODE_MAP_BUILDER){
		    graphics.setColor(Constants.COLOR_AVATAR_RED);
        } else {
		    graphics.setColor(Constants.COLOR_AVATAR_WHITE);
        }
        int offset = 6;
        graphics.drawOval(BUTTON_BUILD_X-((offset+2)/2), BUTTONS_Y-((offset+2)/2), iconWidth+offset, iconWidth+offset);
        try {
            if(gameMode.getMode() == Constants.MODE_MAP_BUILDER) graphics.drawImage(buildActiveImg, BUTTON_BUILD_X+(offset/2), BUTTONS_Y+(offset/2), iconWidth-offset, iconWidth-offset, null);
		    else graphics.drawImage(buildInactiveImg, BUTTON_BUILD_X+(offset/2), BUTTONS_Y+(offset/2), iconWidth-offset, iconWidth-offset, null);
        } catch (Exception e){
            e.printStackTrace();
        }
	}

    
    // Draws the brain/AI button
	private void drawBrainButton(Graphics graphics){
        if (gameMode.getMode() == Constants.MODE_AI_GAME){
		    graphics.setColor(Constants.COLOR_AVATAR_RED);
        } else {
		    graphics.setColor(Constants.COLOR_AVATAR_WHITE);
        }
        int offset = 6;
        graphics.drawOval(BUTTON_BRAIN_X-((offset+2)/2), BUTTONS_Y-((offset+2)/2), iconWidth+offset, iconWidth+offset);
        try {
            if (gameMode.getMode() == Constants.MODE_AI_GAME){
		        graphics.drawImage(brainActiveImg, BUTTON_BRAIN_X+(offset/2), BUTTONS_Y+(offset/2), iconWidth-offset, iconWidth-offset, null);
            } else {
		        graphics.drawImage(brainInactiveImg, BUTTON_BRAIN_X+(offset/2), BUTTONS_Y+(offset/2), iconWidth-offset, iconWidth-offset, null);
            }
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
     * Process user input for the top bar.
     * E.g. button clicks for
     *      AI Gameplay,
     *      Player Gameplay,
     *      Building Mode,
     *      Save button
     *      Load button
     *
     */
    public void processUserInput(){
        showLoadMap();

        // check if user clicked on save button
        if (getInputManager().isMouseClicked()  && mapSaverLoader.saveButtonClicked()){
            mapSaverLoader.saveButtonLogic();
        }
        //check if user clicked on load button
        if (getInputManager().isMouseClicked()  && mapSaverLoader.loadButtonClicked()){
            mapSaverLoader.loadButtonLogic();
        }

        // 	building mode Button
        if (getInputManager().isMouseClicked()
                && isBuildModeButtonClicked(getInputManager().getMouseClickedX(), getInputManager().getMouseClickedY())) {
            gameMode.changeMode(Constants.MODE_MAP_BUILDER, true);
        }
        // 	game-play mode Button
        if (getInputManager().isMouseClicked()
                && isGamePlayModeButtonClicked(getInputManager().getMouseClickedX(), getInputManager().getMouseClickedY())) {
            gameMode.changeMode(Constants.MODE_PLAYER_GAME, true);
        }
        // 	AI mode Button
        if (getInputManager().isMouseClicked()
                && isBrainButtonClicked(getInputManager().getMouseClickedX(), getInputManager().getMouseClickedY())) {
            gameMode.changeMode(Constants.MODE_AI_GAME, true);
        }
    }

    /**
     * Should show map window if load-button was clicked on finish screen
     */
    private void showLoadMap(){
        if (shouldLoadMap){
            shouldLoadMap = false;
            mapSaverLoader.loadButtonLogic();
        }    
    }

	/**
	 * Takes away .csv extention from a filename for The MapName at the TopBar
	 * */
	public void setMapName(String mapName) {
		this.mapName=mapName;
	}

    public void setGame(Game game){
        this.game=game;
        this.mapSaverLoader = new MapSaverLoader(this.game);
    }

    public RoundRectangle2D getSaveButton() {
        return saveButton;
    }

    public RoundRectangle2D getLoadButton() {
        return loadButton;
    }

    public boolean isGamePlayModeButtonClicked(int mouseX, int mouseY){
        return gamePlayButton.contains(mouseX, mouseY);
    }
    public boolean isBuildModeButtonClicked(int mouseX, int mouseY){
        return buildButton.contains(mouseX, mouseY);
    }

    public boolean isBrainButtonClicked(int mouseX, int mouseY){
        return brainButton.contains(mouseX, mouseY);
    }

    public boolean getShouldLoadMap(){return shouldLoadMap;}
    public void setShouldLoadMap(boolean shouldLoad){this.shouldLoadMap=shouldLoad;}
}

