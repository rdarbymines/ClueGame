package clueGame;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.Map;
import java.util.Set;

public class Board {
	private int numRows;
	private int numColumns;
	public static final int BOARD_SIZE = 1;
	private BoardCell[][] board;
	private static Map<Character, String> rooms;
	private Map<BoardCell, LinkedList<BoardCell>> adjMatrix;
	private Set<BoardCell> targets;
	private Set<BoardCell> visited;
	private String boardConfigFile;
	private String roomConfigFile;	
	
	public Board() {
		boardConfigFile = "ClueLayout.csv";
		roomConfigFile = "ClueLegend.txt";
	}
	
	public Board(String boardConfig, String roomConfig) {
		boardConfigFile = boardConfig;
		roomConfigFile = roomConfig;
	}
	
	public void initialize() {
		adjMatrix = new HashMap<BoardCell, LinkedList<BoardCell>>();
		rooms = new HashMap<Character, String>();
		visited = new HashSet<BoardCell>();
		targets = new HashSet<BoardCell>();
		try {
			loadRoomConfig();
			loadBoardConfig();
		}
		catch (Exception e) {			
			System.out.println(e.getMessage());
		}	
		//calcAdjacencies();		
	}
	
	public void loadRoomConfig() throws BadConfigFormatException {
		try {
			FileReader reader = new FileReader(roomConfigFile);
			BufferedReader buffered = new BufferedReader(reader);
			String line = "";								
			while ((line = buffered.readLine()) != null) {
				if (line.split(", ").length != 3)
					throw new BadConfigFormatException(3);
				String room = "";
				char identifier;
				identifier = line.charAt(0);
				room = line.split(", ")[1];
				rooms.put(identifier,  room);				
				//System.out.println(identifier);
			}
		}		
		catch (FileNotFoundException e) {
			System.out.println(e.getMessage());
		}
		catch (IOException e) {
			System.out.println(e.getMessage());
		}
	}
	
	public void loadBoardConfig() throws BadConfigFormatException {		
		try {
			FileReader reader = new FileReader(boardConfigFile);
			BufferedReader buffered = new BufferedReader(reader);
			String line = "";
			int row = 0;			
			ArrayList<String[]> boardInfo = new ArrayList<String[]>();
			while ((line = buffered.readLine()) != null) {
				String[] rowInfo =  line.split(",");
				boardInfo.add(rowInfo);
				row++;			
			}
			numColumns = boardInfo.get(0).length;
			numRows = row;		
			board = new BoardCell[numRows][numColumns];
			for (int rowi = 0; rowi < numRows; rowi++) {
				if (boardInfo.get(rowi).length != numColumns) throw new BadConfigFormatException(2);
				for (int coli = 0; coli < numColumns; coli++) {		
					board[rowi][coli] = new BoardCell(rowi,coli);
					board[rowi][coli].loadCell(boardInfo.get(rowi)[coli]);			
					if (!rooms.containsKey(board[rowi][coli].getInitial())) throw new BadConfigFormatException(1);
				}				
			}			
			
		}catch (BadConfigFormatException e) {
			throw e;
		}
		catch (Exception e) {
			System.out.println(e.getMessage());
		}
	}
	
	public void calcAdjacencies() {
		
	}
	
	public void calcTargets(int row, int column, int pathLength) {
		
	}
	
	public BoardCell getCellAt(int row, int column) {		
		return board[row][column];
	}

	public static Map<Character, String> getRooms() {
		return rooms;
	}

	public int getNumRows() {
		return numRows;
	}

	public int getNumColumns() {
		return numColumns;
	}

	public LinkedList<BoardCell> getAdjList(int row, int col) {
		LinkedList<BoardCell> adjacents = new LinkedList<BoardCell>();
		for (int i = -1; i < 2; i++)
			for (int j = -1; j < 2; j++) {
				if (Math.abs(i) != Math.abs(j)) {
					if (col+j >= 0 && col+j < numColumns && row+i >= 0 && row+i < numRows)
						adjacents.add(board[row+i][col+j]);
				}					
			}
		return adjacents;
	}

	public Set<BoardCell> getTargets() {
		return targets;
	}
}
