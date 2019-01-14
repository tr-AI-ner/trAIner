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
import game.Game;

public class ExitScreen extends UIElement {

	int fontSize = 24;
	Font font = new Font(Constants.DEFAULT_FONT, Font.PLAIN, fontSize);
    String yesButtonText = "Yes", noButtonText = "No";
    String message = "Would you like to quit this session?";

    int textX, textY, yesButtonX, yesButtonY, noButtonX, noButtonY;
    int buttonWidth = 100, buttonHeight = 30;

	private RoundRectangle2D yesButton, noButton;

    // currently selected button of menu
    int selectedButton = -1;
    final int SEL_BTN_YES = 0, SEL_BTN_NO = 1;
    // last time a button was selected with the arrow keyboard keys (in millis)
    long lastSelectedTime = 0;
    // last time enter was pressed on keyboard
    long lastEnterPressed = 0;

    boolean yesSelected = false;

	public ExitScreen(int x, int y, int width, int height, Color backgroundColor, Setup setup, InputManager inputManager) {
		super(x, y, width, height, backgroundColor, setup, inputManager);

        int offsetX = 10;
        yesButtonX = (width/2) - buttonWidth - offsetX;
        noButtonX = (width/2) + offsetX;

        yesButtonY = (height/2) - buttonHeight;
        noButtonY = (height/2) - buttonHeight;

        //textX = 0;
        textY = yesButtonY - 100;

        yesButton = new RoundRectangle2D.Float(yesButtonX, yesButtonY, 
                buttonWidth, buttonHeight,Constants.BUTTON_ARCH_WH, Constants.BUTTON_ARCH_WH);
        noButton = new RoundRectangle2D.Float(noButtonX, noButtonY, 
                buttonWidth, buttonHeight,Constants.BUTTON_ARCH_WH, Constants.BUTTON_ARCH_WH);


    }

	@Override
	public void draw(Graphics graphics) {
        drawBackground(graphics);
        drawText(graphics);
        drawButtons(graphics, yesButtonX, yesButtonY, yesButtonText, 
                (selectedButton==SEL_BTN_YES)); // yes button
        drawButtons(graphics, noButtonX, noButtonY, noButtonText, 
                (selectedButton==SEL_BTN_NO)); // no button
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
     * Draws the message above the buttons of the exit screen.
     *
     * @param graphics
     */
    private void drawText(Graphics graphics){
		graphics.setColor(Constants.COLOR_AVATAR_WHITE);
		graphics.setFont(font);

        FontMetrics fm = graphics.getFontMetrics();
        // center text according to x coordinate
        int x = (getWidth() - fm.stringWidth(message)) / 2;
		graphics.drawString(message, x, textY);
    }

    /**
     * Draws either a Yes or No button.
     *
     * @param graphics
     * @param x coordinate of button
     * @param y coordinate of button
     * @param text of the button
     */
    private void drawButtons(Graphics graphics, int x, int y, String text, boolean selected){
		graphics.setColor(Constants.COLOR_AVATAR_WHITE);
		graphics.setFont(font);

        FontMetrics fm = graphics.getFontMetrics();
		int textX = x + (buttonWidth/2) - (fm.stringWidth(text)/2);
        int textY = y + fm.getHeight();

        if (selected){
            graphics.fillRoundRect(x,y,buttonWidth,buttonHeight,Constants.BUTTON_ARCH_WH,Constants.BUTTON_ARCH_WH);
            graphics.setColor(Constants.COLOR_HEADER_2);
            graphics.drawString(text, textX, textY);
        } else {
            graphics.drawRoundRect(x,y,buttonWidth,buttonHeight,Constants.BUTTON_ARCH_WH,Constants.BUTTON_ARCH_WH);
            graphics.drawString(text, textX, textY);
        }
    }

    public void processUserInput(Game game){
        if (getInputManager().getKeyResult()[Constants.KEY_LEFT]) {
            changeSelectedButton(true);
        }
        else if (getInputManager().getKeyResult()[Constants.KEY_RIGHT]) {
            changeSelectedButton(false);
        }
        if (getInputManager().getKeyResult()[Constants.KEY_ENTER] && canSelectButtonAgain(lastEnterPressed)){
            if (selectedButton == SEL_BTN_YES){
                System.out.println("Yes pressed with enter");
                yesSelected = true;
                game.changeMode(Main.NEXT_MODE, true);
            }
            else if (selectedButton == SEL_BTN_NO){
                System.out.println("No pressed with enter");
                Main.NEXT_MODE = Constants.MODE_NONE;
                game.changeMode(Constants.MODE_MENU, true);
            }
            selectedButton = -1;
            lastEnterPressed = System.currentTimeMillis();
        }
        if (getInputManager().isMouseClicked()){
            int clickedButtonMode = getMouseSelectedMode(getInputManager().getMouseClickedX(),getInputManager().getMouseClickedY());
            if (clickedButtonMode == SEL_BTN_YES){
                yesSelected = true;
                game.changeMode(Main.NEXT_MODE, true);
            }
            else if (clickedButtonMode == SEL_BTN_NO){
                Main.NEXT_MODE = Constants.MODE_NONE;
                game.changeMode(Constants.MODE_MENU, true);
            }
            selectedButton = -1;
        }
    }

    private int getMouseSelectedMode(int mouseX, int mouseY){
        if (isYesButtonClicked(mouseX, mouseY)){
            return SEL_BTN_YES;
        }
        if (isNoButtonClicked(mouseX, mouseY)){
            return SEL_BTN_NO;
        }
        return -1;
    }

    private void changeSelectedButton(boolean left){
        if(canSelectButtonAgain(lastSelectedTime)){
            if (left){
                if (selectedButton-1 < -1){
                    selectedButton = 1;
                } else {
                    selectedButton--;
                }
            } else {
                if (selectedButton+1 > 1){
                    selectedButton = -1;
                } else {
                    selectedButton++;
                }
            }
        }
        lastSelectedTime = System.currentTimeMillis();
    }

    /**
     * Decides whether a next button can be selected based on elapsed time of last button change.
     *
     * @return whether a new button can be selected
     */
    private boolean canSelectButtonAgain(long lastTime){
        //in milliseconds
        long puffer = 200;
        return (System.currentTimeMillis() > (lastTime+puffer));
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
        this.message = s1 + getModeText(oldMode) + s2 + getModeText(newMode) + "?";
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

    private boolean isYesButtonClicked(int mouseX, int mouseY){
        return yesButton.contains(mouseX, mouseY);
    }
    private boolean isNoButtonClicked(int mouseX, int mouseY){
        return noButton.contains(mouseX, mouseY);
    }

    public boolean wasYesSelected(){return yesSelected;}
    public void resetYesSelected(){yesSelected=false;}

}





