package experiment;

public class BoardCell {
	private int row;
	private int col;
	
	public BoardCell(int cellRow, int cellCol) {
		row = cellRow;
		col = cellCol;		
	}
	
	public int getRow() {
		return row;
	}
	
	public int getCol() {
		return col;
	}
}