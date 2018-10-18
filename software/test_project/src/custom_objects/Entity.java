package custom_objects;

import java.awt.Color;

public abstract class Entity {

	private int x;
	private int y;
	private int width;
	private int height;
	
	private double alpha;
	private Color color;
	
	public Entity(int x, int y, int width, int height, double alpha, Color color){
		this.x = x;
		this.y = y; 
		this.width = width;
		this.height = height;
		this.alpha = alpha;
		this.color = color;
	}
	
	public Entity(int x, int y, int width, int height){
		this.x = x;
		this.y = y;
		this.width = width;
		this.height = height;
	}
	
	@Override
	public String toString() {
		return "entity object, x: "+x+", y: "+y+", width: "+width+", height: "+height+
				", alpha: "+alpha+
				", color: "+color.getRed()+","+color.getGreen()+","+color.getBlue();
	}

	public int getX() {
		return x;
	}
	public void setX(int x) {
		this.x = x;
	}

	public int getY() {
		return y;
	}
	public void setY(int y) {
		this.y = y;
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
	
	
	
	
	
}
