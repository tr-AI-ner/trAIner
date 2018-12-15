package map_builder;

import java.awt.Color;

/**
 * map element of type: Laser
 * 
 * Any custom behavior should be implemented here
 * 
 * @author Patrick
 *
 */
public class ElementLaser extends MapElement {

	public ElementLaser(int gridX, int gridY, Color elementColor) {
		super(gridX, gridY, MapType.LASER, elementColor);
	}
    
    public ElementLaser(ElementLaser object){
        super(object.getGridX(), object.getGridY(), MapType.LASER, object.getColor());
    }

}
