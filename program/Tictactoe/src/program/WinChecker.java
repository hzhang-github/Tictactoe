package program;

import java.util.List;

public class WinChecker {
	int matrixSize;
	
	public void setMatrixSize(int m)
	{
		matrixSize = m;
	}
	
	public boolean checkIfWin(List<Integer> moves)
	{
		boolean win = false;
		if(moves.size() < matrixSize)
			return win;
		
		int [][]theMatrix = new int[matrixSize][matrixSize];
		for(int i=0; i<matrixSize; i++)
		{
			for(int j=0; j<matrixSize; j++)
				theMatrix[i][j] = 0;
		}
		
		for(int m = 0; m < moves.size(); m++)
		{
			int pos1d = moves.get(m);
			Position pos2d = Position.convertTo2DPos(pos1d);
			theMatrix[pos2d.x][pos2d.y] = 1;
		}
		
		//check vertical
		for(int i=0; i<matrixSize; i++)
		{
			int sum = 0;
			for(int j=0; j<matrixSize; j++)
			{
				sum += theMatrix[i][j];
			}
			if(sum == matrixSize)
				return true;
		}
		
		//check horizontal
		for(int i=0; i<matrixSize; i++)
		{
			int sum = 0;
			for(int j=0; j<matrixSize; j++)
			{
				sum += theMatrix[j][i];
			}
			if(sum == matrixSize)
				return true;
		}
		
		//check diagonal
		int sum1 = 0;
		int sum2 = 0;
		for(int i=0; i<matrixSize; i++)
		{
			sum1 += theMatrix[i][i];
			sum2 += theMatrix[i][matrixSize - i -1];
		}
		
		if(sum1 == matrixSize || sum2 == matrixSize)
			return true;
		
		return win;
	}
	
}
