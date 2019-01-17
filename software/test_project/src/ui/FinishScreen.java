/** 
  * This class is responsible for drawing the finish screen.
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
import game.Game;
import game.GameMode;

public class FinishScreen extends UIElement {

	int fontSize = 24;
	Font font = new Font(Constants.DEFAULT_FONT, Font.PLAIN, fontSize);

    String finishText = "Congratulations, you won!";
    int finishY = 50;
    
    String[] buttonsText = new String[]{"Load Different Map", "Build New Map", "Restart Game"};
    int buttonX = 0;
    //buttons load, build, restart
    int[] buttonsY = new int[]{0, 0, 0};
    int buttonWidth = 220, buttonHeight = 30;

    private RoundRectangle2D[] buttons;
    
    // currently selected button of menu
    int selectedButton = -1;
    // last time a button was selected with the arrow keyboard keys (in millis)
    long lastSelectedTime = 0;
    // last time enter was pressed on keyboard
    long lastEnterPressed = 0;

    GameMode gameMode;
    Game game;
    
	public FinishScreen(int x, int y, int width, int height, Color backgroundColor, Setup setup, InputManager inputManager, GameMode gameMode) {
		super(x, y, width, height, backgroundColor, setup, inputManager);
        this.gameMode = gameMode;

        int offsetY = 10;
        buttonsY[0] = (height/2) - buttonHeight - offsetY;
        buttonsY[1] = buttonsY[0] + buttonHeight + offsetY;
        buttonsY[2] = buttonsY[1] + buttonHeight + offsetY;
        buttonX = (width/2) - (buttonWidth/2);

        initButtons();
    }

    /**
     * Initalizes the RoundRectangles for all buttons.
     */
    private void initButtons(){
        buttons = new RoundRectangle2D[buttonsText.length];
        for (int b=0; b<buttonsText.length; b++){
            buttons[b] = new RoundRectangle2D.Float(buttonX, buttonsY[b], buttonWidth, buttonHeight, buttonHeight, buttonHeight);
        }
    }

	@Override
	public void draw(Graphics graphics) {
        drawBackground(graphics);
        drawFinishText(graphics);
        drawAllButtons(graphics);
	}

    /**
     * Draws background of the finish screen.
     *
     * @param graphics
     */
    private void drawBackground(Graphics graphics){
		graphics.setColor(Constants.COLOR_BACKGROUND);
		graphics.fillRect(0, 0,getSetup().getFrameWidth()+12,getSetup().getFrameHeight()+12);
    }

    /**
     * Draws the finish text.
     *
     * @param graphics
     */
    private void drawFinishText(Graphics graphics){
		graphics.setColor(Constants.COLOR_AVATAR_WHITE);
		graphics.setFont(font);
        FontMetrics fm = graphics.getFontMetrics();

        int tempX = (getWidth() - fm.stringWidth(finishText)) / 2;
        graphics.drawString(finishText, tempX, finishY);
    }

    /**
     * Draws all the buttons.
     *
     * @param graphics
     */
    private void drawAllButtons(Graphics graphics){
        for (int button=0; button < buttonsText.length; button++){
            drawButton(graphics, buttonX, buttonsY[button], 
                    buttonsText[button], (button==selectedButton));
        }
    }

    /**
     * Draws an individual button.
     *
     * @param graphics
     * @param x starting coordinate of button
     * @param y straring coordinate of button
     * @param text that should be inside the button
     * @param selected currently by user
     */
    private void drawButton(Graphics graphics, int x, int y, String text, boolean selected){
		graphics.setColor(Constants.COLOR_AVATAR_WHITE);
		graphics.setFont(font);

        FontMetrics fm = graphics.getFontMetrics();
		int textX = x + (buttonWidth/2) - (fm.stringWidth(text)/2);
        int textY = y + fm.getHeight();

        if (selected){
            graphics.fillRoundRect(x, y,
                    buttonWidth,buttonHeight,
                    buttonHeight,buttonHeight);
            graphics.setColor(Constants.COLOR_HEADER_2);
            graphics.drawString(text, textX, textY);
        } else {
            graphics.drawRoundRect(x, y,
                    buttonWidth,buttonHeight,
                    buttonHeight,buttonHeight);
            graphics.drawString(text, textX, textY);
        }
    }

    /**
     * Processes the user input of the finish screen.
     *
     * User can select 3 different buttons on Finish screen:
     *      Load    -   Loads a new map
     *      Build   -   Switch to building mode
     *      Restart -   Restart current map
     */
    public void processUserInput(){
        if (getInputManager().getKeyResult()[Constants.KEY_UP]) {
            changeSelectedButton(true);
        }
        if (getInputManager().getKeyResult()[Constants.KEY_DOWN]) {
            changeSelectedButton(false);
        }
        if (getInputManager().getKeyResult()[Constants.KEY_ENTER] && canSelectButtonAgain(lastEnterPressed)){
            //gameMode.changeMode(gameMode.getPreviousMode(), false);
            System.out.println("Selected mode: "+getSelectedMode(selectedButton));
            selectedButton = -1;
            lastEnterPressed = System.currentTimeMillis();
        }
        if(getInputManager().isMouseClicked()){
            //int clickedButtonMode = getMouseSelectedMode(getInputManager().getMouseClickedX(),getInputManager().getMouseClickedY());
            //if (clickedButtonMode > -1){
            //    gameMode.changeMode(clickedButtonMode, false);
            //    resumeEnabled = true;
            //    selectedButton = -1;
            //}
        }
    }

    private void changeSelectedButton(boolean increase){
        if (canSelectButtonAgain(lastSelectedTime)){
            if (!increase){
                if (selectedButton+1 > buttonsText.length-1)
                    selectedButton = 0;
                else
                    selectedButton++;
            } else {
                if (selectedButton-1 < 0)
                    selectedButton = buttonsText.length-1;
                else
                    selectedButton--;
            }
            lastSelectedTime = System.currentTimeMillis();
        }
    }

    /**
     * Decides whether a next button can be selected based on elapsed time of last button change.
     *
     * @param lastTime the button was selected in milliseconds
     *
     * @return whether a new button can be selected
     */
    private boolean canSelectButtonAgain(long lastTime){
        //in milliseconds
        long puffer = 200;
        return (System.currentTimeMillis() >= lastTime+puffer);
    }

    private int getSelectedMode(int selButton){
        switch (selButton){
            case 0: return 0;
            case 1: return Constants.MODE_MAP_BUILDER;
            case 2: return Constants.MODE_PLAYER_GAME;
            default: selectedButton = -1; 
                     return Constants.MODE_FINISH;
        }
    }

    private boolean loadButtonClicked(int mouseX, int mouseY){
        return buttons[0].contains(mouseX, mouseY);
    }
    private boolean buildButtonClicked(int mouseX, int mouseY){
        return buttons[1].contains(mouseX, mouseY);
    }
    private boolean restartButtonClicked(int mouseX, int mouseY){
        return buttons[2].contains(mouseX, mouseY);
    }


}





