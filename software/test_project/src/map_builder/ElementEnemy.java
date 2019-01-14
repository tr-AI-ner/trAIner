package map_builder;

import java.util.ArrayList;
import java.awt.Color;
import functionality.Constants;
import map_builder.*;

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
        temp = gridX;
	}

    public ElementEnemy(ElementEnemy object){
        super(object.getGridX(), object.getGridY(), MapType.ENEMY, object.getColor());
        sourceX = object.getGridX();
        sourceY = object.getGridY();
        this.temp = object.getGridX();
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

    /**
     * updates an enemy - makes it move forward and backward
     *
     * the enemy will either bounce off the border of the map or other map elements (currently only walls)
     *
     * @author Patrick
     *
     */
    public void update(ArrayList<MapElement> mapElements) {
        ArrayList<Integer> collidingElems = findCollidingElements(mapElements);

        if (collidingElems.size() > 0){
            int leftColliding = -1, rightColliding = -1;
            int leftDistance = 10000, rightDistance = 10000;
            for(int i=0; i<collidingElems.size(); i++){
                MapElement elem = mapElements.get(collidingElems.get(i));
                if(elem.getGridX() < this.getGridX() && Math.abs(elem.getGridX()-this.getGridX())<leftDistance){
                    leftColliding = i;
                }
                if(elem.getGridX() > this.getGridX() && Math.abs(elem.getGridX()-this.getGridX())<rightDistance){
                    rightColliding = i;
                }
            }

            if ((rightColliding > -1 && getGridX() >= mapElements.get(collidingElems.get(rightColliding)).getGridX()-1) 
                    || getGridX() >= Constants.GRID_COLUMNS-1){
                forward = false;
            }
            else if ((leftColliding > -1 && getGridX() <= mapElements.get(collidingElems.get(leftColliding)).getGridX()+1) 
                    || getGridX() <= 0){
                forward = true;
            }

            if (forward){ temp++; }
            else { temp--; }

        } else {
            if (getGridX() >= Constants.GRID_COLUMNS-1){
                forward = false;
            }
            else if (getGridX() <= 0){
                forward = true;
            }

            if (forward){ temp++; }
            else { temp--; }
        }
        
        this.setGridX(temp);
    }

    /**
     * finds all colliding map elements (currently only maps) on same y-coordinate as the enemy
     * 
     * @return an arraylist of indeces which elements of @param mapElements are concerned
     *
     * @author Patrick
     *
     */
    private ArrayList<Integer> findCollidingElements(ArrayList<MapElement> mapElements){
        ArrayList<Integer> collidingElems = new ArrayList<>();

        for (int i=0; i < mapElements.size(); i++){
            if(mapElements.get(i).getMapType()==MapType.WALL && mapElements.get(i).getGridY() == this.getGridY()){
                collidingElems.add(i);
            }
        }

        return collidingElems;
    }

    public void reset() {
        setGridX(sourceX);
        setGridY(sourceY);
        temp = sourceX;
    }
}
