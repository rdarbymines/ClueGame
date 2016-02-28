package clueTests;

import java.util.LinkedList;
import java.util.Set;

//Doing a static import allows me to write assertEquals rather than
//assertEquals
import static org.junit.Assert.*;
import org.junit.BeforeClass;
import org.junit.Test;

import clueGame.Board;
import clueGame.BoardCell;

public class ClueTargetTest {
	// We make the Board static because we can load it one time and 
	// then do all the tests. 
	private static Board board;
	@BeforeClass
	public static void setUp() {
		board = new Board("ClueLayout.csv", "ClueLegend.txt");
		board.initialize();
	}

	// Ensure that player does not move around within room
	// These cells are LIGHT RED on the planning spreadsheet
	@Test
	public void testAdjacenciesInsideRooms()
	{
		// Test a corner
		LinkedList<BoardCell> testList = board.getAdjList(0, 0);
		assertEquals(0, testList.size());
		// Test one that has walkway underneath
		testList = board.getAdjList(5, 0);
		assertEquals(0, testList.size());
		// Test one that has walkway above
		testList = board.getAdjList(9, 0);
		assertEquals(0, testList.size());
		// Test one that is in middle of room
		testList = board.getAdjList(18, 2);
		assertEquals(0, testList.size());
		// Test one beside a door
		testList = board.getAdjList(4, 2);
		assertEquals(0, testList.size());
		// Test one in a corner of room
		testList = board.getAdjList(3, 11);
		assertEquals(0, testList.size());
	}

	// Ensure that the adjacency list from a doorway is only the
	// walkway. NOTE: This test could be merged with door 
	// direction test. 
	// These tests are LIGHT GREEN on the planning spreadsheet
	@Test
	public void testAdjacencyRoomExit()
	{
		// TEST DOORWAY RIGHT 
		LinkedList<BoardCell> testList = board.getAdjList(4, 3);
		assertEquals(1, testList.size());
		assertTrue(testList.contains(board.getCellAt(4, 4)));
		// TEST DOORWAY LEFT 
		testList = board.getAdjList(14, 19);
		assertEquals(1, testList.size());
		assertTrue(testList.contains(board.getCellAt(14, 18)));
		//TEST DOORWAY DOWN
		testList = board.getAdjList(3, 8);
		assertEquals(1, testList.size());
		assertTrue(testList.contains(board.getCellAt(4,8)));
		//TEST DOORWAY UP
		testList = board.getAdjList(9, 2);
		assertEquals(1, testList.size());
		assertTrue(testList.contains(board.getCellAt(10, 2)));
		
	}
	
	// Test adjacency at entrance to rooms
	// These tests are LIGHT ORANGE in planning spreadsheet
	@Test
	public void testAdjacencyDoorways()
	{
		// Test beside a door direction RIGHT
		LinkedList<BoardCell> testList = board.getAdjList(4, 4);
		assertTrue(testList.contains(board.getCellAt(4, 3)));
		assertTrue(testList.contains(board.getCellAt(4, 5)));
		assertTrue(testList.contains(board.getCellAt(5, 4)));
		assertTrue(testList.contains(board.getCellAt(3, 4)));
		assertEquals(3, testList.size()); //4
		// Test beside a door direction DOWN
		testList = board.getAdjList(4, 8);
		assertTrue(testList.contains(board.getCellAt(4, 9)));
		assertTrue(testList.contains(board.getCellAt(4, 7)));
		assertTrue(testList.contains(board.getCellAt(5, 8)));
		assertTrue(testList.contains(board.getCellAt(3, 8)));
		assertEquals(4, testList.size());
		// Test beside a door direction LEFT
		testList = board.getAdjList(14, 18);
		assertTrue(testList.contains(board.getCellAt(14, 19)));
		assertTrue(testList.contains(board.getCellAt(15, 18)));
		assertTrue(testList.contains(board.getCellAt(14, 17)));
		assertTrue(testList.contains(board.getCellAt(13, 18)));
		assertEquals(4, testList.size());
		// Test beside a door direction UP
		testList = board.getAdjList(8, 2);
		assertTrue(testList.contains(board.getCellAt(8, 1)));
		assertTrue(testList.contains(board.getCellAt(8, 3)));
		assertTrue(testList.contains(board.getCellAt(9, 2)));
		assertTrue(testList.contains(board.getCellAt(7, 2)));
		assertEquals(4, testList.size());
	}

