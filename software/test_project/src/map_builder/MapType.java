package map_builder;

public enum MapType {

	EMPTY, WALL, LAND, WATER, START, FINISH;    // START and FINISH are also LAND

	public static final String REPRESENTATIONSTRING = " W+.SF";

	public char representation() {
		return  REPRESENTATIONSTRING.charAt(this.ordinal());
	}
	
}
