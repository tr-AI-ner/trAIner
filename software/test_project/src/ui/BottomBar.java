package ui;

import java.awt.*;
import java.awt.geom.Rectangle2D;
import java.awt.image.BufferedImage;
import java.io.IOException;

import functionality.Constants;
import functionality.Setup;

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

	int plusButtonX = Constants.WINDOW_MAP_MARGIN + 610;
	int minusButtonX = Constants.WINDOW_MAP_X0 + 685;
	int plusButtonY = getY() + 18;
	int minusButtonY = getY() + 18;

	int widthRect = 22;
	int heightRect = 18;
    int widthImg = 30;
    int heightImg = 26;

    int playButtonX = Constants.WINDOW_MAP_MARGIN + 360;
	int playButtonY = getY() + 15;
	int pauseButtonX = Constants.WINDOW_MAP_X0 + 410;
	int pauseButtonY = getY() + 15;


	public Rectangle speedPlusButton = new Rectangle(plusButtonX, plusButtonY, widthRect, heightRect );
	public Rectangle speedMinusButton = new Rectangle(minusButtonX, minusButtonY ,widthRect, heightRect);
	public Rectangle playButton = new Rectangle(playButtonX, playButtonY, widthImg, heightImg);
	public Rectangle pauseButton = new Rectangle(pauseButtonX, pauseButtonY, widthImg, heightImg);

	int fontSize = 16;
	Font font = new Font(Constants.DEFAULT_FONT, Font.PLAIN, fontSize);

	// Variable for increasing speed
	int speedUp = 1;

	BufferedImage playImg;
	BufferedImage pauseImg;


	public BottomBar(int x, int y, int width, int height, Color backgroundColor, Setup setup) {
		super(x, y, width, height, backgroundColor, setup);

    /**
    *	Getting two images for Pause and Play Button
    */
		try {
			playImg = ImageIO.read(getClass().getResourceAsStream("/playicon.png"));

			pauseImg = ImageIO.read(getClass().getResourceAsStream("/pauseicon.png"));

		} catch (IOException e) {
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
		drawPlusButton(graphics);
		drawMinusButton(graphics);
		drawSpeedString(graphics);
		
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

	// Draws speed variable with an string
	private void drawSpeedString(Graphics graphics){
		graphics.setColor(Constants.COLOR_AVATAR_WHITE);
		graphics.setFont(font);

		int speedXvalue = Constants.WINDOW_MAP_MARGIN + 650;
		int speedYvalue = getY() + 33;
		int speed = this.getSpeedUp();

        graphics.drawString("SpeedX: ", Constants.WINDOW_MAP_X0 + 530, getY() + 33);
		graphics.drawString(speed + "x", speedXvalue, speedYvalue);
	}

	// Draws an play button
	private void drawPlayButton(Graphics graphics){
		graphics.setColor(Constants.COLOR_BACKGROUND);

		int imgPlayX = Constants.WINDOW_MAP_MARGIN + 360;
		int imgPlayY = getY() + 15;

		graphics.drawRect(plusButtonX, playButtonY, widthImg, heightImg);
		graphics.drawImage(playImg, imgPlayX, imgPlayY, widthImg, heightImg, null);

	}
	// Draws an pause button
	private void drawPauseButton(Graphics graphics){
		graphics.setColor(Constants.COLOR_BACKGROUND);

		int imgPauseX = Constants.WINDOW_MAP_MARGIN + 410;
		int imgPauseY = getY() + 15;

		graphics.drawRect(pauseButtonX, pauseButtonY, widthImg, heightImg);
		graphics.drawImage(pauseImg, imgPauseX, imgPauseY, widthImg, heightImg, null);

	}
	// Draws an minus button
	private void drawMinusButton(Graphics graphics){
		graphics.setColor(Constants.COLOR_AVATAR_WHITE);
		graphics.setFont(font);

		String s = "-";
		int minusStringX = Constants.WINDOW_MAP_MARGIN + 618;
		int minusStringY = getY() + 32;

		graphics.drawRect(minusButtonX , minusButtonY, widthRect, heightRect );
		graphics.drawString(s, minusStringX, minusStringY);
	}


	// Draws an plus button
	private void drawPlusButton(Graphics graphics){
		graphics.setColor(Constants.COLOR_AVATAR_WHITE);
		graphics.setFont(font);

		String s = "+";
		int plusStringX = Constants.WINDOW_MAP_MARGIN + 692;
		int plusStringY = getY() + 32;

		graphics.drawRect(plusButtonX , plusButtonY ,widthRect, heightRect);
		graphics.drawString(s,plusStringX, plusStringY);

	}

	public boolean isSpeedPlusButtonClicked(int mouseClickedX, int mouseClickedY){
	    return speedPlusButton.contains(mouseClickedX,mouseClickedY);
	}

	public boolean isSpeedMinusButtonClicked(int mouseClickedX, int mouseClickedY){
		return speedMinusButton.contains(mouseClickedX, mouseClickedY);
	}

	public int getSpeedUp(){
		return speedUp;
	}

	public int setSpeedUp(int speed){
		return  speedUp = speed;
	}

	public boolean isPlayButtonClicked(int mouseClickedX, int mouseClickedY){
	    return playButton.contains(mouseClickedX, mouseClickedY);
    }

    public boolean isPauseButtonClicked(int mouseClickedX, int mouseClickedY){
        return pauseButton.contains(mouseClickedX, mouseClickedY);
    }
}
