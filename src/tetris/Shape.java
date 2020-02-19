package tetris;

public class Shape {
	
	public final static Coord[] SHAPE_1 = 
		{ new Coord(0,0),new Coord(1,0),new Coord(2,0),new Coord(1,1) }; // T
	public final static Coord[] SHAPE_2 = 
		{ new Coord(0,0),new Coord(0,1),new Coord(1,0),new Coord(1,1) }; // Square
	public final static Coord[] SHAPE_3 = 
		{ new Coord(0,0),new Coord(0,1),new Coord(1,1),new Coord(1,2) }; // Stair 1
	public final static Coord[] SHAPE_4 = 
		{ new Coord(1,0),new Coord(1,1),new Coord(0,1),new Coord(0,2) }; // Stair 2
	public final static Coord[] SHAPE_5 = 
		{ new Coord(0,0),new Coord(0,1),new Coord(0,2),new Coord(1,0) }; // L 1
	public final static Coord[] SHAPE_6 = 
		{ new Coord(1,0),new Coord(1,1),new Coord(1,2),new Coord(0,0) }; // L 2
	public final static Coord[] SHAPE_7 = 
		{ new Coord(0,0),new Coord(0,1),new Coord(0,2),new Coord(0,3) }; // Line
	
	public final static Coord[][] SHAPES = {SHAPE_1, SHAPE_2, SHAPE_3, SHAPE_4, SHAPE_5, SHAPE_6, SHAPE_7};
	
	private Shape() {
	}
	
	public static Coord[] randomShape() {
		return SHAPES[(int) ( Math.random() * Shape.SHAPES.length)];
	}
	

}
