package custom_objects;

import java.awt.Color;

import functionality.Setup;

public class Avatar extends Entity {
	
	private int toMoveX = getX();
	private int toMoveY = getY();
	
	private Setup setup;

	public Avatar(int x, int y, int width, int height, Color color) {
		super(x, y, width, height);
		this.setColor(color);
	}
	
	public void move(int incX, int incY){
		this.toMoveX = (int) (this.toMoveX != 0 ? this.toMoveX : this.getX() + incX); 
		this.toMoveY = (int) (this.toMoveY != 0 ? this.toMoveY : this.getY() + incY); 
		
		if(incY == 0) // left-arrow right-arrow
			this.toMoveX = (this.getX() + incX);
		else if(incX == 0)  // up-arrow down-arrow
			this.toMoveY = (this.getY() + incY);
		
		if(!checkForCollision()){
			this.setX(toMoveX);
			this.setY(toMoveY);
		} else {
			toMoveX = getX();
			toMoveY = getY();
			System.out.println("colliding with border");
		}
	}
	
	/**
	 *  checks if avatar collides with border,
	 *  if so return true
	 * @return
	 */
	public boolean checkForCollision(){
		return (
				this.toMoveX <= 0 ||
				this.toMoveY <= 0 ||
				this.toMoveX >= (setup.getFrameWidth() - getWidth()) ||
				this.toMoveY >= (setup.getFrameHeight() - getHeight())
				);
	}
	
	public void setSetup(Setup setup){
		this.setup = setup;
	}
	
//	public void controlMove(int incX, int incY) {
//		//Ok this magically works, don't ask why, no time
//		
//		this.toMoveX = (int) (this.toMoveX != 0 ? this.toMoveX : this.getX() + incX); 
//		this.toMoveY = (int) (this.toMoveY != 0 ? this.toMoveY : this.getY() + incY); 
//
////		this.tMath.ceil(this.toMoveX*this.speed);
//		
//		if(incY == 0) // A D
//			this.toMoveX = (this.getX() + incX);
//		else if(incX == 0)  // W S
//			this.toMoveY = (this.getY() + incY);
//	}
//		
//	public void move(){
//		this.move(this.toMoveX, this.toMoveY);
//		this.toMoveX = 0;
//		this.toMoveY = 0;
//	}
//	
//	public void move(int toX, int toY){
//		if(toX < 1 || toY < 1 || toX > Setup.frameWidth -1 || toY > Setup.frameHeight -1){
//			return;
//		}
//		
//		this.setX(toX);
//		this.setY(toY);
//	}

}
