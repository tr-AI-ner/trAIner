package map_builder;

import java.awt.Graphics;

import functionality.Constants;

public class Map {
	
	//full window size 1280x720
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
	
	char[][] map;
//	final int mapPixelWidth = 1024;
//	final int mapPixelHeight = 576;
	
//	final int mapElementWidth = mapPixelWidth / 4;
//	final int mapElementHeight = mapPixelHeight / 4;
	
	public Map(){
		map = new char[Constants.WINDOW_MAP_WIDTH][Constants.WINDOW_MAP_HEIGHT];
		initBasicMap();
	}
	
	/**
	 * init basic map with only LAND
	 */
	private void initBasicMap(){
		for(int col=0; col<Constants.WINDOW_MAP_WIDTH; col+=4){
			for(int row=0; row<Constants.WINDOW_MAP_HEIGHT; row+=4){
				map[col][row] = MapType.LAND.representation();
			}
		}
	}
	
	/**
	 * draw full map
	 * 
	 * @param graphics
	 */
	public void draw(Graphics graphics){
		graphics.setColor(Constants.COLOR_MAP_LAND);
		graphics.fillRect(Constants.WINDOW_MAP_MARGIN,
						  Constants.WINDOW_HEADER_HEIGHT+Constants.WINDOW_MAP_MARGIN, 
						  Constants.WINDOW_MAP_WIDTH, 
						  Constants.WINDOW_MAP_HEIGHT);
	}

}
