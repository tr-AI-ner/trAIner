package map_builder;

import java.awt.Color;

/**
 * map element of type: Start
 * 
 * Any custom behavior should be implemented here
 * 
 * @author Patrick
 *
 */
public class ElementStart extends MapElement {

	public ElementStart(int gridX, int gridY, Color elementColor) {
		super(gridX, gridY, MapType.START, elementColor);
	}

}
