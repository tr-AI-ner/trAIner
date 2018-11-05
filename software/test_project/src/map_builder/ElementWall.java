package map_builder;

import java.awt.Color;
import java.awt.Graphics;

import custom_objects.EntityType;
import functionality.Constants;

public class ElementWall extends MapElement{



//	public ElementWall(int x, int y, int width, int height, Color elementColor) {
//		super(x, y, width, height, MapType.WALL, elementColor);
//	}
	public ElementWall(int gridX, int gridY, Color elementColor){
		super(gridX, gridY, MapType.WALL, elementColor);
		
	}
	

	@Override
	public void draw(Graphics graphics) {
//		System.out.println("okay drawing specific ElementWall");
		//draw fill
//		graphics.setColor(getColor());
//		graphics.fillRect(getMapX(), getMapY(), getWidth(), getHeight());

		//draw border
		//			graphics.setColor(Color.DARK_GRAY);
//		graphics.drawRect(getMapX(), getMapY(), getWidth(), getHeight());
		
		graphics.setColor(getColor());
		graphics.fillRect(
				(getGridX() * Constants.MAP_ELEMENT_SIZE) + Constants.WINDOW_MAP_MARGIN, 
				(getGridY()*Constants.MAP_ELEMENT_SIZE) + Constants.WINDOW_MAP_MARGIN+Constants.WINDOW_HEADER_HEIGHT, 
				getWidth(), getHeight());
		
		// draw border
		// set custom color here with graphics.setColor(borderColor);
		graphics.drawRect(
				(getGridX() * Constants.MAP_ELEMENT_SIZE) + Constants.WINDOW_MAP_MARGIN, 
				(getGridY()*Constants.MAP_ELEMENT_SIZE) + Constants.WINDOW_MAP_MARGIN+Constants.WINDOW_HEADER_HEIGHT, 
				getWidth(), getHeight());
	}

	//	@Override
	//	public String toString() {
	//		return "ElementWall - x: "+getX()+", y: "+getY()+", width: "+getWidth()+
	//				", height: "+getHeight();
	//	}

}
