package ui;

import java.awt.*;
import java.awt.RenderingHints;
import java.awt.Font;
import java.awt.geom.Rectangle2D;
import java.awt.geom.RoundRectangle2D;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

import functionality.Constants;
import functionality.Setup;
import functionality.InputManager;
import functionality.FontLoader;
import game.Main;
import game.GameMode;
import game.Game;

import javax.imageio.ImageIO;

/**
 * This class is responsible for drawing the bottom bar of the game.
 * 
 * Any customizations for the bar should be added here.
 * 
 * @author Patrick
 *
 */
public class BottomBar extends UIElement {
	
	int copyrightFontSize = 10;

	int plusButtonY = getY() + 18;
	int minusButtonY = getY() + 18;

	int widthRect = 22;
	int heightRect = 18;
    int iconWidth = 30;

    int playButtonX = (getWidth() / 2) - iconWidth; 
	int playButtonY = getY() + (getHeight() / 2) - (iconWidth / 2);
	int pauseButtonX = (getWidth() / 2) + iconWidth; 
	int pauseButtonY = getY() + (getHeight() / 2) - (iconWidth / 2);

    int exitButtonX = Constants.WINDOW_MAP_WIDTH + (Constants.WINDOW_MAP_MARGIN * 2) + Constants.WINDOW_RIGHT_BAR_WIDTH - (iconWidth*2);
    int exitButtonY = Constants.WINDOW_MAP_HEIGHT + (Constants.WINDOW_MAP_MARGIN * 2) + Constants.WINDOW_HEADER_HEIGHT + ((Constants.WINDOW_HEADER_HEIGHT/2) - (iconWidth/2));

    int helpButtonX = exitButtonX - (iconWidth*3) + 10;
    int helpButtonY = Constants.WINDOW_MAP_HEIGHT + (Constants.WINDOW_MAP_MARGIN * 2) + Constants.WINDOW_HEADER_HEIGHT + ((Constants.WINDOW_HEADER_HEIGHT/2) - (iconWidth/2));

    int previewButtonWidth = 90, previewButtonHeight = 30;
    int previewButtonX = (getWidth() / 2) - (previewButtonWidth / 2);
    int previewButtonY = getY() + (getHeight()/2) - (previewButtonHeight/2);
	
    private Rectangle playButton = new Rectangle(playButtonX, playButtonY, iconWidth, iconWidth);
	private Rectangle pauseButton = new Rectangle(pauseButtonX, pauseButtonY, iconWidth, iconWidth);
	private Rectangle helpButton = new Rectangle(helpButtonX, helpButtonY, iconWidth, iconWidth);
    Rectangle exitButton = new Rectangle(exitButtonX, exitButtonY, iconWidth, iconWidth);
	private RoundRectangle2D previewButton = new RoundRectangle2D.Float(previewButtonX, previewButtonY, previewButtonWidth, previewButtonHeight, 
            Constants.BUTTON_ARCH_WH, Constants.BUTTON_ARCH_WH);

    // directory name where images should be loaded from 
    String dirName = "../resources/";
    String pathPlayButton = "playicon.png";
    String pathPauseButton = "pauseicon.png";
    String pathHelpButton = "question.png";
    String pathExitButton = "exit.png";

    String namePreviewButton = "Preview";

	int fontSize = 16;
	Font font = new Font(Constants.DEFAULT_FONT, Font.PLAIN, fontSize);

    BufferedImage exitImg, helpImg, pauseImg, playImg;

    GameMode gameMode;

