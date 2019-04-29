package storage;
import java.io.Serializable;
import java.util.HashSet;
import java.util.LinkedList;

import constants.BackupStatus;

public class RecordStorage implements Serializable{
	private static final long serialVersionUID = -4523787079041951940L;
	private HashSet<FileRecord> recStore = new HashSet<FileRecord>();
	
	public void addToStore(FileRecord f) {
		this.recStore.add(f);
	}
	
	public FileRecord getFromStore(String name) {
		FileRecord query = new FileRecord(name);
		
		for (FileRecord f : this.recStore) {
			if (f.equals(query)) {
				return f;
			}
		}	
		return null;
	}
	
	/*
	 * Use this method before saving the RecordStorage to the persistent file. 
	 * No need to keep the Undecided. We will re-add them and re-attribute features next time.
	 */
	@SuppressWarnings("unchecked")
	public void purgeUndecided() {
		HashSet<FileRecord> copy = (HashSet<FileRecord>) recStore.clone();
		
		for (FileRecord f : copy) {
			if(f.getBackupStatus() == BackupStatus.UNDECIDED) {
				this.recStore.remove(f);
			}
		}
	}
	
	/*
	 * Feature attribution code will get this set and add features to files
	 * Decision code will get this set and possibly decide to act on files (change their backup status).
	 */
	public HashSet<FileRecord> getUndecidedFiles() {
		HashSet<FileRecord> undecided = new HashSet<FileRecord>();
		
		for(FileRecord f : this.recStore) {
			if (f.getBackupStatus() == BackupStatus.UNDECIDED) {
				undecided.add(f);
			}
		}
		return undecided;
	}
	
	public LinkedList<FileRecord> getAllKeptFiles() {
		LinkedList<FileRecord> keptList = new LinkedList<FileRecord>();
		
		for (FileRecord f : this.recStore) {
			if (f.getBackupStatus() == BackupStatus.KEPT) {
				keptList.add(f);
			}
		}
		
		return keptList;
	}
	
	public String getAllKeptFilesString() {
		StringBuilder res = new StringBuilder();
		
		for (FileRecord f : this.recStore) {
			if (f.getBackupStatus() == BackupStatus.KEPT) {
				res.append(f + "\n");
			}
		}
		
		return res.toString();
	}
	
	public LinkedList<FileRecord> getAllIgnoredFiles() {
		LinkedList<FileRecord> keptList = new LinkedList<FileRecord>();
		
		for (FileRecord f : this.recStore) {
			if (f.getBackupStatus() == BackupStatus.IGNORED) {
				keptList.add(f);
			}
		}
		
		return keptList;
	}
	
	public String getAllIgnoredFilesString() {
		StringBuilder res = new StringBuilder();
		
		for (FileRecord f : this.recStore) {
			if (f.getBackupStatus() == BackupStatus.IGNORED) {
				res.append(f + "\n");
			}
		}
		
		return res.toString();
	}
}
