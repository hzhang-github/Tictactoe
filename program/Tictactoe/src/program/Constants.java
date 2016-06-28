package program;

import java.util.List;
public final class Constants
{
	public final static int mathix_size = 3;
	public final static int total_positions = mathix_size * mathix_size;
	public final static String histoyFile = "historymoves.txt";
	
	public static String listToString(List<Integer> list)
	{
		String str = "";
		for(int i=0; i<list.size(); i++)
		{
			int d = list.get(i);
			str += d + "|";
		}
		return str;
	}
	
}