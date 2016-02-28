package clueTests;

import static org.junit.Assert.*;

import org.junit.Test;

import java.io.FileNotFoundException;
import java.util.Map;

import org.junit.*;

import clueGame.*;

public class ClueTest {
	Board board;
	
	//create board
	@Before
	public void setUp() {
		board = new Board();
		board.initialize();
	}
	
	//tests if have correct number of rooms and the rooms map correctly
	@Test
	public void roomsTest() {
		Map<Character, String> rooms = board.getRooms();
		assertEquals(11, rooms.size());
		assertEquals("Conservatory", rooms.get('C'));
		assertEquals("Ballroom", rooms.get('B'));
		assertEquals("Billiard Room", rooms.get('R'));
		assertEquals("Dining Room", rooms.get('D'));
		assertEquals("Walkway", rooms.get('W'));
		assertEquals("Library", rooms.get('L'));
		assertEquals("Kitchen", rooms.get('K'));
		assertEquals("Study", rooms.get('S'));
		assertEquals("Lounge", rooms.get('O'));
		assertEquals("Hall", rooms.get('H'));
		assertEquals("Closet", rooms.get('X'));
	}
	
	//number of rows and columns is correct 
	@Test
	public void boardDimensionsTest() {
		int totalCells = board.getNumRows()*board.getNumColumns(); 
		assertEquals(22, board.getNumRows());
		assertEquals(23, board.getNumColumns());
		assertEquals(506, totalCells);
	}
	
	//test door directions and existence of doors 
	@Test
	public void doorDirectionTest() {
		BoardCell room = board.getCellAt(4, 3);
		assertTrue(room.isDoorway());
		assertEquals(DoorDirection.RIGHT, room.getDoorDirection());
		room = board.getCellAt(3, 8);
		assertTrue(room.isDoorway());
		assertEquals(DoorDirection.DOWN, room.getDoorDirection());
		room = board.getCellAt(1, 14);
		assertTrue(room.isDoorway());
		assertEquals(DoorDirection.LEFT, room.getDoorDirection());
		room = board.getCellAt(9, 2);
		assertTrue(room.isDoorway());
		assertEquals(DoorDirection.UP, room.getDoorDirection());

		//test not door - room and walkway
		room = board.getCellAt(5, 0);
		assertFalse(room.isDoorway());	

		BoardCell cell = board.getCellAt(6, 0);
		assertFalse(cell.isDoorway());	
	}
	
	//test number of doors is correct 
	@Test
	public void numDoorsTest() {
		int numDoors = 0;
		for (int i = 0; i < board.getNumRows(); i++) {
			for (int j = 0; j < board.getNumColumns(); j++) {
				BoardCell testCell = board.getCellAt(i, j);
				if (testCell.isDoorway()) 
					numDoors++;
			}
		}
		assertEquals(14, numDoors);
	}
	
	//test rooms have correct initial 
	@Test
	public void roomInitialTest() {
		assertEquals('C', board.getCellAt(0, 0).getInitial());
		assertEquals('B', board.getCellAt(0, 14).getInitial());
		assertEquals('R', board.getCellAt(9, 15).getInitial());
		assertEquals('D', board.getCellAt(15, 9).getInitial());
		assertEquals('W', board.getCellAt(0, 4).getInitial());
		assertEquals('L', board.getCellAt(12, 19).getInitial());
		assertEquals('K', board.getCellAt(0, 5).getInitial());
		assertEquals('S', board.getCellAt(17, 18).getInitial());
		assertEquals('O', board.getCellAt(15, 0).getInitial());
		assertEquals('H', board.getCellAt(9, 0).getInitial());
		assertEquals('X', board.getCellAt(7, 9).getInitial());
	}
	
	@Test (expected = BadConfigFormatException.class)
	public void testBadColumns() throws BadConfigFormatException, FileNotFoundException {
		Board board = new Board("ClueLayoutBadColumns.csv", "ClueLegend.txt" );
		board.loadRoomConfig();
		board.loadBoardConfig();
	}
	
	@Test (expected = BadConfigFormatException.class)
	public void testBadRoom() throws BadConfigFormatException, FileNotFoundException {
		Board board =  new Board("ClueLayoutBadRoom.csv","ClueLegend.txt");
		board.loadRoomConfig();
		board.loadBoardConfig();
	}
	
	@Test (expected = BadConfigFormatException.class)
	public void testBadRoomFormat() throws BadConfigFormatException, FileNotFoundException {
		Board board = new Board("ClueLayout.csv", "ClueLegendBadFormat.txt");
		board.loadRoomConfig();
		board.loadBoardConfig();
	}
}
