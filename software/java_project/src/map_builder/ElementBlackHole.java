package map_builder;

import functionality.Constants;

import java.awt.*;

/**
 * map element of type: Black_Hole
 * 
 * Any custom behavior should be implemented here
 * 
 * @author Patrick
 * @author Kasparas
 *
 */
public class ElementBlackHole extends MapElement {

    //The hole to which the avatar will be transported to
    private ElementBlackHole attachedBlackHole;

	public ElementBlackHole(int gridX, int gridY, Color elementColor) {
        super(gridX, gridY,2,2, MapType.BLACK_HOLE, elementColor);
    }
    public ElementBlackHole(int gridX, int gridY, Color elementColor, ElementBlackHole attachedBlackHole) {
        super(gridX, gridY,2,2, MapType.BLACK_HOLE, elementColor);
    }

    public ElementBlackHole(ElementBlackHole object){
        super(object.getGridX(), object.getGridY(),2,2, MapType.BLACK_HOLE, object.getColor());
    }

	@Override
	public void draw(Graphics graphics) {
		int x = (getGridX() * Constants.MAP_ELEMENT_SIZE) + Constants.WINDOW_MAP_MARGIN;
		int y = (getGridY()*Constants.MAP_ELEMENT_SIZE) + Constants.WINDOW_MAP_MARGIN+Constants.WINDOW_HEADER_HEIGHT;

		Graphics2D g2d = (Graphics2D)graphics;

		g2d.setColor(getColor());
		g2d.fillRect(x, y, getWidth(), getHeight());


		//Border color is just the main color but with an alpha value
		Color color = new Color(getColor().getRed(),getColor().getGreen(),getColor().getBlue(),85);
		g2d.setColor(color);
		g2d.setStroke(new BasicStroke(10));
		g2d.drawRect(x, y, getWidth(), getHeight());
		g2d.setStroke(new BasicStroke(1));
	}

    public MapElement getAttachedBlackHole() {
        return attachedBlackHole;
    }

    public void setAttachedBlackHole(ElementBlackHole attachedBlackHole) {
        this.attachedBlackHole = attachedBlackHole;
    }
}
