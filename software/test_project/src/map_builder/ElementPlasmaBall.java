package map_builder;

import functionality.Constants;

import java.awt.*;

/**
 * map element of type: PlasmaBall
 * 
 * Any custom behavior should be implemented here
 * 
 * @author Patrick
 *
 */
public class ElementPlasmaBall extends MapElement {

	/**
	 *
	 *Direction variables for the walk functions
	 *
	 * @author Kasparas
	 *
	 */
	boolean shouldGoRight = true;
	boolean shouldGoDown = true;
	private int sourceX,sourceY;

	public ElementPlasmaBall(int gridX, int gridY, Color elementColor) {
		super(gridX, gridY, MapType.PLASMA_BALL, elementColor);
        sourceX = gridX;
        sourceY = gridY;
	}



	/**
	 *
	 * This functions checks which direction on the X axis the element should go and goes there unless it
	 * reaches the side of the map at which point it turns around
	 *
	 * @author Kasparas
	 *
	 */
	private void getNextXCoordinate() {
		int endX = Constants.WINDOW_MAP_WIDTH/Constants.MAP_ELEMENT_SIZE;
		int currentX = getGridX();
		if(shouldGoRight) {
			if(currentX+2 < endX) {
				setGridX(currentX + 1);
			} else {
				shouldGoRight = false;
			}
		} else {
			if(currentX-1 > 0) {
				setGridX(currentX - 1);
			} else {
				shouldGoRight = true;
			}
		}
	}

	/**
	 *
	 * This functions checks which direction on the Y axis the element should go and goes there unless it
	 * reaches the vertical limit of the map at which point it turns around
	 *
	 * @author Kasparas
	 *
	 */
	private void getNextYCoordinate() {
		int endY = Constants.WINDOW_MAP_HEIGHT/Constants.MAP_ELEMENT_SIZE;
		int currentY = getGridY();
		if(shouldGoDown) {
			if(currentY+2 < endY) {
				setGridY(currentY + 1);
			} else {
				shouldGoDown = false;
			}
		} else {
			if(currentY-1 > 0) {
				setGridY(currentY - 1);
			} else {
				shouldGoDown = true;
			}
		}
	}

    /**
	 *
	 * This functions checks which direction on the Y axis the element should go and goes there unless it
	 * reaches the vertical limit of the map at which point it turns around
	 *
	 * @author Kasparas
	 *
	 */
	private void getNextYCoordinateSin() {
		int endY = Constants.WINDOW_MAP_HEIGHT/Constants.MAP_ELEMENT_SIZE;
		double nextY = Math.sin((double)getGridX());
		setGridY((int)(nextY * 2 + 18));
	}

	public void update() {
		getNextXCoordinate();
		getNextYCoordinate();
		//getNextYCoordinateSin();
	}
	public void reset() {
	    setGridX(sourceX);
	    setGridY(sourceY);
    }

}
