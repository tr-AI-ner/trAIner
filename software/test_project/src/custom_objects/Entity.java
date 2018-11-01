package custom_objects;

import java.awt.Color;
import java.awt.Graphics;

import functionality.Constants;

public abstract class Entity {

	private int x;
	private int y;
	private int width;
	private int height;
	
	private double alpha;
	private Color color;
	
	private EntityType type = EntityType.Default;
	
	public Entity(int x, int y, int width, int height, double alpha, Color color, EntityType type){
		this.x = x;
		this.y = y; 
		this.width = width;
		this.height = height;
		this.alpha = alpha;
		this.color = color;
		this.type = type;
	}
	
	public Entity(int x, int y, int width, int height, Color color, EntityType type){
		this.x = x;
		this.y = y; 
		this.width = width;
		this.height = height;
		this.color = color;
		this.type = type;
	}
	
	public Entity(int x, int y, int width, int height, EntityType type){
		this.x = x;
		this.y = y;
		this.width = width;
		this.height = height;
		this.type = type;
	}
	
	/**
	 * draw a basic entity
	 * (any class extending this one should override this function)
	 * 
	 * @param graphics
	 */
	public void draw(Graphics graphics){
		//draw fill
		graphics.setColor(getColor());
		graphics.fillRect(getX(), getY(), getWidth(), getHeight());
		//draw border with same color
		graphics.drawRect(getX(), getY(), getWidth(), getHeight());
	}
	
	@Override
	public String toString() {
		return "entity object of type "+type.name()+", x: "+x+", y: "+y+", width: "+width+", height: "+height+
				", alpha: "+alpha+
				", color: "+color.getRed()+","+color.getGreen()+","+color.getBlue();
	}

	public int getX() {
		return x;
	}
	public void setX(int x) {
		this.x = x;
	}
	public int getMapX(){
		return x + Constants.WINDOW_MAP_X0;
	}

	public int getY() {
		return y;
	}
	public void setY(int y) {
		this.y = y;
	}
	public int getMapY(){
		return y + Constants.WINDOW_MAP_Y0;
	}
	
	public int getWidth() {
		return width;
	}
	public void setWidth(int width) {
		this.width = width;
	}

	public int getHeight() {
		return height;
	}
	public void setHeight(int height) {
		this.height = height;
	}

	public double getAlpha() {
		return alpha;
	}
	public void setAlpha(double alpha) {
		this.alpha = alpha;
	}

	public Color getColor() {
		return color;
	}
	public void setColor(Color color) {
		this.color = color;
	}

	public EntityType getType() {
		return type;
	}
//	public String getTypeString(){
//		
//	}
	public void setType(EntityType type) {
		this.type = type;
	}
	
	
	
}
