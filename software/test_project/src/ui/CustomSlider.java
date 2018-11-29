package ui;

import java.awt.Color;
import java.awt.Graphics;

import functionality.Constants;
import functionality.Setup;

public class CustomSlider extends UIElement{

	private Color sliderColor;
	
	int sliderCircleRadius = 16;
	int offset = 2;
	
	public CustomSlider(int x, int y, int width, int height, Color sliderColor, Setup setup) {
		super(x, y, width, height, Constants.COLOR_MAP_LAND, setup);	//doesn't use background color
		
	}
	
	@Override
	public void draw(Graphics graphics) {
		super.draw(graphics);
		
		graphics.setColor(new Color(255,0,0));
		graphics.fillRoundRect(getX(), getY(), getWidth(), getHeight(), getHeight(), getHeight());
		
		graphics.setColor(Constants.COLOR_AVATAR_WHITE);
		graphics.fillOval(getX()+(getWidth()/2)-(sliderCircleRadius/2), 
				getY()-(sliderCircleRadius/2)+offset, 
				sliderCircleRadius, sliderCircleRadius);
	}

}
