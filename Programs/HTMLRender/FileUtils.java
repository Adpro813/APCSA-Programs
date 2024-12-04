import java.io.File;
import java.io.FileNotFoundException;
import java.io.PrintWriter;
import java.util.Scanner;
/**
 * FileUtilities for reading and writing
 *
 * @author Aditya Dendukuri
 * @since August 23,2024
 */
public class FileUtils {
	/**
	 * Opens a file to read using the scanner class
	 * 
	 * @param fileName name of the file to Open
	 * @return the Scanner object to the file
	 */
	public static Scanner openToRead(String fileName) {
		Scanner input = null;
		try {
			input = new Scanner(new File(fileName));
		}

		catch (FileNotFoundException e) {
			System.err.println("Error: Can not open " + fileName + " for reading.");
			System.exit(72);
		}

		return input;
	}

	/**
	 * Opens a file to write using the printWriter class\
	 * 
	 * @param fileName name of the file to open
	 * @return the printWriter object to the file
	 */
	public static PrintWriter openToWrite(String fileName) {
		PrintWriter output = null;

		try {
			output = new PrintWriter(new File(fileName));
		} catch (FileNotFoundException e) {
			System.err.println("Error: Can not open " + fileName + " for writing.");
			System.exit(73);
		}
		return output;

	}
}
