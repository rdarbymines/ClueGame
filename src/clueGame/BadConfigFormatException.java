package clueGame;

public class BadConfigFormatException extends Exception{
	public BadConfigFormatException(int errorType) {		
		super("Bad Config Format");
		switch (errorType) {
		case 1:
			System.out.println("Room does not exist in room config file.");
			break;
		case 2:
			System.out.println("Number of columns not consistent between rows in board config file.");
			break;
		case 3: 
			System.out.println("Improper format in room legend config file.");
			break;
		}
	}
}
