package Exceptions;

public class wrongDataException extends Exception{
	
	int code;
	
	public wrongDataException(int code) {
		this.code=code;
	}
	
	public String getMessage() {
		String msg = "Error "+code+": ";
		switch (code) {
		case 100:
			return msg+"Wrong format. Use only integers in the given file, please."+"\n";
		case 101:
			return msg+"Wrong format. Field data must start with a blank space."+"\n";
		case 200:
			return msg+"Position x of truck cannot be equal or bigger than number of columns."+"\n";
		case 201:
			return msg+"Position y of truck cannot be equal or bigger than number of rows."+"\n";
		case 202:
			return msg+"Objective amount of sand K cannot be greater than the MAX amount of sand."+"\n";
		case 203:
			return msg+"All field must have an integer and positive amount of sand, and it cannot be greater than the MAX."+"\n";
		case 204:
			return msg+"Mean of sand not equal to K (Objective amount). Please, introduce different information on the file."+"\n";
		default:
			return "Unknown error";
		}
	}
}
