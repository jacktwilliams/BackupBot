package drivers;

import java.util.List;

import probability.Probability;
import storage.FileRecord;

public interface BackupManager {
	
	public void setProbElem(Probability p);
	
	/*
	 * Handles physical backing up in storage as well as updating probability model and file record.
	 */
	public void keepFile(FileRecord f);
	
	/*
	 * Handles physical backing up in storage as well as updating probability model and file record.
	 */
	public void ignoreFile(FileRecord f);
	
	public List<FileRecord> getQuestionableFiles(int num);
	
	/*
	 * I am planning on doing a length-limited priority queue.
	 * So we can add all files to this list and we will keep the top 500 most questionable or what not.
	 * This list will also be persistent. The PersistentManager will write the queue to a file by calling
	 * 	getAllQuestionableFiles, and then we will implement setQuestionableFiles in this class.
	 */
	public void addQuestionableFile(FileRecord f);
	
	public void setQuestionableFiles(List<FileRecord> questionables);
	
	
	
}
