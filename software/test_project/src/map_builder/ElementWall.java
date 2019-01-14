package map_builder;

import java.awt.*;

/**
 * map element of type: Wall
 * 
 * Any custom behavior should be implemented here
 * 
 * @author Patrick
 *
 */
public class ElementWall extends MapElement{


	public ElementWall(int gridX, int gridY, Color elementColor){
		super(gridX, gridY, MapType.WALL, elementColor);
		
	}
    
    public ElementWall(ElementWall object){
        super(object.getGridX(), object.getGridY(), MapType.WALL, object.getColor());
    }

}
