package clueGame;

public class BoardCell {
	private int row;
	private int column;
	private char initial;
	private DoorDirection dDirection;
	
	public BoardCell(int row, int col) {
		this.row = row;
		this.column = col;
	}
	public boolean isWalkway() {
		return true;
	}
	
	public boolean isRoom() {
		return true;
	}
	
	public boolean isDoorway() {
		return true;
	}

	public DoorDirection getDoorDirection() {
		// TODO Auto-generated method stub
		return dDirection;
	}

	public char getInitial() {
		// TODO Auto-generated method stub
		return initial;
	}
}
