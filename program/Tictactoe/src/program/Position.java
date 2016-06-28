package program;

public class Position {
	public int x, y;
	
	public static Position convertTo2DPos(int pos1d)
	{
		Position pos2d = new Position();
		pos2d.x = pos1d % Constants.mathix_size;
		pos2d.y = pos1d / Constants.mathix_size;
		
		return pos2d;
	}
	
	public static int converTo1DPos(Position pos2d)
	{
		return pos2d.y * Constants.mathix_size + pos2d.x;
	}
}
