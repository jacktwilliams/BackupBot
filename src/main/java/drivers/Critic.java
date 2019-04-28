package drivers;
import java.util.Scanner;

import storage.FileRecord;


public class Critic {
	
	private static Scanner scan;
	private static String input;
	private static int menuSelect;
	private static boolean validInput;
	
	public static void runCritic(BackupManager backup) {
		
		scan = new Scanner(System.in);
		
		System.out.println("Begin training session now? [y/n]");
		
		input = scan.next().toLowerCase();
		
		if (input.equals("y")) {
			runTraining(backup);
		}
		else if (input.equals("n")) {
			runMenu(backup);
		}
		else {
			runCritic(backup);
		}
		
	}
	
	private static void runTraining(BackupManager backup) {
		
		System.out.println("Welcome to the training session!");
		System.out.println("We will display files names one at a time.");
		System.out.println("Please indicate if you would like said file backed up or not.\n");
		
		outerLoop:
		for (FileRecord file: backup.getQuestionableFiles(10)) {
			
			validInput = false;
			
			System.out.println("File: " + file.toString() + " [y/n/exit]");
			
			while (!validInput) {
				
				input = scan.next().toLowerCase();
				
				if (input.equals("y")) {
					validInput = true;
					backup.keepFile(file);
				}
				else if (input.equals("n")) {
					validInput = true;
					backup.ignoreFile(file);
				}
				else if (input.equals("exit")) {
					validInput = true;
					System.out.println("Now exiting the training session.\n");
					break outerLoop;
				}
				else {
					System.out.println("Invalid input. Try again. [y/n/exit]");
				}
			}
		}
	}
	
	private static void runMenu(BackupManager backup) {
		
		boolean runMenu = true;
		
		System.out.println("Welcome to the menu!");
		System.out.println("Input a number to run the given command.\n");
		System.out.println(" [1] List all kept files.");
		System.out.println(" [2] List all ignored files.");
		System.out.println(" [3] Keep a file.");
		System.out.println(" [4] Ignore a file.");
		System.out.println(" [5] Exit.\n");
		
		validInput = false;
		
		while (!validInput) {
			
			menuSelect = scan.nextInt();
			
			switch (menuSelect) {
				case 1:
					validInput = true;
					backup.getAllKeptFiles();
					break;
					
				case 2:
					validInput = true;
					backup.getAllIgnoreFiles();
					break;
					
				case 3:
					validInput = true;
					System.out.println("Please input the path of a file you with to keep.");
					input = scan.nextLine();
					if (backup.keepFile(input)) {
						System.out.println("Successfully kept " + input);
					}
					else {
						System.out.println("Path not found for " + input);
					}
					break;
					
				case 4:
					validInput = true;
					System.out.println("Please input the path of a file you wish to ignore.");
					input = scan.nextLine();
					if (backup.ignoreFile(input)) {
						System.out.println("Successfully ignored " + input);
					}
					else {
						System.out.println("Path not found for " + input);
					}
					break;
					
				case 5:
					validInput = true;
					runMenu = false;
					break;
					
				default:
					System.out.println("Invalid input.");
					runMenu(backup);
			}
		}
		
		if (runMenu) {
			runMenu(backup);
		}
		
	}

}
