package experiment;
import java.util.*;

public class IntBoard {
	private Map<BoardCell, LinkedList<BoardCell>> adjMtx;
	private Set<BoardCell> visited;
	private Set<BoardCell> targets;
	private BoardCell[][] grid;
	private int rowLength;
	private int colLength;
	
	public IntBoard(int rows, int cols) {
		grid = new BoardCell[rows][cols];
		for (int i = 0; i < rows; i ++)
			for (int j = 0; j < cols; j++)
				grid[i][j] = new BoardCell(i,j);
		adjMtx = new HashMap<BoardCell, LinkedList<BoardCell>>();
		visited = new HashSet<BoardCell>();
		targets = new HashSet<BoardCell>();	
		rowLength = rows;
		colLength = cols;
		calcAdjacencies();		
	}
	
	public void calcAdjacencies() {
		for (int i = 0; i < rowLength; i++)
			for (int j=0; j< colLength; j++)
				adjMtx.put(grid[i][j], getAdjList(grid[i][j]));
	}
	
	public void calcTargets(BoardCell cell, int pathLength) {
		visited.clear();
		targets.clear();
		visited.add(cell);
		findTargets(cell, pathLength);
	}
	
	public void findTargets(BoardCell thisCell, int numSteps) {
		
		LinkedList<BoardCell> adjCells = adjMtx.get(thisCell);
		LinkedList<BoardCell> checkers = new LinkedList<BoardCell>();
		for (Iterator<BoardCell> localCells = adjCells.iterator(); localCells.hasNext(); )
		{
			BoardCell localCell = localCells.next();
			if (!visited.contains(localCell))
				checkers.add(localCell);
		}
		for (Iterator<BoardCell> checkCells = checkers.iterator(); checkCells.hasNext();) {
			BoardCell checkCell = checkCells.next();
			visited.add(checkCell);
			if (numSteps == 1) 
				targets.add(checkCell);
			else 
				findTargets(checkCell, numSteps - 1);
			visited.remove(checkCell);
			
		}
	}
	
	public Set<BoardCell> getTargets() {
		return targets;		
	}
	
	public LinkedList<BoardCell> getAdjList(BoardCell cell) {
		LinkedList<BoardCell> adjacents = new LinkedList<BoardCell>();
		int col = cell.getCol();
		int row = cell.getRow();
		for (int i = -1; i < 2; i++)
			for (int j = -1; j < 2; j++) {
				if (Math.abs(i) != Math.abs(j)) {
					if (col+j >= 0 && col+j < colLength && row+i >= 0 && row+i < rowLength)
						adjacents.add(grid[row+i][col+j]);
				}					
			}
		return adjacents;
	}		
	
	public BoardCell getCell(int row, int col) {
		return grid[row][col];
	}
	
}
