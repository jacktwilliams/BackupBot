package drivers;
import java.util.List;
import java.io.File;
import java.lang.ProcessBuilder.Redirect;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.PriorityQueue;
import java.util.Scanner;

import constants.UserConfigConstants;
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
		recStore = PersistentManager.getFileRecords();
		prob = PersistentManager.getProbElem();
		bman = new BackupManagerImpl();
		bman.setQuestionableFiles(PersistentManager.getQuestionableFiles());
		bman.setProbElem(prob);
		bman.setRecStore(recStore);
		
		runBackupCycle();
		
		PersistentManager.storeFileRecords(recStore);
		PersistentManager.storeProbElem(prob);
		
		Critic.runCritic(bman);

		PersistentManager.storeQuestionableFiles(bman.getQuestionableFiles());		
	}
	
	public static void runBackupCycle() throws ClassNotFoundException {
		HashSet<FileRecord> undecided = getDecisionFiles();
		FeatureAttribution.attributeFeatures(undecided);
		PriorityQueue<BackupAction> choices = Decision.getOrderedBackupList(undecided, bman);
		bman.backupFiles(choices);
	}
	
	public static HashSet<FileRecord> getDecisionFiles() throws ClassNotFoundException {
		produceFindCommand();
	    List<String> com = new ArrayList<String>();
	    com.add("/bin/bash");
	    com.add("-c");
	    com.add(UserConfigConstants.INSTALLDIR + "/findCom.sh");
		ProcessBuilder pb = new ProcessBuilder(com);
		//pb.directory(new File("/"));
		pb.redirectErrorStream(true);		
		
		Scanner readF = null;
		try {
			Process p = pb.start();
			readF = new Scanner(p.getInputStream());
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
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
	
	public static void produceFindCommand() {
	    List<String> com = new ArrayList<String>();
	    com.add("/bin/bash");
	    com.add("-c");
	    com.add(UserConfigConstants.INSTALLDIR + "/constructFind.py " + UserConfigConstants.CRAWLBASEDIR);
		ProcessBuilder pb = new ProcessBuilder(com);
		//pb.directory(); don't think this needs to be set
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
