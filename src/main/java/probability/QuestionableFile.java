package probability;

import storage.FileRecord;

/*
 * This class holds a file along with it's current probability of desirability.
 * These are non persistent objects.
 */
public class QuestionableFile implements Comparable<QuestionableFile> {
	private FileRecord file;
	private double probDesirable;
	
	public QuestionableFile(FileRecord f, double p) {
		this.file = f;
		this.probDesirable = p;
	}
	
	public double getProbDesirable() {
		return this.probDesirable;
	}
	
	public FileRecord getFile() {
		return this.file;
	}
	
	public int compareTo(QuestionableFile o) {
		// TODO Auto-generated method stub
		double uncertainty = Math.abs(this.probDesirable - .5);
		double oUncertainty = Math.abs(o.getProbDesirable() - .5);
		if(this.equals(o)) {
			return 0;
		}
		else if (uncertainty > oUncertainty){
			return 1;
		}
		else {
			return -1;
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
		QuestionableFile x = (QuestionableFile) o;
		if(this.file.toString().equals(x.getFile().toString())) {
			return true;
		}
		else {
			return false;
		}
	}
	
	public String toString() {
		return this.getFile().toString();
	}
}
