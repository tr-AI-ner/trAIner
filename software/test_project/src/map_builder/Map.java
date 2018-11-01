package map_builder;

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

}
