package map_builder;

import java.awt.Color;

/**
 * map element of type: Finish
 * 
 * Any custom behavior should be implemented here
 * 
 * @author Patrick
 *
 */
public class ElementFinish extends MapElement{

	public ElementFinish(int gridX, int gridY, Color elementColor) {
		super(gridX, gridY, MapType.FINISH, elementColor);
		
	}

}
