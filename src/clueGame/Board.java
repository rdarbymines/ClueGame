package clueGame;

import java.util.LinkedList;
import java.util.Map;
import java.util.Set;

public class Board {
	private int numRows;
	private int numColumns;
	public static final int BOARD_SIZE = 1;
	private BoardCell[][] board;
	private Map<Character, String> rooms;
	private Map<BoardCell, LinkedList<BoardCell>> adjMatrix;
	private Set<BoardCell> targets;
	private String boardConfigFile;
	private String roomConfigFile;
	
	public Board() {
		
	}
	
	public void initialize() {
		
	}
	
	public void loadRoomConfig(String roomConfigFile) {
		
	}
	
	public void loadBoardConfig(String filename) {
		
	}
	
	public void calcAdjacencies() {
		
	}
	
	public void calcTargets(int row, int column, int pathLength) {
		
	}
	
	public BoardCell getCellAt(int row, int column) {
		BoardCell b = new BoardCell(row, column);
		return b;
	}

	public Map<Character, String> getRooms() {
		// TODO Auto-generated method stub
		return rooms;
	}

	public int getNumRows() {
		// TODO Auto-generated method stub
		return numRows;
	}

	public int getNumCols() {
		// TODO Auto-generated method stub
		return numColumns;
	}
}
