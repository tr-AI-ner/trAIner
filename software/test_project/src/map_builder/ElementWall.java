package map_builder;

import java.awt.Color;
import java.awt.Graphics;

public class ElementWall extends MapElement{



	public ElementWall(int x, int y, int width, int height, Color elementColor) {
		super(x, y, width, height, MapType.WALL, elementColor);
	}

	@Override
	public void draw(Graphics graphics) {
//		System.out.println("okay drawing specific ElementWall");
		//draw fill
		graphics.setColor(getColor());
		graphics.fillRect(getX(), getY(), getWidth(), getHeight());

		//draw border
		//			graphics.setColor(Color.DARK_GRAY);
		graphics.drawRect(getX(), getY(), getWidth(), getHeight());
	}

	//	@Override
	//	public String toString() {
	//		return "ElementWall - x: "+getX()+", y: "+getY()+", width: "+getWidth()+
	//				", height: "+getHeight();
	//	}

}
