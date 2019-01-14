package functionality;

import java.awt.Color;
import java.awt.geom.RoundRectangle2D;

public final class Constants {

	public static final long FRAME_MINIMUM_MILLIS = 1;
	
	// basic window sizes
	public static final int WINDOW_MAP_WIDTH = 1024; //1024 or 960
	public static final int WINDOW_MAP_HEIGHT = 576; //576 or 540
	public static final int WINDOW_MAP_MARGIN = 20;
	public static final int WINDOW_HEADER_HEIGHT = 52; //52 or 70 - same for footer height
	public static final int WINDOW_RIGHT_BAR_WIDTH = 216; //216 or 280
	public static final int WINDOW_RIGHT_BAR_HEIGHT = 616; //616 or 580
	
//	public static final int WINDOW_MAP_WIDTH = 960; //1024 or 960
//	public static final int WINDOW_MAP_HEIGHT = 540; //576 or 540
//	public static final int WINDOW_MAP_MARGIN = 20;
//	public static final int WINDOW_HEADER_HEIGHT = 70; //52 or 70 - same for footer height
//	public static final int WINDOW_RIGHT_BAR_WIDTH = 280; //216 or 280
//	public static final int WINDOW_RIGHT_BAR_HEIGHT = 580; //616 or 580
	
	public static final int WINDOW_MAP_X0 = WINDOW_MAP_MARGIN;
	public static final int WINDOW_MAP_Y0 = WINDOW_HEADER_HEIGHT + WINDOW_MAP_MARGIN;
	
    // width & height of a grid element (square) in pixels
	public static final int MAP_ELEMENT_SIZE = 16;
    // number of grid columns
    public static final int GRID_COLUMNS = WINDOW_MAP_WIDTH / MAP_ELEMENT_SIZE;
    // number of grid rows
    public static final int GRID_ROWS = WINDOW_MAP_HEIGHT / MAP_ELEMENT_SIZE;

	// Avatar settings
    public static final int AVATAR_HEIGHT = MAP_ELEMENT_SIZE;
    public static final int AVATAR_WIDTH = MAP_ELEMENT_SIZE;

    public static final int AVATAR_START_X = (WINDOW_MAP_X0+WINDOW_MAP_WIDTH - AVATAR_WIDTH / 2)/2;
    public static final int AVATAR_START_Y = (WINDOW_MAP_HEIGHT+WINDOW_MAP_Y0 - AVATAR_HEIGHT/2) / 2;


	//colors
	public static final Color COLOR_BACKGROUND = new Color(38,40,71); // #262847
	public static final Color COLOR_HEADER_1 = new Color(30,37,59); // #1E253B
	public static final Color COLOR_HEADER_2 = new Color(42,50,77); // #2A324D
	public static final Color COLOR_RIGHT_BAR_HEADER = new Color(44,47,81); // #2C2F51
    //map elements colors
    public static final Color COLOR_WALL = new Color(249,62,65); // #262847
	public static final Color COLOR_FORCE_FIELD = new Color(64,186,245); // #1E253B
	public static final Color COLOR_LASER = new Color(72,159,223); // #2A324D
	public static final Color COLOR_PLASMA_BALL = new Color(240,100,73); // #343B62
	public static final Color COLOR_ENEMY = new Color(22,244,208); // #16F4D0
	public static final Color COLOR_BLACK_HOLE = new Color(22,25,27); // #6C4D6F
	public static final Color COLOR_MAP_LAND = new Color(52,59,98); // #343B62
	public static final Color COLOR_MAP_START = new Color(65,72,111); // #41486F
	public static final Color COLOR_MAP_FINISH = new Color(108,77,111); // #6C4D6F
	public static final Color COLOR_WATER = new Color(108,77,111); // #6C4D6F
  
	public static final Color COLOR_AVATAR_RED = new Color(252,137,130); // #FC8982
	public static final Color COLOR_AVATAR_BLUE = new Color(56,110,255); // #386EFF
	public static final Color COLOR_AVATAR_WHITE = new Color(222,226,237); // #DEE2ED
	
	public static final Color COLOR_ACCENT = new Color(127,119,229); // #7F77E5
	public static final Color COLOR_ACCENT_2 = new Color(154,240,144); // #9AF090

	public static final Color COLOR_COPYRIGHT = new Color(67,76,114); // #DEE2ED
	
	
	// strings
	public static final String COPYRIGHT = "© 2018 trAIner. All rights reserved";
	public static final String GAME_NAME = "trAIner";
	public static final String DEFAULT_FONT = "Helvetica";

    // Genetic Algorithm parameters max
    public static final int MAX_POPULATION_SIZE = 1000;
    public static final int MAX_SPEED = 1000;
    public static final int MAX_NO_OF_MOVES = 1000;
    public static final int MAX_NO_OF_GENERATIONS = 1000;
    public static final int MAX_MUTATION_RATE = 1000;
	
	
	
    //Load and Save Button
	public static final int BUTTON_SAVE_X = 360; 
	public static final int BUTTON_LOAD_X = 280;
	public static final int BUTTON_SAVE_Y = 35;
	public static final int BUTTON_LOAD_Y = 35;
	public static final int BUTTON_OFFSET_X = 15;
	public static final int BUTTON_OFFSET_Y = 20;
	
	public static final int BUTTON_ARCH_WH = 30;
	public static final int BUTTON_WIDTH = 70;
	public static final int BUTTON_HEIGHT = 30;
	

}
