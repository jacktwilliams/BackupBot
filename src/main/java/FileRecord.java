import java.io.File;
import java.nio.file.Path;
import java.util.HashSet;

import constants.BackupStatus;

public class FileRecord {
	private BackupStatus status = BackupStatus.UNDECIDED;
	private Path path;
	private boolean isDir;
	private HashSet<Feature> features;
	
	public FileRecord(String p) {
		path = new File(p).toPath();
		isDir = checkDirStatus();
	}
	
	private boolean checkDirStatus() {
		return path.toUri().toString().endsWith("/");
	}
	
	/*
	 * TODO: handle moved files. 
	 * Being able to keep track of the file accurately depends on this toString method. 
	 * Equals depends on this method. When we go to add a moved file to the recStore, they won't be equal, so we will add a new record.
	 */
	public String toString() {
		return path.toUri().toString();
	}
	
	public int hashCode() {
		return this.toString().hashCode();
	}
	
	public boolean equals(Object o) {
		if (this == o) {
			return true;
		}
		if (o == null) {
			return false;
		}
		if (o.getClass() != this.getClass()) {
			return false;
		}
		FileRecord x = (FileRecord) o;
		if(this.toString().equals(x.toString())) {
			return true;
		}
		else {
			return false;
		}
	}

	public boolean getDirStatus() {
		return isDir;
	}
	
	public BackupStatus getBackupStatus() {
		return this.status;
	}
	
	public void addFeature(Feature f) {
		this.features.add(f);
	}
}
