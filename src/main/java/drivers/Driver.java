package drivers;
import java.io.File;
import java.lang.ProcessBuilder.Redirect;
import java.util.HashSet;
import java.util.Scanner;

import probability.Decision;
import probability.Probability;
import storage.FileRecord;
import storage.PersistentManager;
import storage.RecordStorage;

public class Driver {
	public static RecordStorage recStore;
	public static Probability prob;

	public static void main(String[] args) throws ClassNotFoundException {
		// TODO Auto-generated method stub
		runBackupCycle();
		//PersistentManager.save(recStore, prob);
	}
	
	public static void runBackupCycle() throws ClassNotFoundException {
		HashSet<FileRecord> undecided = getDecisionFiles();
		FeatureAttribution.attributeFeatures(undecided);
		prob = new Probability();
		Decision.getOrderedBackupList(undecided, prob);
		/*BackupManager.backupAsAppropriate(recStore);*/
	}
	
	public static HashSet<FileRecord> getDecisionFiles() throws ClassNotFoundException {
		produceFindCommand();
		String baseDir = "/home/jack/dev/";
		ProcessBuilder pb = new ProcessBuilder("/home/jack/.backupBot/findCom.sh");
		pb.directory(new File(baseDir));
		File logF = new File("findLog.txt");
		pb.redirectError(Redirect.appendTo(logF));
		pb.redirectOutput(Redirect.appendTo(logF));		
		
		Scanner readF = null;
		try {
			pb.start();
			readF = new Scanner(logF);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		recStore = PersistentManager.getFileRecords();
		while(readF.hasNextLine()) {
			FileRecord current = new FileRecord(readF.nextLine().replaceFirst(".", baseDir));
			if (!current.getDirStatus()) {
				//add files which we haven't previously KEPT or IGNORED
				recStore.addToStore(current);
			}
		}

		readF.close();
		return recStore.getUndecidedFiles();
	}
	
	public static void produceFindCommand() {
		ProcessBuilder pb = new ProcessBuilder("/home/jack/.backupBot/constructFind.py");
		pb.directory(new File("/home/jack/.backupBot"));
		pb.redirectErrorStream(true);
		try {
			pb.start();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
}
