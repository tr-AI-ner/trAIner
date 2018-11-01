package map_builder;

import java.awt.Color;
import java.awt.Graphics;

import custom_objects.Entity;
import custom_objects.EntityType;

public abstract class MapElement extends Entity {
	
//	private int x;
//	private int y;
//	
//	private int width;
//	private int height;
	
	private MapType mapType;
	
//	private Color elementColor;
	
	public MapElement(int x, int y, int width, int height, MapType mapType, Color elementColor){
		super(x, y, width, height, elementColor, EntityType.MapElement);
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
		graphics.setColor(getColor());
		graphics.fillRect(getMapX(), getMapY(), getWidth(), getHeight());
		
		//draw border
//		graphics.setColor(Color.DARK_GRAY);
		graphics.drawRect(getMapX(), getMapY(), getWidth(), getHeight());
	}
	
	@Override
	public String toString() {
		return "MapElement ("+mapType.name()+") - x: "+getX()+", y: "+getY()+", width: "+getWidth()+
				", height: "+getHeight();
	}
	
//	public boolean isCollidingWith(Entity e){
//		return (
//				
//				);
//	}


	public MapType getMapType() {
		return mapType;
	}
	public void setMapType(MapType mapType) {
		this.mapType = mapType;
	}
	
}
