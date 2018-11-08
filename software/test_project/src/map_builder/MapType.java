package map_builder;

public enum MapType {

//	EMPTY, WALL, LAND, WATER, START, FINISH;    // START and FINISH are also LAND
	
	//_		S		F	  W			O		E		~		L		P
	LAND, START, FINISH, WALL, BLACK_HOLE, ENEMY, WATER, LASER, PLASMA_BALL;

//	public static final String REPRESENTATIONSTRING = " W+.SF";
	public static final String REPRESENTATIONSTRING = "_SFWOE~LP";

	public char representation() {
		return  REPRESENTATIONSTRING.charAt(this.ordinal());
	}
	
}
