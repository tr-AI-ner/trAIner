package ui;

import java.awt.Color;
import java.awt.Graphics;

import functionality.Setup;
import functionality.InputManager;

/**
 * 
 * abstract class that can be used for basic UI elements,
 * such as toolbars, buttons, ...
 * 
 * for the game, 3 specific bars should be created:
 * - top-bar
 * - bottom-bar
 * - right-bar
 * 
 * @author Patrick
 *
 */
public abstract class UIElement {

	private int x;
	private int y;
	private int width;
	private int height;
	
	private Color backgroundColor;
	
	private Setup setup;
    private InputManager inputManager;
	
	public UIElement(int x, int y, int width, int height, Color backgroundColor, Setup setup, InputManager inputManager){
		this.x = x;
		this.y = y;
		this.width = width;
		this.height = height;
		this.backgroundColor = backgroundColor;
		this.setup = setup;
        this.inputManager = inputManager;
	}

	/**
	 * default draw method,
	 * should be overridden for custom behavior
	 * 
	 * @param graphics
	 */
	public void draw(Graphics graphics){
		graphics.setColor(backgroundColor);
		graphics.fillRect(x, y, width, height);
	}
	
	
	public int getX() {
		return x;
	}
	public void setX(int x) {
		this.x = x;
	}

	public int getY() {
		return y;
	}
	public void setY(int y) {
		this.y = y;
	}

	public int getWidth() {
		return width;
	}
	public void setWidth(int width) {
		this.width = width;
	}

	public int getHeight() {
		return height;
	}
	public void setHeight(int height) {
		this.height = height;
	}

	public Color getBackgroundColor() {
		return backgroundColor;
	}
	public void setBackgroundColor(Color backgroundColor) {
		this.backgroundColor = backgroundColor;
	}
	
	public Setup getSetup() {
		return setup;
	}

	public void setSetup(Setup setup) {
		this.setup = setup;
	}
	
	public InputManager getInputManager() {
		return inputManager;
	}

	public void setInputManager(InputManager inputManager) {
		this.inputManager = inputManager;
	}
	
	
}
