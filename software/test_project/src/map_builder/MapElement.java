package map_builder;

import java.awt.Color;
import java.awt.Graphics;

import custom_objects.Entity;
import custom_objects.EntityType;
import functionality.Constants;

public abstract class MapElement extends Entity {
	
	private int gridX;
	private int gridY;
//	
//	private int width;
//	private int height;
	
	private MapType mapType;
	
//	private Color elementColor;
	
//	public MapElement(int x, int y, int width, int height, MapType mapType, Color elementColor){
//		super(x, y, width, height, elementColor, EntityType.MapElement);
//		this.mapType = mapType;
//	}
	
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
		//draw fill
//		graphics.setColor(getColor());
//		graphics.fillRect(getMapX(), getMapY(), getWidth(), getHeight());
//		
//		//draw border
////		graphics.setColor(Color.DARK_GRAY);
//		graphics.drawRect(getMapX(), getMapY(), getWidth(), getHeight());
		
		// draw fill
		graphics.setColor(getColor());
		graphics.fillRect(
				(gridX * Constants.MAP_ELEMENT_SIZE) + Constants.WINDOW_MAP_MARGIN, 
				(gridY*Constants.MAP_ELEMENT_SIZE) + Constants.WINDOW_MAP_MARGIN+Constants.WINDOW_HEADER_HEIGHT, 
				getWidth(), getHeight());
		
		// draw border
		// set custom color here with graphics.setColor(borderColor);
		graphics.drawRect(
				(gridX * Constants.MAP_ELEMENT_SIZE) + Constants.WINDOW_MAP_MARGIN, 
				(gridY*Constants.MAP_ELEMENT_SIZE) + Constants.WINDOW_MAP_MARGIN+Constants.WINDOW_HEADER_HEIGHT, 
				getWidth(), getHeight());
	}
	
	@Override
	public String toString() {
		return "MapElement ("+mapType.name()+") - gridX: "+getGridX()+", gridY: "+getGridY()+", width: "+getWidth()+
				", height: "+getHeight();
	}
	
//	public boolean isCollidingWith(Entity e){
//		return (
//				
//				);
//	}

	
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
