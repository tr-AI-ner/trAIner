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

    int settingsButtonX = Constants.WINDOW_MAP_WIDTH + (Constants.WINDOW_MAP_MARGIN * 2) + Constants.WINDOW_RIGHT_BAR_WIDTH - (iconWidth*2);
    int settingsButtonY = Constants.WINDOW_MAP_HEIGHT + (Constants.WINDOW_MAP_MARGIN * 2) + Constants.WINDOW_HEADER_HEIGHT + ((Constants.WINDOW_HEADER_HEIGHT/2) - (iconWidth/2));

    int previewButtonWidth = 90, previewButtonHeight = 30;
    int previewButtonX = (getWidth() / 2) - (previewButtonWidth / 2);
    int previewButtonY = getY() + (getHeight()/2) - (previewButtonHeight/2);
	int BUTTON_ARCH_WH = 30;
    boolean tmp = false;
	
    private Rectangle playButton = new Rectangle(playButtonX, playButtonY, iconWidth, iconWidth);
	private Rectangle pauseButton = new Rectangle(pauseButtonX, pauseButtonY, iconWidth, iconWidth);
	private Rectangle settingsButton = new Rectangle(settingsButtonX, settingsButtonY, iconWidth, iconWidth);
	private RoundRectangle2D previewButton = new RoundRectangle2D.Float(previewButtonX, previewButtonY, previewButtonWidth, previewButtonHeight, BUTTON_ARCH_WH, BUTTON_ARCH_WH);

    // directory name where images should be loaded from 
    String dirName = "../resources/";
    String pathPlayButton = "playicon.png";
    String pathPauseButton = "pauseicon.png";
    String pathSettingsButton = "settings.png";

    String namePreviewButton = "Preview";

	int fontSize = 16;
	Font font = new Font(Constants.DEFAULT_FONT, Font.PLAIN, fontSize);

	BufferedImage playImg;
	BufferedImage pauseImg;
    BufferedImage settingsImg;

	public BottomBar(int x, int y, int width, int height, Color backgroundColor, Setup setup, InputManager inputManager) {
		super(x, y, width, height, backgroundColor, setup, inputManager);

		try {
            playImg = ImageIO.read(new File(dirName, pathPlayButton));
            pauseImg = ImageIO.read(new File(dirName, pathPauseButton));
            settingsImg = ImageIO.read(new File(dirName, pathSettingsButton));
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
		if (Main.MODE == 1 || Main.MODE == 2){
            drawPreviewButton(graphics);
        } else {
            drawPlayButton(graphics);
		    drawPauseButton(graphics);
        }
        drawSettingsButton(graphics);
		
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
		
        if(Main.MODE == 2){
		    graphics.fillRoundRect(previewButtonX,previewButtonY,previewButtonWidth,previewButtonHeight,BUTTON_ARCH_WH,BUTTON_ARCH_WH);
            graphics.setColor(Constants.COLOR_HEADER_2);
            graphics.drawString(namePreviewButton, nameX, nameY);
        } else {
		    graphics.drawRoundRect(previewButtonX,previewButtonY,previewButtonWidth,previewButtonHeight,BUTTON_ARCH_WH,BUTTON_ARCH_WH);
            graphics.drawString(namePreviewButton, nameX, nameY);
        }
	}

	
    // Draws the settings button
	private void drawSettingsButton(Graphics graphics){
		graphics.setColor(Constants.COLOR_AVATAR_WHITE);
        int offset = 6;
        graphics.drawOval(settingsButtonX-((offset+2)/2), settingsButtonY-((offset+2)/2), iconWidth+offset, iconWidth+offset);

        try {
		    graphics.drawImage(settingsImg, settingsButtonX+(offset/2), settingsButtonY+(offset/2), iconWidth-offset, iconWidth-offset, null);
        } catch (Exception e){
            e.printStackTrace();
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
    
    public boolean isSettingsButtonClicked(int mouseClickedX, int mouseClickedY){
        return settingsButton.contains(mouseClickedX, mouseClickedY);
    }

}
