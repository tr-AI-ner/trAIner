package map_builder;

import java.awt.Color;

/**
 * map element of type: Watwe
 * 
 * Any custom behavior should be implemented here
 * 
 * @author Patrick
 *
 */
public class ElementWater extends MapElement {

	public ElementWater(int gridX, int gridY, Color elementColor) {
		super(gridX, gridY, MapType.WATER, elementColor);
	}
    
    public ElementWater(ElementWater object){
        super(object.getGridX(), object.getGridY(), MapType.WATER, object.getColor());
    }

}
