package ui;

import java.awt.*;
import java.awt.geom.RoundRectangle2D;

import functionality.Constants;
import functionality.Setup;
import functionality.InputManager;
import game.Main;
import game.Game;

/**
 * This class is responsible for drawing the menu of the game.
 * 
 * Any customizations for the menu should be added here.
 * 
 * @author Patrick
 *
 */
public class Menu extends UIElement{

	int fontSize = 24;
	Font font = new Font(Constants.DEFAULT_FONT, Font.PLAIN, fontSize);

    int middleX, middleY, startButtonX;

    int[] buttonsY;
    int buttonSpace = 20;

    String[] buttonNames = new String[]{
        "Resume", "New AI Game", "Player Game", "Build Map", "Help", "Exit"
    };
	
    private RoundRectangle2D[] buttons;

    // currently selected button of menu
    int selectedButton = -1;
    // last time a button was selected with the arrow keyboard keys (in millis)
    long lastSelectedTime = 0;
    // last time enter was pressed on keyboard
    long lastEnterPressed = 0;

    // when first starting the game, resume button should not be enabled
    boolean resumeEnabled = false;

	public Menu(int x, int y, int width, int height, Color backgroundColor, Setup setup, InputManager inputManager) {
		super(x, y, width, height, backgroundColor, setup, inputManager);
        initMenu();
    }

    /**
     * Initializes the basic layout of the menu.
     */
    private void initMenu(){
        middleX = getSetup().getFrameWidth() / 2;
        startButtonX = middleX - (Constants.MENU_BUTTON_WIDTH/2);
        middleY = getSetup().getFrameHeight() / 2;

        calcYPositions();
        initButtons();
    }

    /**
     * Calculates the y positions of all buttons and saves them in the buttonsY[].
     */
    private void calcYPositions(){
        buttonsY = new int[buttonNames.length];

        int startY = middleY - ((buttonNames.length/2) * Constants.MENU_BUTTON_HEIGHT) - ((buttonNames.length/2)*buttonSpace);

        for (int b=0; b<buttonNames.length; b++){
            buttonsY[b] = (b*Constants.MENU_BUTTON_HEIGHT) + (b*buttonSpace) + startY;
        }
    }

    /**
     * Initializes the RoundRectangles for all buttons.
     */
    private void initButtons(){
        buttons = new RoundRectangle2D[buttonNames.length];
        for(int b=0; b<buttons.length; b++){
            buttons[b] = new RoundRectangle2D.Float(startButtonX, buttonsY[b], Constants.MENU_BUTTON_WIDTH, 
                    Constants.MENU_BUTTON_HEIGHT, Constants.MENU_BUTTON_HEIGHT, Constants.MENU_BUTTON_HEIGHT);
        }
    }

