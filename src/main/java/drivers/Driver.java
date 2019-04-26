package drivers;
import java.io.File;
import java.lang.ProcessBuilder.Redirect;
import java.util.HashSet;
import java.util.Scanner;

import storage.FileRecord;
import storage.PersistentManager;
import storage.RecordStorage;

public class Driver {
	public static RecordStorage recStore;
	//public static ProbabilityElem prob;

	public static void main(String[] args) throws ClassNotFoundException {
		// TODO Auto-generated method stub
		runBackupCycle();
		//PersistentManager.save(recStore, prob);
	}
	
	public static void runBackupCycle() throws ClassNotFoundException {
		HashSet<FileRecord> undecided = getDecisionFiles();
		FeatureAttribution.attributeFeatures(undecided);
		/*BackupManager.backupAsAppropriate(recStore);*/
	}
	
	public static HashSet<FileRecord> getDecisionFiles() throws ClassNotFoundException {
		ProcessBuilder pb = new ProcessBuilder("./findCom.sh");
		pb.directory(new File("/home/jack"));
		
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
			FileRecord current = new FileRecord(readF.nextLine());
			if (!current.getDirStatus()) {
				//add files which we haven't previously KEPT or IGNORED
				recStore.addToStore(current);
			}
		}

		readF.close();
		return recStore.getUndecidedFiles();
	}
}
