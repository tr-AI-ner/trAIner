package map_builder;

public enum MapType {

//	EMPTY, WALL, LAND, WATER, START, FINISH;    // START and FINISH are also LAND
	
	//_		S		F	  W			O		E		~		L
	LAND, START, FINISH, WALL, BLACK_HOLE, ENEMY, WATER, LASER;

//	public static final String REPRESENTATIONSTRING = " W+.SF";
	public static final String REPRESENTATIONSTRING = "_SFWOE~L";

	public char representation() {
		return  REPRESENTATIONSTRING.charAt(this.ordinal());
	}
	
}
