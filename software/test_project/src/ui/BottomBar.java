package ui;

import java.awt.*;
import java.awt.geom.Rectangle2D;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

import functionality.Constants;
import functionality.Setup;
import functionality.InputManager;

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
    int widthImg = 30;

    int playButtonX = Constants.WINDOW_MAP_MARGIN + (Constants.WINDOW_MAP_WIDTH / 2) - (widthImg/2) - (widthImg / 6);
	int playButtonY = getY() + (getHeight() / 2) - (widthImg / 2);
	int pauseButtonX = Constants.WINDOW_MAP_MARGIN + (Constants.WINDOW_MAP_WIDTH / 2) + (widthImg/2) + (widthImg / 6);
	int pauseButtonY = getY() + (getHeight() / 2) - (widthImg / 2);

	public Rectangle playButton = new Rectangle(playButtonX, playButtonY, widthImg, widthImg);
	public Rectangle pauseButton = new Rectangle(pauseButtonX, pauseButtonY, widthImg, widthImg);

	int fontSize = 16;
	Font font = new Font(Constants.DEFAULT_FONT, Font.PLAIN, fontSize);

	BufferedImage playImg;
	BufferedImage pauseImg;
    // directory name where images should be loaded from 
    String dirName = "../resources/";

	public BottomBar(int x, int y, int width, int height, Color backgroundColor, Setup setup, InputManager inputManager) {
		super(x, y, width, height, backgroundColor, setup, inputManager);

		try {
            playImg = ImageIO.read(new File(dirName, "playicon.png"));
            pauseImg = ImageIO.read(new File(dirName, "pauseicon.png"));
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
		drawPlayButton(graphics);
		drawPauseButton(graphics);
		
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

		int imgPlayX = Constants.WINDOW_MAP_MARGIN + (Constants.WINDOW_MAP_WIDTH / 2) - (widthImg/2) - (widthImg / 6);
		int imgPlayY = getY() + (getHeight() / 2) - (widthImg / 2);

		graphics.drawRect(playButtonX, playButtonY, widthImg, widthImg);
        try {
		    graphics.drawImage(playImg, imgPlayX, imgPlayY, widthImg, widthImg, null);
        } catch (Exception e){
            e.printStackTrace();
        }
	}
	// Draws an pause button
	private void drawPauseButton(Graphics graphics){
		graphics.setColor(Constants.COLOR_BACKGROUND);

		int imgPauseX = Constants.WINDOW_MAP_MARGIN + (Constants.WINDOW_MAP_WIDTH / 2) + (widthImg/2) + (widthImg / 6);
		int imgPauseY = getY() + (getHeight() / 2) - (widthImg / 2);

		graphics.drawRect(pauseButtonX, pauseButtonY, widthImg, widthImg);
        try {
            graphics.drawImage(pauseImg, imgPauseX, imgPauseY, widthImg, widthImg, null);
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
}
