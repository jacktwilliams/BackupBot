import java.io.File;
import java.lang.ProcessBuilder.Redirect;
import java.util.HashSet;
import java.util.Scanner;

public class Driver {

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		runBackupCycle();
	}
	
	public static void runBackupCycle() {
		getDecisionFiles();
	}
	
	public static void getDecisionFiles() {
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
		
		/* Eventually make recStore persistent. Only new files will be added and old files will hold their BackupStatus in the persistent storage.*/
		HashSet<FileRecord> recStore = new HashSet<FileRecord>();
		while(readF.hasNextLine()) {
			FileRecord current = new FileRecord(readF.nextLine());
			if (!current.getDirStatus()) {
				recStore.add(current);
			}
		}
		
		readF.close();
	}
}