	// Test a variety of walkway scenarios
	// These tests are LIGHT PURPLE on the planning spreadsheet
	@Test
	public void testAdjacencyWalkways()
	{
		// Test on top edge of board, just 2 walkway pieces
		LinkedList<BoardCell> testList = board.getAdjList(0, 4);
		assertTrue(testList.contains(board.getCellAt(0, 3)));
		assertTrue(testList.contains(board.getCellAt(1, 4)));
		assertEquals(2, testList.size());
		
		// Test on left edge of board, 2 walkway pieces
		testList = board.getAdjList(13, 0);
		assertTrue(testList.contains(board.getCellAt(13, 1)));
		assertTrue(testList.contains(board.getCellAt(14, 0)));		
		assertEquals(2, testList.size());

		// Test between two rooms, walkways right and left
		testList = board.getAdjList(16,20);
		assertTrue(testList.contains(board.getCellAt(16, 21)));
		assertTrue(testList.contains(board.getCellAt(16, 19)));
		assertEquals(2, testList.size());

		// Test surrounded by 4 walkways
		testList = board.getAdjList(15,7);
		assertTrue(testList.contains(board.getCellAt(15, 8)));
		assertTrue(testList.contains(board.getCellAt(15, 6)));
		assertTrue(testList.contains(board.getCellAt(14, 7)));
		assertTrue(testList.contains(board.getCellAt(16, 7)));
		assertEquals(4, testList.size());
		
		// Test on bottom edge of board, next to 1 room piece
		testList = board.getAdjList(21, 8);
		assertTrue(testList.contains(board.getCellAt(21, 7)));
		assertTrue(testList.contains(board.getCellAt(20, 8)));
		assertEquals(2, testList.size());
		
		// Test on right edge of board, next to 1 room piece
		testList = board.getAdjList(11, 22);
		assertTrue(testList.contains(board.getCellAt(11, 21)));		
		assertEquals(1, testList.size());

		// Test on walkway next to  door that is not in the needed
		// direction to enter
		testList = board.getAdjList(5, 3);
		assertTrue(testList.contains(board.getCellAt(5, 4)));
		assertTrue(testList.contains(board.getCellAt(6, 3)));
		assertEquals(2, testList.size());
	}
	
	
	// Tests of just walkways, 1 step, includes on edge of board
	// and beside room
	// Have already tested adjacency lists on all four edges, will
	// only test two edges here
	// These are LIGHT BLUE on the planning spreadsheet
	@Test
	public void testTargetsOneStep() {
		board.calcTargets(16, 22, 1);
		Set<BoardCell> targets= board.getTargets();
		assertEquals(1, targets.size());
		assertTrue(targets.contains(board.getCellAt(16, 21)));
		
		board.calcTargets(3, 0, 1);
		targets= board.getTargets();
		assertEquals(2, targets.size());
		assertTrue(targets.contains(board.getCellAt(3, 1)));
		assertTrue(targets.contains(board.getCellAt(4, 0)));			
	}
	
	// Tests of just walkways, 2 steps
	// These are LIGHT BLUE on the planning spreadsheet
	@Test
	public void testTargetsTwoSteps() {
		board.calcTargets(16, 22, 2);
		Set<BoardCell> targets= board.getTargets();
		assertEquals(1, targets.size());
		assertTrue(targets.contains(board.getCellAt(16, 20)));
		
		board.calcTargets(3, 0, 2);
		targets= board.getTargets();
		assertEquals(1, targets.size());
		assertTrue(targets.contains(board.getCellAt(1, 4)));
			
	}
	
