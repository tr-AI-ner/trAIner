package custom_objects;

import java.awt.Color;

import functionality.Setup;

public class Avatar extends Entity {
	
	private int toMoveX = getX();
	private int toMoveY = getY();

	public Avatar(int x, int y, int width, int height, Color color) {
		super(x, y, width, height);
		this.setColor(color);
	}
	
	public void move(int incX, int incY){
		this.toMoveX = (int) (this.toMoveX != 0 ? this.toMoveX : this.getX() + incX); 
		this.toMoveY = (int) (this.toMoveY != 0 ? this.toMoveY : this.getY() + incY); 
		
		if(incY == 0) // A D
			this.toMoveX = (this.getX() + incX);
		else if(incX == 0)  // W S
			this.toMoveY = (this.getY() + incY);
		
		this.setX(toMoveX);
		this.setY(toMoveY);
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
