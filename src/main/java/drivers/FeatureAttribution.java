package drivers;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.HashSet;
import java.util.Scanner;

import constants.FeatureTypes;
import probability.Feature;
import storage.FileRecord;

public class FeatureAttribution {
	
	private static Scanner scan;
	
	public static void attributeFeatures(HashSet<FileRecord> undecided) {
		for (FileRecord file: undecided) {
			addSize(file);
			addExtension(file, file.toString());
		}
	}
	
	private static void addSize(FileRecord file) {
		String ls = "";
		try {
			ls = runLS(file.toString());
			
			String sizeValue = "";
			scan = new Scanner(ls);
	
			sizeValue = Integer.toString(scan.nextInt());
	
			Feature size = new Feature(FeatureTypes.SIZE, sizeValue);
	
			file.addFeature(size);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			System.out.println("Error getting stuff");
			e.printStackTrace();
		}
	}
	
	private static void addExtension(FileRecord file, String path) {
		String extensionValue = "";
		
		if (!file.getDirStatus()) {
			extensionValue = path.substring(path.lastIndexOf(".") + 1);
			
			Feature extension = new Feature(FeatureTypes.EXTENSION, extensionValue);
			file.addFeature(extension);
		}
	}
	
	private static String runLS(String path) throws IOException {
		String output = "";
		
		String command = "ls -s " + path;
		Process proc = Runtime.getRuntime().exec(command);
		
		BufferedReader reader = new BufferedReader(new InputStreamReader(proc.getInputStream()));
		output = reader.readLine();
		
		return output;
	}

}
