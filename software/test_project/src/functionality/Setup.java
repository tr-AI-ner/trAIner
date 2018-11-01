package functionality;

import java.awt.Toolkit;

public class Setup {

	// size of the displayed part of the world
	private boolean fullScreen = false; //Change this one to make it full screen
	private int scale = 4; //Change this one for different resolution
	public static int frameWidth;
	public static int frameHeight;

	private int entitySpeed = 2; //Base speed
	private int newEntitySpeed = 2; //new Speed

	public Setup() {
		if(fullScreen) {
			frameWidth = Toolkit.getDefaultToolkit().getScreenSize().width;
			frameHeight = Toolkit.getDefaultToolkit().getScreenSize().height;
		}
		else {
			frameWidth = 320 * scale;	//320*4 = 1280
			frameHeight = 180 * scale;	//180*4 = 720
		}
	}


	public void setFullScreen(boolean bool) { fullScreen = bool; }
	public boolean getFullScreen() { return fullScreen; }
	
	public void setScale(int x) { scale = x; }
	public int getScale() { return scale; }
	
	public int getFrameWidth() { return frameWidth; }
	public int getFrameHeight() { return frameHeight; }
	
	public int getEntitySpeed() { return entitySpeed; }
	public void setNewEntitySpeed(int x) { newEntitySpeed = x; }
	public int getNewEntitySpeed() { return newEntitySpeed; }

}