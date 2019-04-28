package drivers;

import java.io.IOException;
import java.util.List;
import java.util.PriorityQueue;
import java.util.Scanner;

import probability.BackupAction;
import probability.Probability;
import probability.QuestionableFile;
import storage.FileRecord;

public class BackupManagerImpl {
	private static final String STORAGELOC = "/home/jack/dev/backupTest/";
	public static final int STORAGESIZE = 30016144; //in kb

	private static final int maxQuestionable = 500;
	private Probability probElem;
	private PriorityQueue<QuestionableFile> questionable;

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

	public void setQuestionableFiles(List<FileRecord> questionables) {
		
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
		ProcessBuilder proc = new ProcessBuilder("/home/jack/.backupBot/backup.sh");
		proc.redirectErrorStream(true);
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
		
	}

}
