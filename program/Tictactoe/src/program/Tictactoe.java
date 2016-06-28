package program;

import java.util.List;
import java.util.Random;
import java.util.Scanner;
import java.util.ArrayList;

public class Tictactoe {

	private List<Integer> allMoves;
	private List<Integer> machineMoves;
	private List<Integer> playerMoves;
	
	private MoveMaker moveMaker;
	private WinChecker winChecker;

	public void init()
	{
		allMoves = new ArrayList<Integer>();
		machineMoves = new ArrayList<Integer>();
		playerMoves = new ArrayList<Integer>();
	}
	
	public void setWinChecker(WinChecker wc)
	{
		winChecker = wc;
		winChecker.setMatrixSize(Constants.mathix_size);
	}
	
	public void setMoveMaker(MoveMaker movemaker)
	{
		moveMaker = movemaker;
		loadHistory();
	}
	
	public void playGame()
	{
		Scanner scanIn = new Scanner(System.in);
		
		while(true) {
			//machine move first
			boolean can_move = checkIfCanMove();
			if(can_move == false) {
				outputEndMessage();
				break;
			}
			
			int ind = getMachineMove();
			addMove(ind, true);
			showCurMoves();
			boolean iswin = winChecker.checkIfWin(machineMoves);
			if(iswin) {
				outputWinMessage(true);
				break;
			}
			
			//payer move
			can_move = checkIfCanMove();
			if(can_move == false) {
				outputEndMessage();
				break;
			}
			
			ind = getPlayerMove(scanIn);
			if(ind < 0)
				break;
			
			addMove(ind, false);
			//showCurMoves();
			iswin = winChecker.checkIfWin(playerMoves);
			if(iswin) {
				outputWinMessage(false);
				break;
			}
		}
		
		scanIn.close();
	}
	
	private boolean checkIfCanMove()
	{
		return allMoves.size() < Constants.total_positions;
	}
	
	private int getMachineMove()
	{
		int move = 0;
		if(allMoves.size() < 1)
		{
			Random randomGen = new Random();
			move = randomGen.nextInt(Constants.total_positions);
			return move;
		}
		
		List<Integer> availMoves = getAvailMoves();
		move = moveMaker.getBestMoveFrom(allMoves, availMoves);
		
		return move;
	}
	
	private List<Integer> getAvailMoves()
	{
		List<Integer> availMoves = new ArrayList<Integer>();
		for(int i=0; i<Constants.total_positions; i++) {
			if(! allMoves.contains(i))
				availMoves.add(new Integer(i));
		}
		
		return availMoves;
	}
	
	private void addMove(int ind, boolean ismachine)
	{
		allMoves.add(new Integer(ind));
		if(ismachine)
			machineMoves.add(new Integer(ind));
		else
			playerMoves.add(new Integer(ind));
	}
	
	private int getPlayerMove(Scanner scanIn)
	{
		List<Integer> availMoves = getAvailMoves();
		int move = moveMaker.getPlayerMoveFrom(scanIn, availMoves);
		return move;
	}
	
	private void outputEndMessage()
	{
		showCurMoves();
		System.out.println("The game end without any winner.");
	}
	
	private void outputWinMessage(boolean ismachine)
	{
		showCurMoves();
		if(ismachine) {
			System.out.println("The machine win!");
		} else {
			System.out.println("You win!");
			saveHistory();
		}
	}
	
	private void loadHistory()
	{
		moveMaker.loadHistory(Constants.histoyFile);
	}
	
	private void saveHistory()
	{
		moveMaker.saveHistory(allMoves, Constants.histoyFile);
	}
	
	void showCurMoves()
	{
		System.out.println("Machine moves - X, your moves - O");
		String blanks = "  ";
		String line = " ";
		for(int i=0; i<Constants.mathix_size; i++)
		{
			int showind = i + 1;
			line += blanks + showind;
		}
		System.out.println(line);
		
		for(int j=0; j<Constants.mathix_size; j++)
		{
			int showind = j + 1;
			line = "" + showind;
			for(int i=0; i<Constants.mathix_size; i++) 
			{
				line += blanks;
				
				String showpos = " ";
				Position pos2d = new Position();
				pos2d.x = i;
				pos2d.y = j;
				int pos1d = Position.converTo1DPos(pos2d);
				if(machineMoves.contains(pos1d))
					showpos = "X";
				else if(playerMoves.contains(pos1d))
					showpos = "O";
				line += showpos;
			}
			System.out.println(line);
		}
	}
	
	//the main()
	public static void main(String []args)
	{
		Tictactoe ttt = new Tictactoe();
		MoveMaker moveMaker = new MoveMaker();
		WinChecker winChecke = new WinChecker();
		
		ttt.init();
		ttt.setMoveMaker(moveMaker);
		ttt.setWinChecker(winChecke);
		
		ttt.playGame();
	}
}
