/** 
  * This class is responsible for drawing the exit screen.
  *
  * Any customizations for the menu should be added here.
  * 
  * @author Patrick
  * 
  */
package ui;

import java.awt.*;
import java.awt.geom.RoundRectangle2D;

import functionality.Constants;
import functionality.Setup;
import functionality.InputManager;
import game.Main;

public class ExitScreen extends UIElement {

	int fontSize = 24;
	Font font = new Font(Constants.DEFAULT_FONT, Font.PLAIN, fontSize);
    String yesButtonText = "Yes", noButtonText = "No";
    String message = "Would you like to quit this session?";

    int textX, textY, yesButtonX, yesButtonY, noButtonX, noButtonY;
    int buttonWidth = 70, buttonHeight = 30;

	private RoundRectangle2D yesButton, noButton;

	public ExitScreen(int x, int y, int width, int height, Color backgroundColor, Setup setup, InputManager inputManager) {
		super(x, y, width, height, backgroundColor, setup, inputManager);

        int offsetX = 10;
        yesButtonX = (width/2) - buttonWidth - offsetX;
        noButtonX = (width/2) + offsetX;

        yesButtonY = (height/2) - buttonHeight;
        noButtonY = (height/2) - buttonHeight;

        yesButton = new RoundRectangle2D.Float(yesButtonX, yesButtonY, 
                buttonWidth, buttonHeight,Constants.BUTTON_ARCH_WH, Constants.BUTTON_ARCH_WH);
        noButton = new RoundRectangle2D.Float(noButtonX, noButtonY, 
                buttonWidth, buttonHeight,Constants.BUTTON_ARCH_WH, Constants.BUTTON_ARCH_WH);


    }

	@Override
	public void draw(Graphics graphics) {
        drawBackground(graphics);
        drawText(graphics);
        drawButtons(graphics, yesButtonX, yesButtonY, yesButtonText); // yes button
        drawButtons(graphics, noButtonX, noButtonY, noButtonText); // no button
	}

    /**
     * Draws background of menu.
     *
     * @param graphics
     */
    private void drawBackground(Graphics graphics){
		graphics.setColor(Constants.COLOR_BACKGROUND);
		graphics.fillRect(0, 0,getSetup().getFrameWidth()+12,getSetup().getFrameHeight()+12);
    }

    private void drawText(Graphics graphics){
		graphics.drawString(message, textX, textY);
    }

    private void drawButtons(Graphics graphics, int x, int y, String text){
		graphics.setColor(Constants.COLOR_AVATAR_WHITE);
		graphics.setFont(font);

		// Determine the Y coordinate for the text (note we add the ascent, as in java 2d 0 is top of the screen)
		int textX = x;
        int textY = y;
        //BUTTON_SAVE_Y= getY() + ((getHeight() - graphics.getFontMetrics(font).getHeight()) / 2)
		//		+ graphics.getFontMetrics(font).getAscent();

		graphics.drawString(text, textX, textY);
		graphics.drawRoundRect(x,y,buttonWidth,buttonHeight,Constants.BUTTON_ARCH_WH,Constants.BUTTON_ARCH_WH);
    }

    /**
     * Sets message of exit screen according to old and new mode.
     *
     * @param oldMode from which to switch from
     * @param newMode from which to switch to
     */
    public void setText(int oldMode, int newMode){
        String s1 = "Would you like to exit the ";
        String s2 = " to start the ";
        this.message = s1 + getModeText(oldMode) + s2 + getModeText(newMode);
    }

    /**
     * Gets string of a mode.
     *
     * @param mode 
     * @return the mode string
     */
    private String getModeText(int mode){
        switch (mode){
            case Constants.MODE_PLAYER_GAME: return "Player Game Mode";
            case Constants.MODE_AI_GAME: return "AI Game Mode";
            case Constants.MODE_MAP_BUILDER: return "Build Mode";
            default: return "";
        }
    }


}





