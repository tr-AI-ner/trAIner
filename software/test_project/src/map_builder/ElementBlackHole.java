package map_builder;

import java.awt.Color;

/**
 * map element of type: Black_Hole
 * 
 * Any custom behavior should be implemented here
 * 
 * @author Patrick
 *
 */
public class ElementBlackHole extends MapElement {

	public ElementBlackHole(int gridX, int gridY, Color elementColor) {
		super(gridX, gridY, MapType.BLACK_HOLE, elementColor);
	}

}
