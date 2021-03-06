package functionality;

import java.awt.event.*;
import java.awt.*;

public class InputManager extends Component implements KeyListener, MouseListener, MouseMotionListener {

    private static final long serialVersionUID = 42L;

	// everything a user can press on keyboard or mouse	
	private int mousePressedX, mousePressedY, mouseMovedX, mouseMovedY, mouseButton;
	private int mouseClickedX, mouseClickedY, mouseButtonClicked;
	private char keyPressed;

	private char secondKeyPressed;
	private boolean isSecondKeyEvent;

	// if Mouse was clicked, Key was pressed or Mouse is still hold down
	private boolean isMouseEvent, isKeyEvent, isMousePressed, isMouseDragged, isMouseClicked;
	
	//boolean array to "save" keys
	private boolean[] keyArray = new boolean[10]; 
	//0=up-arrow 1=down-arrow   2=left-arrow   3=right-arrow    4=ESC   5=SHIFT
    //6=build-mode 7=empty-map 8=AI-mode
	//UP, DOWN, LEFT, RIGHT, ESCAPE, RUN
	
	private long lastMouseProcessTime = 0;

	@Override
	public void mousePressed(MouseEvent evt){
		// an input Event occurs
		isMouseEvent   = true;
		mousePressedX  = evt.getX();
		mousePressedY  = evt.getY();
		mouseButton    = evt.getButton();
		isMousePressed = true;
	}

	@Override
	public void mouseReleased(MouseEvent evt){ 
		isMousePressed = false;
	}

	@Override
	public void mouseMoved(MouseEvent evt){ 
		mouseMovedX=evt.getX();
		mouseMovedY=evt.getY();
	}

	@Override
	public void mouseDragged(MouseEvent evt){}

	/**
	 * if the key is being pressed sets the respective key to true
	 * i.e. when w is being pressed sets keyArray[0] to true, which is up
	 */
	@Override
	public void keyPressed(KeyEvent evt){

		//these commands work better on MacBook (please don't delete them)
		if(evt.getKeyCode() == KeyEvent.VK_UP) { keyArray[Constants.KEY_UP] = true; } //up-arrow goes UP
		if(evt.getKeyCode() == KeyEvent.VK_DOWN) { keyArray[Constants.KEY_DOWN] = true; } //down-arrow goes DOWN
		if(evt.getKeyCode() == KeyEvent.VK_LEFT) { keyArray[Constants.KEY_LEFT] = true; } //left-arrow goes LEFT
		if(evt.getKeyCode() == KeyEvent.VK_RIGHT) { keyArray[Constants.KEY_RIGHT] = true; } //right-arrow goes RIGHT
		if(evt.getKeyCode() == KeyEvent.VK_ESCAPE) { keyArray[Constants.KEY_ESCAPE] = true; } //ESC CLOSE_GAME
		if(evt.getKeyCode() == KeyEvent.VK_G) { keyArray[Constants.KEY_G] = true; } //Change into game mode
		if(evt.getKeyCode() == KeyEvent.VK_B) { keyArray[Constants.KEY_B] = true; } //Change into build mode
		if(evt.getKeyCode() == KeyEvent.VK_E) { keyArray[Constants.KEY_E] = true; } //Initialize an Empty map
		if(evt.getKeyCode() == KeyEvent.VK_A) { keyArray[Constants.KEY_A] = true; } //Change into AI mode
		if(evt.getKeyCode() == KeyEvent.VK_ENTER) { keyArray[Constants.KEY_ENTER] = true; } //Select option in menu
        //System.out.println(evt.getKeyCode());
	}

	/**
	 * if the key is not being pressed sets the respective key to false
	 * i.e. when w is being pressed sets keyArray[0] to false, which is up
	 */
	@Override
	public void keyReleased(KeyEvent evt){

		if(evt.getKeyCode() == KeyEvent.VK_UP) { keyArray[Constants.KEY_UP] = false; }
		if(evt.getKeyCode() == KeyEvent.VK_DOWN) { keyArray[Constants.KEY_DOWN] = false; }
		if(evt.getKeyCode() == KeyEvent.VK_LEFT) { keyArray[Constants.KEY_LEFT] = false; }
		if(evt.getKeyCode() == KeyEvent.VK_RIGHT) { keyArray[Constants.KEY_RIGHT] = false; }
		if(evt.getKeyCode() == KeyEvent.VK_ESCAPE) { keyArray[Constants.KEY_ESCAPE] = false; }
		if(evt.getKeyCode() == KeyEvent.VK_G) { keyArray[Constants.KEY_G] = false; }
		if(evt.getKeyCode() == KeyEvent.VK_B) { keyArray[Constants.KEY_B] = false; }
		if(evt.getKeyCode() == KeyEvent.VK_E) { keyArray[Constants.KEY_E] = false; }
		if(evt.getKeyCode() == KeyEvent.VK_A) { keyArray[Constants.KEY_A] = false; }
		if(evt.getKeyCode() == KeyEvent.VK_ENTER) { keyArray[Constants.KEY_ENTER] = false; } 
	}

	@Override
	public void mouseEntered(MouseEvent evt){}
	@Override
	public void mouseExited(MouseEvent evt){}



	@Override
	public void mouseClicked(MouseEvent evt){
        isMouseClicked   = true;
        mouseClickedX=evt.getX();
        mouseClickedY=evt.getY();
        mouseButtonClicked = evt.getButton();
	}

	@Override
	public void keyTyped(KeyEvent evt){}	

    /**
     * Print-out string of key-array for debugging purposes.
     *
     * @return 
     */
    public String getKeyArray(){
        String s = "";
        String[] vals = new String[]{"up","down","left","right","esc","g","b","e","a","enter"};
        for (int i=0; i<keyArray.length; i++){
            s += ""+vals[i]+": "+keyArray[i]+", ";
        }
        return s;
    }

	public int getMousePressedX(){return mousePressedX;}
	public int getMousePressedY(){return mousePressedY;}
	public int getMouseMovedX(){return mouseMovedX;}
	public int getMouseMovedY(){return mouseMovedY;}
	public int getMouseClickedX(){return mouseClickedX;}
	public int getMouseClickedY(){return mouseClickedY;}
	public int getMouseButton(){return mouseButton;}

	public char getKeyPressed(){return keyPressed;}
	public char getSecondKeyPressed(){return secondKeyPressed;}

	public boolean areTwoKeysPressed(){
		return isKeyEvent && isSecondKeyEvent;
	}



	public boolean getIsMouseEvent(){return isMouseEvent;}
	public boolean getIsMouseDragged(){return isMouseDragged;}
	public boolean getIsKeyEvent(){return isKeyEvent;}
	public boolean getIsMousePressed(){return isMousePressed;}
	
	//a way to get the boolean array of keys
	public boolean[] getKeyResult() {return keyArray;}
	
	public long getLastMouseProcessTime() {
		return this.lastMouseProcessTime;
	}
	
	public void updateLastMouseProcessTime() {
		this.lastMouseProcessTime = System.currentTimeMillis();
	}

	public boolean isMouseClicked() {return isMouseClicked;}
	public void setMouseClicked(boolean mouseClicked) {
		isMouseClicked = mouseClicked;
        //also reset the mouse button
        /*if (mouseClicked==false){
            mouseButton = 0;
        }*/
	}

	public int getMouseButtonClicked(){return mouseButtonClicked;}


    public void resetEnterKey(){keyArray[Constants.KEY_ENTER] = false;}

}
