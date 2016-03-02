package clueGame;

public class BoardCell {
	private int row;
	private int column;
	private char initial;
	private DoorDirection dDirection;
	private boolean isDoor;
	
	public BoardCell(int row, int col) {
		this.row = row;
		this.column = col;
		isDoor = false;
	}
	
	public int getRow() {
		return row;
	}
	
	public int getColumn() {
		return column;
	}
	
	public boolean isWalkway() {
		return (initial == 'W');
	}
	
	public boolean isRoom() {
		return (initial != 'W' && initial != 'X');
	}
	
	public boolean isDoorway() {
		return isDoor;
	}

	public DoorDirection getDoorDirection() {
		return dDirection;
	}

	public char getInitial() {
		return initial;
	}
	
	public void loadCell(String identifier) {
		if (identifier.length() == 2) {
			isDoor = true;
			switch (identifier.charAt(1)) {
			case 'L':
				dDirection = DoorDirection.LEFT;
				break;
			case 'U':
				dDirection = DoorDirection.UP;
				break;
			case 'R':
				dDirection = DoorDirection.RIGHT;
				break;
			case 'D':
				dDirection = DoorDirection.DOWN;
				break;
			default:
				dDirection = DoorDirection.NONE;
				isDoor = false;
				break;
			}
		}
		else {
			dDirection = null;
		}
		initial = identifier.charAt(0);
		
	}
}
