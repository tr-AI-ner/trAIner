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

	int plusX = Constants.WINDOW_MAP_MARGIN + 630;
	int minusX = Constants.WINDOW_MAP_X0 + 662;
	int plusY = getY() + 18;
	int minusY = getY() + 18;
	int widthRect = 22;
	int heightRect = 18;
	int playX = Constants.WINDOW_MAP_MARGIN + 360;
	int playY = getY() + 15;
	int pauseX = Constants.WINDOW_MAP_X0 + 410;
	int pauseY = getY() + 15;
	int widthImg = 30;
	int heightImg = 26;

	public Rectangle plusButton = new Rectangle(plusX, plusY, widthRect, heightRect );
	public Rectangle minusButton = new Rectangle(minusX, minusY ,widthRect, heightRect);
	public Rectangle playButton = new Rectangle(playX, playY, widthImg, heightImg);
	public Rectangle pauseButton = new Rectangle(pauseX, pauseY, widthImg, heightImg);

	int fontSize = 16;
	Font font = new Font(Constants.DEFAULT_FONT, Font.BOLD, fontSize);
	Font fontX = new Font(Constants.DEFAULT_FONT, Font.BOLD, 16);

	int speedUp = 1;
	BufferedImage playImg;
	BufferedImage pauseImg;


	public BottomBar(int x, int y, int width, int height, Color backgroundColor, Setup setup) {
		super(x, y, width, height, backgroundColor, setup);

		try {
			playImg = ImageIO.read(getClass().getResourceAsStream("/play-right-arrow-control-circular-button.png"));

			pauseImg = ImageIO.read(getClass().getResourceAsStream("/pause-button-circle.png"));

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
	private void drawSpeedString(Graphics graphics){
		graphics.setColor(Constants.COLOR_AVATAR_WHITE);
		graphics.setFont(fontX);

		int copyX = Constants.WINDOW_MAP_MARGIN + 598;
		int copyY = getY() + 33;
		int speed = this.getSpeedUp();

		graphics.drawString(speed + "x", copyX, copyY);
	}

	private void drawPlayButton(Graphics graphics){
		//Color playColor = new Color(value, 0, 0);
		graphics.setColor(Constants.COLOR_BACKGROUND);

		int copyX = Constants.WINDOW_MAP_MARGIN;
		int copyY = getY();



		//graphics.drawRect(copyX + 410, copyY + 20,22,18);
		graphics.drawRect(playX, playY, widthImg, heightImg);
		graphics.drawImage(playImg, copyX + 360, copyY + 15, 30, 26, null);


	}

	private void drawPauseButton(Graphics graphics){
		graphics.setColor(Constants.COLOR_BACKGROUND);

		int copyX = Constants.WINDOW_MAP_MARGIN;
		int copyY = getY();

		//Graphics g2d = pauseImg.getGraphics();

		graphics.drawRect(pauseX, pauseY, widthImg, heightImg);
		graphics.drawImage(pauseImg, copyX + 410, copyY + 15, 30, 26, null);



	}

	private void drawPlusButton(Graphics graphics){

		graphics.setColor(Constants.COLOR_AVATAR_WHITE);
		graphics.setFont(font);
		String s = "+";
		int copyX = Constants.WINDOW_MAP_MARGIN;
		int copyY = getY();

		graphics.drawString("SpeedX: ", copyX + 530, copyY + 33);

		graphics.drawRect(plusX , plusY, widthRect, heightRect );
		//graphics.drawRect(plusX, plusY, widthRect + 1, heightRect );
		graphics.drawString(s, copyX + 638, copyY + 32);
		//graphics.fillRect(copyX + 550, copyY + 19 , 22, 18);


	}

	private void drawMinusButton(Graphics graphics){

		graphics.setColor(Constants.COLOR_AVATAR_WHITE);
		graphics.setFont(font);
		//graphics.BasicStroke(6f);
		String s = "-";
		int copyX = Constants.WINDOW_MAP_MARGIN;
		int copyY = getY();

		graphics.drawRect(minusX , minusY ,widthRect, heightRect);
		//graphics.drawRect(minusX, minusY ,widthRect + 1, heightRect);

		graphics.drawString(s,copyX + 671, copyY + 32);

	}

	public Rectangle getPlusButton() {
		return plusButton;
	}

	public Rectangle getMinusButton() {
		return minusButton;
	}

	public int getSpeedUp(){
		return speedUp;
	}

	public int setSpeedUp(int speed){
		return  speedUp = speed;
	}

	public Rectangle getPlayButton(){
		return playButton;
	}

	public Rectangle getPauseButton(){
		return pauseButton;
	}


}
