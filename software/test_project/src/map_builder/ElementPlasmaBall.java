package map_builder;

import java.awt.Color;

/**
 * map element of type: PlasmaBall
 * 
 * Any custom behavior should be implemented here
 * 
 * @author Patrick
 *
 */
public class ElementPlasmaBall extends MapElement {

	public ElementPlasmaBall(int gridX, int gridY, Color elementColor) {
		super(gridX, gridY, MapType.PLASMA_BALL, elementColor);
	}

}
