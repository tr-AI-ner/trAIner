package map_builder;

public class Map {
	
	int[][] map;
	int mapPixelWidth = 852;
	int mapPixelHeight = 520;
	
	final int mapElementWidth = mapPixelWidth / 4;
	final int mapElementHeight = mapPixelHeight / 4;
	
	public Map(){
		map = new int[mapElementWidth][mapElementHeight];
		initBasicMap();
	}
	
	private void initBasicMap(){
		for(int col=0; col<mapElementWidth; col+=4){
			for(int row=0; row<mapElementHeight; row+=4){
				
			}
		}
	}

}
