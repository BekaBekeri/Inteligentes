package Exceptions;

/*****************************************************************************************
 * Class Name: wrongDataException
 * Class Description: Class created to manage the reading and writing of the class Broker
 *
 *****************************************************************************************/
public class wrongDataException extends Exception{
	
	int code;
	
	/**************************************************************
	 * Class name: wrongDataException
	 * Class Description: constructor of the class
	 * @param code: an integer containing the error code
	 **************************************************************/
	public wrongDataException(int code) {
		this.code=code;
	}
	
	/*********************************************************************************************
	 * Class Name: getMessage
	 * Class Description: class whose responsibility is returning an String 
	 *		containing the description of the error.
	 ********************************************************************************************/
	public String getMessage() {
		String msg = "Error "+code+": ";
		switch (code) {
		case 100:
			return msg+"Wrong format. Use only integers in the range [0,127] in the given file, please."+"\n";
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
		case 300:
			return msg+"To avoid performance issues, the maximun number for all values has been limited from [0, 255]. Please modify the file";
		default:
			return "Unknown error";
		}
	}
}
