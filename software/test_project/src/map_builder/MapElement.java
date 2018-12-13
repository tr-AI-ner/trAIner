package map_builder;

import java.awt.*;

import custom_objects.Entity;
import custom_objects.EntityType;
import functionality.Constants;

public abstract class MapElement extends Entity {
	
	private int gridX;
	private int gridY;

	
	private MapType mapType;

	public MapElement(int gridX, int gridY, int width, int height, MapType mapType, Color elementColor){
		super(gridX*Constants.MAP_ELEMENT_SIZE, gridY*Constants.MAP_ELEMENT_SIZE,
				width * Constants.MAP_ELEMENT_SIZE, height * Constants.MAP_ELEMENT_SIZE, elementColor, EntityType.MapElement);
		this.gridX = gridX;
		this.gridY = gridY;
		this.mapType = mapType;
	}
	
	public MapElement(int gridX, int gridY, MapType mapType, Color elementColor){
		super(gridX*Constants.MAP_ELEMENT_SIZE, gridY*Constants.MAP_ELEMENT_SIZE, 
				Constants.MAP_ELEMENT_SIZE, Constants.MAP_ELEMENT_SIZE, elementColor, EntityType.MapElement);
		this.gridX = gridX;
		this.gridY = gridY;
		this.mapType = mapType;
	}
	
	/**
	 * move the element
	 * @param newX
	 * @param newY
	 */
	public void move(int newX, int newY){
		this.setX(newX);
		this.setY(newY);
	}
	
	public boolean mousePressedInRange(int mouseX, int mouseY){
		return (getX() <= mouseX
				&& getX()+getWidth() >= mouseX
				&& getY() <= mouseY
				&& getY()+getHeight() >= mouseY
				);
	}
	
	/**
	 * draws a basic rectangle as map-element
	 * 
	 * other classes extending this class should overwrite this function
	 * to have the element appear differently
	 */
	public void draw(Graphics graphics){

        Graphics2D g2d = (Graphics2D)graphics;
        int x = (gridX * Constants.MAP_ELEMENT_SIZE) + Constants.WINDOW_MAP_MARGIN;
        int y = (gridY*Constants.MAP_ELEMENT_SIZE) + Constants.WINDOW_MAP_MARGIN+Constants.WINDOW_HEADER_HEIGHT;

		// draw fill
		g2d.setColor(getColor());
        g2d.fillRect(x, y, getWidth(), getHeight());
        //Border color is just the main color but with an alpha value
        Color color = new Color(getColor().getRed(),getColor().getGreen(),getColor().getBlue(),85);
        g2d.setColor(color);
        g2d.setStroke(new BasicStroke(5));
        g2d.drawRect(x, y, getWidth(), getHeight());
        g2d.setStroke(new BasicStroke(1));
		// draw border
		// set custom color here with graphics.setColor(borderColor);
        g2d.drawRect(x, y, getWidth(), getHeight());

	}

    public void drawTemporary(Graphics graphics){
        Graphics2D g2d = (Graphics2D)graphics;
        int x = getX();
        int y = getY();

		// draw fill
		g2d.setColor(getColor());
        g2d.fillRect(x, y, getWidth(), getHeight());
        //Border color is just the main color but with an alpha value
        Color color = new Color(getColor().getRed(),getColor().getGreen(),getColor().getBlue(),85);
        g2d.setColor(color);
        g2d.setStroke(new BasicStroke(5));
        g2d.drawRect(x, y, getWidth(), getHeight());
        g2d.setStroke(new BasicStroke(1));
		// draw border
		// set custom color here with graphics.setColor(borderColor);
        g2d.drawRect(x, y, getWidth(), getHeight());
    
    }

    /**
     *
     */
    public static Entity getSpecificInstance(MapType theType, int gridX, int gridY){
        switch (theType){
            case START: return new ElementStart(gridX, gridY, Constants.COLOR_MAP_START);
            case FINISH: return new ElementFinish(gridX, gridY, Constants.COLOR_MAP_FINISH);
            case WALL: return new ElementWall(gridX, gridY, Constants.COLOR_WALL);
            case BLACK_HOLE: return new ElementBlackHole(gridX, gridY, Constants.COLOR_BLACK_HOLE);
            case ENEMY: return new ElementEnemy(gridX, gridY, Constants.COLOR_ENEMY);
            case WATER: return new ElementWater(gridX, gridY, Constants.COLOR_WATER);
            case LASER: return new ElementLaser(gridX, gridY, Constants.COLOR_LASER);
            case PLASMA_BALL: return new ElementPlasmaBall(gridX, gridY, Constants.COLOR_PLASMA_BALL);
            default: return new ElementLand(gridX, gridY, Constants.COLOR_MAP_LAND);
        }
    }

	@Override
	public String toString() {
		return "MapElement ("+mapType.name()+") - gridX: "+getGridX()+", gridY: "+getGridY()+", width: "+getWidth()+
				", height: "+getHeight();
	}

    /**
     *  Update the position of the dynamic element
     */
	public void update(){}

    /**
     * Reset the dynamic element to their starting position
     */
	public void reset() {}

	public int getGridX() {
		return gridX;
	}
	public void setGridX(int gridX) {
		this.setX(gridX*Constants.MAP_ELEMENT_SIZE);
		this.gridX = gridX;
	}

	public int getGridY() {
		return gridY;
	}
	public void setGridY(int gridY) {
		this.setY(gridY*Constants.MAP_ELEMENT_SIZE);
		this.gridY = gridY;
	}

	public MapType getMapType() {
		return mapType;
	}
	public void setMapType(MapType mapType) {
		this.mapType = mapType;
	}
	
}
