package program;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.Scanner;
import java.io.*;

public class MoveMaker {
	private List<String> failedHistory;
	
	public MoveMaker()
	{
		failedHistory = new ArrayList<String>();
	}
	
	public int getBestMoveFrom(List<Integer> allMoves, List<Integer> availMoves)
	{
		if(failedHistory.size() < 1)
			return getARamdonMove(availMoves);
		
		String curmoves = listToString(allMoves);
		for(int i=0; i<availMoves.size(); i++)
		{
			int m = availMoves.get(i);
			boolean check = checkNewMove(curmoves, m);
			if(check == false)
				return m;
		}
		
		return getARamdonMove(availMoves);
	}
	
	private boolean checkNewMove(String curmoves, int m)
	{
		boolean check = false;
		if(failedHistory.size() < 1)
			return check;
		
		String newmoves = curmoves + m + "|";
		
		for(int i=0; i<failedHistory.size(); i++)
		{
			String pastmoves0 = failedHistory.get(i);
			String pastmoves = pastmoves0;
			if(pastmoves.equals(newmoves))
				return true;
			
			pastmoves = rotate(pastmoves0, 90);
			if(pastmoves != null && pastmoves.equals(newmoves))
				return true;
			
			pastmoves = rotate(pastmoves0, 180);
			if(pastmoves != null && pastmoves.equals(newmoves))
				return true;
			
			pastmoves = rotate(pastmoves0, 270);
			if(pastmoves != null && pastmoves.equals(newmoves))
				return true;
		}
		
		return check;
	}
	
	private String rotate(String pastmoves, int rotation)
	{
		String [] movearr = pastmoves.split("|");
		List<Integer> tmplist = new ArrayList<Integer>();
		for(int i=0; i<movearr.length; i++) {
			String s = movearr[i];
			if(s != null && s.length() > 0)
			{
				try {
					int m = Integer.parseInt(s);
					int mrot = transform(m, rotation);
					tmplist.add(mrot);
				} catch(Exception e) {
					return null;
				}
			}
		}
		return Constants.listToString(tmplist);
	}
	
	private int transform(int m, int rotation)
	{
		if(rotation == 0)
			return m;
		
		Position pos2d = Position.convertTo2DPos(m);
		Position pos2dnew = new Position();
		
		if(rotation == 90) {
			pos2dnew.x = Constants.mathix_size - pos2d.y - 1;
			pos2dnew.y = pos2d.x;
		} else if(rotation == 180) {
			pos2dnew.x = Constants.mathix_size - pos2d.x - 1;
			pos2dnew.y = Constants.mathix_size - pos2d.y - 1;
		} else if(rotation == 270) {
			pos2dnew.x = pos2d.y;
			pos2dnew.y = Constants.mathix_size - pos2d.x - 1;
		} else {
			pos2dnew.x = pos2d.x;
			pos2dnew.y = pos2d.y;
		}
		
		return Position.converTo1DPos(pos2dnew);
	}
	
	private int getARamdonMove(List<Integer> availMoves)
	{
		int size = availMoves.size();
		if(size == 1)
			return availMoves.get(0);
		
		Random randomGen = new Random();
		int newmoveind = randomGen.nextInt(size);
		int move = availMoves.get(newmoveind);
		
		return move;
	}
	
	private String listToString(List<Integer> list)
	{
		String str = "";
		for(int i=0; i<list.size(); i++)
		{
			int d = list.get(i);
			str += d + "|";
		}
		return str;
	}
	
	public void loadHistory(String fileName)
	{
		try {
            FileReader fileReader = new FileReader(fileName);
            BufferedReader bufferedReader = new BufferedReader(fileReader);

            String line;
            while((line = bufferedReader.readLine()) != null) {
            	failedHistory.add(line);
            }   

            bufferedReader.close();
        }
        catch(FileNotFoundException ex) {
            System.out.println("Unable to open file '" + 
                fileName + "'");                
        }
        catch(IOException ex) {
            System.out.println("Error reading file '" 
                + fileName + "'");                  
            // ex.printStackTrace();
        }
	}
	
	public void saveHistory(List<Integer> allMoves, String fileName)
	{
		try {
            FileWriter fileWriter = new FileWriter(fileName);
            BufferedWriter bufferedWriter = new BufferedWriter(fileWriter);

            for(int i=0; i<failedHistory.size(); i++)
    		{
    			String pastmoves = failedHistory.get(i);
    			bufferedWriter.write(pastmoves);
    			bufferedWriter.newLine();
    		}

            String newmoves = listToString(allMoves);
            bufferedWriter.write(newmoves);
            bufferedWriter.newLine();
            bufferedWriter.flush();
            
            bufferedWriter.close();
        }
        catch(IOException ex) {
            System.out.println("Error writing to file '"
                + fileName + "'");
            // ex.printStackTrace();
        }
	}
	
	//player move
	public int getPlayerMoveFrom(Scanner scanIn, List<Integer> availMoves)
	{
		int pos1d = 0;
		
		while(true)
		{
			System.out.println("\nEnter a position xy (q = quit): ");
			
			String str = scanIn.nextLine();
			if(str.equalsIgnoreCase("q"))
			{
				return -1;
			}

			if(str.length() != 2) {
				System.out.println("wrong input: " + str);
				continue;
			}
			
			String xstr = str.substring(0, 1);
			String ystr = str.substring(1);
			Position pos2d = new Position();
			try {
				pos2d.x = Integer.parseInt(xstr) - 1;
				pos2d.y = Integer.parseInt(ystr) - 1;
			} catch(Exception e) {
				System.out.println("wrong input: " + str);
				continue;
			}
			
			pos1d = Position.converTo1DPos(pos2d);
			
			if(availMoves.contains(pos1d))
				break;
			
			System.out.println("wrong input: " + str);
		}
		
		return pos1d;
	}
	
}
