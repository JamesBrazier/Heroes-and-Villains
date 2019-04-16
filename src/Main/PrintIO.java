package Main;
import java.io.IOException;
import java.util.Scanner;
/**
 * @author jbr185
 * this is for the command line, don't look at it, its hideous!
 */
public class PrintIO {
	
	private static Scanner inputStream = new Scanner(System.in);
	private static String input;
	private static final int pageLength = 80;
	private static final int stringLength = 16;
	
	/**
	 * Prints the inputed object's toString up to the pageLength constant and overflows on 
	 * to new lines (ends with a newline character)
	 * @param object printed object
	 */
	public static void print(Object object) {
		String string = object.toString();
		System.out.println(string);
		//if (string.length() <= pageLength) {
		//	System.out.println(string);
		//} else {
		//	int i = pageLength;
		//	while (string.charAt(i) != ' ' || string.charAt(i) != '\n') {
		//		System.out.println(string.charAt(i) + i);
		//		i--;
		//		if (i == 0) {
		//			break;
		//		}
		//	}
		//	System.out.println(string.substring(0, i));
		//	print(string.substring(i));
		//}
	}
	/**
	 * takes in a list of objects and prints the index and toString for each member
	 * @param objects list of objects to print
	 */
	public static void printList(Object[] objects) {
		for (int i = 0; i < objects.length; i++) {
			print(" " + i + ": " + objects[i]);
		} 
	}
	/**
	 * Prints a separator bar up to the page length
	 */
	public static void printBar() {
		String string = "";
		for (int i = 0; i < pageLength; i++) {
			string = string + "-";
		}
		System.out.println(string);
	}
	/**
	 * Prints a separator bar up to the page length of the inputed character
	 */
	public static void printBar(char character) {
		String string = "";
		for (int i = 0; i < pageLength; i++) {
			string = string + character;
		}
		System.out.println(string);
	}
	/**
	 * prints the object's toString flanked by two separating bars
	 * @param object
	 */
	public static void printPopup(Object object) {
		printBar();
		print(object);
		printBar();
	}
	/**
	 * prints the toString of the object flanked by "**"
	 * @param object
	 */
	public static void printMessage(Object object) {
		print("** " + object + " **");
	}
	/**
	 * prints a blank new line
	 */
	public static void printLn() {
		System.out.println();
	}
	/**
	 * uses the scanner to take in a input that is a string less than the string length
	 * @return returns the inputed string
	 */
	public static String inputString() {
		System.out.print("> ");
		input = inputStream.next();
		while (input.length() >= stringLength) {
			print("** Invalid input, please use " + stringLength + " characters or less **");
			System.out.print("> ");
			input = inputStream.next();
		}
		return input;
	}
	/**
	 * uses the scanner to take in a input that is a single character
	 * @return returns the inputed character
	 */
	public static char inputChar() {
		System.out.print("> ");
		input = inputStream.next();
		while (input.length() != 1) {
			print("** Invalid input, please type a single character **");
			System.out.print("> ");
			input = inputStream.next();
		}
		return input.toCharArray()[0];
	}
	/**
	 * uses the scanner to take in a input that is a integer
	 * @return returns the inputed integer
	 */
	public static int inputInt() {
		System.out.print("> ");
		input = inputStream.next();
		do {
			try {
				return Integer.parseInt(input);
			} catch (NumberFormatException NFE) {
				print("** Invalid input, please type a number **");
				System.out.print("> ");
				input = inputStream.next();
			}
		} while (true);
	}
	/**
	 * uses the scanner to take in a input that is a integer within a given range
	 * @return returns the integer
	 */
	public static int inputIntRange(int lower, int upper) {
		int input = inputInt();
		while (input < lower || input > upper) {
			print("** Invalid input, please type a number between " + lower + " and " + upper + " **");
			input = inputInt();
		}
		return input;
	}
	/**
	 * Prompts the user to press enter to continue
	 */
	public static void pause() {
		System.out.println("Press enter to continue...");
        try {
            System.in.read(new byte[2]);
        } catch (IOException e) {
            e.printStackTrace();
        }
	}
	public static void main(String[] args) {
		pause();
	}
}
