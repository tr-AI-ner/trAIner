package map_builder;

import java.awt.Color;

import functionality.Constants;

public enum MapType {

//	EMPTY, WALL, LAND, WATER, START, FINISH;    // START and FINISH are also LAND
	
	//_		S		F	  W			O		E		~		L		P
	LAND, START, FINISH, WALL, BLACK_HOLE, ENEMY, WATER, LASER, PLASMA_BALL;

//	public static final String REPRESENTATIONSTRING = " W+.SF";
	public static final String REPRESENTATIONSTRING = "_SFWOE~LP";

	public char representation() {
		return  REPRESENTATIONSTRING.charAt(this.ordinal());
	}
	
	public Color getTypeColor(){
		switch (this) {
		case LAND: 			return Constants.COLOR_MAP_LAND;
		case START: 		return Constants.COLOR_MAP_START;
		case FINISH: 		return Constants.COLOR_MAP_FINISH;
		case WALL: 			return Constants.COLOR_WALL;
		case BLACK_HOLE: 	return Constants.COLOR_BLACK_HOLE;
		case ENEMY: 		return Constants.COLOR_ENEMY;
		case LASER: 		return Constants.COLOR_LASER;
		case PLASMA_BALL: 	return Constants.COLOR_PLASMA_BALL;
		default:			return Constants.COLOR_MAP_LAND;
		}
	}
	
	public static MapType getTypeFromColor(char c){
		char[] chars = REPRESENTATIONSTRING.toCharArray();
		
		for (int i=0; i<chars.length; i++){
			if (c==chars[i]){
				return MapType.values()[i];
			}
		}
		return MapType.LAND;
	}
	
}
