package map_builder;

import java.awt.Color;
import java.awt.Graphics;
import java.util.ArrayList;

import custom_objects.Avatar;
import custom_objects.Entity;
import functionality.Constants;

public class Map {
	
	//map size 1024 x 576
	/*
	 * ___________________________________________________________________
	 * | ______________________________________________
	 * | |
	 * | |
	 * | |
	 * | |
	 * | |
	 * | |
	 * | |
	 * | |
	 * | |
	 * | |
	 * | ------------------------------------------------
	 * ___________________________________________________________________
	 * 
	 */
	
	// how many rows the grid has
	int rows = Constants.WINDOW_MAP_HEIGHT / Constants.MAP_ELEMENT_SIZE;
	// how many columns the grid has
	int columns = Constants.WINDOW_MAP_WIDTH / Constants.MAP_ELEMENT_SIZE;
	
	char[][] map;
//	final int mapPixelWidth = 1024;
//	final int mapPixelHeight = 576;
	
//	final int mapElementWidth = mapPixelWidth / 4;
//	final int mapElementHeight = mapPixelHeight / 4;
	
	public Map(){
		map = new char[columns][rows];
		initBasicMap();
	}
	
	/**
	 * init basic map with only LAND
	 */
	private void initBasicMap(){
		for(int col=0; col<columns; col++){
			for(int row=0; row<rows; row++){
				map[col][row] = MapType.LAND.representation();
			}
		}
	}
	
	/**
	 * draw full map
	 * 
	 * @param graphics
	 */
	public void draw(Graphics graphics, ArrayList<Entity> entities){
//		graphics.setColor(Constants.COLOR_MAP_LAND);
//		graphics.setColor(Constants.COLOR_BACKGROUND);
//		graphics.fillRect(Constants.WINDOW_MAP_MARGIN,
//						  Constants.WINDOW_HEADER_HEIGHT+Constants.WINDOW_MAP_MARGIN, 
//						  Constants.WINDOW_MAP_WIDTH, 
//						  Constants.WINDOW_MAP_HEIGHT);
		
		// update entities in map array
		updateEntitiesInMap(entities);
		
		// set grid color for map
		graphics.setColor(Constants.COLOR_MAP_LAND);
		
		// draw grid for map
		for(int i=0; i<columns; i++){
			for(int j=0; j<rows; j++){
				// draw grid
				graphics.drawRect(
						(i * Constants.MAP_ELEMENT_SIZE) + Constants.WINDOW_MAP_MARGIN, 
						(j*Constants.MAP_ELEMENT_SIZE)+Constants.WINDOW_HEADER_HEIGHT+Constants.WINDOW_MAP_MARGIN, 
						Constants.MAP_ELEMENT_SIZE, 
						Constants.MAP_ELEMENT_SIZE
				);
			}
		}
		
		//TODO: this could be moved in the above loop if map elements have static colors
		// draw entities
		for (Entity e: entities){
			if (!(e instanceof Avatar))
				e.draw(graphics);

		}
		
		// draw entities which are avatar(s) only, since they're not bound to the grid (they can move freely)
		for (Entity avatar: entities){
			if (avatar instanceof Avatar){
				avatar.draw(graphics);
			}
		}
		
	}
	
	private void updateEntitiesInMap(ArrayList<Entity> entities){
		// clear map
		initBasicMap();
		
		// add map elements to map
		for (Entity e: entities){
			if ((e instanceof MapElement)){
				map[((MapElement)e).getGridX()][((MapElement)e).getGridY()] = 
						((MapElement)e).getMapType().representation();
			}
		}
	}
	

}