	/**
	 * Draws the menu with background & other UI elements.
     *
     * @param graphics
	 */
	@Override
	public void draw(Graphics graphics) {
        drawBackground(graphics);
        drawButtons(graphics);
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
     * Draws all buttons
     *
     * @param graphics
     */
    private void drawButtons(Graphics graphics){
        for(int b=0; b<buttonNames.length; b++){
            drawSingleButton(
                    b,
                    graphics,
                    startButtonX, buttonsY[b],
                    Constants.MENU_BUTTON_WIDTH, Constants.MENU_BUTTON_HEIGHT,
                    buttonNames[b],
                    (b==selectedButton)
            );
        }
    }

    /**
     * Draws a single button based on given values.
     * 
     * @param index - of button
     * @param x - coordinate
     * @param y - coordinate
     * @param width of button
     * @param height of button
     * @param text that should be inside the button
     * @param selected - whether the button is selected or not
     */
    private void drawSingleButton(int index, Graphics graphics, int x, int y, int width, int height, String text, boolean selected){
        boolean isActiveMode = (index!=5 && index!=0 && getSelectedMode(index)==Main.PREVIOUS_MODE);
        if (index==0 && !resumeEnabled){
            graphics.setColor(Constants.COLOR_COPYRIGHT);
        } else {
            if (isActiveMode){
                graphics.setColor(Constants.COLOR_AVATAR_RED);
            } else {
                graphics.setColor(Constants.COLOR_AVATAR_WHITE);
            }
        }
		graphics.setFont(font);

		// Determine the Y coordinate for the text (note we add the ascent, as in java 2d 0 is top of the screen)
        int textX = x + (width/6);
        int textY = y + ((height - graphics.getFontMetrics(font).getHeight()) / 2) + graphics.getFontMetrics(font).getAscent();
		
        if(selected){
            graphics.fillRoundRect(x,y,width,height,Constants.MENU_BUTTON_HEIGHT,Constants.MENU_BUTTON_HEIGHT);
            graphics.setColor(Constants.COLOR_HEADER_2);
            graphics.drawString(text, textX, textY);
        
        } else {
            graphics.drawRoundRect(x,y,width,height,Constants.MENU_BUTTON_HEIGHT,Constants.MENU_BUTTON_HEIGHT);
            graphics.drawString(text, textX, textY);
        }
    }

    /**
     * Changes the selected button according to user keyboard presses.
     *
     * @param increase - whether the button above should be selected
     */
    public void changeSelectedButton(boolean increase){
        if(canSelectButtonAgain(lastSelectedTime)){
            if(!increase){
                if(selectedButton+1 > buttonNames.length-1){
                    if(!resumeEnabled)
                        selectedButton = 1;
                    else 
                        selectedButton = 0;
                } else {
                    if(selectedButton+1==0 && !resumeEnabled)
                        selectedButton += 2;
                    else
                        selectedButton++;
                }
            } else {
                if(selectedButton-1 < 0 && resumeEnabled){
                    selectedButton = buttonNames.length - 1;
                } 
                else if(selectedButton-1 < 1 && !resumeEnabled){
                    selectedButton = buttonNames.length - 1;
                } else {
                    selectedButton--;
                }
            }
            lastSelectedTime = System.currentTimeMillis();
        }
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
     * When enter is pressed on a button, the mode of the button will be returned.
     * Buttons:
     *  0 - Resume
     *  1 - AI Game
     *  2 - Player Game
     *  3 - Build
     *  4 - Help
     *  5 - Exit
     *
     * @return the selected mode
     */
    public int getSelectedMode(int selButton){
        switch (selButton){
            case 0:
                if(resumeEnabled)
                    return Main.PREVIOUS_MODE;
                else
                    return Constants.MODE_MENU;
            case 1: 
                //if (Main.PREVIOUS_MODE != Constants.MODE_AI_GAME){
                //    Main.NEXT_MODE = Constants.MODE_AI_GAME;
                //    return Constants.MODE_EXIT;
                //}
                return Constants.MODE_AI_GAME;
            case 2: 
                //if (Main.PREVIOUS_MODE != Constants.MODE_PLAYER_GAME){
                //    Main.NEXT_MODE = Constants.MODE_PLAYER_GAME;
                //    return Constants.MODE_EXIT;
                //}
                return Constants.MODE_PLAYER_GAME;
            case 3: 
                //if (Main.PREVIOUS_MODE != Constants.MODE_MAP_BUILDER){
                //    Main.NEXT_MODE = Constants.MODE_MAP_BUILDER;
                //    return Constants.MODE_EXIT;
                //}
                return Constants.MODE_MAP_BUILDER;
            case 4: return Constants.MODE_HELP;
            case 5: System.exit(0);
            default: selectedButton = -1; 
                    return Constants.MODE_MENU;
            //default: return Constants.MODE_PLAYER_GAME;
        }
    }

    /**
     * Handles whether user clicked on a button with the mouse.
     *
     * @param mouseX
     * @param mouseY
     *
     * @return selected-mode
     *          (if no button was clicked returns -1)
     */
    public int getMouseSelectedMode(int mouseX, int mouseY){
        selectedButton = -1;

        for(int b=0; b<buttonNames.length; b++){
            if(b==0 && !resumeEnabled && buttons[0].contains(mouseX,mouseY)){
                return selectedButton;
            }
            else if(buttons[b].contains(mouseX, mouseY)){
                selectedButton = b;
                return getSelectedMode(selectedButton);
            }
        }

        return selectedButton; 
    }

    /**
     * Processes mouse clicks and keyboard presses of user in Menu Mode.
     */
    public void processUserInput(Game game){
         
        if (getInputManager().getKeyResult()[Constants.KEY_UP]) {
            changeSelectedButton(true);
        }
        if (getInputManager().getKeyResult()[Constants.KEY_DOWN]) {
            changeSelectedButton(false);
        }
        if (getInputManager().getKeyResult()[Constants.KEY_ENTER] && canSelectButtonAgain(lastEnterPressed)){
            if(selectedButton==0 && !resumeEnabled){}
            else {
                Main.MODE = getSelectedMode(selectedButton); 
                Main.PREVIOUS_MODE = Main.MODE;
                resumeEnabled = true;
                selectedButton = -1;
            }
            lastEnterPressed = System.currentTimeMillis();
        }
        if(getInputManager().isMouseClicked()){
            int clickedButtonMode = getMouseSelectedMode(getInputManager().getMouseClickedX(),getInputManager().getMouseClickedY());
            if (clickedButtonMode > -1){
                Main.MODE = clickedButtonMode;
                Main.PREVIOUS_MODE = Main.MODE;
                resumeEnabled = true;
                selectedButton = -1;
            }
        }
    }

    public int getSelectedButton(){return selectedButton;}
    public void setSelectedButton(int s){this.selectedButton=s;}

}





