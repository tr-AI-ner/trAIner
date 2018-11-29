package custom_objects;

import java.awt.*;

import functionality.Constants;
import functionality.Setup;
import game.Game;
import map_builder.MapElement;

public class Avatar extends Entity {
	
	private int toMoveX = getX();
	private int toMoveY = getY();
	
	private Setup setup;
	private Game game;

	public Avatar(int x, int y, int width, int height, Color color) {
		super(x, y, width, height, EntityType.Avatar);
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
		}
	}
	
    /**
	 *  checks if avatar collides with any objects or border,
	 *  if so returns true
	 * @return
	 */
	private boolean checkForCollision(){
		return (
				collidingWithBorder() || 
				collidingWithMapElement()
				);
	}
	
	/**
	 *  checks if avatar collides with border,
	 *  if so returns true
	 * @return
	 */
	private boolean collidingWithBorder(){
		return (
				       this.toMoveX <= 0 
					|| this.toMoveY <= 0
					|| this.toMoveX >= (Constants.WINDOW_MAP_WIDTH - getWidth()) 
					|| this.toMoveY >= (Constants.WINDOW_MAP_HEIGHT - getHeight())
				);
	}
	
	/**
	 *  checks if avatar collides with another map-element,
	 *  if so returns true
	 * @return
	 */
	private boolean collidingWithMapElement(){
		for (MapElement element: game.getMapElements()){
			if (toMoveX+getWidth() >= element.getX() 
				&& toMoveX <= (element.getX()+element.getWidth())
				&& (toMoveY+getHeight() >= element.getY() 
				&& toMoveY <= (element.getY()+element.getHeight()))
				)
				return true;
		}
		// no collision detected
		return false;
	}
	
	/**
	 * draws the avatar
	 * 
	 * @param graphics
	 */
	public void draw(Graphics graphics){
		Graphics2D g2d = (Graphics2D)graphics;
		g2d.setColor(getColor());
		g2d.fillRect(getMapX(), getMapY(), getWidth(), getHeight());
		//draw border
		Color color = new Color(getColor().getRed(),getColor().getGreen(),getColor().getBlue(),85);
		g2d.setStroke(new BasicStroke(3));
		g2d.setColor(color);
		g2d.drawRect(getMapX(), getMapY(), getWidth(), getHeight());
		g2d.setStroke(new BasicStroke(1));
	}
	
	
	
	public void setSetup(Setup setup){
		this.setup = setup;
	}
	public void setGame(Game game){
		this.game = game;
	}
	

}
