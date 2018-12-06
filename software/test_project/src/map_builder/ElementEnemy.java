package map_builder;

import java.awt.Color;
import functionality.Constants;

/**
 * map element of type: Enemy
 * 
 * Any custom behavior should be implemented here
 * 
 * @author Patrick
 *
 */
public class ElementEnemy extends MapElement {

    private int sourceX,sourceY;

    int temp = 0;
    //move right == true | move left == false
    boolean forward = true;

	public ElementEnemy(int gridX, int gridY, Color elementColor) {
		super(gridX, gridY, MapType.ENEMY, elementColor);
        sourceX = gridX;
        sourceY = gridY;
	}


    public void update() {
        if (getGridX() >= Constants.GRID_COLUMNS-1){
            forward = false;
        }
        else if (getGridX() <= 0){
            forward = true;
        }

        if (forward){ temp++; }
        else { temp--; }
        
        this.setGridX(temp);
    }

    public void reset() {
        setGridX(sourceX);
        setGridY(sourceY);
    }
}
