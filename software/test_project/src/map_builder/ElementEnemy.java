package map_builder;

import java.awt.Color;

/**
 * map element of type: Enemy
 * 
 * Any custom behavior should be implemented here
 * 
 * @author Patrick
 *
 */
public class ElementEnemy extends MapElement {

	public ElementEnemy(int gridX, int gridY, Color elementColor) {
		super(gridX, gridY, MapType.ENEMY, elementColor);
	}

}
