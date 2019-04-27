package probability;

import storage.FileRecord;

/*
 * This class is for sorting our potential backup choices in a priority queue.
 * We decided not to keep expected utility as part of the FileRecord class (we could've avoided writing this class)
 * Because that class is persistent and expected utilities are dynamic by their definition. 
 */
public class BackupAction implements Comparable<BackupAction> {
	private FileRecord file;
	private int expectedUtility;
	
	public BackupAction(FileRecord f, int eu) {
		this.file = f;
		this.expectedUtility = eu;
	}

	public int compareTo(BackupAction o) {
		// TODO Auto-generated method stub
		if(this.equals(o)) {
			return 0;
		}
		else if (this.expectedUtility < o.getExpectedUtility()){
			return -1;
		}
		else {
			return 1;
		}
	}
	
	public boolean equals(Object o) {
		if (this == o) {
			return true;
		}
		if (o == null) {
			throw new NullPointerException();
		}
		if (o.getClass() != this.getClass()) {
			return false;
		}
		BackupAction x = (BackupAction) o;
		if(this.file.toString().equals(x.getFile().toString())) {
			return true;
		}
		else {
			return false;
		}
	}
	
	public FileRecord getFile() {
		return file;
	}
	
	public int getExpectedUtility() {
		return this.expectedUtility;
	}
}
