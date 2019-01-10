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
import game.Main;

public class HelpScreen extends UIElement {

	int fontSize = 24;
	Font font = new Font(Constants.DEFAULT_FONT, Font.PLAIN, fontSize);


	public HelpScreen(int x, int y, int width, int height, Color backgroundColor, Setup setup, InputManager inputManager) {
		super(x, y, width, height, backgroundColor, setup, inputManager);
    }

	@Override
	public void draw(Graphics graphics) {
        drawBackground(graphics);
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








}





