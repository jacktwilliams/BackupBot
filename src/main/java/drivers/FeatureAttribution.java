package drivers;
import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
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
			
			scan = new Scanner(ls);
	
			int sizeValue = scan.nextInt();
	
			Feature size = new Feature(FeatureTypes.SIZE, sizeValue);
	
			file.addFeature(size);
			file.setSize(sizeValue);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			System.out.println("Error getting stuff");
			e.printStackTrace();
		}
	}
	
	private static void addExtension(FileRecord file, String path) {
		String extensionValue = "";
		int indexOfExtension = 0;
		
		if (!file.getDirStatus()) {
			indexOfExtension = path.lastIndexOf(".");
			
			if (indexOfExtension != -1) {
				extensionValue = path.substring(path.lastIndexOf(".") + 1);	
			}
			else {
				extensionValue = "none";
			}
			
			Feature extension = new Feature(FeatureTypes.EXTENSION, extensionValue);
			file.addFeature(extension);
		}
	}
	
	private static String runLS(String path) throws IOException {
		String output = "";
		ArrayList<String> com = new ArrayList<String>();
		com.add("/bin/bash");
		com.add("-c");
		com.add("ls -s -k " + path);
		ProcessBuilder procB = new ProcessBuilder(com);
		procB.directory(new File("/"));
		procB.redirectErrorStream(true);
		Process proc = procB.start();
		
		BufferedReader reader = new BufferedReader(new InputStreamReader(proc.getInputStream()));
		output = reader.readLine();
		System.out.println(output);
		return output;
	}

}
