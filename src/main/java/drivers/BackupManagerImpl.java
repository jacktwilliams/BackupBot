package drivers;

import probability.Feature;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.PriorityQueue;
import java.util.Scanner;

import constants.UserConfigConstants;
import probability.BackupAction;
import probability.Probability;
import probability.QuestionableFile;
import storage.FileRecord;
import storage.RecordStorage;

public class BackupManagerImpl {
	private static final int maxQuestionable = 500;
	private Probability probElem;
	private PriorityQueue<QuestionableFile> questionable;
	private RecordStorage recStore;

	public BackupManagerImpl() {
		questionable = new PriorityQueue<QuestionableFile>(maxQuestionable);
	}
	
	public void setProbElem(Probability p) {
		this.probElem = p;
	}

	public void keepFile(FileRecord f) {
		probElem.keepFile(f);
		backupFile(f);
	}

	public void ignoreFile(FileRecord f) {
		probElem.ignoreFile(f);
		rmFile(f);
	}

	public PriorityQueue<QuestionableFile> getQuestionableFiles() {
		return this.questionable;
	}

	public void addQuestionableFile(QuestionableFile f) {
		this.questionable.add(f);		
	}

	public void setQuestionableFiles(PriorityQueue<QuestionableFile> questionables) {
		this.questionable = questionables;
	}

	public Probability getProbElem() {
		// TODO Auto-generated method stub
		return this.probElem;
	}
	
	public void backupFiles(PriorityQueue<BackupAction> files) {
		for (BackupAction file : files) {
			keepFile(file.getFile());
		}
	}
	
	private static void backupFile(FileRecord f) {
	    List<String> com = new ArrayList<String>();
	    com.add("/bin/bash");
	    com.add("-c");
	    com.add("rsync -aqzrR " + f.toString() + " " + UserConfigConstants.STORAGEDIR);
		ProcessBuilder proc = new ProcessBuilder(com);
		proc.redirectErrorStream(true);
		//proc.directory(new File("/"));
		Scanner readErr = null;
		try {
			Process p = proc.start();
			readErr = new Scanner(p.getInputStream());
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		while(readErr.hasNextLine()) {
			System.out.println(readErr.nextLine());
		}
	}
	
	private static void rmFile(FileRecord f) {
		List<String> com = new ArrayList<String>();
		com.add("/bin/bash");
		com.add("-c");
		com.add("rm -rf " + UserConfigConstants.STORAGEDIR + f.toString());
		ProcessBuilder pb = new ProcessBuilder(com);
		pb.redirectErrorStream(true);
		Scanner readErr = null;
		try {
			Process p = pb.start();
			readErr = new Scanner(p.getInputStream());
		} catch (IOException e) {
			e.printStackTrace();
		}
		while (readErr.hasNextLine()) {
			System.out.println(readErr.nextLine());
		}
	}

	public boolean keepFile(String input) {
		FileRecord f = this.recStore.getFromStore(input);
		if(f == null) {
			return false;
		}
		this.keepFile(f);
		return true;
	}

	public boolean ignoreFile(String input) {
		FileRecord f = this.recStore.getFromStore(input);
		if(f == null) {
			return false;
		}
		rmFile(f);
		return true;
	}
	
	public void setRecStore(RecordStorage rs) {
		this.recStore = rs;
	}
	
	public String getAllKeptFilesString() {
		return this.recStore.getAllKeptFilesString();
	}
	
	public String getAllIgnoredFilesString() {
		return this.recStore.getAllIgnoredFilesString();
	}

	public void trainingResponse(QuestionableFile file) {
		/*
		 * all FeatureCountTups of the file have been updated if the response switched the file from KEEP to IGNORE
		 * 	or vice versa. Go through all files. If they have one of these features. Recalculate their probability.
		 * Then, re-insert into queue to get a re-ordering.
		 */
		HashSet<Feature> updatedFeats = file.getFile().getFeatures();
		LinkedList<QuestionableFile> toUpdate = new LinkedList<QuestionableFile>();
		
		for (QuestionableFile qf : this.questionable) {
			if (fileHasAnyFeatureInSet(qf, updatedFeats)) {
				qf.setProbDesirable(this.probElem.getProbabilityOfDesirabilityForFile(qf.getFile()));
				toUpdate.add(qf);
			}
		}
		
		for (QuestionableFile qf : toUpdate) {
			this.questionable.remove(qf);
			this.questionable.add(qf);
		}
	}
	
	private boolean fileHasAnyFeatureInSet(QuestionableFile qf, HashSet<Feature> feats) {
		for (Feature f : qf.getFile().getFeatures()) {
			if (feats.contains(f)) {
				return true;
			}
		}
		return false;
	}

	public QuestionableFile getMostQuestionable() {
		QuestionableFile f = this.questionable.peek();
		this.questionable.remove(f);
		return f;
	}

}
