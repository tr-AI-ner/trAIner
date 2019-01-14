/** 
  * This class is responsible for drawing the help screen.
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
import game.GameMode;

public class HelpScreen extends UIElement {

	int fontSize = 24;
	Font font = new Font(Constants.DEFAULT_FONT, Font.PLAIN, fontSize);

    String helpText = "Press Esc to open the Menu.\n\n"+
                        "Player Game\t->\tPlay the game with the avatar by moving around with the arrow keys\n\n"+
                        "AI Game\t->\tLet the AI play the game.\n\n"+
                        "Build Mode\t->\tBuild your own maps.";
    int helpY = 50;
    
    String closeButtonText = "Close";
    int closeButtonX = 0, closeButtonY = 0;
    int buttonWidth = 100, buttonHeight = 30;

    GameMode gameMode;
    
    // makes sure not to close help immediately after showing it
    long lastOpened;

	public HelpScreen(int x, int y, int width, int height, Color backgroundColor, Setup setup, InputManager inputManager, GameMode gameMode) {
		super(x, y, width, height, backgroundColor, setup, inputManager);
        this.gameMode = gameMode;

        int offsetY = 60;
        closeButtonY = height - offsetY;
        closeButtonX = (width/2) - (buttonWidth/2);
    }

	@Override
	public void draw(Graphics graphics) {
        drawBackground(graphics);
        drawHelpText(graphics);
        drawCloseButton(graphics);
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

    /**
     * Draws the help text.
     *
     * @param graphics
     */
    private void drawHelpText(Graphics graphics){
		graphics.setColor(Constants.COLOR_AVATAR_WHITE);
		graphics.setFont(font);

		drawMultiLineString(graphics, helpText, helpY);
    }

    private void drawMultiLineString(Graphics g, String text, int y) {
        FontMetrics fm = g.getFontMetrics();
        for (String line : text.split("\n")){
            // center text according to x coordinate
            int tempX = (getWidth() - fm.stringWidth(line)) / 2;
            g.drawString(line, tempX, y += g.getFontMetrics().getHeight());
        }
    }

    /**
     * Draws the close button.
     *
     * @param graphics
     */
    private void drawCloseButton(Graphics graphics){
		graphics.setColor(Constants.COLOR_AVATAR_WHITE);
		graphics.setFont(font);

        FontMetrics fm = graphics.getFontMetrics();
		int textX = closeButtonX + (buttonWidth/2) - (fm.stringWidth(closeButtonText)/2);
        int textY = closeButtonY + fm.getHeight();

        graphics.fillRoundRect(closeButtonX,
                closeButtonY,
                buttonWidth,buttonHeight,
                Constants.BUTTON_ARCH_WH,Constants.BUTTON_ARCH_WH);
        graphics.setColor(Constants.COLOR_HEADER_2);
        graphics.drawString(closeButtonText, textX, textY);
    }

    /**
     * Processes the user input of the exit screen.
     *
     * Help screen can either be close by clicking enter (since close button is already selected)
     * or clicking with the mouse on the close button.
     */
    public void processUserInput(){
        if (getInputManager().getKeyResult()[Constants.KEY_ENTER] && canCloseHelp()){
            gameMode.changeMode(gameMode.getPreviousMode(), false);
        }
    }

    private boolean canCloseHelp(){
        long puffer = 500;
        return (System.currentTimeMillis() >= lastOpened+puffer);
    }

    public void setLastOpenedTime(long lastOpened){this.lastOpened=lastOpened;}

}





