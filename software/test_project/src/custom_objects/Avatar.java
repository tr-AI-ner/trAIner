package custom_objects;

import java.awt.*;

import functionality.Constants;
import functionality.Setup;
import game.Game;
import map_builder.ElementBlackHole;
import map_builder.MapElement;
import map_builder.MapType;
import map_builder.ElementStart;

public class Avatar extends Entity {
	
	private int toMoveX = getX();
	private int toMoveY = getY();
	
	private Setup setup;
	private Game game;
	private ElementBlackHole currentlyTouched;
	//Store the starting position of the Avatar so that we can reset it easily
	private int sourceX,sourceY;

	public Avatar(int x, int y, int width, int height, Color color) {
		super(x, y, width, height, EntityType.Avatar);
		sourceX=x;
		sourceY=y;
		this.setColor(color);
	}
	
	public void move(int incX, int incY){
		this.toMoveX = (int) (this.toMoveX == 0 ? this.toMoveX : this.getX() + incX); //POSSIBLE BUGS WITH KEYBOARD MODE AS OUR GAME WAS BUILT ON LIES
		this.toMoveY = (int) (this.toMoveY == 0 ? this.toMoveY : this.getY() + incY);//POSSIBLE BUGS WITH KEYBOARD MODE AS OUR GAME WAS BUILT ON LIES

		
		if(incY == 0) // left-arrow right-arrow
			this.toMoveX = (this.getX() + incX);
		else if(incX == 0)  // up-arrow down-arrow
			this.toMoveY = (this.getY() + incY);


			if(!checkForCollision()){
				if(blackHoled()) {

					toMoveX = currentlyTouched.getAttachedBlackHole().getX() + 4 * Constants.MAP_ELEMENT_SIZE;
					toMoveY =  currentlyTouched.getAttachedBlackHole().getY() +  4 *  Constants.MAP_ELEMENT_SIZE;

					int sideOffset = 3 * Constants.MAP_ELEMENT_SIZE;
					int attachedX = currentlyTouched.getAttachedBlackHole().getX();
					int attachedY = currentlyTouched.getAttachedBlackHole().getY();

					if(this.toMoveX <= 0  || this.toMoveX >= (Constants.WINDOW_MAP_WIDTH - getWidth())) {
						toMoveX = attachedX  - sideOffset ;
					} else {
						toMoveX = attachedX  +  sideOffset;
					}


					if(this.toMoveY <= 0  || this.toMoveY >= (Constants.WINDOW_MAP_HEIGHT - getHeight())) {
						toMoveY =  attachedY  - sideOffset;
					} else {
						toMoveY =  attachedY  + sideOffset;
					}

				} else if(finished()) {
					toMoveX = game.getStart().getX();
					toMoveY = game.getStart().getY();
					game.restart();
				}
			} else {
				toMoveX = getX();
				toMoveY = getY();
			}



		this.setX(toMoveX);
		this.setY(toMoveY);
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
				       this.toMoveX < 0 
					|| this.toMoveY < 0
					|| this.toMoveX > (Constants.WINDOW_MAP_WIDTH - getWidth()) 
					|| this.toMoveY > (Constants.WINDOW_MAP_HEIGHT - getHeight())
				);
	}


    /**
     * @return whether the avatar is inside or touching the black hole
     */
    private boolean blackHoled(){
        for (MapElement element: game.getMapElements()){
            if (toMoveX+getWidth() > element.getX()
                    && toMoveX < (element.getX()+element.getWidth())
                    && (toMoveY+getHeight() > element.getY()
                    && toMoveY < (element.getY()+element.getHeight()))
                    && element.getMapType() == MapType.BLACK_HOLE
            ) {
                currentlyTouched = (ElementBlackHole) element;
                return true;
            }
        }
        return false;
    }

	/**
	 * @return if the avatar is touching the finish block
	 */
	private boolean finished(){

		for (MapElement element: game.getMapElements()){
			if (toMoveX+getWidth() > element.getX()
					&& toMoveX < (element.getX()+element.getWidth())
					&& (toMoveY+getHeight() > element.getY()
					&& toMoveY < (element.getY()+element.getHeight()))
					&& element.getMapType() == MapType.FINISH
			) {
                System.out.println("in avatar finished");
				return true;
			}
		}
		return false;
	}

	/**
	 * @return if avatar collides with another map-element
	 */
	private boolean collidingWithMapElement(){

		for (MapElement element: game.getMapElements()){
			if (toMoveX+getWidth() > element.getX() 
					&& toMoveX < (element.getX()+element.getWidth())
					&& (toMoveY+getHeight() > element.getY()
					&& toMoveY < (element.getY()+element.getHeight()))
					&& element.getMapType() != MapType.BLACK_HOLE
					&& element.getMapType() != MapType.START
					&& element.getMapType() != MapType.FINISH
				)
				return true;
		}
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
        
        int offset = 3;
        int fillOffset = 2;
		
        //g2d.fillRect(getMapX(), getMapY(), getWidth(), getHeight());
        g2d.fillRect(getMapX()+fillOffset, getMapY()+fillOffset, getWidth()-fillOffset*2, getHeight()-fillOffset*2);
		//draw border
		Color color = new Color(getColor().getRed(),getColor().getGreen(),getColor().getBlue(),85);
		g2d.setStroke(new BasicStroke(3));
		g2d.setColor(color);
		//g2d.drawRect(getMapX(), getMapY(), getWidth(), getHeight());
		g2d.drawRect(getMapX()+offset, getMapY()+offset, getWidth()-offset*2, getHeight()-offset*2);
		g2d.setStroke(new BasicStroke(1));
	}


    /**
     * resets the avatar position to the starting position
     */
    public void reset() {
        setX(sourceX);
        setY(sourceY);
        toMoveX=sourceX;
        toMoveY=sourceY;
    }


	public void setSetup(Setup setup){
		this.setup = setup;
	}
	public void setGame(Game game){
		this.game = game;
	}
	
	public void setToStart(ElementStart start) {
		setX(start.getGridX());
		setY(start.getGridY());
	}
	
}
