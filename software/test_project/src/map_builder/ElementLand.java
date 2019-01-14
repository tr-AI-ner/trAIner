package map_builder;

import java.awt.Color;

/**
 * map element of type: Land
 * 
 * Any custom behavior should be implemented here
 * 
 * @author Patrick
 *
 */
public class ElementLand extends MapElement {

	public ElementLand(int gridX, int gridY, Color elementColor) {
		super(gridX, gridY, MapType.LAND, elementColor);
	}
    
    public ElementLand(ElementLand object){
        super(object.getGridX(), object.getGridY(), MapType.LAND, object.getColor());
    }

}