	public BottomBar(int x, int y, int width, int height, Color backgroundColor, Setup setup, InputManager inputManager, GameMode gameMode) {
		super(x, y, width, height, backgroundColor, setup, inputManager);
        this.gameMode = gameMode;

		try {
            playImg = ImageIO.read(new File(dirName, pathPlayButton));
            pauseImg = ImageIO.read(new File(dirName, pathPauseButton));
            helpImg = ImageIO.read(new File(dirName, pathHelpButton));
            exitImg = ImageIO.read(new File(dirName, pathExitButton));
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	/**
	 * draw bottom-bar with background & other UI elements
	 * 
	 */
	@Override
	public void draw(Graphics graphics) {
		drawBackground(graphics);
		drawCopyright(graphics);
		if (gameMode.getMode() == Constants.MODE_MAP_BUILDER || gameMode.getMode() == Constants.MODE_PREVIEW){
            drawPreviewButton(graphics);
        } else {
            drawPlayButton(graphics);
		    drawPauseButton(graphics);
        }
        drawHelpButton(graphics);
        drawExitButton(graphics);
		
		//draw other UI-elements here...
	}
	
	/**
	 * draw bottom-bar background with gradients 
	 * 
	 * procedure:
	 * 		draw first gradient to half-screen-size & then inverse gradient 
	 * 		& draw the second gradient on remaining screen
	 * 
	 */
	private void drawBackground(Graphics graphics){
		Graphics2D g2d = (Graphics2D) graphics;
		g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);

		int startX = 0, startY = getY(), endX = (getSetup().getFrameWidth())/2, endY = getY()+getHeight();

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
	
	private void drawCopyright(Graphics graphics){
		graphics.setColor(Constants.COLOR_COPYRIGHT);
		graphics.setFont(new Font(Constants.DEFAULT_FONT, Font.PLAIN, copyrightFontSize));
		
		int copyX = Constants.WINDOW_MAP_MARGIN;
		int copyY = getY() + (getHeight()/2) + (copyrightFontSize/2);
		graphics.drawString(Constants.COPYRIGHT, copyX, copyY);
	}

	// Draws an play button
	private void drawPlayButton(Graphics graphics){
		graphics.setColor(Constants.COLOR_BACKGROUND);
        try {
		    graphics.drawImage(playImg, playButtonX, playButtonY, iconWidth, iconWidth, null);
        } catch (Exception e){
            e.printStackTrace();
        }
	}
	
    // Draws a pause button
	private void drawPauseButton(Graphics graphics){
		graphics.setColor(Constants.COLOR_BACKGROUND);
        try {
            graphics.drawImage(pauseImg, pauseButtonX, pauseButtonY, iconWidth, iconWidth, null);
        } catch (Exception e){
            e.printStackTrace();
        }
	}
    
	/**
	 * Draw the preview button.
	 *
	 * @param graphics
	 */
	private void drawPreviewButton(Graphics graphics){
		graphics.setColor(Constants.COLOR_AVATAR_WHITE);
		graphics.setFont(font);
		// Determine the Y coordinate for the text (note we add the ascent, as in java 2d 0 is top of the screen)
		int nameY = getY() + ((getHeight() - graphics.getFontMetrics(font).getHeight()) / 2)
				+ graphics.getFontMetrics(font).getAscent();
		int nameX = previewButtonX + (previewButtonWidth/6);
		
        if(gameMode.getMode() == Constants.MODE_PREVIEW){
		    graphics.fillRoundRect(previewButtonX,previewButtonY,previewButtonWidth,previewButtonHeight,Constants.BUTTON_ARCH_WH,Constants.BUTTON_ARCH_WH);
            graphics.setColor(Constants.COLOR_HEADER_2);
            graphics.drawString(namePreviewButton, nameX, nameY);
        } else {
		    graphics.drawRoundRect(previewButtonX,previewButtonY,previewButtonWidth,previewButtonHeight,Constants.BUTTON_ARCH_WH,Constants.BUTTON_ARCH_WH);
            graphics.drawString(namePreviewButton, nameX, nameY);
        }
	}

	
    // Draws the help button
	private void drawHelpButton(Graphics graphics){
		graphics.setColor(Constants.COLOR_AVATAR_WHITE);
        int offset = 6;
        graphics.drawOval(helpButtonX-((offset+2)/2), helpButtonY-((offset+2)/2), iconWidth+offset, iconWidth+offset);

        try {
		    graphics.drawImage(helpImg, helpButtonX+(offset/2), helpButtonY+(offset/2), iconWidth-offset, iconWidth-offset, null);
        } catch (Exception e){
            e.printStackTrace();
        }
	}

    // Draws the exit button
	private void drawExitButton(Graphics graphics){
		graphics.setColor(Constants.COLOR_AVATAR_WHITE);
        int offset = 6;
        graphics.drawOval(exitButtonX-((offset+2)/2), exitButtonY-((offset+2)/2), iconWidth+offset, iconWidth+offset);

        try {
		    graphics.drawImage(exitImg, exitButtonX+(offset/2), exitButtonY+(offset/2), iconWidth-offset, iconWidth-offset, null);
        } catch (Exception e){
            e.printStackTrace();
        }
	}

    /**
     * Process the user input for the bottom bar.
     * E.g. button clicks for 
     *      Preview,
     *      Exit,
     *      Help,
     *      Play,
     *      Pause
     */
    public void processUserInput(Game game){
        // check if preview button was clicked
        if((gameMode.getMode()==Constants.MODE_MAP_BUILDER || gameMode.getMode()==Constants.MODE_PREVIEW) && getInputManager().isMouseClicked() 
                && isPreviewButtonClicked(getInputManager().getMouseClickedX(), getInputManager().getMouseClickedY())){
            gameMode.changeMode(Constants.MODE_PREVIEW, false);
        }

        // 	Exit Button to open memu 
        if (getInputManager().isMouseClicked()
                && isExitButtonClicked(getInputManager().getMouseClickedX(), getInputManager().getMouseClickedY())) {
            gameMode.changeMode(Constants.MODE_MENU, false);
        }
        // 	Help Button to open help screen
        if (getInputManager().isMouseClicked()
                && isHelpButtonClicked(getInputManager().getMouseClickedX(), getInputManager().getMouseClickedY())) {
            gameMode.changeMode(Constants.MODE_HELP, false);
        }

        if (gameMode.getMode() != Constants.MODE_MAP_BUILDER && gameMode.getMode() != Constants.MODE_PREVIEW && getInputManager().isMouseClicked()){
            // 	Play Button to play the game
            if (isPlayButtonClicked(getInputManager().getMouseClickedX(), getInputManager().getMouseClickedY())) {
                game.setAiRunning(true);
            }
            //  Pause Button to pause the game
            if (isPauseButtonClicked(getInputManager().getMouseClickedX(), getInputManager().getMouseClickedY())) {
                game.setAiRunning(false);
            }
        }
    }

	public boolean isPlayButtonClicked(int mouseClickedX, int mouseClickedY){
	    return playButton.contains(mouseClickedX, mouseClickedY);
    }

    public boolean isPauseButtonClicked(int mouseClickedX, int mouseClickedY){
        return pauseButton.contains(mouseClickedX, mouseClickedY);
    }
    
    public boolean isPreviewButtonClicked(int mouseClickedX, int mouseClickedY){
        return previewButton.contains(mouseClickedX, mouseClickedY);
    }
    
    public boolean isHelpButtonClicked(int mouseClickedX, int mouseClickedY){
        return helpButton.contains(mouseClickedX, mouseClickedY);
    }
    public boolean isExitButtonClicked(int mouseX, int mouseY){
        return exitButton.contains(mouseX, mouseY);
    }

}