	// Tests of just walkways, 4 steps
	// These are LIGHT BLUE on the planning spreadsheet
	@Test
	public void testTargetsFourSteps() {
		board.calcTargets(3, 0, 4);
		Set<BoardCell> targets= board.getTargets();
		assertEquals(1, targets.size());
		assertTrue(targets.contains(board.getCellAt(4, 7)));
		
		board.calcTargets(16,22, 4);
		targets= board.getTargets();
		assertEquals(1, targets.size());
		assertTrue(targets.contains(board.getCellAt(16, 18)));

	}	
	
	// Tests of just walkways plus one door, 6 steps
	// These are LIGHT BLUE on the planning spreadsheet

	@Test
	public void testTargetsSixSteps() {
		board.calcTargets(0, 3, 6);
		Set<BoardCell> targets= board.getTargets();
		assertEquals(3, targets.size());
		assertTrue(targets.contains(board.getCellAt(5, 4)));
		assertTrue(targets.contains(board.getCellAt(4, 5)));	
		assertTrue(targets.contains(board.getCellAt(4, 3)));				
	}	
	
	// Test getting into a room
	// These are LIGHT BLUE on the planning spreadsheet

	@Test 
	public void testTargetsIntoRoom()
	{
		// One room is exactly 2 away
		board.calcTargets(19, 15, 2);
		Set<BoardCell> targets= board.getTargets();
		assertEquals(8, targets.size());
		// directly left and right
		assertTrue(targets.contains(board.getCellAt(19, 13)));
		assertTrue(targets.contains(board.getCellAt(19, 17)));
		// directly up and down
		assertTrue(targets.contains(board.getCellAt(17, 15)));
		assertTrue(targets.contains(board.getCellAt(21, 15)));
		// one up/down, one left/right
		assertTrue(targets.contains(board.getCellAt(18, 14)));
		assertTrue(targets.contains(board.getCellAt(18, 16)));
		assertTrue(targets.contains(board.getCellAt(20, 14)));
		assertTrue(targets.contains(board.getCellAt(20, 16)));
	}
	
	// Test getting into room, doesn't require all steps
	// These are LIGHT BLUE on the planning spreadsheet
	@Test
	public void testTargetsIntoRoomShortcut() 
	{
		board.calcTargets(9, 7, 3);
		Set<BoardCell> targets= board.getTargets();
		assertEquals(8, targets.size());
		// directly up and down
		assertTrue(targets.contains(board.getCellAt(6, 7)));
		assertTrue(targets.contains(board.getCellAt(12, 7)));
		// up then right
		assertTrue(targets.contains(board.getCellAt(8, 5)));
		assertTrue(targets.contains(board.getCellAt(7, 6)));
		// up then left
		assertTrue(targets.contains(board.getCellAt(7, 8)));
		// down and right
		assertTrue(targets.contains(board.getCellAt(9, 8)));
		assertTrue(targets.contains(board.getCellAt(11, 8)));
		// into the rooms
		assertTrue(targets.contains(board.getCellAt(11, 6)));	
	}

	// Test getting out of a room
	// These are LIGHT BLUE on the planning spreadsheet
	@Test
	public void testRoomExit()
	{
		// Take one step, essentially just the adj list
		board.calcTargets(1, 14, 1);
		Set<BoardCell> targets= board.getTargets();
		// Ensure doesn't exit through the wall
		assertEquals(1, targets.size());
		assertTrue(targets.contains(board.getCellAt(1, 13)));
		// Take two steps
		board.calcTargets(1, 14, 2);
		targets= board.getTargets();
		assertEquals(3, targets.size());
		assertTrue(targets.contains(board.getCellAt(0, 14)));
		assertTrue(targets.contains(board.getCellAt(2, 14)));
		assertTrue(targets.contains(board.getCellAt(1, 12)));
	}

}