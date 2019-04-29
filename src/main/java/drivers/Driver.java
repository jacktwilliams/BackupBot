package drivers;
import java.util.List;
import java.io.File;
import java.lang.ProcessBuilder.Redirect;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.PriorityQueue;
import java.util.Scanner;

import probability.BackupAction;
import probability.Decision;
import probability.Probability;
import storage.FileRecord;
import storage.PersistentManager;
import storage.RecordStorage;

public class Driver {
	public static RecordStorage recStore;
	public static Probability prob;
	public static BackupManagerImpl bman;

	public static void main(String[] args) throws ClassNotFoundException {
		// TODO Auto-generated method stub
		runBackupCycle();
		//PersistentManager.save(recStore, prob);
	}
	
	public static void runBackupCycle() throws ClassNotFoundException {
		HashSet<FileRecord> undecided = getDecisionFiles();
		FeatureAttribution.attributeFeatures(undecided);
		prob = new Probability();
		bman = new BackupManagerImpl();
		bman.setProbElem(prob);
		bman.setRecStore(recStore);
		PriorityQueue<BackupAction> choices = Decision.getOrderedBackupList(undecided, bman);
		bman.backupFiles(choices);
		Critic.runCritic(bman);
	}
	
	public static HashSet<FileRecord> getDecisionFiles() throws ClassNotFoundException {
		//String baseDir = "/home/jack/dev";
		produceFindCommand("/home/jack/.backupBot");
	    List<String> com = new ArrayList<String>();
	    com.add("/bin/bash");
	    com.add("-c");
	    com.add("/home/jack/.backupBot/findCom.sh");
		ProcessBuilder pb = new ProcessBuilder(com);
		pb.directory(new File("/"));
		pb.redirectErrorStream(true);		
		
		Scanner readF = null;
		try {
			Process p = pb.start();
			readF = new Scanner(p.getInputStream());
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		recStore = PersistentManager.getFileRecords();
		while(readF.hasNextLine()) {
			String givenPath = readF.nextLine();
			FileRecord current = new FileRecord(givenPath);
			if (!current.getDirStatus()) {
				//add files which we haven't previously KEPT or IGNORED
				recStore.addToStore(current);
			}
		}
		
		readF.close();
		return recStore.getUndecidedFiles();
	}
	
	public static void produceFindCommand(String instDir) {
	    List<String> com = new ArrayList<String>();
	    com.add("/bin/bash");
	    com.add("-c");
	    com.add("/home/jack/.backupBot/constructFind.py /home/jack/dev");
		ProcessBuilder pb = new ProcessBuilder(com);
		pb.directory(new File(instDir));
		pb.redirectErrorStream(true);
		
		Scanner readErr = null;
		try {
			Process p = pb.start();
			readErr = new Scanner(p.getInputStream());
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		while(readErr.hasNextLine()) {
			System.out.println(readErr.nextLine());
		}
		readErr.close();
	}
}
